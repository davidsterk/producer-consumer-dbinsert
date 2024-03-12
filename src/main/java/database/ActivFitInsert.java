/*
 * Description: Class ActivFitInsert. Extends SensorTypeInsert and implements the SQLInertStrategy for ActivFit Table
 */

package database;

import messages.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.Connection;
import java.sql.SQLException;

public class ActivFitInsert extends SensorTypeInsert implements SqlInsertStrategy {

  private final JSONParser parser = new JSONParser();
  public ActivFitInsert(Connection conn, Task task) throws SQLException {
    super(conn, task);
    super.insertStmt = "INSERT INTO activfit(sensorname, "
            + "starttime, endtime, activity, duration) Values (?, ?, ?, ?, ?)";
    super.setPrepareStatement();
  }

  /*
  Implements the SqlInsertStrategy. Creates the VAlUES part of the insert statement
   */
  @Override
  public void insertSQL() throws SQLException {
    JSONObject json = super.task.getMessage();
    super.pstmt.setString(1, json.get("sensor_name").toString());
    super.pstmt.setString(2, ((JSONObject) json.get("timestamp")).get("start_time").toString());
    super.pstmt.setString(3, ((JSONObject) json.get("timestamp")).get("end_time").toString());
    super.pstmt.setString(4, ((JSONObject) json.get("sensor_data")).get("ActivFit").toString());
    super.pstmt.setString(5, ((JSONObject) json.get("sensor_data")).get("duration").toString());

    super.pstmt.execute();
  }

}
