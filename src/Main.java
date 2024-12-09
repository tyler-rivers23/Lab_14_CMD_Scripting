import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class Main {
    public static void main(String[] args) {
        File fileToScan = null;

        if (args.length > 0) {
            // Command-line argument provided
            String fileName = args[0];
            fileToScan = new File(fileName);

            if (!fileToScan.exists() || !fileToScan.canRead()) {
                System.out.println("Error: File not found or not readable: " + fileName);
                fileToScan = null; // Reset to trigger JFileChooser
            }
        }

        if (fileToScan == null) {
            // Fallback to JFileChooser
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                fileToScan = fileChooser.getSelectedFile();
            } else {
                System.out.println("No file selected. Exiting.");
                return; // Exit program
            }
        }

        // Process the file
        try (Scanner scanner = new Scanner(fileToScan)) {
            System.out.println("Scanning file: " + fileToScan.getAbsolutePath());
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File could not be opened.");
        }
    }
}
