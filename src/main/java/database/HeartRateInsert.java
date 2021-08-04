/*
 * Description: Class HeartRateInsert. Extends SensorTypeInsert and implements the SQLInertStrategy for HeartRate Table
 */

package database;

import messages.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HeartRateInsert extends SensorTypeInsert implements SqlInsertStrategy {

  public static final String HEARTRATE_TABLE_INSERT = "INSERT INTO heartrate (sensorname, "
          + "timestamp, bpm) Values (?, ?, ?)";

  private JSONParser parser = new JSONParser();
  public HeartRateInsert(Connection conn, Task task) throws SQLException {
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
    super.pstmt.setString(3, ((JSONObject) json.get("sensor_data")).get("bpm").toString());

    super.pstmt.execute();
  }

  /*
Sets the insert statement to the Table specific one
*/
  @Override
  protected PreparedStatement prepareStatement() throws SQLException {
    return super.conn.prepareStatement(HEARTRATE_TABLE_INSERT);
  }
}
