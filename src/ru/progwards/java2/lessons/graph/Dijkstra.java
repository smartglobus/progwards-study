package ru.progwards.java2.lessons.graph;

import java.util.*;

// Версия для ориентированного графа.
public class Dijkstra {
    private int[][] graph; // матрица смежности, нумерация от 0
    private final int GRAPH_SIZE;
    int[][] resultGraph; // ???
    private final static int INF = Integer.MAX_VALUE;
    private Vertex[] vertexArr; // массив вершин
    private Queue<Vertex> vertexQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.dist));

    public Dijkstra(int[][] graph) {
//        if (graph.length != graph[0].length) System.out.println("Граф не квадратный!");
        this.graph = graph;
        GRAPH_SIZE = graph.length;
        vertexArr = new Vertex[graph.length];

        for (int i = 0; i < GRAPH_SIZE; i++) vertexArr[i] = new Vertex(INF);
    }

    public int[][] find(int n) { // не понял, что возвращать

        if (n < 0 || n > GRAPH_SIZE - 1) {
            System.out.println("Нет такой вершины!");
            return null;
        }

        vertexArr[n].dist = 0;
        for (int i = 0; i < GRAPH_SIZE; i++) {
            Vertex v = vertexArr[i];
            vertexQueue.add(v);
            for (int j = 0; j < GRAPH_SIZE; j++)
                if (graph[i][j] > 0) v.outEdges.add(new Edge(graph[i][j], vertexArr[j]));
        }

        while (!vertexQueue.isEmpty()) {
            Vertex minV = vertexQueue.poll(); // взять вершину с минимальной dist
            if (minV.dist == INF) return null; // остались только недоступные вершины. Выход!
            minV.visited = true;
            for (int i = 0; i < minV.outEdges.size(); i++) {
                Edge e = minV.outEdges.get(i);
                Vertex secondV = e.second;
                if (!secondV.visited) {
                    int newDist = minV.dist + e.weight;
                    if (secondV.dist > newDist) {
                        vertexQueue.remove(secondV);
                        secondV.dist = newDist;
                        vertexQueue.add(secondV);
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[][] arr = {{0, 7, 9, 0, 0, 14}, {7, 0, 10, 15, 0, 0}, {9, 10, 0, 11, 0, 2}, {0, 15, 11, 0, 6, 0}, {0, 0, 0, 6, 0, 9}, {14, 0, 2, 0, 9, 0}};
        Dijkstra test = new Dijkstra(arr);
        int[][] arr2 = {{0, 3, 2, 7, 0, 0}, {3, 0, 0, 0, 9, 0}, {2, 0, 0, 0, 0, 0}, {7, 0, 0, 0, 0, 6}, {0, 9, 0, 0, 0, 0}, {0, 0, 4, 0, 8, 0}};
        Dijkstra test2 = new Dijkstra(arr2);

        test2.find(5);

    }

    class Vertex {
//        int num;
        int dist;
        boolean visited;
        List<Edge> outEdges = new ArrayList<>();

        Vertex(int dist) {
            this.dist = dist;
//            this.num = num;
        }
    }

    class Edge {
        int weight;
        Vertex second;

        Edge(int weight, Vertex second) {
            this.weight = weight;
            this.second = second;
        }
    }

}

