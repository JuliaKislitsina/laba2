package laba2.classes;

import java.util.Arrays;
import java.util.ArrayList;

import julia.*;

public class Main {
	public static void main(String[] args) {
		
		System.out.println("Введите количество вершин ");
		int answer = 9;
		System.out.println("Введите начальную вершину ");
		int u = 2;
		
		
		Graph g = new Graph(answer);
		System.out.println("Белман ");
		g.addArc(0, 5, 3);
		g.addArc(0, 2, 2);
		g.addArc(0, 4, -3);
		g.addArc(1, 0, 2);
		g.addArc(2, 0, -1);
		g.addArc(2, 4, 2);
		g.addArc(3, 0, 3);
		g.addArc(3, 1, 2);
		g.addArc(3, 4, -2);
		g.addArc(3, 8, 2);
		g.addArc(4, 6, 1);
		g.addArc(4, 7, 4);
		g.addArc(5, 7, 2);
		g.addArc(6, 3, 3);
		g.addArc(6, 8, 2);
		g.addArc(7, 2, 2);
		g.addArc(7, 5, -1);
		g.addArc(8, 7, -1);
		g.addArc(8, 4, 2);
		
		BellmanFord bellmanFord = new BellmanFord(g);
		System.out.println("Tree: " + Arrays.toString(bellmanFord.getTree(u)));
		System.out.println("Dist: " + Arrays.toString(bellmanFord.getDistances(u)));

		Graph1 g1 = new Graph1(answer);
		g1.addArc(0, 5, 3);
		g1.addArc(0, 2, 2);
		g1.addArc(0, 4, -3);
		g1.addArc(1, 0, 2);
		g1.addArc(2, 0, -1);
		g1.addArc(2, 4, 2);
		g1.addArc(3, 0, 3);
		g1.addArc(3, 1, 2);
		 g1.addArc(3, 4, -2);
		g1.addArc(3, 8, 2);
		g1.addArc(4, 6, 1);
		g1.addArc(4, 7, 4);
		g1.addArc(5, 7, 2);
		g1.addArc(6, 3, 3);
    g1.addArc(0, 0, 0);
	g1.addArc(7, 2, 2);
	g1.addArc(7, 5, -1);
	g1.addArc(8, 7, -1);
	g1.addArc(8, 4, 2);
		
		System.out.println("Дейкстра Kуча");
		Dijkstra dijkstra = new Dijkstra(g);
		System.out.println("Tree: " + Arrays.toString(dijkstra.getTree(u)));
		System.out.println("Dist: " + Arrays.toString(dijkstra.getDistances(u)));
		
		System.out.println("Джонсон ");
		Johnson johnson = new Johnson(g);
		// По результату выводим два пути между некоторыми парами вершин
		System.out.print("Path (u - 5): " + Arrays.toString(johnson.getPath(u, 5).toArray(new Integer[0])));
		System.out.println("; Distance: " + johnson.getPathLength(u, 5));

	
		
		System.out.println("Флойд ");	
			MinPaths floyd = new MinPaths(g1);
			// По результату выводим два пути между некоторыми парами вершин
			System.out.print("Path (u - 5): " + Arrays.toString(floyd.getPath(u, 5).toArray(new Integer[0])));
			System.out.println("; Distance: " + floyd.getPathLength(u, 5));
			

			System.out.println("Дейкстра ");	
				Dag dag = new Dag(g1);
				System.out.println("Tree: " + Arrays.toString(dag.getTree(u)));
				System.out.println("Dist: " + Arrays.toString(dag.getDistances(u)));
			
			
					}
		}

