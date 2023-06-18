package AM_archiver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeArchiver {

    void deArchive(String fileName, String outName) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             FileOutputStream fileOutputStream = new FileOutputStream(outName)) {

            byte[] bytes = fileInputStream.readAllBytes();
//            for (byte b : bytes) System.out.print("_" + b);

            for (int i = 0; i < bytes.length; i++) {
                List<Integer> codedBlockDescriptor = new ArrayList<>();
                byte cbd = bytes[i]; // cbd = codedBlockDescriptor
                int codedBlockLength = 0;// количество байтов в блоке. Следующий байт codedBlockDescriptor будет bytes[i + codedBlockLength + 1]
                for (int j = 0; j < 8; j++) {
                    int n = (cbd & 1) + 1; // +1 т.к. ноль в cbd соответствует 1 байту данных, а единица соответствует ссылке из 2 байтов
                    cbd >>= 1;
                    codedBlockLength += n;
                    codedBlockDescriptor.add(0, n);
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DeArchiver deArchiver = new DeArchiver();
        deArchiver.deArchive("C:\\Users\\User\\Pictures\\BachMonogrm1_copy.txt", "C:\\Users\\User\\Pictures\\BachMonogrm1_copy2.txt");

    }
}
