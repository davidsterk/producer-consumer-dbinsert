/*
 * Description: Class Driver. Facade Design Pattern. Encapsulates the application logic
 */

import messages.Consumer;
import messages.Producer;
import messages.Task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Driver {
  private int threads;
  BlockingQueue<Task> blockingQueue;
  ArrayList<Thread> threadList;
  Thread producer;

  /*
  initializes variables
   */
  public Driver(int threads) {
    this.threads = threads;
    blockingQueue = new LinkedBlockingDeque<>();
    producer = new Thread(new Producer(blockingQueue, "input.txt", this.threads));
  }

  /*
  executes the producer and consumer threads
   */
  public void run() throws SQLException, InterruptedException {
    System.out.println("Running");
    producer.start();
    createConsumers(threads);
    startConsumers();
  }

  /*
  creates Consumer Threads
   */
  public void createConsumers(int threads) throws SQLException {
    threadList = new ArrayList<>();
    for(int i = 0; i<threads; i++) {
      Thread thread = new Thread(new Consumer(blockingQueue));
      threadList.add(thread);
    }
  }
/*
starts consumer threads
 */
  private void startConsumers() {
    for(Thread thread : threadList) {
      thread.start();
    }
  }

}
