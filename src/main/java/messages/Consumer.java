/*
 * Description: Class Consumer. Defines the Consumer in a Producer Consumer design pattern. Meant to run in its own
 * thread
 */

package messages;

import database.*;
import enums.SensorType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable, SqlInsertStrategy {
  private Connection conn;
  private SqlInsertStrategy insert;
  private BlockingQueue<Task> taskQueue;
  private Task task;
  private StrategyFactory factory;


  public Consumer(BlockingQueue<Task> taskQueue) throws SQLException {
    conn = MySqlConnector.getConnection();
    this.taskQueue = taskQueue;
    this.insert = new NullInsert();
    factory = new SqlInsertFactory();
  }

  /*
  Pops from the queue and chooses an insert Strategy based on the SensorType
  Executes the sql code
  if Sensortype is a Poison Pill, terminate
   */
  @Override
  public void run() {
    while (true) {
      try {
        Task task = taskQueue.take();
        if(task.getType().equals(SensorType.POISON_PILL)) {
          System.out.println(Thread.currentThread().getName() + " - Poison Pill Recieved - Exiting...");
          conn.close();
          Thread.currentThread().interrupt();
          return;
        }
        insert = factory.createStatement(conn, task);
        insertSQL();
        conn.commit();
        System.out.println(Thread.currentThread().getName() + " Inserted into " + task.getType() + " " +
                task.getMessage());
      } catch (InterruptedException | SQLException e) {
        try {
          conn.close();
          Thread.currentThread().interrupt();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
      }
    }
  }

  /*
  Implementation of the SqlInsertStrategy strategy
   */
  @Override
  public void insertSQL() throws SQLException {
    insert.insertSQL();
  }

}
