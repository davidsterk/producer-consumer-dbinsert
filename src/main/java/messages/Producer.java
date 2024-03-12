/*
 * Description: Class Producer. Opens the input file and Creates a task object for each line in the file.
 * Puts the task object in the queue. Once the file is read, a poison pill is created for each thread.
 *
 */

package messages;

import enums.SensorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{

  private final Logger logger = LogManager.getLogger(this.getClass());
  BlockingQueue<Task> taskQueue;
  private final JSONParser parser = new JSONParser();
  private final File inputFile;
    private final int threadCount;
  public Producer(BlockingQueue<Task>  taskQueue, String fileName, int threadCount) {
    inputFile = new File("input//"+fileName);
    this.taskQueue = taskQueue;
    this.threadCount = threadCount;
  }

/*
Opens the file and creates a Task object for each line. Tasks are put in the queue
 */
  public void produce() {
    try {

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
          String output;
          while((output = br.readLine()) != null) {
            output = output.trim();
            try {
              if(!output.isEmpty()){
                JSONObject json  = (JSONObject) parser.parse(output);
                Task task = new Task(SensorType.getSensorType(json.get("sensor_name").toString().toLowerCase()), json);
                taskQueue.put(task);
              }
            } catch (Exception ex) {
              logger.error("Error parsing JSON: " + ex.getMessage());
              Thread.currentThread().interrupt();
              System.exit(1);
            }
          }
          br.close();
          createPoisonPill();
    } catch (IOException | InterruptedException e) {
      logger.error("Error reading file: " + e.getMessage());
    }

  }

  /*
  creates poison pill tasks based on number of consumer threads
   */
  private void createPoisonPill() throws InterruptedException {
    for(int i = 0; i<threadCount; i++) {
      Task task = new Task(SensorType.POISON_PILL, null);
      taskQueue.put(task);
    }

  }

  /*
  called the produce method.
   */
  @Override
  public void run() {
    produce();
  }
}
