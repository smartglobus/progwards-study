package ru.progwards.java1.lessons.datetime;

import java.time.Instant;
import java.util.*;

public class Profiler {

    private static Map<String, StatisticInfo> allSections = new HashMap<>();// словарь (по сути, множество) из уникальных секций
    //    private static SortedMap<Long, StatisticInfo> insAndOuts = new TreeMap<>();// сортированный по времени таймлайн со всеми временами первого входа и последнего выхода
    private static ArrayList<StatisticInfo> sectionsList = new ArrayList<>();// текущий список уникальных секций

    public static void enterSection(String name) {
        long enterTime = Instant.now().toEpochMilli();
        if (allSections.putIfAbsent(name, new StatisticInfo()) == null) {
            allSections.get(name).sectionName = name;
//            allSections.get(name).firstEnterTime = enterTime;
            sectionsList.add(allSections.get(name));
        }
        allSections.get(name).lastEnterTime = enterTime;     //                      нужно ли оно ЗДЕСЬ???
        allSections.get(name).selfTimeCorrection = 0;
        System.out.println(allSections.get(name).sectionName +" open");
    }

    public static void exitSection(String name) {
        long exitTime = Instant.now().toEpochMilli();
//        allSections.get(name).lastExitTime = exitTime;
        long lastDuration = exitTime - allSections.get(name).lastEnterTime;
        allSections.get(name).fullTime += (int) lastDuration;
        allSections.get(name).selfTime += (int) (lastDuration + allSections.get(name).selfTimeCorrection);
//        allSections.get(name).selfTimeCorrection = 0;
        System.out.println(allSections.get(name).sectionName +" close   lastDuration = "+lastDuration+",  corr = " +allSections.get(name).selfTimeCorrection);

        allSections.get(name).lastExitTime = exitTime;                                                                       // нужно ли оно???
        allSections.get(name).count++;

        // !!! надо не создавать новый список, а дополнять существующий при создании новой секции!!!

        for (StatisticInfo s : sectionsList) {
            if (s.lastEnterTime >= s.lastExitTime) { // если секция s ещё не завершилась
                s.selfTimeCorrection -= lastDuration + allSections.get(name).selfTimeCorrection;
            }
        }
//        System.out.println(allSections.get("pr1").sectionName+"  corr = " +allSections.get("pr1").selfTimeCorrection);
    }

    public static List<StatisticInfo> getStatisticInfo() {


//        ArrayList<StatisticInfo> sectionsList = new ArrayList<>(allSections.values());// список уникальных секций
        sectionsList.sort(new Comparator<StatisticInfo>() {
            @Override
            public int compare(StatisticInfo o1, StatisticInfo o2) {
                return o1.sectionName.compareTo(o2.sectionName);
            }
        });

        return sectionsList;
    }


    public static void main(String[] args) {

        try {
            for (int i = 0; i < 3; i++) {
                enterSection("pr1");
                Thread.sleep(100);
                if (i == 2) {
                    for (int j = 0; j < 2; j++) {
                        enterSection("pr2");
                        Thread.sleep(200);
                        enterSection("pr3");
                        Thread.sleep(100);
                        exitSection("pr3");
                        exitSection("pr2");
                    }
                }
                exitSection("pr1");
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (StatisticInfo si : getStatisticInfo()) {
            System.out.println(si.sectionName + "  " + si.fullTime + "  " + si.selfTime + "  " + si.count);
        }
    }
}

class StatisticInfo {

    public String sectionName;// - имя секции
    public int fullTime;// - полное время выполнения секции в миллисекундах.
    public int selfTime;// - чистое время выполнения секции в миллисекундах. Для вложенных секций, из времени выполнения внешней секции нужно вычесть времена выполнения вложенных секций.
    public int count;// - количество вызовов. В случае, если вызовов более одного, fullTime и selfTime содержат суммарное время выполнения всех вызовов.

    //    public long firstEnterTime;
    public long lastEnterTime;
    public long lastExitTime;// нужно ли оно???
    public long selfTimeCorrection;


}