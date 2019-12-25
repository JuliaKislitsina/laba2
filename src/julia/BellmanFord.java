package julia;

import java.util.Arrays;
import java.util.Iterator;

public class BellmanFord {
	final Graph graph;		// ����, ��� �������� ������������ ����������
	
	int src = -1;			// ��������� �������, ���� �� ������� �������������
	int nVert;				// ����� ������ � �����
	
	double[] distances;		// ������ ����������
	int[] tree;				// ������ ������ �� ����������� �����

	public BellmanFord(Graph g) {
		graph = g;
		nVert = g.getCount();
		distances = new double[nVert];
		tree = new int[nVert];
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
			bellmanFord(u);
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
			bellmanFord(u);
		}
		return distances;
	}
	
	/**
	 * ���������� ����
	 * @param from		����� ��������� �������
	 * @param fromTo	���� (�������� � �������� �������)
	 * @return			True, ���� ���������� ������� � ��������� ����������,
	 * 					����� False.
	 */
	private boolean relax(int from, Graph.Arc fromTo) {
		int to = fromTo.to();
		double newDist = distances[from] + fromTo.weight();
		if (newDist < distances[to]) {
			distances[to] = newDist;
			tree[to] = from;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ���������� ��������� �������� - ����� �� ���������� ���������� �����
	 * �� �������� ������� � ������������ ����� ��� ������ ������������� �����.
	 * @param s
	 * @return
	 */
	private boolean bellmanFord(int s) {
		src = s;
		// ������������� ��������
		for (int i = 0; i < nVert; ++i) {
			distances[i] = Double.POSITIVE_INFINITY;
			tree[i] = -1;
		}
		distances[s] = 0;
		
		// ���� ���������?
		boolean changed = true;
		// ���� ����� (N+1)-�� ���� ��������� ��� ��� ����������,
		// ������, � ����� ������� ���� � ������������� �����.
		for (int step = 0; step <= nVert && changed; ++step) {
			changed = false;
			// ���� �� ���� ����� �� ���� ����������� �����-���� ������
			for (int u = 0; u < nVert; ++u) {
				if (distances[u] != Double.POSITIVE_INFINITY) {
					for (Iterator<Graph.Arc> iArc = graph.arcs(u); iArc.hasNext(); ) {
						Graph.Arc arc = iArc.next();
						// ���������� ����
						if (relax(u, arc)) {
							changed = true;
						}
					}
				}
			}
		}
		return !changed;
	}
	
}