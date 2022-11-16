import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static String readByLines(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        StringBuilder builder = new StringBuilder();

        while (scanner.hasNextLine()){
            builder.append(scanner.nextLine());
        }

        return builder.toString();
    }

    public static void scanDirectory(File directory) throws IOException {

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
    public static void main(String[] args) throws IOException {
        File file = new File("test");
        scanDirectory(file);
    }
}