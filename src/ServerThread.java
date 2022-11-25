import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;
import java.util.List;
import java.util.Set;

public class ServerThread extends Thread {
    private final Socket socket;
    private final InvertedIndex invertedIndex;

    public ServerThread(Socket socket, InvertedIndex invertedIndex) {
        this.socket = socket;
        this.invertedIndex = invertedIndex;
    }

    @Override
    public void run() {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            boolean isIndexed;
            synchronized (invertedIndex) {
                isIndexed = invertedIndex.isIndexed();
                outputStream.writeBoolean(isIndexed);
                if (!isIndexed) {
                    FileHandler fileHandler = new FileHandler(invertedIndex);
                    File file = new File("acllmdb");
                    fileHandler.scanDirectory(file);
                    List<File> files = fileHandler.getAllFiles();
                    int numberOfThreads = inputStream.readInt();
                    Indexer indexer = new Indexer(fileHandler);
                    outputStream.writeLong(indexer.index(numberOfThreads, files));
                }
            }

            boolean findOneMoreWord;

            do {
                String word = inputStream.readUTF();
                System.out.println("Client want to find such word: " + word);
                Set<String> indexedFiles = invertedIndex.getListOfFilesByKey(word);
                if (indexedFiles == null || indexedFiles.isEmpty()) {
                    System.out.println("No files found for this word");
                    outputStream.writeUTF("no files found");
                } else {
                    System.out.println("Files were found");
                    outputStream.writeUTF(indexedFiles.toString());
                }
                findOneMoreWord = inputStream.readUTF().equals("y");
                System.out.println("Client " + (findOneMoreWord ? "" : "don't ") + "want to find one more word");
            } while (findOneMoreWord);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
