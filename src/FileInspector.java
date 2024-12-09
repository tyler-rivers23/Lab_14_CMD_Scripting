import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileInspector
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile = null;
        String rec = "";
        int line = 0;
        int wordCnt = 0;
        int charCnt = 0;
        String[] words;
        ArrayList<String> lines = new ArrayList<>();

        try {
            // Check if a command line argument for the file path is provided
            if (args.length > 0) {
                selectedFile = new File(args[0]); // Use the first argument as the file
                if (!selectedFile.exists() || !selectedFile.isFile()) {
                    System.out.println("The provided file does not exist or is not a valid file.");
                    return;
                }
            } else {
                // No command-line argument, let the user choose a file interactively
                File workingDirectory = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(workingDirectory);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                } else {
                    System.out.println("No file selected. Exiting program.");
                    return;
                }
            }

            // Now that we have a valid file (either from command line or file chooser), proceed with processing
            Path file = selectedFile.toPath();
            InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while (reader.ready()) {
                rec = reader.readLine();
                lines.add(rec);
                line++;
                charCnt += rec.length();
                words = rec.split(" ");
                wordCnt += words.length;
                System.out.printf("\nLine %4d %-60s ", line, rec);
            }

            for (String l : lines) {
                System.out.println(l);
            }

            if (lines.size() > 5) {
                String[] fields = lines.get(5).split(", ");
                for (String f : fields) {
                    System.out.println(f);
                }
            }

            reader.close(); // Close the file after processing

            System.out.println("\n\nData file read!");
            System.out.println("File: " + selectedFile.getName());
            System.out.println("File Stats: ");
            System.out.println("Total lines: " + line);
            System.out.println("Total words: " + wordCnt);
            System.out.println("Total characters: " + charCnt);

        } catch (FileNotFoundException e) {
            System.out.println("File not found!!!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

