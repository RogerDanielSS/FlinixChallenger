package DTO;

import DAO.Conect_to_data_base;
import DAO.Exceptions.Exception_DataBaseConnection;
import DAO.Exceptions.Exception_DuplicatePost;

public class User {
  String userName, email, password, create_time, id;
  Conect_to_data_base dataBase = new Conect_to_data_base();



  public User(String userName, String email, String password, String create_time, String id) {
    this.userName = userName;
    this.email = email; 
    this.password = password;
    this.create_time = create_time;
    this.id = id;
  } // User constructor ends here



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


  
  public String getDoctorByID(String id){
    dataBase = new Conect_to_data_base();
    String searchResult = "";
    try {
      
      searchResult = dataBase.genericSearchDoctor("id", id);

    } catch(Exception_DataBaseConnection e){
      System.out.println("An error ocurried while tryng to access data base");
      e.printStackTrace();

    }

    return searchResult;
  } // getDoctorByID ends here



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
