package AM_archiver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Archiver {
    private final static int WINDOW = 4095; // размер скользящего окна
    private final static int MIN_CODE = 3; // минимальная длина кодируемой повторяющейся последовательности
    private final int BUF_LIM = 15 + MIN_CODE; // максимальный размер буфера сравнения, т.е. максимальная длина кодируемой повторяющейся последовательности
    private ArrayList<Byte> outBytes = new ArrayList<>();
    private ArrayList<Byte> codedBlock = new ArrayList<>();// блок из служебного байта-указателя и 8 некодированных байтов или ссылок
    private List<Boolean> codedBlockDescript = new ArrayList<>();
    private boolean isEnOfFile;

    void archive(String fileName) {

        Path path = Paths.get(fileName);
        Path parent = path.getParent();
        String newName = path.getFileName().toString() + ".arch";
        String outFile = parent.resolve(newName).toString();

        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             FileOutputStream fileOutputStream = new FileOutputStream(outFile)) {

            byte[] bytes = fileInputStream.readAllBytes();

            // записываем первые биты (от 0 до MIN_CODE-1)без кодировки
            for (int i = 0; i < MIN_CODE; i++) {
                addBytes(false, bytes[i]);
            }

            // bufStart - позиция указателя начала буфера, т.е. начала кодируемого участка
            for (int bufStart = MIN_CODE; bufStart < bytes.length; bufStart++) { // первый кусок до MIN_CODE не кодируется
                if (bufStart == bytes.length - 1)
                    isEnOfFile = true; // сигнал для формирования последнего descriptorByte для п-ти <= 8 байтов
                int windowStartPosition = (bufStart - WINDOW) >= 0 ? bufStart - WINDOW : 0; // вычислить начало окна windowStartPosition

                // обнуление параметров ссылки
                int distance = 0;
                int length = MIN_CODE;

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
                                }
                            } else
                                break; // если совпадения с буфером в этом участке окна закончились, прервать накопление lengthCount и продолжить с wndPos++
                        }
                    }
                }

                if (distance > 0) {
                    bufStart += length - 1;
                    if (bufStart >= bytes.length - 1)
                        isEnOfFile = true; // сигнал для формирования последнего descriptorByte для п-ти <= 8 байтов

                    // блок записи ссылки из 16 бит. Первые 12 кодируют distance, а остальные 4 length - MIN_CODE (MIN_CODE = 3)
                    // т.к. длина последовательности не меньше 3, то в 4 бита (0...15) помещаются значения (length-3) диапазона 3...18
                    int distAndLength = (distance << 4) + (length - 3);
                    byte linkByte1 = (byte) (distAndLength >> 8); // первые 8 битов distance
                    byte linkByte2 = (byte) distAndLength; // оставшиеся 4 бита distance и 4 бита length
                    addBytes(true, linkByte1, linkByte2);
                } else {
                    // запись бита без кодирования
                    addBytes(false, bytes[bufStart]);
                }
            }

            for (Byte b : outBytes) fileOutputStream.write(b);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addBytes(boolean isLink, byte... bts) {

        for (byte b : bts) codedBlock.add(b);
        codedBlockDescript.add(isLink);

        // формирование служеного байта и запись его в начало блока
        if (isEnOfFile || codedBlockDescript.size() == 8) {
            codedBlock.add(0, descriptorByte());
            outBytes.addAll(codedBlock);
            codedBlock.clear();
        }
    }

    private byte descriptorByte() {
        int result = 0;
        for (boolean bl : codedBlockDescript) {
            result <<= 1;
            result += bl ? 1 : 0;
        }
        result <<= (8 - codedBlockDescript.size()); // стандартизует последний descriptorByte для п-ти < 8 байтов
        codedBlockDescript.clear();
        return (byte) result;
    }


    public static void main(String[] args) {
        Archiver exr = new Archiver();
        exr.archive("C:\\Users\\User\\Pictures\\testingArc 3_98Mb.bmp");
    }
}
