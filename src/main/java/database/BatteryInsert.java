/*
 * Description: Class BatterInsret. Extends SensorTypeInsert and implements the SQLInertStrategy for BatterySensor Table
 */

package database;

import messages.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatteryInsert extends SensorTypeInsert implements SqlInsertStrategy {

  public static final String BATTERY_TABLE_INSERT = "INSERT INTO batterysensor (sensorname, "
          + "timestamp, percent, charging) Values (?, ?, ? , ?)";

  private JSONParser parser = new JSONParser();
  public BatteryInsert(Connection conn, Task task) throws SQLException {
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
    super.pstmt.setString(3, ((JSONObject) json.get("sensor_data")).get("percent").toString());
    super.pstmt.setString(4, ((JSONObject) json.get("sensor_data")).get("charging").toString());

    super.pstmt.execute();
  }

  /*
Sets the insert statement to the Table specific one
 */
  @Override
  protected PreparedStatement prepareStatement() throws SQLException {
    return super.conn.prepareStatement(BATTERY_TABLE_INSERT);
  }
}
