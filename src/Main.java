import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        InvertedIndex invertedIndex = new InvertedIndex();
        FileHandler fileHandler = new FileHandler(invertedIndex);
        File file = new File("test");
        fileHandler.scanDirectory(file);
        String s = scanner.nextLine();
        Set<String> files = invertedIndex.getListOfFilesByKey(s);
        System.out.println(files);
    }
}