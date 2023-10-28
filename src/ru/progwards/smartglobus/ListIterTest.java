package ru.progwards.smartglobus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

public class ListIterTest {

    static final int ELEMENTS_COUNT = 38;

    public static void main(String[] args) {
        List<Integer> linkedList = new LinkedList<>();
        ListIterator<Integer> listIterator = linkedList.listIterator();

        for (int i = 0; linkedList.size() < ELEMENTS_COUNT; i++) {
            if (listIterator.previousIndex() >= linkedList.size() / 2) {
                listIterator.previous();
                listIterator.set(76 - i);
            }
            listIterator.add(i);


        }
        for (Integer e : linkedList)
            System.out.println(e);

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            list.add(i * 4);
        }
        ListIterator<Integer> it = list.listIterator();
        iterator3(it);

        for (Integer v : list)
            System.out.println(v);

        int[] a = {3, 4, 5, 6, 7, 8};
//        System.out.println(array2queue(a));
        dequeueTest();
        System.out.println(list);
        System.out.println(list.size());
        Integer[] i = new Integer[list.size() + 1];
        for (Integer in : i) System.out.print(in + " ");
        System.out.println();
        i[5] = 6666;
        for (Integer in : i) System.out.print(in + " ");
        System.out.println();
        list.toArray(i);

        for (Integer in : i) System.out.print(in + " ");
        System.out.println();
        System.out.println(list);
        LocalDateTime ldt2 = LocalDateTime.of(2019, 05, 05, 22, 24);
        System.out.println(ldt2);
        ZoneId zid1 = ZoneId.of("Europe/Moscow");
        System.out.println(zid1.getRules().getOffset(Instant.now()));
        System.out.println(createInstant());
        System.out.println(parseZDT("01.01.2020 16:27:14.444 +0300 Moscow Standard Time"));
        System.out.println(createDate());

    }

    static Instant createInstant() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2020, 1, 1, 15, 0, 0, 1, ZoneId.of("Europe/Moscow"));
        return Instant.from(zonedDateTime);
    }

    // Напишите метод, с сигнатурой ZonedDateTime parseZDT(String str), который возвращает ZonedDateTime,
// парся строку вида "01.01.2020 16:27:14.444 +0300 Moscow Standard Time"
    static ZonedDateTime parseZDT(String str) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS Z zzzz");
        TemporalAccessor ta = dtf.parse(str);
        return ZonedDateTime.from(ta);
    }
