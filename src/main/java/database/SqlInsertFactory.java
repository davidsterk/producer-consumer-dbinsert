/*
 * Description: Class SqlInsertFactory. Implements the StrategyFactory interface
 */
package database;

import messages.Task;

import java.sql.Connection;
import java.sql.SQLException;

public class SqlInsertFactory implements StrategyFactory {

    /*
    Returns a SqlInsertStrategy sublcass
     */
  @Override
  public SqlInsertStrategy createStatement(Connection conn, Task task) throws SQLException {
      SqlInsertStrategy insert;
      switch(task.getType()) {
      case ACTIVITY:
        insert = new ActivityInsert(conn, task);
        break;
      case ACTIVFIT:
        insert = new ActivFitInsert(conn, task);
        break;
      case HEART_RATE:
        insert = new HeartRateInsert(conn, task);
        break;
      case BATTERY:
        insert = new BatteryInsert(conn, task);
        break;
      case BLUETOOTH:
        insert = new BluetoothInsert(conn, task);
        break;
      case LIGHT:
        insert = new LightInsert(conn, task);
        break;
      default:
        insert = new NullInsert();
        break;
    }
    return insert;
  }
}
