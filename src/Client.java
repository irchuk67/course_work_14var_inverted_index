import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Scanner scanner = new Scanner(System.in);
    private InvertedIndex invertedIndex;
    private static int numberOfThreads;
    private static long executionTime;
    private static DataInputStream inputStream;
    private static DataOutputStream outputStream;


    public Client(InvertedIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8000)) {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            boolean reindex = false;
            System.out.println("Welcome to 'Indexer'!!! \nHere you can search words in movie reviews. \nSo we can start our work ;)");

            System.out.println("First step: files indexing. Now you will enter number of threads that will be used to index files.");

            boolean isIndexed = inputStream.readBoolean();
            System.out.println(isIndexed);
            if (!isIndexed) {
                System.out.println("Please, enter number of threads: ");
                numberOfThreads = scanner.nextInt();
                outputStream.writeInt(numberOfThreads);
                executionTime = inputStream.readLong();
                System.out.printf("Time for parallel execution with %wordToSearch: %wordToSearch ms\n", numberOfThreads, executionTime);
            } else {
                System.out.println("Files have already been indexed by another client.");
            }

            System.out.println("Please, enter word, you want to search: ");

            String wordToSearch = scanner.nextLine().toLowerCase();
            outputStream.writeUTF(wordToSearch);
            String indexedFiles = inputStream.readUTF();
            String cleanString = indexedFiles.replace("[", "").replace("]", "");
            String[] filesWithWord = cleanString.split(", ");
            System.out.println("Files that contain the word that you have searched for:");
            for (String filePath : filesWithWord) {
                System.out.println(filePath);
            }

        } catch (Exception e) {
            throw e;
        }

    }
}