static Date createDate(){
//    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
//    try {
//        Date date = sdf.parse("28.02.1986");
//        return date;
//    } catch (ParseException e) {
//        e.printStackTrace();
//    }
//    return null;
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(1986,2,28,0,0,0);
        return new Date(cal.getTimeInMillis());
    }
    /*
    Реализуйте метод с сигнатурой void checkAndAdd(TreeMap<Integer, String> map, Integer key, String value),
    который добавляет пару key-value в map при выполнении следующих условий:

    значение с таким key должно отсутствовать
    значение key долно быть больше головы TreeMap
    значение key долно быть меньше хвоста TreeMap
     */
    void checkAndAdd(TreeMap<Integer, String> map, Integer key, String value) {
        if (!map.isEmpty())
            if (key > map.firstKey() && key < map.lastKey()) map.putIfAbsent(key, value);
    }

    public static class GeoPoint {
        double lat, lon;

        GeoPoint(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Double.compare(geoPoint.lat, lat) == 0 &&
                    Double.compare(geoPoint.lon, lon) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(lat, lon);
        }

        @Override
        public String toString() {
            return "(" + lat + ", " + lon + ')';
        }


        public static void main(String[] args) {
            Map<GeoPoint, String> hashMap = new HashMap<>();
            hashMap.put(new GeoPoint(48.4647991, 135.0598811), "Хабаровск");
            hashMap.put(new GeoPoint(45.2271915, 147.8796713), "Курильск");

            GeoPoint alzamayGP = new GeoPoint(55.5550493, 98.6644106);
            hashMap.put(alzamayGP, "Алзамай");
            hashMap.put(new GeoPoint(55.4255534, 38.2640794), "Бронницы");
            hashMap.put(new GeoPoint(57.9906873, 31.3555076), "Старая Русса");

            System.out.println(hashMap.get(alzamayGP));
            System.out.println("XK = " + Map.entry(alzamayGP, "Алзамай").hashCode());
            System.out.println("хеш-код alzamayGP = " + alzamayGP.hashCode());

            alzamayGP.lon = 98.6644105;

            System.out.println("XK = " + Map.entry(alzamayGP, "Алзамай").hashCode());
            System.out.println("хеш-код alzamayGP = " + alzamayGP.hashCode());

            GeoPoint one = new GeoPoint(45.2271915, 147.8796714);
            hashMap.put(one, "Курильск 1");
//            System.out.println(one.hashCode());
            GeoPoint two = new GeoPoint(45.2271915, 147.8796715);
            hashMap.put(two, "Курильск 2");
//            System.out.println(two.hashCode());
            GeoPoint three = new GeoPoint(45.2271915, 147.8796716);
            hashMap.put(three, "Курильск 3");
            GeoPoint four = new GeoPoint(45.2271915, 147.8796717);
            hashMap.put(four, "Курильск 4");
            GeoPoint one1 = new GeoPoint(45.2271916, 147.8796714);
            hashMap.put(one, "Курильск 1");
            GeoPoint two1 = new GeoPoint(45.2271917, 147.8796715);
            hashMap.put(two, "Курильск 2");
            GeoPoint three1 = new GeoPoint(45.2271918, 147.8796716);
            hashMap.put(three, "Курильск 3");
            GeoPoint four1 = new GeoPoint(45.2271919, 147.8796717);
            hashMap.put(four, "Курильск 4");
            GeoPoint three2 = new GeoPoint(45.227191, 147.8796716);
            hashMap.put(three, "Курильск 3");
            GeoPoint four2 = new GeoPoint(45.227191, 147.8796717);
            hashMap.put(four, "Курильск 4");

            System.out.println("XK = " + Map.entry(alzamayGP, "Алзамай").hashCode());

            System.out.println(hashMap.size());
            for (var entry : hashMap.entrySet())
                if (alzamayGP.equals(entry.getKey()))
                    System.out.println(entry);
            System.out.println(hashMap.get(alzamayGP));
            for (var entry : hashMap.entrySet()) System.out.println(entry);
        }
    }

    //вставляет в HashMap пару <n> "Число n", если она там отсутствует,  Проверить от 1 до максимального числа size включительно.
// Метод возвращает количество добавленных элементов. Пример пары: 1 "Число 1"
    int fillHoles(Map<Integer, String> map, int size) {
        int count = 0;
        for (int i = 1; i <= size; i++) {
            String tryToPut = map.putIfAbsent(i, "Число " + i);
            if (tryToPut == null) count++;
        }
        return count;
    }


    HashMap<Integer, String> a2map(int[] akey, String[] aval) {
        HashMap<Integer, String> result = new HashMap<>();
        for (int i = 0; i < akey.length; i++) result.put(akey[i], aval[i]);
        return result;
    }

    static void dequeueTest() {
        ArrayDeque deque = new ArrayDeque<>();

        for (int i = 0; i <= 10; i++) {
            deque.offer(i);
            if (i % 2 == 0)
                deque.poll();
        }

        System.out.println(deque);
    }

    static ArrayDeque<Integer> array2queue(int[] a) {
        ArrayDeque<Integer> a2q = new ArrayDeque<>();
        for (Integer i : a)
            a2q.offer(i);
        return a2q;
    }


    public Set<Integer> a2set(int[] a) {
        Set<Integer> sI = new HashSet<>();
        for (Integer i : a)
            sI.add(i);
        return sI;
    }

    public static void iterator3(ListIterator<Integer> iterator) {

        while (iterator.hasNext()) {
            int ind = iterator.nextIndex();
            Integer n = iterator.next();
            if (n % 3 == 0)
                iterator.set(ind);
        }
    }
        /*
        Напишите метод с сигнатурой public void iterator3(ListIterator<Integer> iterator)
        который заменяет значение каждого элемента, которое кратно 3 на значение его индекса.
         */

    static int sumLastAndFirst(ArrayDeque<Integer> deque) {
        if (!deque.isEmpty()) return deque.getFirst() + deque.getLast();
        return 0;
    }

}

