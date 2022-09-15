package DAO.Exceptions;

public class Exception_FieldNotFound extends Exception{
  String table;
  String field;

  /** Exception used to inform that field wasn't found in table
   * @param table table that was being searched
   * @param field field that was being searched in the referred table
   */
  public Exception_FieldNotFound(String table, String field){
    this.table = table;
    this.field = field;
  }

  /**
   * @return error message
   */
  public String getCauseMessage(){
    return "field " + field + " does not exist in table " + table;
  }
}
