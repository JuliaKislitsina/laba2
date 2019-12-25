package julia;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ������������� ������������ ����� �������� ���������.
 * �������� �� ���� - ������������ ����� ("����� ����").
 */
public class Graph {
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
		
		public void addWeight(double delta) {
			weight += delta;
		}
	};

	private List<List<Arc>> lGraph;	// ������ ���������
	private int nVertex;			// ����� ������

	/**
	 * ����������� ������� ����� � �������� ������ ������
	 * @param nVert ����� ������
	 */
	public Graph(int nVert) {
		lGraph = new ArrayList<List<Arc>>();
		for (int i = 0; i < nVert; ++i) {
			lGraph.add(new ArrayList<Arc>());
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
		
		lGraph.get(from).add(new Arc(to, info));
	}
	
	public int addVertex() {
		lGraph.add(new ArrayList<Arc>());
		nVertex++;
		return lGraph.size() - 1;
	}
	
	public void removeVertex(int u) {
		lGraph.remove(u);
		nVertex--;
		for (List<Arc> list : lGraph) {
			for (Iterator<Arc> iArc = list.iterator(); iArc.hasNext(); ) {
				Arc arc = iArc.next();
				if (arc.to == u) {
					iArc.remove();
				} else if (arc.to > u) {
					arc.to--;
				}
			}
		}
	}
	
	/**
	 * �������� ���, ������� �� �������� �������
	 * @param u	�������� �������
	 * @return
	 */
	public Iterator<Arc> arcs(int u) {
		return lGraph.get(u).iterator();
	}
}