package julia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ���������� ��������� ������ - �������� �� ���������� ����������� �����
 * ����� ����� ������ ������ � ��������������� ����������� �����.
 */
public class MinPaths {
	private double[][] paths;	// ������� ����������
	private int[][] directions;	// ������� �����������
	
	/**
	 * ����������� ��������� �������� ������ - ��������
	 * @param g
	 */
	public MinPaths(Graph1 g) {
		floyd(g);
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
	 * ���������� ��������� ������ - �������� �� ���������� ����������� �����
	 * ����� ����� ������ ������ � �����
	 * @param g
	 */
	private void floyd(Graph1 g) {
		int nVert = g.getCount();
		// ��������� ��������� ������� ����� - ��� ������� ���������,
		// � ������� �������������� ���� ����� ����� "�������������",
		// ����� "���" (u, u), ������� ����� "�����" 0.
		paths = g.buildMatrix();
		// ���������� ��������� ������� �����������
		directions = new int[nVert][nVert];
		for (int i = 0; i < nVert; ++i) {
			int[] line = directions[i];
			for (int j = 0; j < nVert; ++j) {
				line[j] = i == j || paths[i][j] == Double.POSITIVE_INFINITY ? -1 : i;
			}
		}
		
		// ���������� �������� ������ - ��������
		for (int k = 0; k < nVert; ++k) {
			for (int i = 0; i < nVert; ++i) {
				if (i != k && paths[i][k] < Double.POSITIVE_INFINITY) {
					for (int j = 0; j < nVert; ++j) {
						double newPath = paths[i][k] + paths[k][j];
						if (newPath < paths[i][j]) {
							paths[i][j] = newPath;
							directions[i][j] = directions[k][j];
						}
					}
				}
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
