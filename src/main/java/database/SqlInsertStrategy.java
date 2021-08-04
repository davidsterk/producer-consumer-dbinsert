/*
 * Description: Interface SqlInsertStrategy. Defines the strategy method
 */
package database;

import java.sql.SQLException;

/*
Defines strategy method
 */
public interface SqlInsertStrategy {

  void insertSQL() throws SQLException;
}
