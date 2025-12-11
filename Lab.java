import java.util.*;
import java.io.*;

public class Lab {

    public static void main (String[] args) {
        // Variables to hold the current and next nodes in the path
        Node u, v;
        // The GUI object that draws the maze on the screen
        DrawLab display;
        // Delay for animation (in milliseconds), default is 0
        int delay = 0;

        // Check if the user typed the input file name in the command line
        if (args.length != 1 && args.length != 2)
            System.out.println("Usage: java Solve labyrithFile OR java Solve labyrinthFile speed");
        else {
            // If the user provided a second argument, use it as the speed delay
            if (args.length == 2) delay = Integer.parseInt(args[1]);
            
            // Open the window and draw the initial maze from the file
            display = new DrawLab(args[0]);
            
            try {
                BufferedReader in;
                String line;

                // Create the Solve object which parses the text file and builds the graph
                Solve program = new Solve(args[0]);

                // Run the DFS algorithm to find the list of nodes from Start to Exit
                Iterator solution = program.findExit();

                // If a valid path was found (not null)
                if (solution != null) {
                    // Get the starting node
                    if (solution.hasNext()) u = (Node)solution.next();
                    else return;
                    
                    // Loop through the rest of the nodes in the solution path
                    while (solution.hasNext()) {
                        v = (Node)solution.next(); // Get the next node
                        Thread.sleep(delay);       // Pause for animation effect
                        display.drawEdge(u,v);     // Draw a red line between the nodes
                        u = v;                     // Move forward to the next step
                    }
                }
                else {
                    // If the algorithm returned null, no path exists
                    System.out.println("No solution was found");
                    System.out.println("");
                }

                // Wait for the user to press Enter before closing the window
                in = new BufferedReader(
                        new InputStreamReader(System.in));
                System.out.println("Press RET to finish");
                line = in.readLine();

            }
            // Catch errors related to the maze file format
            catch (LabyrinthException e) {
                System.out.println("Error reading labyrinth");
            }
            // Catch errors related to keyboard input
            catch (IOException in) {
                System.out.println("Error reading from keyboard");
            }
            // Catch any other unexpected errors
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            // Close the graphical window and end the program
            display.dispose();
            System.exit(0);
        }
    }
}