package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DAO.Exceptions.Exception_DataBaseConnection;
import DAO.Exceptions.Exception_DuplicatePost;
import DAO.Exceptions.Exception_FieldNotFound;

public class Conect_to_data_base {
  String server_url = "jdbc:mysql://localhost/flinixchallenger";



  /**
   * Method used to make a generic search in the table doctors from database, you have to especify the field (column) you want to analyse and the value you are looking for. If field and searchKey are empty, method returns all doctors
   * 
   * @param field Field of database table you want to analyse
   * @param searchKey Value you are looking for in the column
   * 
   * @return A String containig the name of the fields, and it values. If the search doesn't find anything, method returns an empty String. If field and searchKey are empty, method returns all doctors
   * 
   * @throws Exception_DataBaseConnection originates from a SQLException, method throw this Exception if occurs any trouble with database
   * @throws Exception_FieldNotFound originates from a SQLSyntaxErrorException, method throw this Exception if field searched does not exist in the table doctors
   */
  public String genericSearchDoctor(String field, String searchKey) throws Exception_DataBaseConnection, Exception_FieldNotFound {

    try (Connection connection = DriverManager.getConnection(this.server_url, "root", "")) {
      String sql_commandLine = "select * from doctors";
      
      if(field != "" && searchKey != "")
      sql_commandLine += " where " + field + " = '" + searchKey + "'";

      PreparedStatement genericSearch = connection.prepareStatement(sql_commandLine);

      ResultSet searchResult_ResultSet = genericSearch.executeQuery();

      return formatDoctorsSearch(searchResult_ResultSet);

    } catch(SQLSyntaxErrorException e){

      System.out.println("field " + field + " does not exist in table " + "doctors");
      e.printStackTrace();
      throw new Exception_FieldNotFound("doctors", "field");

    } catch (SQLException e) {
      System.out.println("An error ocurred in " + "genericSearchDoctor" + " while trying to access data base");
      e.printStackTrace();
      throw new Exception_DataBaseConnection("genericSearch");
    
    }     
  } // genericSearch ends here

  /** this method is used to format search made by genericSearchDoctor method
   * @param searchResult ResultSet to be formated
   * @return A String containig the name of the fields, and it values. If ResultSet searchResult is empty, method returns an empty String
   * @throws SQLException
   */
  private String formatDoctorsSearch(ResultSet searchResult) throws SQLException {

    String formatedResult = String.format("%-10s %-50s %-50s %-10s\n", "id", "name", "crm", "created_by_id");

    while (searchResult.next()) {
      String id = searchResult.getString("id");
      String name = searchResult.getString("name");
      String crm = searchResult.getString("crm");
      String created_by_id = searchResult.getString("created_by_id");

      formatedResult += String.format("%-10s %-50s %-50s %-10s\n", id, name, crm, created_by_id);
    } // while ends here

    if (formatedResult.length() > 127)
      return formatedResult;

    return "";
  } // formatDoctorsSearch ends here



  /** Method used to make generic posts in database. This verion of method accepts SQL functions 
   * @param table Table in database which you want to create a new tuple/register
   * @param values Array of values needed to create the new tuple/register
   * @param positionOfValuesToBeUsedAsSQLFunction an Integer array containing  the positions of values array you want to use as an SQL function instead of value, so the method will not put single quotes in this value. Per exemple, value default won't become 'default' in SQL command
   * @return true if tuple/register was created successfully, false if not
   * @throws Exception_DataBaseConnection originates from a SQLException, method throw this Exception if occurs any trouble with database connection or values array is not filled corretly
   * @throws Exception_DuplicatePost originates from a SQLIntegrityConstraintViolationException, method throw this Exception an primary or uniq field already exist in database
   */
  public boolean genericPost(String table, String[] values, Integer[] positionOfValuesToBeUsedAsSQLFunction)
      throws Exception_DataBaseConnection, Exception_DuplicatePost {

    List<Integer> positionOfValuesToBeUsedAsSQLFunction_Arraylist = new ArrayList<>(
        Arrays.asList(positionOfValuesToBeUsedAsSQLFunction));

    // I just made it this way cause I dont want others classes to need to import
    // ArrayList

    String valuesFomartedToCommandLine = "";

    if (!positionOfValuesToBeUsedAsSQLFunction_Arraylist.contains(0))
      valuesFomartedToCommandLine += "'";

    valuesFomartedToCommandLine += values[0];

    if (!positionOfValuesToBeUsedAsSQLFunction_Arraylist.contains(0))
      valuesFomartedToCommandLine += "'";

    for (int valueIndex = 1; valueIndex < values.length; valueIndex++) {
      valuesFomartedToCommandLine += ", ";

      if (!positionOfValuesToBeUsedAsSQLFunction_Arraylist.contains(valueIndex))
        valuesFomartedToCommandLine += "'";

      valuesFomartedToCommandLine += values[valueIndex];

      if (!positionOfValuesToBeUsedAsSQLFunction_Arraylist.contains(valueIndex))
        valuesFomartedToCommandLine += "'";

    }

    String sql_commandLine = "insert into " + table + " value (" + valuesFomartedToCommandLine + ");";

    try (Connection connection = DriverManager.getConnection(this.server_url, "root", "")) {

      PreparedStatement genericPost = connection.prepareStatement(sql_commandLine);

      return genericPost.execute();

    } catch (SQLIntegrityConstraintViolationException e) {

      System.out.println("Primary or unique keys already exist in data base");
      throw new Exception_DuplicatePost();

    } catch (SQLException e) {

      System.out.println("An error ocurred in " + "genericPost" + " while trying to access data base, please, certify that field are filled correctly");
      e.printStackTrace();
      throw new Exception_DataBaseConnection("genericPost");

    }

  }// genericPost ends here


  /** Method used to make generic posts in database. This verion of method accepts SQL functions 
   * @param table Table in database which you want to create a new tuple/register
   * @param values Array of values needed to create the new tuple/register
   * @return true if tuple/register was created successfully, false if not
   * @throws Exception_DataBaseConnection originates from a SQLException, method throw this Exception if occurs any trouble with database connection or values array is not filled corretly
   * @throws Exception_DuplicatePost originates from a SQLIntegrityConstraintViolationException, method throw this Exception an primary or uniq field already exist in database
   */
  public boolean genericPost(String table, String[] values)
      throws Exception_DataBaseConnection, Exception_DuplicatePost {

    // I just made it this way cause I dont want others classes to need to import
    // ArrayList

    String valuesFomartedToCommandLine = "'" + values[0] + "'";

    for (int valueIndex = 1; valueIndex < values.length; valueIndex++) {
      valuesFomartedToCommandLine += ", ";
      valuesFomartedToCommandLine += "'";
      valuesFomartedToCommandLine += values[valueIndex];
      valuesFomartedToCommandLine += "'";

    }

    String sql_commandLine = "insert into " + table + " value (" + valuesFomartedToCommandLine + ");";

    try (Connection connection = DriverManager.getConnection(this.server_url, "root", "")) {

      PreparedStatement genericPost = connection.prepareStatement(sql_commandLine);

      return genericPost.execute();

    } catch (SQLIntegrityConstraintViolationException e) {

      throw new Exception_DuplicatePost();

    } catch (SQLException e) {

      System.out.println("An error ocurred in " + "genericPost" + " while trying to access data base, please, certify that field are filled correctly");
      e.printStackTrace();
      throw new Exception_DataBaseConnection("genericPost");

    }
  }// genericPost ends here

} //Conect_to_data_base class ends here
