package ru.progwards.java1.lessons.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FilesSelect {
    // Реализовать метод с сигнатурой public void selectFiles(String inFolder, String outFolder, List<String> keys), который сортирует файлы по их содержимому.
    // Нужно просмотреть содержимое всех файлов, с расширением txt, содержащихся в каталоге inFolder с подкаталогами,
    // и если файл содержит ключевое слово из коллекции keys, то скопировать его в подпапку с соответствующим именем,
    // этого элемента keys, все подпапки должны находиться в outFolder.
    public void selectFiles(String inFolder, String outFolder, List<String> keys) {
        Path pathOut = Paths.get(outFolder);
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.txt");
        try {
            Files.walkFileTree(Paths.get(inFolder), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (pathMatcher.matches(file)) {
                        String fileToString = Files.readString(file);
                        for (String k : keys) {
                            if (fileToString.contains(k)) {
                                Path kPath = pathOut.resolve(Paths.get(k));
                                if (Files.notExists(kPath)) {
                                    Files.createDirectory(kPath);
                                }
                                File f = new File(file.toString());
                                Files.copy(file, kPath.resolve(Paths.get(f.getName())), StandardCopyOption.REPLACE_EXISTING);
                            }
                        }
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
    }

    public static void main(String[] args) {
        List<String> keys = new ArrayList<>();
        keys.add("private");
        keys.add("объект");
        FilesSelect filesSelect = new FilesSelect();
        filesSelect.selectFiles("C:\\Users\\User\\Documents\\Progwards\\test folder", "C:\\Users\\User\\Documents\\Progwards\\test out folder", keys);
    }
}
