package DAO.Exceptions;

public class Exception_DuplicatePost extends Exception{
  
  /**
   * Exception used to inform that tuple already exists in the table
   */
  public Exception_DuplicatePost(){}
  
  /**
   * @return error message
   */
  public String getCauseMessage(){
    return "Primary or unique keys already exist in data base";
  }
}
