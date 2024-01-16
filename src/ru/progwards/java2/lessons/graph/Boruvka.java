package ru.progwards.java2.lessons.graph;

import java.util.*;

// алгоритм подразумевает, что граф неориентированный. То есть, для заданной структуры все in и out рёбра всегда идут парой и равны по весу.
public class Boruvka {

    static <N, E> List<Edge<N, E>> minTree(Graph<N, E> graph) throws Exception {
        List<Edge<N, E>> edgesT = new ArrayList<>();

        // блок инициализации
        Map<Node<N, E>, CC<N, E>> nodeCCMap = new HashMap<>();
        for (Node<N, E> node : graph.nodes) nodeCCMap.put(node, new CC<>(node));
        for (CC<N, E> cc : nodeCCMap.values())
            for (Edge<N, E> edge : cc.parent.out) {
                CCedge<N, E> cce = new CCedge<>(edge);
                cce.CCin = nodeCCMap.get(edge.in);
                cc.edgesCCqueue.add(cce);
            }

        List<CC<N, E>> ccList = new ArrayList<>(nodeCCMap.values());
        do {
            for (CC<N, E> cc : ccList) {
                CCedge<N, E> minEdge = null;
                CC<N, E> secondCC = null;

                while (!cc.edgesCCqueue.isEmpty()) {
                    minEdge = cc.edgesCCqueue.poll();
                    secondCC = minEdge.CCin;
                    if (!cc.parent.equals(secondCC.parent)) break;
                }
                if (minEdge == null){
                    throw new Exception("В графе есть недостижимые вершины. Алгоритм невыполним.");
                }
                // удаление встречного ребра secondCC -> minEdge
                for (CCedge cce : secondCC.edgesCCqueue) {
                    if (cce.edge.in == minEdge.edge.out && cce.edge.out == minEdge.edge.in) {
                        secondCC.edgesCCqueue.remove(cce);
                        break;
                    }
                }
// так как удалить одну из сливаемых компонент нельзя без нарушения работы итератора, две компоненты после слияния дублируются.
                cc.edgesCCqueue.addAll(secondCC.edgesCCqueue);
                secondCC.parent = cc.parent;
                secondCC.edgesCCqueue = cc.edgesCCqueue;
                edgesT.add(minEdge.edge);

                if (edgesT.size() == graph.nodes.size() - 1) return edgesT;
            }
        } while (edgesT.size() < graph.nodes.size() - 1);

        return edgesT;
    }


    public static void main(String[] args) throws Exception {
        Node A = new Node();
        Node B = new Node();
        Node C = new Node();
        Node D = new Node();
        Node E = new Node();
        Node F = new Node();
        Node G = new Node();
        Node X = new Node();

        // исходящие рёбра
        Edge AB = new Edge(A, B, 7, "AB");
        Edge AD = new Edge(A, D, 4, "AD");
        Edge BA = new Edge(B, A, 7, "BA");
        Edge BC = new Edge(B, C, 11, "BC");
        Edge BE = new Edge(B, E, 10, "BE");
        Edge BD = new Edge(B, D, 9, "BD");
        Edge CB = new Edge(C, B, 11, "CB");
        Edge CE = new Edge(C, E, 5, "CE");
        Edge DA = new Edge(D, A, 4, "DA");
        Edge DB = new Edge(D, B, 9, "DB");
        Edge DE = new Edge(D, E, 15, "DE");
        Edge DF = new Edge(D, F, 6, "DF");
        Edge EB = new Edge(E, B, 10, "EB");
        Edge EC = new Edge(E, C, 5, "EC");
        Edge ED = new Edge(E, D, 15, "ED");
        Edge EF = new Edge(E, F, 12, "EF");
        Edge EG = new Edge(E, G, 8, "EG");
        Edge FD = new Edge(F, D, 6, "FD");
        Edge FE = new Edge(F, E, 12, "FE");
        Edge FG = new Edge(F, G, 13, "FG");
        Edge GE = new Edge(G, E, 8, "GE");
        Edge GF = new Edge(G, F, 13, "GF");
        Edge XB = new Edge(X,B, 1,"XB");

        A.out.addAll(List.of(AB, AD));
        B.out.addAll(List.of(BA, BC, BD, BE));
        C.out.addAll(List.of(CB, CE));
        D.out.addAll(List.of(DA, DB, DE, DF));
        E.out.addAll(List.of(EB, EC, ED, EF, EG));
        F.out.addAll(List.of(FD, FE, FG));
        G.out.addAll(List.of(GE, GF));
        X.out.addAll(List.of(XB));

        Graph graph = new Graph();
        graph.nodes.addAll(List.of(A, B, C, D, E, F, G, X));
        graph.edges.addAll(List.of(AB, AD, BA, BC, BE, BD, CB, CE, DA, DB, DE, DF, EB, EC, ED, EF, EG, FD, FE, FG, GE, GF, XB));

        List<Edge> result = new ArrayList<>(minTree(graph));
        result.forEach(x -> System.out.println(x.info + " " + x.weight));
    }
}

class Node<N, E> {
    N info; // информация об узле
    List<Edge<N, E>> in; // массив входящих ребер
    List<Edge<N, E>> out = new ArrayList<>(); // массив исходящих ребер
}

class Edge<N, E> {
    E info; // информация о ребре
    Node<N, E> out; // вершина, из которой исходит ребро
    Node<N, E> in; // вершина, в которую можно попасть по этому ребру
    double weight; // стоимость перехода

    public Edge(Node<N, E> out, Node<N, E> in, double weight, E info) {
        this.out = out;
        this.in = in;
        this.weight = weight;
        this.info = info;
    }
}

class Graph<N, E> {
    List<Node<N, E>> nodes = new ArrayList<>();
    List<Edge<N, E>> edges = new ArrayList<>();
}


class CC<N, E> {
    Queue<CCedge<N, E>> edgesCCqueue = new PriorityQueue<>(Comparator.comparingDouble(value -> value.edge.weight));
    Node<N, E> parent;

    CC(Node<N, E> parent) {
        this.parent = parent;
    }
}

class CCedge<N, E> {
    Edge<N, E> edge;
    CC<N, E> CCin; // компонента связности, в которую можно попасть по этому ребру

    CCedge(Edge<N, E> edge) {
        this.edge = edge;
    }
}