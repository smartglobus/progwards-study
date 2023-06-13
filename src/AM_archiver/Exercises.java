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
    private int length;
    private int distance;

    private List<Link> tempLinks = new ArrayList<>();
    private List<List<Byte>> vocabulary; // or Map ???
    private int wordPosition;

    // отладочные переменные
    private int inpFileSize; // отладка
    private int sumLength; // отладка
    private int sumDistance; // отладка
    private int minDistance = WINDOW; // отладка

    void readFile(String fileName, String outName) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             FileOutputStream fileOutputStream = new FileOutputStream(outName)) {

            byte[] bytes = fileInputStream.readAllBytes();
            inpFileSize = bytes.length; // отладка
//            for (byte b : bytes) System.out.print(b + "_");
            // bufStart - позиция указателя начала буфера, т.е. начала кодируемого участка
            for (int bufStart = MIN_CODE; bufStart < bytes.length - BUF_LIM; bufStart++) { // последниЙ кусок размера BUF_LIM не кодируем
// записываем первые биты (от 0 до MIN_CODE-1)без кодировки
                windowStartPosition = (bufStart - WINDOW) >= 0 ? bufStart - WINDOW : 0; // вычислить начало окна windowStartPosition
                Link link = new Link(MIN_CODE); // надо ли каждый шаг содавать новый объект? Нужен ли вообще объект???

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
                                        }else break;
                                    }
                            }


                            // блок создания ссылки
                            if (lengthCount >= link.length) {
                                link.linkPosition = bufStart; // отладка
                                link.distance = bufStart - wndPos; // wndPos здесь - первый бит повторяющейся последовательности
                                link.length = lengthCount;
                            }
                        } else{

                            break;
                        }
                    }

                } else{
                    // записываем бит без кодирования
                }
            }
            if (link.distance > 0) {
                tempLinks.add(link); // записать Link в результат
                bufStart += link.length;

                sumLength += link.length; // отладка
                sumDistance += link.distance; // отладка
                if (link.distance < minDistance) minDistance = link.distance; // отладка
            }
        }

        tempLinks.stream().filter(e -> e.length > 0).forEach(e -> System.out.println("pos " + e.linkPosition + ", dist = " + e.distance + ", lenght " + e.length));
        fileOutputStream.write(bytes);
    } catch(
    IOException e)

    {
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

    // возвращает 16 бит, из которых первые 12 кодируют distance, а остальные 4 length
    private short linkShort(int distance, int length) {
// длина последовательности от 3 до 18 (length-3 помещается в 4 бита, от 0 до 15)
        return 0;
    }


    public static void main(String[] args) {
        Exercises exr = new Exercises();
        exr.readFile("C:\\Users\\User\\IdeaProjects\\Progwards first project\\src\\AM_archiver\\file1.txt", "C:\\Users\\User\\Pictures\\BachMonogrm1_copy.jpg");


        System.out.println("\nсжатие до " + ((exr.inpFileSize - exr.sumLength) * 100 / exr.inpFileSize) + "%");
        System.out.println(WINDOW);
        System.out.println("средняя дистанция " + exr.sumDistance / exr.tempLinks.size());
        System.out.println("средняя длина " + exr.sumLength / exr.tempLinks.size());
        System.out.println("minDistance " + exr.minDistance);
    }
}