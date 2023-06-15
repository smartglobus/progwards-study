package AM_archiver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Exercises {
    private final static int WINDOW = 16384; // размер скользящего окна
    private final static int MIN_CODE = 3; // минимальная длина кодируемой повторяющейся последовательности
    private final int BUF_LIM = 15 + MIN_CODE; // максимальный размер буфера сравнения, т.е. максимальная длина кодируемой повторяющейся последовательности
    private int windowStartPosition;
    private ArrayList<Byte> outBytes = new ArrayList<>();
    private ArrayList<Byte> codedBlock = new ArrayList<>();// блок из служебного байта-указателя и 8 некодированных байтов или ссылок
    int codedBlockCount;// счётчик добавлений в codedBlock (не равен количеству добавленных бит!)
    private boolean[] linkDescript = new boolean[8];
    private int length;
    private int distance;

    private List<Link> tempLinks = new ArrayList<>();


    // отладочные переменные
    private int linkPosition; // отладка
    private int inpFileSize; // отладка
    private int sumLength; // отладка
    private int sumDistance; // отладка
    private int minDistance = WINDOW; // отладка

    void readFile(String fileName, String outName) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             FileOutputStream fileOutputStream = new FileOutputStream(outName)) {

            byte[] bytes = fileInputStream.readAllBytes();
            inpFileSize = bytes.length; // отладка
            // записываем первые биты (от 0 до MIN_CODE-1)без кодировки
            for (int i = 0; i < MIN_CODE; i++) {
                addBytes(bytes[i]);
                linkDescript[i] = false; // можно пропустить, они и так false изначально
//                    codedBlockCount = codedBlockCount < 8 ? codedBlockCount + 1 : 0;
            }
//            for (byte b : bytes) System.out.print(b + "_");
            // bufStart - позиция указателя начала буфера, т.е. начала кодируемого участка
            for (int bufStart = MIN_CODE; bufStart < bytes.length - BUF_LIM; bufStart++) { // первый кусок до MIN_CODE и последниЙ размера BUF_LIM не кодируются


                windowStartPosition = (bufStart - WINDOW) >= 0 ? bufStart - WINDOW : 0; // вычислить начало окна windowStartPosition
                Link link = new Link(MIN_CODE); // надо ли каждый шаг содавать новый объект? Нужен ли вообще объект???
                // обнуление параметров ссылки
                distance = 0;
                length = MIN_CODE;
                linkPosition = bufStart; // отладка

                // цикл поиска совпадения символа в окне с первым символом буфера
                for (int wndPos = windowStartPosition; wndPos < bufStart; wndPos++) {

                    int lengthCount = 0; // длина текущей п-ти совпадений
                    if (bytes[wndPos] == bytes[bufStart]) {
                        for (int j = 0; j < BUF_LIM && j <= bufStart - wndPos; j++) {// j не больше  BUF_LIM, и (wndPos + j) не превышает bufStart

                            if (bytes[wndPos + j] == bytes[bufStart + j]) { // подсчёт подряд совпадающих пар
                                lengthCount++;

                                // блок проверки на многократно повторяющиеся последовательности
                                if (wndPos + j == bufStart - 1) { // если совпадения продолжились до конца окна
                                    j++;
                                    for (int i = 0; i <= j && j < BUF_LIM; i++, j++) {
                                        if (i == j) i = 0; // i крутится от 0 до j
                                        if (bytes[wndPos + i] == bytes[bufStart + j]) {// если следующий байт буфера равен первому символу кодируемой п-ти
                                            lengthCount++;
                                        } else break;
                                    }
                                }


                                // блок создания или обновления ссылки
                                if (lengthCount >= length) {
                                    link.linkPosition = bufStart; // отладка
                                    link.distance = bufStart - wndPos; // wndPos здесь - первый бит повторяющейся последовательности
                                    link.length = lengthCount;

                                    distance = bufStart - wndPos;
                                    length = lengthCount;
                                    linkPosition = bufStart; // отладка
                                }
                            } else {
// если совпадения с буфером в этом участке окна закончились, прервать накопление lengthCount и продолжить с wndPos++
                                break;
                            }
                        }
                    }
                }
                // блок записи ссылки
                if (link.distance > 0) {
                    tempLinks.add(link); // записать Link в результат
                    bufStart += link.length;

                    sumLength += link.length; // отладка
                    sumDistance += link.distance; // отладка
                    if (link.distance < minDistance) minDistance = link.distance; // отладка
                }else {
                    // записываем бит без кодирования
                    addBytes(bytes[bufStart]);
                    linkDescript[codedBlockCount++] = false;

                }
            }

            tempLinks.stream().filter(e -> e.length > 0).forEach(e -> System.out.println("pos " + e.linkPosition + ", dist = " + e.distance + ", lenght " + e.length));

//            fileOutputStream.write(outBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Link {
        int linkPosition; // отладка
        int distance;
        int length;

        public Link(int length) {
            this.length = length;
        }

    }

    // записывает в результат в виде двух byte последовательность из 16 бит, из которых первые 12 кодируют distance, а остальные 4 length
    private void writeLink(int distance, int length) {
// длина последовательности от 3 до 18 (length-3 помещается в 4 бита, от 0 до 15)
        short s = (short) ((distance << 3) + length);
        linkDescript[codedBlockCount] = true; //
        codedBlockCount++;
    }

    private void addBytes(byte b) {

        if (codedBlockCount < 8) {
            codedBlock.add(b);

        } else {

            codedBlock.add(0, descriptorByte());// сформировать служеный байт и вписать его в начало блока
            outBytes.addAll(codedBlock);
            codedBlock.clear();
            codedBlock.add(b);
            codedBlockCount = 0;
        }
    }

    private byte descriptorByte() {
//        linkDescript[1]=true;
//        linkDescript[3]=true;
//        linkDescript[7]=true;
        int result = 0;
        for (boolean bl : linkDescript) {
            result <<= 1;
            result += bl ? 1 : 0;
        }
//        System.out.println(Integer.toBinaryString(result));
        return (byte) result;
    }


    public static void main(String[] args) {
        Exercises exr = new Exercises();
        exr.readFile("C:\\Users\\User\\IdeaProjects\\Progwards first project\\src\\AM_archiver\\file1.txt", "C:\\Users\\User\\Pictures\\BachMonogrm1_copy.jpg");


        System.out.println("\nсжатие до " + ((exr.inpFileSize - exr.sumLength) * 100 / exr.inpFileSize) + "%");
        System.out.println(WINDOW);
        System.out.println("средняя дистанция " + exr.sumDistance / exr.tempLinks.size());
        System.out.println("средняя длина " + exr.sumLength / exr.tempLinks.size());
        System.out.println("minDistance " + exr.minDistance);

        System.out.println(exr.descriptorByte());

    }
}
