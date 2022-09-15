package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DAO.Exceptions.Exception_DataBaseConnection;
import DAO.Exceptions.Exception_DuplicatePost;

public class Conect_to_data_base {
  String server_url = "jdbc:mysql://localhost/flinixchallenger";



  public String genericSearchDoctor(String column, String searchKey) throws Exception_DataBaseConnection {

    try (Connection connection = DriverManager.getConnection(this.server_url, "root", "")) {
      String sql_commandLine = "select * from doctors where " + column + " = '" + searchKey + "'";

      PreparedStatement genericSearch = connection.prepareStatement(sql_commandLine);

      ResultSet searchResult_ResultSet = genericSearch.executeQuery();

      return formatDoctorsSearch(searchResult_ResultSet);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception_DataBaseConnection("genericSearch");
    
    }     
  } // genericSearch ends here

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

      throw new Exception_DuplicatePost();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception_DataBaseConnection("genericPost");

    }

  }// genericPost ends here



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

      throw new Exception_DataBaseConnection("genericPost");

    }
  }// genericPost ends here

} //Conect_to_data_base class ends here
