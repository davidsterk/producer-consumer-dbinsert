/*
* Description: Class Producer. Opens the input file and Creates a task object for each line in the file.
* Puts the task object in the queue. Once the file is read, a poison pill is created for each thread.
*/

package messages;

import enums.SensorType;
import org.json.simple.JSONObject;

public class Task {

  private final JSONObject message;
  private final SensorType sensorType;

  public Task(SensorType sensorType, JSONObject message) {
    this.sensorType = sensorType;
    this.message = message;
  }

  /*
  returns the Json message
   */
  public JSONObject getMessage() {
    return message;
  }

  /*
  returns the SensorType
   */
  public SensorType getType() {
    return sensorType;
  }
}
