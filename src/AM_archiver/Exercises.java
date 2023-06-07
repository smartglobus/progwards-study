package AM_archiver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Exercises {
    private final static int WINDOW = 300;
    private final static int MIN_CODE = 3;
    private int maxBuf = 30;
    private int windowStartPosition;

    private List<Link> tempLinks = new ArrayList<>();
    private List<List<Byte>> vocabulary; // or Map ???
    private int wordPosition;

    private int inpFileSize; // отладка
    private int sumLength; // отладка

    void readFile(String fileName, String outName) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             FileOutputStream fileOutputStream = new FileOutputStream(outName)) {

                byte[] bytes = fileInputStream.readAllBytes();
                inpFileSize = bytes.length; // отладка
//            byte[] bytes = new byte[]{0, 1, 2, 3, 6, 8, 3, 6, 2, 3, 6, 8, 3, 4, 2, 7, 5, 3, 7, 2, 3, 6, 8, 6, 5, 7, 8, 9, 5, 5, 7, 8, 0};
//            for (byte b : bytes) System.out.print(b + "_");
            // проход по массиву bytes
            // i - позиция указателя начала буфера
            for (int i = MIN_CODE; i < bytes.length - maxBuf; i++) { // последниЙ кусок размера maxBuf не кодируем

                windowStartPosition = (i - WINDOW) >= 0 ? i - WINDOW : 0; // вычислить начало окна windowStartPosition
                Link link = new Link(0, MIN_CODE); // надо ли каждый шаг содавать новый объект? Нужен ли вообще объект???
                // цикл поиска совпадения символа в окне с первым символом буфера
                for (int wp = windowStartPosition; wp < i - MIN_CODE; wp++) {

                    int lengthCount = 0;
                    if (bytes[wp] == bytes[i]) {
                        lengthCount++;
                        for (int j = 1; j < maxBuf && j < i - wp; j++) { // j не больше размера буфера и не даёт [wp+j] "залезть" в буфер
                            if (bytes[wp + j] == bytes[i + j]) {
                                lengthCount++;

                            } else {
                                if (lengthCount >= link.length) {
                                    link.distance = i - wp;
                                    link.length = lengthCount;
                                    wp += lengthCount - 1;
                                }
                                break;
                            }
                        }

//
//                        link.distance = i - wp; // ??? - check!!!
//                        link.length = 3;
//
//                        currDistance = i - wp;
//                        currLength = 3;

// здесь идёт цикл по наращиванию буфера c сохранением последовательности в промежуточный словарь
// при наращивании буфера следить, чтобы не выйти за конец массива bytes (сделал, ограничив максимум i в основном цикле)
//                        repSeq.add(bytes[wp++]);
//                        repSeq.add(bytes[wp++]);
//                        repSeq.add(bytes[wp++]);

//                        for (int n = i + 3; n < i || repSeq.size()< maxBuf; n++) {
//                            if (bytes[wp] == bytes[n]) {
//                                repSeq.add(bytes[wp++]);
//                            }
//                        }


                    } else {
                        // записываем символы без кодирования
                    }
                }
                if (link.distance > 0) {
                    tempLinks.add(link); // записать Link в результат
                    i += link.length;
                    sumLength += link.length; // отладка
                }
            }

            tempLinks.stream().filter(e -> e.length > 0).forEach(e -> System.out.println(e.distance + " " + e.length));
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Link {
        int distance;
        int length;

        public Link(int distance, int length) {
            this.distance = distance;
            this.length = length;
        }
    }


    void compress() {

    }

    public static void main(String[] args) {
        Exercises exr = new Exercises();
        exr.readFile("C:\\Users\\User\\Pictures\\BachMonogrm1.jpg", "C:\\Users\\User\\Pictures\\BachMonogrm1_copy.jpg");
        String song = "Слышу голос из Прекрасного Далека,\n" +
                "Голос утренний в серебряной росе.\n" +
                "Слышу голос, и манящая дорога\n" +
                "Кружит голову, как в детстве карусель.\n" +
                "\n" +
                "Припев:\n" +
                "Прекрасное Далеко,\n" +
                "Не будь ко мне жестоко,\n" +
                "Не будь ко мне жестоко -\n" +
                "Жестоко не будь.\n" +
                "От чистого истока\n" +
                "В Прекрасное Далеко,\n" +
                "В Прекрасное Далеко\n" +
                "Я начинаю путь.";
        char[] songToComp = song.toCharArray();
        byte[] bytes = song.getBytes();
        for (char ch : songToComp) {
//            System.out.println(ch);
        }
        System.out.println(((exr.inpFileSize - exr.sumLength)*100/exr.inpFileSize) + "%");
    }
}
