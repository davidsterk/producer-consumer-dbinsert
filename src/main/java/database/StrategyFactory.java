/*
 * Description: Interface StrategyFactory. defines the createStatement method
 */
package database;

import messages.Task;

import java.sql.Connection;
import java.sql.SQLException;

public interface StrategyFactory {

  /*
  Used by concrete implementations to pick a strategy
   */
  SqlInsertStrategy createStatement(Connection conn, Task task) throws SQLException;
}
