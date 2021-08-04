/*
 * Description: Class Main. Entry Point
 */

import org.apache.log4j.Logger;

import java.sql.SQLException;


public class Main {

  private static Logger logger = Logger.getLogger(Main.class);


  /**
   * A main method to run examples.
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

    System.out.println("Creating " + threads + " Consumer(s)...");
    Driver run = new Driver(threads);
    run.run();

  }



}
