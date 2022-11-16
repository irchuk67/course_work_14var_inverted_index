import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class FileHandler {
    public String readByLines(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        StringBuilder builder = new StringBuilder();

        while (scanner.hasNextLine()){
            builder.append(scanner.nextLine());
        }

        return builder.toString();
    }

    public void scanDirectory(File directory) throws IOException {

        if(directory.isDirectory()){
            File[] files = directory.listFiles();
            for (File file : Objects.requireNonNull(files)){
                scanDirectory(file);
            }
        }else{
            String readFile = readByLines(directory);
            System.out.println(readFile);
        }
    }
}
