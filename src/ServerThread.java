import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;
import java.util.List;
import java.util.Set;

public class ServerThread extends Thread {
    private Socket socket;
    private InvertedIndex invertedIndex;

    public ServerThread(Socket socket, InvertedIndex invertedIndex) {
        this.socket = socket;
        this.invertedIndex = invertedIndex;
    }

    @Override
    public void run() {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            FileHandler fileHandler = new FileHandler(invertedIndex);
            File file = new File("acllmdb");
            fileHandler.scanDirectory(file);
            List<File> files = fileHandler.getAllFiles();
            System.out.println(invertedIndex.isIndexed());
            outputStream.writeBoolean(invertedIndex.isIndexed());
            if(!invertedIndex.isIndexed()){
                int numberOfThreads = inputStream.readInt();
                Indexer indexer = new Indexer(fileHandler);
                outputStream.writeLong(indexer.index(numberOfThreads, files));
            }


            String word = inputStream.readUTF();
            Set<String> indexedFiles = invertedIndex.getListOfFilesByKey(word);
            outputStream.writeUTF(indexedFiles.toString());
        } catch (Exception e) {
            System.out.println("Error in server thread: " + e);
        }

    }
}
