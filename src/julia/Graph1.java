package julia;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ������������� ������������ ����� �������� ���������.
 * �������� �� ���� - ������������ ����� ("����� ����"). ��� ������
 */
public class Graph1 {
	/**
	 * ������������� ���� �����
	 */
	public static class Arc {
		double weight;	// �������� �� ����
		int to;			// ����� �������, � ������� ����� ����

		public Arc(int to, double info) {
			this.to = to; this.weight = info;
		}
		
		public double weight() { return weight; }
		
		public int to() { return to; }
	};

	private final List<Arc>[] lGraph;	// ������ ���������
	private final int nVertex;			// ����� ������

	/**
	 * ����������� ������� ����� � �������� ������ ������
	 * @param nVert ����� ������
	 */
	@SuppressWarnings("unchecked")
	public Graph1(int nVert) {
		lGraph = new List[nVert];
		for (int i = 0; i < nVert; ++i) {
			lGraph[i] = new ArrayList<Arc>();
		}
		nVertex = nVert;
	}
	
	/**
	 * ����� ������ �����
	 * @return
	 */
	public int getCount() { return nVertex; }

	/**
	 * ���������� ���� � ����. ��������������, ��� ����� ����� ���� � ����� �� ����.
	 * @param from	������ ���� (����� �������)
	 * @param to	����� ���� (����� �������)
	 * @param info	�������� �� ����
	 */
	public void addArc(int from, int to, double info) {
		assert from < nVertex && from >= 0;
		assert to < nVertex && to >= 0;
		
		lGraph[from].add(new Arc(to, info));
	}
	
	/**
	 * �������� ���, ������� �� �������� �������
	 * @param u	�������� �������
	 * @return
	 */
	public Iterator<Arc> arcs(int u) {
		return lGraph[u].iterator();
	}
	
	public double[][] buildMatrix() {
		double[][] matrix = new double[nVertex][nVertex];
		for (int i = 0; i < nVertex; ++i) {
			Iterator<Arc> iArc = arcs(i);
			double[] line = matrix[i];
			for (int j = 0; j < nVertex; ++j) {
				line[j] = Double.POSITIVE_INFINITY;
			}
			while (iArc.hasNext()) {
				Arc arc = iArc.next();
				line[arc.to] = arc.weight;
			}
			line[i] = 0;
		}
		return matrix;
	}
}