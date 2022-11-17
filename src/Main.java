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
        while(numberOfThreads > files.size()){
            System.out.println("Number of threads is too large!!! Please, enter lower number of threads: ");
            numberOfThreads = scanner.nextInt();
        }

        ThreadIndexator[] threadIndexators = new ThreadIndexator[numberOfThreads];


        for (int i = 0; i < numberOfThreads; i++){
            threadIndexators[i] = new ThreadIndexator(files,
                    fileHandler,
                    (files.size()/numberOfThreads) * i,
                    (files.size()/numberOfThreads) * (i+1)
                    );
        }

        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads; i++){
            threadIndexators[i].start();
        }
        for (int i = 0; i < numberOfThreads; i++){
            threadIndexators[i].join();
        }
        System.out.printf("Time for parallel execution with %s: %s ms\n",  numberOfThreads, (System.currentTimeMillis() - currentTime));


        System.out.println("Please, enter word, you want to search: ");
        scanner.nextLine();
        String s = scanner.nextLine().toLowerCase();
        Set<String> indexedFiles = invertedIndex.getListOfFilesByKey(s);
        System.out.println(indexedFiles);
    }
}