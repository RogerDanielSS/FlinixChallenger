package DAO.Exceptions;

public class Exception_DuplicatePost extends Exception{
  public String getCauseMessage(){
    return "Primary or unique keys already exist in data base";
  }
}
