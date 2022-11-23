import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

public class Client {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8000)) {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            System.out.println("Please, enter number of threads: ");
            int numberOfThreads = scanner.nextInt();
            outputStream.writeInt(numberOfThreads);

            System.out.println("Please, enter word, you want to search: ");
            scanner.nextLine();
            String s = scanner.nextLine().toLowerCase();
            outputStream.writeUTF(s);
            String indexedFiles = inputStream.readUTF();
            System.out.println(indexedFiles);
        } catch (Exception e) {
            throw e;
        }

    }
}
