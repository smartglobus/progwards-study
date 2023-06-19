package AM_archiver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeArchiver {
    private byte[] bytes;
    private List<Byte> outBytes = new ArrayList<>();

    void deArchive(String fileName, String outName) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             FileOutputStream fileOutputStream = new FileOutputStream(outName)) {

            bytes = fileInputStream.readAllBytes();

            for (int i = 0; i < bytes.length; i++) {// i++ здесь задаёт переход от последнего байта данных к следующему codedBlockDescriptor
                int cbd = bytes[i]; // cbd = codedBlockDescriptor
                for (int j = 0; j < 8; j++) {
                    int n = (cbd & 128) > 0 ? 2 : 1; // ноль в cbd соответствует 1 байту данных, а единица соответствует ссылке из 2 байтов

                    if (n == 1 && i < bytes.length - 1) {
                        outBytes.add(bytes[++i]);
                    } else if (i < bytes.length - 2) { // n == 2
                        byte linkByte1 = bytes[++i];
                        byte linkByte2 = bytes[++i];
                        int link = (linkByte1 << 8) + (linkByte2 & 255);
                        int length = (link & 15) + 3; // 4 последних бита, + 3
                        int distance = (link >> 4) & 4095; // 12 битов, с 5 по 16
                        writeSequence(distance, length);
                    }
                    cbd <<= 1;
                }
            }

            for (Byte b : outBytes) fileOutputStream.write(b);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSequence(int distance, int length) {
        int seqStartPosition = outBytes.size() - distance;
        for (int i = 0, j = 0; i < length; i++, j++) {
            if (j == distance) j = 0;
            outBytes.add(outBytes.get(seqStartPosition + j));
        }
    }

    public static void main(String[] args) {
        DeArchiver deArchiver = new DeArchiver();
        deArchiver.deArchive("C:\\Users\\User\\Pictures\\BachMonogrm1_copy.txt", "C:\\Users\\User\\Pictures\\BachMonogrm1_copy2.txt");

    }
}
