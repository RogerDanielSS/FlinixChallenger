package DTO;

import DAO.Conect_to_data_base;
import DAO.Exceptions.Exception_DataBaseConnection;
import DAO.Exceptions.Exception_DuplicatePost;
import DAO.Exceptions.Exception_FieldNotFound;

public class User {
  String userName, email, password, create_time, id;
  Conect_to_data_base dataBase = new Conect_to_data_base();



  /**
   * Constructor of User class
   * @param userName name of user
   * @param email name of user
   * @param password password of user
   * @param create_time create_time of user
   * @param id id of user (primary key)
   */
  public User(String userName, String email, String password, String create_time, String id) {
    this.userName = userName;
    this.email = email; 
    this.password = password;
    this.create_time = create_time;
    this.id = id;
  } // User constructor ends here



  /** 
   * Method used 
   * @param id id of doctor be posted (primary key)
   * @param name name of doctor to be posted (max 45 characters)
   * @param CRM CRM of doctor to be posted (max 45 characters)
   * 
   * @return true if doctor was successfully posted, false if not
   */
  public boolean postDoctor(String id, String name, String CRM) {
    String[] valuesToBePosted = { id, name, CRM, this.id };

    try {

      return dataBase.genericPost("doctors", valuesToBePosted);

    } catch (Exception_DataBaseConnection e) {

      System.out.println("An error ocurried while tryng to access data base");
      e.printStackTrace();

    } catch (Exception_DuplicatePost e) {

      System.out.println(e.getCauseMessage());
      e.printStackTrace();

    }

    return false;
  } // postDoctor ends here


  
  /**
   * Method that search doctor using id 
   * @param id id of doctor be posted (primary key)
   * 
   * @return String containig the name of the fields of doctors table, and it values. If the search doesn't find anything, method returns an empty String
   */
  public String getDoctorByID(String id){
    dataBase = new Conect_to_data_base();
    String searchResult = "";
    try {
      
      searchResult = dataBase.genericSearchDoctor("id", id);

    } catch(Exception_DataBaseConnection e){
      System.out.println("An error ocurried while tryng to access data base");
      e.printStackTrace();

    } catch (Exception_FieldNotFound e) {
      e.printStackTrace();
    }

    return searchResult;
  } // getDoctorByID ends here

  

  /**
   * Method that get all doctors from doctors table
   * 
   * @return String containig the name of the fields of doctors table, and it values. If the search doesn't find anything, method returns an empty String
   */
  public String getDoctors(){
    dataBase = new Conect_to_data_base();
    String searchResult = "";
    try {
      
      searchResult = dataBase.genericSearchDoctor("", "");

    } catch(Exception_DataBaseConnection e){
      System.out.println("An error ocurried while tryng to access data base");
      e.printStackTrace();

    } catch (Exception_FieldNotFound e) {
      e.printStackTrace();
    }

    return searchResult;
  } // getDoctorByID ends here



  /**
   * Method that search doctor using CRM 
   * @param CRM CRM of doctor be posted (primary key)
   * 
   * @return String containig the name of the fields of doctors table, and it values. If the search doesn't find anything, method returns an empty String
   */
  public String getDoctorByCRM(String CRM) throws Exception {
    String searchResult = "";

    try {
      
      searchResult = dataBase.genericSearchDoctor("crm", CRM);

    } catch(Exception_DataBaseConnection e){
      System.out.println("An error ocurried while tryng to access data base");
      e.printStackTrace();

    }

    return searchResult;
  }// getDoctorByCRM ends here
} // User class ends here
