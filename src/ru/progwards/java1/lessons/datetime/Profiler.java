package ru.progwards.java1.lessons.datetime;

import java.time.Instant;
import java.util.*;

public class Profiler {

    private static Map<String, StatisticInfo> allSections = new HashMap<>();// словарь (по сути, множество) из уникальных секций
    private static SortedMap<Long, StatisticInfo> insAndOuts = new TreeMap<>();// сортированный по времени таймлайн со всеми временами первого входа и последнего выхода

    public static void enterSection(String name) {
        long enterTime = Instant.now().toEpochMilli();
        if (allSections.putIfAbsent(name, new StatisticInfo()) == null) {
            allSections.get(name).sectionName = name;
            allSections.get(name).firstEnterTime = enterTime;
        }
//        allSections.get(name).lastEnterTime = enterTime;// нужно ли оно ЗДЕСЬ???

    }

    public static void exitSection(String name) {
        long exitTime = Instant.now().toEpochMilli();
//        allSections.get(name).lastExitTime = exitTime;
        allSections.get(name).fullTime = (int) (exitTime - allSections.get(name).firstEnterTime);
//        allSections.get(name).selfTime += (int) (exitTime - allSections.get(name).lastEnterTime);// нужно ли оно ЗДЕСЬ???
//        allSections.get(name).lastExitTime = exitTime;// нужно ли оно???
        allSections.get(name).count++;
    }

    public static List<StatisticInfo> getStatisticInfo() {
//        for (Map.Entry<String, StatisticInfo> entry : allSections.entrySet()) {
//            insAndOuts.put(entry.getValue().firstEnterTime, entry.getValue());// добавляются все времена входа
//            insAndOuts.put(entry.getValue().firstEnterTime + entry.getValue().fullTime, entry.getValue());// добавляются все времена выхода
//        }
//
//        for (long l = insAndOuts.firstKey(); l <= insAndOuts.lastKey(); ) {
//            for (var entry : insAndOuts.keySet()) {
//
//            }
//        }

        ArrayList<StatisticInfo> sectionsList = new ArrayList<>(allSections.values());// список уникальных секций
        sectionsList.sort(new Comparator<StatisticInfo>() {
            @Override
            public int compare(StatisticInfo o1, StatisticInfo o2) {
                return o1.sectionName.compareTo(o2.sectionName);
            }
        });
        for (StatisticInfo s : sectionsList) s.selfTime = s.fullTime;// предварительная инициализация selfTime

        for (StatisticInfo sectionOfInterest : sectionsList) {
            for (StatisticInfo s : sectionsList) {
                //если секция s началась позже sectionOfInterest но закончилась раньше её окончания, значит секция s вложенная
                if (s.firstEnterTime > sectionOfInterest.firstEnterTime && s.firstEnterTime < (sectionOfInterest.firstEnterTime + sectionOfInterest.fullTime)) {
                    sectionOfInterest.selfTime -= s.fullTime;
                }
            }
        }

        return sectionsList;
    }


    public static void main(String[] args) {

    }
}

class StatisticInfo {

    public String sectionName;// - имя секции
    public int fullTime;// - полное время выполнения секции в миллисекундах.
    public int selfTime;// - чистое время выполнения секции в миллисекундах. Для вложенных секций, из времени выполнения внешней секции нужно вычесть времена выполнения вложенных секций.
    public int count;// - количество вызовов. В случае, если вызовов более одного, fullTime и selfTime содержат суммарное время выполнения всех вызовов.

    public long firstEnterTime;
//    public long lastEnterTime;
//    public long lastExitTime;// нужно ли оно???


}