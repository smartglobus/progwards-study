package ru.progwards.java2.lessons.patterns;

import java.time.Instant;
import java.util.*;

public enum Profiler {
    INSTANCE;

    private Profiler() {
    }

    private Map<String, StatisticInfo> allSections = new HashMap<>();  // словарь из уникальных секций
    private ArrayList<StatisticInfo> sectionsList = new ArrayList<>();  // текущий список уникальных секций

    public void enterSection(String name) {
        long enterTime = Instant.now().toEpochMilli();
        if (allSections.putIfAbsent(name, new StatisticInfo()) == null) {
            allSections.get(name).sectionName = name;
            sectionsList.add(allSections.get(name));
        }
        allSections.get(name).lastEnterTime = enterTime;
        allSections.get(name).selfTimeCorrection = 0; // обнуление прошлой коррекции
    }

    public void exitSection(String name) {
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

    public List<StatisticInfo> getStatisticInfo() {
        sectionsList.sort(new Comparator<StatisticInfo>() {
            @Override
            public int compare(StatisticInfo o1, StatisticInfo o2) {
                return o1.sectionName.compareTo(o2.sectionName);
            }
        });
        return sectionsList;
    }


//    public static void main(String[] args) {
//        try {
//            for (int i = 0; i < 3; i++) {
//                enterSection("pr1");
//                Thread.sleep(100);
//                if (i == 2) {
//                    for (int j = 0; j < 2; j++) {
//                        enterSection("pr2");
//                        Thread.sleep(200);
//                        enterSection("pr3");
//                        Thread.sleep(100);
//                        exitSection("pr3");
//                        exitSection("pr2");
//                    }
//                }
//                exitSection("pr1");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        for (StatisticInfo si : getStatisticInfo()) {
//            System.out.println(si.sectionName + "  " + si.fullTime + "  " + si.selfTime + "  " + si.count);
//        }
//    }


    public class StatisticInfo {
        public String sectionName;  // - имя секции
        public int fullTime;  // - полное время выполнения секции в миллисекундах.
        public int selfTime;  // - чистое время выполнения секции в миллисекундах. Для вложенных секций, из времени выполнения внешней секции нужно вычесть времена выполнения вложенных секций.
        public int count;  // - количество вызовов. В случае, если вызовов более одного, fullTime и selfTime содержат суммарное время выполнения всех вызовов.

        public long lastEnterTime;
        public long lastExitTime;
        public long selfTimeCorrection;
    }
}