package DAO.Exceptions;

public class Exception_DataBaseConnection extends Exception{
  String methodName;


  /**
   * Exception used to internal informs that conneting to database gone wrong
   * @param methodName name of the method that was trying to connect to database
   */
  public Exception_DataBaseConnection(String methodName){
    this.methodName = methodName;
  }

  /**
   * @return error message
   */
  public String getCauseMessage(){
    return "An error ocurred in " + methodName + " while trying to access data base";
  }
}
