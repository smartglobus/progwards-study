package ru.progwards.java1.lessons.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//В заданном каталоге и его подкаталогах найти файлы, точно совпадающие по названию (и расширению), дате-времени последнего изменения, размеру и по содержимому.
// Сигнатура метода public List<List<String>> findDuplicates(String startPath), результат - список, содержащий списки строк с именами и полными путями совпадающих файлов.
public class FindDuplicates {
    public List<List<String>> findDuplicates(String startPath) {
        List<List<String>> findDuplicates = new ArrayList<>();
        List<FileData> fileDataList = new ArrayList<>();

        try {
            Files.walkFileTree(Paths.get(startPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    File pickFile = new File(file.toString());
                    if (pickFile.isFile()) {
                        FileData fileData = new FileData();
                        fileData.name = file.getFileName().toString();
                        fileData.lastModified = pickFile.lastModified();
                        fileData.length = pickFile.length();
                        fileData.file = pickFile;
                        fileData.path = file.toAbsolutePath();
                        fileData.pathList.add(file.toAbsolutePath().toString());
                        fileDataList.add(fileData);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < fileDataList.size(); i++) {
            for (int j = i + 1; j < fileDataList.size(); ) {
                if (compareFileData(fileDataList.get(i), fileDataList.get(j))) {
                    fileDataList.get(i).pathList.add(fileDataList.get(j).path.toString());
                    fileDataList.remove(j);
                    continue;
                }
                j++;
            }
        }

        for (FileData fd : fileDataList) {
            if (fd.pathList.size() > 1)
                findDuplicates.add(fd.pathList);
        }
        return findDuplicates;
    }

    private boolean compareFileData(FileData fd1, FileData fd2) {
        if (!fd1.name.equals(fd2.name) || fd1.length != fd2.length || fd1.lastModified != fd2.lastModified)
            return false;
        try {
            byte[] fd1toBytes = Files.readAllBytes(fd1.path);
            byte[] fd2toBytes = Files.readAllBytes(fd2.path);
            return Arrays.equals(fd1toBytes, fd2toBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        FindDuplicates fdtest = new FindDuplicates();
        List<List<String>> fdresult = fdtest.findDuplicates("C:\\Users\\User\\Documents\\Progwards\\test folder");
        for (var f:fdresult)
        System.out.println(f);
    }
}

class FileData {
    String name;    // имя + расширение
    long lastModified;
    long length;
    File file;
    Path path;
    List<String> pathList = new ArrayList<>();
}