import database.MySqlConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import database.SqlInsertFactory;
import database.SqlInsertStrategy;
import database.StrategyFactory;
import enums.SensorType;
import messages.Producer;
import messages.Task;
import org.junit.Test;
import static org.junit.Assert.*;

public class Tests {

  @Test
  public void testConnection() throws SQLException {
    Connection conn = MySqlConnector.getConnection();
    conn.close();
    assertNotNull(conn);
  }

  @Test
  public void testTask() {
    Task task = new Task(SensorType.ACTIVITY, null);
    assertEquals(SensorType.ACTIVITY, task.getType());
  }

  @Test
  public void testProducerProcess() throws InterruptedException {
    BlockingQueue<Task>  blockingQueue = new LinkedBlockingDeque<>();
    Thread producer = new Thread(new Producer(blockingQueue, "test.txt", 1));
    producer.start();
    producer.join();
    //3 objects in queue expected: 2 from test file + poison pill
    int expectedLength=3;
    assertEquals(expectedLength, blockingQueue.size());
  }

  @Test
  public void testStrategy() throws SQLException {
    Connection conn = MySqlConnector.getConnection();
    Task task = new Task(SensorType.BATTERY, null);
    StrategyFactory factory = new SqlInsertFactory();
    SqlInsertStrategy insert = factory.createStatement(conn, task);
    assertEquals("BatteryInsert", insert.getClass().getSimpleName());
  }
}
