package julia;

import java.util.Arrays;
import java.util.Iterator;

import julia.Topsort.NotADAGException;

public class Dag {
	final Graph1 graph;		// Граф, для которого производятся вычисления
	
	int src = -1;			// Начальная вершина, пути из которой анализируются
	int nVert;				// число вершин в графе
	
	double[] distances;		// Массив расстояний
	int[] tree;				// Дерево обхода по минимальным путям

	public Dag(Graph1 g3) {
		graph = g3;
		nVert = g3.getCount();
		distances = new double[nVert];
		tree = new int[nVert];
	}
	
	/**
	 * Выдает дерево минимальных путей.
	 * Если дерево еще не построено, запускается алгоритм Дейкстры.
	 * @param u	Номер исходной вершины
	 * @return	Дерево в виде массива обратных дуг
	 */
	public int[] getTree(int u) {
		if (u < 0 || u >= nVert) return null;
		if (u != src) {
			try {
				dag(u);
			} catch (NotADAGException e) {
				return null;
			}
		}
		return tree;
	}
	
	/**
	 * Выдает длины минимальных путей.
	 * Если дерево еще не построено, запускается алгоритм Дейкстры.
	 * @param u	Номер исходной вершины
	 * @return	Массив расстояний до указанных вершин
	 */
	public double[] getDistances(int u) {
		if (u < 0 || u >= nVert) return null;
		if (u != src) {
			try {
				dag(u);
			} catch (NotADAGException e) {
				return null;
			}
		}
		return distances;
	}
	
	public void dag(int s) throws NotADAGException {
		Topsort topsort = new Topsort(graph);
		int[] labels = topsort.getLabels();
		int[] indices = new int[nVert];
		for (int i = 0; i < nVert; ++i) {
			indices[labels[i]] = i;
			distances[i] = Double.POSITIVE_INFINITY;
			tree[i] = -1;
		}
		distances[s] = 0;
		for (int index = labels[s]; index < nVert; ++index) {
			int from = indices[index];
			for (Iterator<Graph1.Arc> iArc = graph.arcs(from); iArc.hasNext(); ) {
				Graph1.Arc arc = iArc.next();
				int to = arc.to();
				double newDist = distances[from] + arc.weight();
				if (newDist < distances[to]) {
					distances[to] = newDist;
					tree[to] = from;
				}
			}
		}
	}
	

}