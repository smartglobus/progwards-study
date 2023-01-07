package ru.progwards.java1.lessons.io2;

import java.io.*;

public class Censor {

    public static void censorFile(String inoutFileName, String[] obscene) throws CensorException {

        try (RandomAccessFile censor = new RandomAccessFile(inoutFileName, "rw")) {

            for (long i = 0; i < censor.length(); ) {
                long currentLinePointer = censor.getFilePointer();// запоминаем положение курсора на начало текущей строки
                String currentLine = censor.readLine();// читаем строку исходного файла
                long nextLinePointer = censor.getFilePointer();// запоминаем положение курсора на начало следущей строки

    // для каждого слова проверяем, есть ли оно в массиве obscene
    // если слово подлежит цензуре, ставим курсор на позицию (начало строки + место найденного слова в этой строке)
    // и перезаписываем звездочки в кол-ве, равном кол-ву букв в этом слове
                String[] currLineArr = currentLine.split("[ ,.!\"?#()]");//разбиваем строку на слова по пробелам и др.
                for (int j = 0; j < currLineArr.length; j++) {
                    for (String s : obscene) {
                        if (currLineArr[j].equals(s)) {
                            censor.seek(currentLinePointer + currentLine.indexOf(currLineArr[j]));
                            for (int z = 0; z < currLineArr[j].length(); z++) {
                                censor.write('*');
                            }
                        }
                    }
                }
                i = nextLinePointer;
            }
        } catch (Exception e) {
            throw new CensorException(e.getMessage(), inoutFileName);
        }
    }


    static class CensorException extends Exception {
        String message;
        String fileName;

        CensorException(String message, String fileName) {
            super(message);
            this.fileName = fileName;
        }

        @Override
        public String toString() {
            return fileName + ":" + message;
        }
    }

    public static void main(String[] args) {
        String[] obs = {"Java", "Oracle", "Sun", "Microsystems"};
        try {
            censorFile("C:\\Users\\User\\IdeaProjects\\Progwards first project\\src\\ru\\progwards\\java1\\lessons\\io2\\inoutFile.txt",
                    obs);
        } catch (CensorException e) {
            e.printStackTrace();
        }
    }
}
/*
Создать статический метод public static void censorFile(String inoutFileName, String[] obscene),
в котором прочитать файл inoutFileName и заменить слова, содержащиеся в String[] obscene на '*',
соответствующие количеству символов в слове, изменения записать в исходный файл.
В случае возникновения ошибки, выбросить свое собственное исключение CensorException в котором сохранить - строку,
полученную у оригинального exception через метод getMessage() и имя файла, в котором возникла ошибка.
В классе перекрыть метод toString(), вернув <имя файла>:<строка ошибки>. Класс CensorException разместить в классе Censor

Например файл содержит:
Java — строго типизированный объектно-ориентированный язык программирования, разработанный компанией Sun Microsystems (в последующем приобретённой компанией Oracle). 
obscene = {"Java", "Oracle", "Sun", "Microsystems"}

Должен выдать результат:
**** — строго типизированный объектно-ориентированный язык программирования, разработанный компанией *** ************ (в последующем приобретённой компанией ******).
 */
