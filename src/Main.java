import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileHandler fileHandler = new FileHandler();
        File file = new File("test");
        fileHandler.scanDirectory(file);
    }
}