/*
 * Description: Class Main. Entry Point
 */

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.SQLException;


public class Main {

  private static Logger logger = LogManager.getLogger(Main.class);

  /**
   * Main method to execute program.
   *
   * @param args not used
   */
  /*
  First parameter argument is # of Consumer threads Default = 10;
   */
  public static void main(String[] args) throws SQLException, InterruptedException {

    // This configuration is for setting up the log4j properties file.
    // It is better to set this using java program arguments.
    // PropertyConfigurator.configure("log4j.properties");

    // Let us create an object of the Main class.
    int threads = 10;
    if(args.length == 1) {
      threads = Integer.parseInt(args[0]);
      Driver run = new Driver(threads);

    }

    logger.info("Creating " + threads + " Consumer(s)...");
    Driver run = new Driver(threads);
    run.run();

  }
}
