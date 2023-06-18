package AM_archiver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercises {
    private String inFileName;
    private String outFileName;
    private final static int WINDOW = 4096; // размер скользящего окна
    private final static int MIN_CODE = 3; // минимальная длина кодируемой повторяющейся последовательности
    private final int BUF_LIM = 15 + MIN_CODE; // максимальный размер буфера сравнения, т.е. максимальная длина кодируемой повторяющейся последовательности
    private int windowStartPosition;
    private ArrayList<Byte> outBytes = new ArrayList<>();
    private ArrayList<Byte> codedBlock = new ArrayList<>();// блок из служебного байта-указателя и 8 некодированных байтов или ссылок

    private List<Boolean> linkDescript = new ArrayList<>();
    private int length;
    private int distance;
    private boolean isEnOfFile;

//    private List<Link> tempLinks = new ArrayList<>();


    public Exercises(String inFileName, String outFileName) {
        this.inFileName = inFileName;
        this.outFileName = outFileName;
    }

    // отладочные переменные
    private int linkPosition; // отладка
    private int inpFileSize; // отладка
    private int sumLength; // отладка
    private int sumDistance; // отладка
    private int minDistance = WINDOW; // отладка

    void readFile() {
        try (FileInputStream fileInputStream = new FileInputStream(inFileName)) {

            byte[] bytes = fileInputStream.readAllBytes();
//            for (byte b : bytes) System.out.print("_" + b);
//            System.out.println();
//            inpFileSize = bytes.length; // отладка
            // записываем первые биты (от 0 до MIN_CODE-1)без кодировки
            for (int i = 0; i < MIN_CODE; i++) {
                addBytes(outFileName,false, bytes[i]);
            }

            // bufStart - позиция указателя начала буфера, т.е. начала кодируемого участка
            for (int bufStart = MIN_CODE; bufStart < bytes.length; bufStart++) { // первый кусок до MIN_CODE не кодируется
                if (bufStart == bytes.length - 1)
                    isEnOfFile = true; // сигнал для формирования последнего descriptorByte для п-ти <= 8 байтов
                windowStartPosition = (bufStart - WINDOW) >= 0 ? bufStart - WINDOW : 0; // вычислить начало окна windowStartPosition
//                Link link = new Link(MIN_CODE); // отладочный объект
                // обнуление параметров ссылки
                distance = 0;
                length = MIN_CODE;

//                linkPosition = bufStart; // отладка

                // цикл поиска совпадения символа в окне с первым символом буфера
                for (int wndPos = windowStartPosition; wndPos < bufStart; wndPos++) {

                    int lengthCount = 0; // длина текущей п-ти совпадений
                    if (bytes[wndPos] == bytes[bufStart]) {
                        int bS_wP = bufStart - wndPos; // ограничитель захода в буфер
                        int endOfFile = bytes.length - 1 - bufStart; // ограничитель выхода за конец bytes
                        for (int j = 0; j < BUF_LIM && j <= bS_wP && j <= endOfFile; j++) { // j меньше BUF_LIM, и (wndPos + j) не превышает bufStart, и (bufStart + j) не превышает bytes.length

                            if (bytes[wndPos + j] == bytes[bufStart + j]) { // подсчёт подряд совпадающих пар
                                lengthCount++;

                                // блок проверки на многократно повторяющиеся последовательности
                                if (wndPos + j == bufStart - 1) { // если совпадения продолжились до конца окна
                                    j++;
                                    int saveJ = j;
                                    for (int i = 0; i <= saveJ && j < BUF_LIM && j <= endOfFile; i++, j++) {
                                        if (i == saveJ)
                                            i = 0; // i "крутится" от 0 до saveJ (т.е., от wndPos до конца окна)
                                        if (bytes[wndPos + i] == bytes[bufStart + j]) { // если следующий байт буфера равен первому символу кодируемой п-ти
                                            lengthCount++;
                                        } else break;
                                    }
                                }


                                // блок создания или обновления ссылки
                                if (lengthCount >= length) {
                                    distance = bufStart - wndPos; // wndPos здесь - первый бит повторяющейся последовательности
                                    length = lengthCount;
//
//                                    linkPosition = bufStart; // отладка
//                                    link.linkPosition = bufStart; // отладка
//                                    link.distance = bufStart - wndPos; // отладка
//                                    link.length = lengthCount; // отладка
                                }
                            } else
                                break; // если совпадения с буфером в этом участке окна закончились, прервать накопление lengthCount и продолжить с wndPos++
                        }
                    }
                }
//
//                if (bufStart == 46167){
//                    int test = 333;
//                }

                // блок записи ссылки из 16 бит. Первые 12 кодируют distance, а остальные 4 length - MIN_CODE (MIN_CODE = 3)
                // т.к. длина последовательности не меньше 3, то в 4 бита (0...15) помещаются значения (length-3) диапазона 3...18
                if (distance > 0) {
                    bufStart += length - 1;
                    if (bufStart >= bytes.length - 1)
                        isEnOfFile = true; // сигнал для формирования последнего descriptorByte для п-ти <= 8 байтов

                    int distAndLength = (distance << 4) + (length - 3);
                    byte linkByte1 = (byte) (distAndLength >> 8); // первые 8 битов distance
                    byte linkByte2 = (byte) distAndLength; // оставшиеся 4 бита distance и 4 бита length
                    addBytes(outFileName,true, linkByte1, linkByte2);




//                    tempLinks.add(link); // записать Link в результат
//                    sumLength += link.length; // отладка
//                    sumDistance += link.distance; // отладка
//                    if (link.distance < minDistance) minDistance = link.distance; // отладка
                } else {
                    // запись бита без кодирования
                    addBytes(outFileName,false, bytes[bufStart]);

                }
            }

//            outBytes.forEach(e -> System.out.print("_" + e));
//            System.out.println();
//            tempLinks.stream().filter(e -> e.length > 0).forEach(e -> System.out.println("pos " + e.linkPosition + ", dist = " + e.distance + ", lenght " + e.length));

//            for (Byte b : outBytes) fileOutputStream.write(b);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void addBytes(String outName, boolean isLink, byte... bts) {

        try (FileOutputStream fileOutputStream = new FileOutputStream(outName,true)) {

            for (byte b : bts) codedBlock.add(b);
            linkDescript.add(isLink);

// сформировать служеный байт и вписать его в начало блока
            if (isEnOfFile || linkDescript.size() == 8) {
                codedBlock.add(0, descriptorByte());
                for (Byte b : codedBlock) fileOutputStream.write(b);

//                outBytes.addAll(codedBlock);

                codedBlock.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte descriptorByte() {
        int result = 0;
        for (boolean bl : linkDescript) {
            result <<= 1;
            result += bl ? 1 : 0;
        }
        result <<= (8 - linkDescript.size()); // сделано для последнего descriptorByte для п-ти < 8 байтов
        linkDescript.clear();
        return (byte) result;
    }


    public static void main(String[] args) {
        Exercises exr = new Exercises("C:\\Users\\User\\IdeaProjects\\Progwards first project\\src\\AM_archiver\\file2.txt", "C:\\Users\\User\\Pictures\\BachMonogrm1_copy.txt");
        exr.readFile();

//        System.out.println("\nсжатие до " + ((exr.inpFileSize - exr.sumLength) * 100 / exr.inpFileSize) + "%");
//        System.out.println(WINDOW);
//        System.out.println("средняя дистанция " + exr.sumDistance / exr.tempLinks.size());
//        System.out.println("средняя длина " + exr.sumLength / exr.tempLinks.size());
//        System.out.println("minDistance " + exr.minDistance);
//
//        System.out.println(exr.descriptorByte());

    }

//    class Link { // отладка
//        int linkPosition;
//        int distance;
//        int length;
//
//        public Link(int length) {
//            this.length = length;
//        }
//    }
}
