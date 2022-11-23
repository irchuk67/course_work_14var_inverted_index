import java.io.File;
import java.util.List;

public class Indexer {
    private FileHandler fileHandler;

    public Indexer(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void index(int numberOfThreads, List<File> files) throws InterruptedException {
        ThreadIndexer[] threadIndexers = new ThreadIndexer[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threadIndexers[i] = new ThreadIndexer(
                    files,
                    fileHandler,
                    (files.size() / numberOfThreads) * i,
                    (files.size() / numberOfThreads) * (i + 1)
            );
        }

        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads; i++) {
            threadIndexers[i].start();
        }
        for (int i = 0; i < numberOfThreads; i++) {
            threadIndexers[i].join();
        }
        System.out.printf("Time for parallel execution with %s: %s ms\n", numberOfThreads, (System.currentTimeMillis() - currentTime));

    }
}
