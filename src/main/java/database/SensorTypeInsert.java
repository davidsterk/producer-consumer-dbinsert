/*
 * Description: Class SensorTypeInsert. Abstract class that that the Strategy insert classes extend
 */

package database;

import messages.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class SensorTypeInsert {

  protected Connection conn;
  protected PreparedStatement pstmt;
  protected Task task;

  /*
  Constructor. Sets Connection and Task objects. Executes the PreparedStatment method.
   */
  public SensorTypeInsert(Connection conn, Task task) throws SQLException {
    this.conn = conn;
    this.task = task;
    pstmt = prepareStatement();
  }

  /*
  Create the insert statement. Overriden by parrent class;
   */
   protected abstract PreparedStatement prepareStatement() throws SQLException;
}
