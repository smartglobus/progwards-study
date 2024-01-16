package ru.progwards.java2.lessons.graph;

import java.util.*;

// Версия для ориентированного графа.
public class Dijkstra {
    private int[][] graph; // матрица смежности, нумерация от 0
    private final int GRAPH_SIZE;
    private int[][] result; // парный массив [номер вершины][дистанция]
    private final static int INF = Integer.MAX_VALUE;
    private Vertex[] vertexArr;
    private Queue<Vertex> vertexQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.dist));

    public Dijkstra(int[][] graph) throws Exception {

        this.graph = graph;
        GRAPH_SIZE = graph.length;
        vertexArr = new Vertex[graph.length];
        result = new int[GRAPH_SIZE][2];

        for (int i = 0; i < GRAPH_SIZE; i++) {
            if (graph.length != graph[i].length) throw new Exception("Матрица смежности не квадратная!");
        }
        for (int i = 0; i < GRAPH_SIZE; i++) vertexArr[i] = new Vertex(INF);
    }

    public int[][] find(int n) throws Exception {

        if (n < 0 || n > GRAPH_SIZE - 1) {
            throw new Exception("Граф не содержит вершину с таким номером!");
        }

        vertexArr[n].dist = 0;
        for (int i = 0; i < GRAPH_SIZE; i++) {
            Vertex v = vertexArr[i];
            vertexQueue.add(v);
            for (int j = 0; j < GRAPH_SIZE; j++)
                if (graph[i][j] > 0) v.outEdges.add(new Edge(graph[i][j], vertexArr[j]));
        }

        while (!vertexQueue.isEmpty()) {
            Vertex minV = vertexQueue.poll();
            if (minV.dist == INF) break; // остались только недоступные вершины.
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
        for (int i = 0; i < GRAPH_SIZE; i++) {
            result[i][0] = i;
            result[i][1] = vertexArr[i].dist;
        }
        return result;
    }


    public static void main(String[] args) throws Exception {
        int[][] arr = {{0, 7, 9, 0, 0, 14}, {7, 0, 10, 15, 0, 0}, {9, 10, 0, 11, 0, 2}, {0, 15, 11, 0, 6, 0}, {0, 0, 0, 6, 0, 9}, {14, 0, 2, 0, 9, 0}};
        Dijkstra test = new Dijkstra(arr);
        int[][] arr2 = {{0, 3, 2, 7, 0, 0}, {3, 0, 0, 0, 9, 0}, {2, 0, 0, 0, 0, 0}, {7, 0, 0, 0, 0, 6}, {0, 9, 0, 0, 0, 0}, {0, 0, 4, 0, 8, 0}};
        Dijkstra test2 = new Dijkstra(arr2);
        int[][] res = test2.find(5);
        for (int i = 0; i < arr2.length; i++) {
            System.out.printf("Vertex %d, distance %d;\n", res[i][0], res[i][1]);
        }
    }

    class Vertex {
        int dist;
        boolean visited;
        List<Edge> outEdges = new ArrayList<>();

        Vertex(int dist) {
            this.dist = dist;
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