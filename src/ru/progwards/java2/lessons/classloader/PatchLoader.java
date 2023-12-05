package ru.progwards.java2.lessons.classloader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class PatchLoader extends ClassLoader {
    private final static String ROOT = "C:\\Users\\User\\Documents\\Progwards\\classLoaderRoot";// без '/' на конце
    private final static String DOT_CLASS = ".class";
    private static PatchLoader loader = new PatchLoader(ROOT);
    private final String basePath;
    private static String lastPatchDate;
    private static String currentDate;
    private static boolean isInitialLoad = true;// на случай, если PatchLoader будет периодически вызываться в цикле

    private static Set<String> classes = new HashSet<>();// реестр всех полных имён загруженных этим загрузчиком классов
    private SortedMap<String, String> updateList = new TreeMap<>();// текущий список на загрузку из полных имён классов и даты
    private SortedSet<String> dateFolders = new TreeSet<>(this::isNewer);// сортированный по возрасту список папок date

    public PatchLoader(String basePath) {
        this(basePath, ClassLoader.getSystemClassLoader());
    }

    public PatchLoader(String basePath, ClassLoader parent) {
        super(parent);
        this.basePath = basePath;
    }

    // метод запуска сканирования корневого каталога и загрузки патчей
    private void rootScanner() {
        File[] rootFiles = new File(ROOT).listFiles();
        if (rootFiles != null) {
            for (File f : rootFiles) {
                String fileName = f.getName();
                if (f.isDirectory() && !dateFolders.contains(fileName) && isDateFolder(fileName)) {
                    dateFolders.add(fileName);
                    dataFolderScanner(fileName);
                    lastPatchDate = dateFolders.last();
                }
            }
        }
        customClassLoader();
    }

// После первичной загрузки по списку isInitialLoad становится false и все обновления делаются новыми экземплярами загрузчика.
    private void customClassLoader() {
        String className = "";
        try {
            if (isInitialLoad) {
                while (!updateList.isEmpty()) {
                    //передать в loadClass строку, склеенную из 8 цифр даты и полного имени класса.
                    // А внутри переопределённых методов разобрать обратно
                    className = updateList.firstKey();
                    currentDate = updateList.remove(className);
                    loader.loadClass(className, false);
                    String absolutePath = ROOT + "\\" + currentDate + "\\" + className.replace(".", "\\") + DOT_CLASS;
                    writeLog(className + " загружен успешно из " + absolutePath + "\n");
                    currentDate = "";
                }
                isInitialLoad = false;
            } else {// заготовка на случай, если PatchLoader будет периодически вызываться в цикле
                // создавать новый загрузчик для каждого загружаемого класса
                while (!updateList.isEmpty()) {
                    className = updateList.firstKey();
                    currentDate = updateList.remove(className);
                    loader = new PatchLoader(ROOT);
                    loader.loadClass(className, true);
                    currentDate = "";
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void dataFolderScanner(String date) {
        Path path = Paths.get(ROOT, date);
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(DOT_CLASS)) {
                        String className = makeClassName(file, date);
                        if (classes.contains(className)) {// класс уже был ранее загружен
                            if (isNewer(date, lastPatchDate) > 0) {
                                updateList.replace(className, date);
                            }
                        } else {// обнаружен новый класс
                            classes.add(className);
                            updateList.put(className, date);
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

    private boolean isDateFolder(String name) {
        if (name.length() != 8) return false;
        try {
            LocalDate.parse(name, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    private int isNewer(String date1, String date2) throws DateTimeParseException {
// чтобы не столкнуться с выбросом исключений, проверка isDateFolder в коде должна всегда происходить перед вызовом isNewer
        if (!isDateFolder(date1)) throw new DateTimeParseException("Wrong folder name! ", date1, 0);
        if (!isDateFolder(date2)) throw new DateTimeParseException("Wrong folder name! ", date2, 0);
        return LocalDate.parse(date1, DateTimeFormatter.BASIC_ISO_DATE).compareTo(LocalDate.parse(date2, DateTimeFormatter.BASIC_ISO_DATE));
    }

    // Вместо ROOT для поиска по папкам date нужно relPath делать относительно ROOT/date, переданного как параметр
    private static String makeClassName(Path path, String date) throws IOException {
        path = path.toAbsolutePath().toRealPath();
        Path relPath = Paths.get(ROOT, date).relativize(path);
        String className = relPath.toString().replaceAll("[\\/\\\\]", ".");
        if (className.toLowerCase().endsWith(DOT_CLASS))
            className = className.substring(0, className.length() - DOT_CLASS.length());
        return className;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> c = findClass(name);
        if (resolve)
            resolveClass(c);
        return c;
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            String classPath = className.replace(".", "/");
            Path classPathName = Paths.get(basePath, currentDate, classPath + DOT_CLASS);
            if (Files.exists(classPathName)) {
                byte[] b = Files.readAllBytes(classPathName);
                try {
                    return defineClass(className, b, 0, b.length);
                } catch (ClassFormatError classFormatError) {
                    classFormatError.printStackTrace();
                } catch (NoClassDefFoundError e) {
                    writeLog(className + " ошибка загрузки " + e.getMessage() + "\n");
                }
            } else {
                return findSystemClass(className);
            }
        } catch (IOException e) {
            throw new ClassNotFoundException(className);
        }
        return null;
    }

    private void writeLog(String logMessage) {
        File logFile = new File(ROOT + "/patchloader.log");
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(String.format("%1$td.%1$tm.%1$tY %1$tH.%1$tM.%1$tS ", new Date()) + logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        PatchLoader testLoader = new PatchLoader("C:\\Users\\User\\Documents\\Progwards\\classLoaderRoot");
        testLoader.rootScanner();
        try {
            Class someClass = Class.forName("classes.loader.study.Something");
            Constructor constructor = someClass.getConstructor();
            Object o = constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
