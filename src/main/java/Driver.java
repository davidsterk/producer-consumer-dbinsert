/*
 * Description: Class Driver. Facade Design Pattern. Encapsulates the application logic
 */

import messages.Consumer;
import messages.Producer;
import messages.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Driver {
  private final Logger logger = LogManager.getLogger(this.getClass());
  private final Thread[] consumers;
  private final Thread producer;

  /*
  initializes variables
   */
  public Driver(int threads, String inputFilePath) throws SQLException {
    BlockingQueue<Task> blockingQueue = new LinkedBlockingDeque<>();
    this.producer = new Thread(new Producer(blockingQueue, inputFilePath, threads));
    this.consumers = new Thread[threads];
    for(int i = 0; i <threads; i++) {
      consumers[i] = new Thread(new Consumer(blockingQueue));
    }
  }

  /*
  executes the producer and consumer threads
   */
  public void run() throws SQLException{
    logger.info("Starting Producer");
    producer.start();
    logger.info("Starting Consumer threads: " + consumers.length);
    for(Thread consumer : consumers) {
      consumer.start();
      logger.info("Starting thread: " + consumer.getName());
    }
  }
}
