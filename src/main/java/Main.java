/*
 * Description: Class Main. Entry Point
 */

import org.apache.commons.cli.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.SQLException;


public class Main {

  private static final Logger LOGGER = LogManager.getLogger(Main.class);

  /**
   * Main method to execute program.
   *
   * @param args not used
   */
  public static void main(String[] args) throws SQLException, InterruptedException {
      Options options = new Options();

      Option threadsOption = new Option("t", "threads", true, "number of threads, default is 10");
      threadsOption.setRequired(false);
      options.addOption(threadsOption);

      Option inputOption = new Option("i", "input", true,
              "input file path, default is current directory input.txt");
      inputOption.setRequired(false);
      options.addOption(inputOption);

      CommandLineParser parser = new DefaultParser();
      HelpFormatter formatter = new HelpFormatter();
      CommandLine cmd;

      try {
          cmd = parser.parse(options, args);
      } catch (ParseException e) {
          LOGGER.error(e.getMessage());
          formatter.printHelp("utility-name", options);

          System.exit(1);
          return;
      }

      int threads = Integer.parseInt(cmd.getOptionValue("threads", "10"));
      String inputFilePath = cmd.getOptionValue("input", "input.txt");

      LOGGER.info("Creating " + threads + " Consumer(s)...");
      LOGGER.info("Reading from file: " + inputFilePath);

      Driver run = new Driver(threads, inputFilePath);
      run.run();
  }
}
