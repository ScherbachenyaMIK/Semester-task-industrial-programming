package main;

//import UI.JavaFxApp;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 0 && args[0].equalsIgnoreCase("ui")) {
            // Launch the UI
            //JavaFxApp.main(null);
        } else {
            // Launch the main.CLI
            CLIMain.main(null);
        }
    }
}