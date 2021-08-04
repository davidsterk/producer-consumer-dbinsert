/*
 * Description: Class Activity. Extends SensorTypeInsert and implements the SQLInertStrategy for Activity Table
 */

package database;

import messages.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActivityInsert extends SensorTypeInsert implements SqlInsertStrategy {

  public static final String ACTIVITY_TABLE_INSERT = "INSERT INTO activity(sensorname, "
          + "timestamp, time_stamp, stepcounts, stepdelta) Values (?, ?, ?, ?, ?)";

  private JSONParser parser = new JSONParser();
  public ActivityInsert(Connection conn, Task task) throws SQLException {
    super(conn, task);
  }

  /*
Implements the SqlInsertStrategy. Creates the VAlUES part of the insert statement
 */
  @Override
  public void insertSQL() throws SQLException {
    JSONObject json = super.task.getMessage();
    super.pstmt.setString(1, json.get("sensor_name").toString());
    super.pstmt.setString(2, json.get("timestamp").toString());
    super.pstmt.setString(3, json.get("time_stamp").toString());
    super.pstmt.setString(4, ((JSONObject) json.get("sensor_data")).get("step_counts").toString());
    super.pstmt.setString(5,  ((JSONObject) json.get("sensor_data")).get("step_delta").toString());

    super.pstmt.execute();
  }

  /*
Sets the insert statement to the Table specific one
 */
  @Override
  protected PreparedStatement prepareStatement() throws SQLException {
    return super.conn.prepareStatement(ACTIVITY_TABLE_INSERT);
  }
}
