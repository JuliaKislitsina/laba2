package julia;

import java.util.Arrays;
import java.util.Iterator;

/**
 * ���������� ��������� �������� ������ ����������� ����� � �����
 * � ��������������� ��������� �� �����.
 * �������� ������� ���������� �� ������ ����� ������������ �
 * �������� ����.
 */
public class Dijkstra {
	/**
	 * ���� �� ������ ������� � ��������� �� ��� - ������� ����.
	 * ��������� ��� ������������ �� �����������.
	 */
	private static class Pair implements Comparable<Pair> {
		int vertex;			// ����� �������
		double distance;	// ���������� �� ���
		
		public Pair(int v, double d) {
			vertex = v;
			distance = d;
		}
		
		@Override
		public int compareTo(Pair p) {
			return 
				distance < p.distance ? -1 :
				distance == p.distance ? vertex - p.vertex : 1;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == null && !(o instanceof Pair)) return false;
			Pair sndPair = (Pair)o;
			return vertex == sndPair.vertex && distance == sndPair.distance;
		}
		
		@Override
		public int hashCode() {
			return vertex ^ new Double(distance).hashCode();
		}
		
		@Override
		public String toString() {
			return "(" + vertex + "," + distance + ")";
		}
	}
	
	final Graph graph;		// ����, ��� �������� ������������ ����������
	
	int src = -1;			// ��������� �������, ���� �� ������� �������������
	int nVert;				// ����� ������ � �����
	
	double[] distances;		// ������ ����������
	int[] tree;				// ������ ������ �� ����������� �����
	
	int[] positions;		// ������� ������ � ����		
	Pair[] binHeap;			// �������� ����
	int heapSize;			// ������ ����
	boolean[] passed;		// ������ ���������� ������
	
	public Dijkstra(Graph g) {
		graph = g;
		nVert = g.getCount();
	}
	
	/**
	 * ������ ������ ����������� �����.
	 * ���� ������ ��� �� ���������, ����������� �������� ��������.
	 * @param u	����� �������� �������
	 * @return	������ � ���� ������� �������� ���
	 */
	public int[] getTree(int u) {
		if (u < 0 || u >= nVert) return null;
		if (u != src) {
			dijkstra(src = u);
		}
		return tree;
	}
	
	/**
	 * ������ ����� ����������� �����.
	 * ���� ������ ��� �� ���������, ����������� �������� ��������.
	 * @param u	����� �������� �������
	 * @return	������ ���������� �� ��������� ������
	 */
	public double[] getDistances(int u) {
		if (u < 0 || u >= nVert) return null;
		if (u != src) {
			dijkstra(u);
		}
		return distances;
	}
	
	/**
	 * ���������� ��������� ��������.
	 * � ���������� ������ ��������� ����� ���������
	 * ������ ����������� ����� � ���������� �� �����.
	 * @param s	��������� �������
	 */
	public void dijkstra(int s) {
		src = s;
		distances = new double[nVert];
		tree = new int[nVert];
		positions = new int[nVert];
		binHeap = new Pair[nVert];
		passed = new boolean[nVert];
		// ������������� ��������
		for (int i = 0; i < nVert; ++i) {
			distances[i] = Double.POSITIVE_INFINITY;
			tree[i] = -1;
			positions[i] = -1;
			heapSize = 0;
		}
		distances[s] = 0;
		
		// ������������� ����
		addToHeap(new Pair(s,0));
		
		while (!emptyHeap()) {
			// ������ �������� �������� ��������� �������
			Pair minPair = extractHeap();
			int vert = minPair.vertex;
			passed[vert] = true;
			
			// ���������� ���������� ���, ������� �� ��������� �������
			for (Iterator<Graph.Arc> iArc = graph.arcs(vert); iArc.hasNext(); ) {
				Graph.Arc arc = iArc.next();
				int end = arc.to();
				if (!passed[end]) {
					double newDist = distances[vert] + arc.weight;
					if (positions[end] == -1) {
						// ����� ������� - ��������� � ����
						addToHeap(new Pair(end, newDist));
						tree[end] = vert;
						distances[end] = newDist;
					} else {
						// ������� ��� ���� � ����, ���������� �� ����������.
						Pair p = getFromHeap(positions[end]);
						if (newDist < p.distance) {
							changeHeap(positions[end], newDist);
							tree[end] = vert;
							distances[end] = newDist;
						}
					}
				}
			}
		}
	}
	
	//--------------------- PRIVATE ---------------------

	/**
	 * ���������� ������� �������� � ���� � ������������ � ������������
	 * (�������������) ����������� �� ���.
	 * @param i			������� �������� � ����
	 * @param newDist	����� ����������
	 */
	private void changeHeap(int i, double newDist) {
		binHeap[i].distance = newDist;
		heapUp(i);
	}

	/**
	 * ������ � �������� ���� �� �������.
	 * @param i	������ ��������
	 * @return
	 */
	private Pair getFromHeap(int i) {
		return binHeap[i];
	}

	/**
	 * ���������� �� ���� �������� � ����������� ����������� �� ����.
	 * @return	������� � ��������� ����������� (���������� �����������).
	 */
	private Pair extractHeap() {
		Pair minPair = binHeap[0];
		positions[minPair.vertex] = -1;
		if (--heapSize > 0) {
			binHeap[0] = binHeap[heapSize];
			binHeap[heapSize] = null;
			positions[binHeap[0].vertex] = 0;
			heapDown(0);
		}
		return minPair;
	}

	/**
	 * ���������� ������ �������� � ����.
	 * @param pair	����� �������
	 */
	private void addToHeap(Pair pair) {
		binHeap[positions[pair.vertex] = heapSize] = pair;
		heapUp(heapSize++);
	}

	/**
	 * ��������, ����� �� ����.
	 * @return
	 */
	private boolean emptyHeap() {
		return heapSize == 0;
	}

	/**
	 * ������������� �������� ���� � �������� �������� ����� �� ����
	 * @param i	������ ��������
	 */
	private void heapUp(int i) {
		Pair pair = binHeap[i];
		int pred = (i - 1) / 2;
		while (pred >= 0 && pair.compareTo(binHeap[pred]) < 0) {
			positions[binHeap[pred].vertex] = i;
			binHeap[i] = binHeap[pred];
			i = pred;
			if (pred == 0) break;
			pred = (i - 1) / 2;
		}
		positions[pair.vertex] = i;
		binHeap[i] = pair;
	}

	/**
	 * ������������� �������� ���� � �������� �������� ���� �� ����
	 * @param i	������ ��������
	 */
	private void heapDown(int i) {
		Pair pair = binHeap[i];
		int next = 2 * i + 1;
		while (next < heapSize) {
			if (next + 1 < heapSize && binHeap[next+1].compareTo(binHeap[next]) < 0) {
				next++;
			}
			if (pair.compareTo(binHeap[next]) <= 0) {
				break;
			}
			positions[binHeap[next].vertex] = i;
			binHeap[i] = binHeap[next];
			i = next;
			next = 2 * i + 1;
		}
		positions[pair.vertex] = i;
		binHeap[i] = pair;
	}
	
	/**
	 * ������� �������� ����������������� ��������� �� ������� ����������
	 * �������� ������������������ ����� �� 10 ������
	 * @param args
	 */
	public static void main(String[] args) {
			}
}