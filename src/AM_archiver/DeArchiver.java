package AM_archiver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DeArchiver {

    void deArchive(String fileName, String outName) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             FileOutputStream fileOutputStream = new FileOutputStream(outName)) {

            byte[] bytes = fileInputStream.readAllBytes();
            for (byte b : bytes) System.out.print("_" + b);

            for (int i = 0; i < bytes.length; i++) {

            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DeArchiver deArchiver = new DeArchiver();
        deArchiver.deArchive("C:\\Users\\User\\Pictures\\BachMonogrm1_copy.txt","C:\\Users\\User\\Pictures\\BachMonogrm1_copy2.txt");
    }
}
