import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        InvertedIndex invertedIndex = new InvertedIndex();
        FileHandler fileHandler = new FileHandler(invertedIndex);
        File file = new File("acllmdb");
        fileHandler.scanDirectory(file);
        List<File> files = fileHandler.getAllFiles();

        System.out.println("Please, enter number of threads: ");
        Scanner scanner = new Scanner(System.in);
        int numberOfThreads = scanner.nextInt();
        while (numberOfThreads > files.size()) {
            System.out.println("Number of threads is too large!!! Please, enter lower number of threads: ");
            numberOfThreads = scanner.nextInt();
        }

        Indexer indexer = new Indexer(fileHandler);
        indexer.index(numberOfThreads, files);

        System.out.println("Please, enter word, you want to search: ");
        scanner.nextLine();
        String s = scanner.nextLine().toLowerCase();
        Set<String> indexedFiles = invertedIndex.getListOfFilesByKey(s);
        System.out.println(indexedFiles);
    }
}