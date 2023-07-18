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
  protected String insertStmt;

  /*
  Constructor. Sets Connection and Task objects. Executes the PreparedStatment method.
   */
  public SensorTypeInsert(Connection conn, Task task) throws SQLException {
    this.conn = conn;
    this.task = task;
  }

 /* Sets the insert statement to the Table specific one */
  protected void setPrepareStatement() throws SQLException {
    pstmt = conn.prepareStatement(insertStmt);
  }
}
