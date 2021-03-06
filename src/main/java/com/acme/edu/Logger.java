package com.acme.edu;

import com.acme.edu.command.IntCommand;
import com.acme.edu.command.StringCommand;
import com.acme.edu.exception.FlushException;
import com.acme.edu.exception.LogException;
import com.acme.edu.exception.SaveException;
import com.acme.edu.saver.ConsoleSaver;
import com.acme.edu.saver.FileSaver;
import com.acme.edu.saver.LoggerSaver;

import java.io.File;

/**
 * Logger facade
 */
public class Logger {
    LoggerController controller = new LoggerController(
            new ConsoleSaver(),
            new FileSaver("src"
                    + File.pathSeparator + "main"
                    + File.pathSeparator + "resources"
                    + "logbook.txt")); //new LoggerController(System.out::println);

    public void log(int message) throws LogException {
        controller.log(new IntCommand(message));
    }

    public void log(String message)  throws LogException {
        controller.log(new StringCommand(message));
    }

    public void flush() throws FlushException {
        controller.flush();
    }
}
