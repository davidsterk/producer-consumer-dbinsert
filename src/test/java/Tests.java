import database.MySqlConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import database.SqlInsertFactory;
import database.SqlInsertStrategy;
import database.StrategyFactory;
import enums.SensorType;
import messages.Consumer;
import messages.Producer;
import messages.Task;
import org.junit.Test;
import static org.junit.Assert.*;

public class Tests {

  @Test
  public void testConnection() throws SQLException {
    Connection conn = MySqlConnector.getConnection();
    conn.close();
  }

  @Test
  public void testTask() throws SQLException {
    StrategyFactory  factory = new SqlInsertFactory();
    Connection conn = MySqlConnector.getConnection();
    Task task = new Task(SensorType.ACTIVITY, null);
    assertEquals(SensorType.ACTIVITY, task.getType());
  }

  @Test
  public void testProcess() throws SQLException {
    BlockingQueue<Task>  blockingQueue = new LinkedBlockingDeque<>();
    Thread producer = new Thread(new Producer(blockingQueue, "test.txt", 1));
    Thread consumer = new Thread(new Consumer(blockingQueue));
    producer.start();
    consumer.start();
  }
}
