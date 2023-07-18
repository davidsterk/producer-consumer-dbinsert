/*
 * Description: Class Producer. Opens the input file and Creates a task object for each line in the file.
 * Puts the task object in the queue. Once the file is read, a poison pill is created for each thread.
 *
 */

package messages;

import enums.SensorType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{

  BlockingQueue<Task> taskQueue;
  private JSONParser parser = new JSONParser();
  private File inputFile;
  private BufferedReader br;
  private int threadCount;
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

          br = new BufferedReader(new FileReader(inputFile));
          String output;
          while((output = br.readLine()) != null) {
            output = output.trim();
            try {
              if(!output.equals("")){
                JSONObject json  = (JSONObject) parser.parse(output);
                Task task = new Task(SensorType.getSensorType(json.get("sensor_name").toString().toLowerCase()), json);
                taskQueue.put(task);
              }
            } catch (Exception ex) {
              ex.printStackTrace();
              Thread.currentThread().interrupt();
              System.exit(1);
            }
          }
          br.close();
          createPoisonPill();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
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
