/*
 * Description: Class BluetoothInsert. Extends SensorTypeInsert and implements the SQLInertStrategy for Bluetooth Table
 */

package database;

import messages.Task;
import org.json.simple.JSONObject;
import java.sql.Connection;
import java.sql.SQLException;

public class BluetoothInsert extends SensorTypeInsert implements SqlInsertStrategy {

  public BluetoothInsert(Connection conn, Task task) throws SQLException {
    super(conn, task);
    super.insertStmt = "INSERT INTO bluetooth (sensorname, "
            + "timestamp, state) Values (?, ?, ?)";
    super.setPrepareStatement();
  }

  /*
Implements the SqlInsertStrategy. Creates the VAlUES part of the insert statement
*/
  @Override
  public void insertSQL() throws SQLException {
    JSONObject json = super.task.getMessage();
    super.pstmt.setString(1, json.get("sensor_name").toString());
    super.pstmt.setString(2, json.get("timestamp").toString());
    super.pstmt.setString(3, ((JSONObject) json.get("sensor_data")).get("state").toString());

    super.pstmt.execute();
  }

}
