package ru.progwards.java2.lessons.classloader;


import javassist.*;
import javassist.tools.reflect.Loader;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.*;


public class SystemProfiler {

    private static Map<String, StatisticInfo> allSections = new HashMap<>();  // словарь из уникальных секций
    private static ArrayList<StatisticInfo> sectionsList = new ArrayList<>();  // текущий список уникальных секций

    public static void enterSection(String name) {
        long enterTime = Instant.now().toEpochMilli();
        if (allSections.putIfAbsent(name, new StatisticInfo()) == null) {
            allSections.get(name).sectionName = name;
            sectionsList.add(allSections.get(name));
        }
        allSections.get(name).lastEnterTime = enterTime;
        allSections.get(name).selfTimeCorrection = 0; // обнуление прошлой коррекции
    }

    public static void exitSection(String name) {
        long exitTime = Instant.now().toEpochMilli();
        long lastDuration = exitTime - allSections.get(name).lastEnterTime;
        allSections.get(name).fullTime += (int) lastDuration;
        allSections.get(name).selfTime += (int) (lastDuration + allSections.get(name).selfTimeCorrection);// накопленная поправка на длину ранее закрытых внутренних секций
        allSections.get(name).lastExitTime = exitTime;
        allSections.get(name).count++;

        // накопление в текущих секциях поправок в коррекцию для будущего вычисления их selfTime (т.е. для тех секций, для которых закрывающаяся "name" является вложенной)
        for (StatisticInfo s : sectionsList) {
            if (s.lastEnterTime >= s.lastExitTime) { // если секция "s" ещё не завершилась
                s.selfTimeCorrection -= lastDuration + allSections.get(name).selfTimeCorrection;
            }
        }
    }

    public static List<StatisticInfo> getStatisticInfo() {
        sectionsList.sort(new Comparator<StatisticInfo>() {
            @Override
            public int compare(StatisticInfo o1, StatisticInfo o2) {
                return Integer.compare(o2.selfTime * o2.count, o1.selfTime * o1.count);
            }
        });
        return sectionsList;
    }

    public static void printStatisticInfo(String fileName) {
        getStatisticInfo();

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("StatisticInfo: " +
                    "Сортировка по произведению чистого времени работы метода на количество его вызовов.\n\n");
            for (StatisticInfo si : sectionsList) writer.write(si.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printToConsoleStatisticInfo() {
        System.out.println("printToConsoleStatisticInfo started...");
        for (StatisticInfo si : sectionsList) System.out.print(si);
    }

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InvocationTargetException {
        ClassPool classPool = ClassPool.getDefault();//
        CtClass modifiedPL = classPool.get("ru/progwards/java2/lessons/trees/TestAVLTreeVsTreeMap".replace('/', '.'));
        CtMethod[] modPLMethods = modifiedPL.getDeclaredMethods();
        for (CtMethod cm : modPLMethods) {
            if (cm.getName().equals("main")) {
                String fileName = cm.getDeclaringClass().getSimpleName() + ".stat";
                cm.insertAfter("ru.progwards.java2.lessons.classloader.SystemProfiler.printStatisticInfo(\"" + fileName + "\");");
            } else {
                String cmName = cm.getLongName();
                cm.insertBefore("ru.progwards.java2.lessons.classloader.SystemProfiler.enterSection(\"" + cmName + "\");");
                cm.insertAfter("ru.progwards.java2.lessons.classloader.SystemProfiler.exitSection(\"" + cmName + "\");");
            }
        }
//        modifiedPL.writeFile();
//        modifiedPL.defrost();

        Class modPL = modifiedPL.toClass(new Loader().getParent(), null);
        Method[] methods = modPL.getDeclaredMethods();
        String[] mainArgs = {};
        methods[0].invoke(null, (Object) mainArgs);
    }
}


class StatisticInfo {
    public String sectionName;  // - имя секции
    public int fullTime;  // - полное время выполнения секции в миллисекундах.
    public int selfTime;  // - чистое время выполнения секции в миллисекундах. Для вложенных секций, из времени выполнения внешней секции нужно вычесть времена выполнения вложенных секций.
    public int count;  // - количество вызовов. В случае, если вызовов более одного, fullTime и selfTime содержат суммарное время выполнения всех вызовов.

    public long lastEnterTime;
    public long lastExitTime;
    public long selfTimeCorrection;

    @Override
    public String toString() {
        return
                "method: " + sectionName +
                        ", \n\tselfTime=" + selfTime +
                        ", count=" + count +
                        ", fullTime=" + fullTime +
                        ";\n";
    }
}