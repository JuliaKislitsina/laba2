package julia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import julia.Graph.Arc;

/**
 * ��������� �������� �������� ���������� ����������� ����� �����
 * ����� ������ ������ � ��������������� ����������� �����.
 */
public class Johnson {
	private double[][] paths;	// ������� ����������
	private int[][] directions;	// ������� �����������

	/**
	 * ����������� ��������� �������� �������� �� �������� �����
	 * @param g
	 */
	public Johnson(Graph g) {
		johnson(g);
	}
	
	/**
	 * ����� ������������ ���� ������� �� ����������� ������� �����
	 * @param from	�������� �������
	 * @param to	������� �������
	 * @return		����� ������������ ���� ����� �������� � ������� ��������.
	 */
	public double getPathLength(int from, int to) {
		return paths[from][to];
	}
	
	/**
	 * ������ ���� ����������� ����� ����� ��������� ����� ���������.
	 * @param from	�������� �������
	 * @param to	������� �������
	 * @return		������ ������� ������, �������� ���� �� �������� �� ������� �������
	 */
	public List<Integer> getPath(int from, int to) {
		List<Integer> path = new ArrayList<Integer>();
		do {
			path.add(0, to);
		} while ((to = directions[from][to]) != -1);
		return path;
	}
	
	/**
	 * ���������� ��������� �������� ���������� ����������� ����� �����
	 * ����� ������ ������ � ��������������� ����������� �����.
	 * @param g	�������� ����
	 */
	private void johnson(Graph g) {
		int nVert = g.getCount();
		
		// 1. ��������� ����� ������� � ���� � �������� ����
		//    ������� ����� �� ��� �� ��� ��������� ������� - O(n).
		int newVertex = g.addVertex();
		for (int u = 0; u < nVert; ++u) {
			g.addArc(newVertex, u, 0);
		}
		
		// 2. ��������� �������� �������� - ����� ��� ���������� ����
		//    ����������� ����� �� ���� ������� �� ��� ������ - O(n*(n+m)).
		BellmanFord bf = new BellmanFord(g);
		double[] f = bf.getDistances(newVertex);
		
		// 3. ������� ����������� ������� � ������������ ����� ���� ��� � ������
		//    ��������� ���� ����� ���, ����� ��� ����� ����� ���������������� - O(n+m).
		g.removeVertex(newVertex);
		for (int u = 0; u < nVert; ++u) {
			for (Iterator<Arc> iArc = g.arcs(u); iArc.hasNext(); ) {
				Arc arc = iArc.next();
				arc.addWeight(f[u] - f[arc.to()]);
			}
		}
		
		// 4. ������ ��� ������ ������� ��������� �������� �������� - O(n*(n+m)*log n)
		Dijkstra dijkstra = new Dijkstra(g);
		paths = new double[nVert][];
		directions = new int[nVert][];
		for (int u = 0; u < nVert; ++u) {
			paths[u] = dijkstra.getDistances(u);
			directions[u] = dijkstra.getTree(u);
		}
		
		// 5. ��������������� �������� ����� ��� - O(n+m).
		for (int u = 0; u < nVert; ++u) {
			for (Iterator<Arc> iArc = g.arcs(u); iArc.hasNext(); ) {
				Arc arc = iArc.next();
				arc.addWeight(f[arc.to()] - f[u]);
			}
		}
		
		// 6. ������������ ������� ���� ����� � ������ �������� ���� ��� - O(n*n).
		for (int i = 0; i < nVert; ++i) {
			for (int j = 0; j < nVert; ++j) {
				paths[i][j] += f[j] - f[i];
			}
		}
	}
	
	/**
	 * ������� �������� ����������������� ��������� �� �������
	 * ���������� �������� ���������������� ����� �� 9 ������
	 * (��������� ���� ����� ������������� �����). 
	 * @param args
	 */

}