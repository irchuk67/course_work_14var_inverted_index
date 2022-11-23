import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Set;

public class ServerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        try (ServerSocket serverSocket = new ServerSocket(8000)){
            Socket socket = serverSocket.accept();
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            InvertedIndex invertedIndex = new InvertedIndex();
            FileHandler fileHandler = new FileHandler(invertedIndex);
            File file = new File("acllmdb");
            fileHandler.scanDirectory(file);
            List<File> files = fileHandler.getAllFiles();

            int numberOfThreads = inputStream.readInt();
            Indexer indexer = new Indexer(fileHandler);
            indexer.index(numberOfThreads, files);

            String word = inputStream.readUTF();
            Set<String> indexedFiles = invertedIndex.getListOfFilesByKey(word);
            outputStream.writeUTF(indexedFiles.toString());
        }catch (Exception e){
            throw e;
        }
    }
}