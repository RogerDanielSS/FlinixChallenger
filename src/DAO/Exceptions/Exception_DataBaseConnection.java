package DAO.Exceptions;

public class Exception_DataBaseConnection extends Exception{
  String methodName;

  public Exception_DataBaseConnection(String methodName){
    this.methodName = methodName;
  }

  public String getCauseMessage(){
    return "An error ocurred in " + methodName + " while trying to access data base";
  }
}
