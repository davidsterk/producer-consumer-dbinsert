/*
 * Description: Class LightInsert. Extends SensorTypeInsert and implements the SQLInertStrategy for LightSensor Table
 */

package database;

import messages.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.Connection;
import java.sql.SQLException;

public class LightInsert extends SensorTypeInsert implements SqlInsertStrategy {

  private JSONParser parser = new JSONParser();
  public LightInsert(Connection conn, Task task) throws SQLException {
    super(conn, task);
    super.insertStmt = "INSERT INTO lightsensor (sensorname, "
            + "timestamp, lux) Values (?, ?, ?)";
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
    super.pstmt.setString(3, ((JSONObject) json.get("sensor_data")).get("lux").toString());

    super.pstmt.execute();
  }

}
