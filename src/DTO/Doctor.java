package DTO;

import DAO.Conect_to_data_base;
import DAO.Exceptions.Exception_DataBaseConnection;
import DAO.Exceptions.Exception_DuplicatePost;

public class Doctor {
  String id, name, crm, created_by_ID;
  Conect_to_data_base dataBase = new Conect_to_data_base();



  /**
   * Constructor of Doctor class
   * @param id id of the doctor
   * @param name name of the doctor
   * @param crm crm of the doctor
   * 
   * @param created_by_ID created_by_ID of the doctor
   */
  public Doctor(String id, String name, String crm, String created_by_ID) {
    this.id = id;
    this.name = name;
    this.crm = crm;
    this.created_by_ID = created_by_ID;
  } // Doctor constructor ends here



  /**
   * Method used to post new produtivities 
   * @param serviceAt data in format YYYY-MM-DD
   * @param value value of produtivity with >TWO< decimals 
   * @param description description with max 255 characters
   * 
   * @return true if produtivity was successfully posted, false if not
   */
  public boolean postProductivity(String serviceAt, String value, String description) {
    String[] valuesToBePosted = { "default", serviceAt, value, description, this.id };
    Integer[] positionOfValuesToBeUsedAsSQLFunction = { 0 };

    try {

      return dataBase.genericPost("productivities", valuesToBePosted, positionOfValuesToBeUsedAsSQLFunction);

    } catch (Exception_DataBaseConnection e) {

      System.out.println("An error ocurried while tryng to access data base");
      e.printStackTrace();

    } catch (Exception_DuplicatePost e) {
      
      System.out.println(e.getCauseMessage());
      e.printStackTrace();
    }

    return false;
  } // postProdutivity ends here


  /**
   * Method used to post new produtivities in current date
   * @param value value of produtivity with >TWO< decimals 
   * @param description description with max 255 characters
   * 
   * @return true if produtivity was successfully posted, false if not
   */
  public boolean postProductivity_CurrentDate(String value, String description) {
    String[] valuesToBePosted = { "default", "current_date()", value, description, this.id };
    Integer[] positionOfValuesToBeUsedAsSQLFunction = { 0, 1 };

    try {

      return dataBase.genericPost("productivities", valuesToBePosted, positionOfValuesToBeUsedAsSQLFunction);

    } catch (Exception_DataBaseConnection e) {

      System.out.println("An error ocurried while tryng to access data base");
      e.printStackTrace();

    } catch (Exception_DuplicatePost e) {

      System.out.println(e.getCauseMessage());
      e.printStackTrace();
    }

    return false;
  }// postProdutivity_CurrentDate ends here
}//Doctor class ends here
