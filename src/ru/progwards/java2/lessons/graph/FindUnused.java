package ru.progwards.java2.lessons.graph;

import java.util.List;
import java.util.stream.Collectors;


public class FindUnused {

    // roots  -  список корневых объектов
    // objects  - список всех объектов системы
    public static List<CObject> find(List<CObject> roots, List<CObject> objects) {

        // предварительная пометка как неиспользуемых для всех объектов-потомков неиспользуемых корней
        for (CObject c : roots) if (c.mark == 0) markUnused(c);

        for (CObject c : roots) if (c.mark != 0) markUsed(c);

        return objects.stream().filter(cObject -> cObject.mark != 0).collect(Collectors.toList());
    }

    private static void markUnused(CObject cObject) {
        cObject.mark = 0;
        for (CObject c : cObject.references) markUnused(c);
    }

    private static void markUsed(CObject cObject) {
// Если метка 1, значит объект уже в обработке. Сам в своё время получит метку 2 (если он не в циклической части графа, что допустимо).
// Если метка 2, обработка уже не нужна
        if (cObject.mark == 0) {
            cObject.mark = 1; // метка ставится на случай, если объект окажется одним из корней, ранее считавшимся неиспользуемым
            for (CObject c : cObject.references)
                markUsed(cObject);
            cObject.mark = 2;
        }
    }



    public static void main(String[] args) {
        CObject aa = new CObject();




    }
}


class CObject {
    public List<CObject> references; // ссылки на другие объекты
    int mark;  // пометка для алгоритма
    // 0 - не используется
    // 1 - посещен
    // 2 - используется
}