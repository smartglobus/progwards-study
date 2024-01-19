package ru.progwards.java2.lessons.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class FindUnused {

    // roots  -  список корневых объектов
    // objects  - список всех объектов системы
    public static List<CObject> find(List<CObject> roots, List<CObject> objects) {

        for (CObject c : objects) c.mark = 0;

        for (CObject c : roots) markUsed(c);

        return objects.stream().filter(cObject -> cObject.mark == 0).collect(Collectors.toList());
    }

    private static void markUsed(CObject cObject) {
// Если метка 1, значит объект уже в обработке. Сам в своё время получит метку 2 (даже если он в циклической части графа, что допустимо).
// Если метка 2, обработка уже не нужна

        cObject.mark = 1;
        for (CObject c : cObject.references)
            if (c.mark == 0)
                markUsed(c);
        cObject.mark = 2;
    }


    public static void main(String[] args) {
        CObject rootA = new CObject(2);
        CObject rootB = new CObject(2);
        CObject rootC = new CObject(2);

        CObject A1 = new CObject(2);
        CObject A2 = new CObject(2);
        CObject A3 = new CObject(2);
        CObject A4 = new CObject(2);
        CObject A5 = new CObject(2);
        CObject A6 = new CObject(2);

        CObject B1 = new CObject(2);
        CObject B2 = new CObject(2);
        CObject B3 = new CObject(2);
        CObject B4 = new CObject(2);
        CObject B5 = new CObject(2);

        CObject C1 = new CObject(2);
        CObject C2 = new CObject(2);

        rootA.references.addAll(List.of(A1, A2, A3));
        rootB.references.addAll(List.of( B2));
        rootC.references.addAll(List.of(C1));
        A1.references.addAll(List.of(A2, A4, A5));
        A5.references.add(A3);
        A3.references.addAll(List.of(rootB, A6));
        A6.references.add(A5);
        B2.references.addAll(List.of( B4, B5));
        C1.references.addAll(List.of(B2, B5));

        List<CObject> roots = new ArrayList<>();
        List<CObject> objects = new ArrayList<>();
        roots.addAll(List.of(rootA, rootB, rootC));
        objects.addAll(List.of(A1, A2, A3, A4, A5, A6, B1, B2, B3, B4, B5, C1, C2));
        objects.addAll(roots);
        List<CObject> resultUnused;
        resultUnused = find(roots, objects);
        System.out.println(resultUnused.size());
    }
}


class CObject {
    public List<CObject> references; // ссылки на другие объекты
    int mark;  // пометка для алгоритма
    // 0 - не используется
    // 1 - посещен
    // 2 - используется

    public CObject(int mark) {
        references = new ArrayList<>();
        this.mark = mark;
    }
}