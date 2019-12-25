package julia;

import java.util.Iterator;

/**
 * ���������� �������� ��������� �������������� ���������� ������ �����
 */
public class Topsort {
	@SuppressWarnings("serial")
	public static class NotADAGException extends Exception {
		public NotADAGException() {
			super("Not a DAG");
		}
	}
	
	Graph1 graph;
	int nVert;
	
	int[] marks;
	boolean[] passed;
	
	int curMark;
	boolean sorted = false;
	
	public Topsort(Graph1 g) {
		graph = g;
		nVert = g.getCount();
		marks = new int[nVert];
		passed = new boolean[nVert];
	}
	
	public int[] getLabels() throws NotADAGException {
		if (!sorted) {
			topsort();
			sorted = true;
		}
		return marks;
	}
	
	private void topsort() throws NotADAGException {
		curMark = nVert;
		for (int i = 0; i < nVert; ++i) {
			passed[i] = false;
			marks[i] = -1;
		}
		for (int start = 0; start < nVert; ++start) {
			if (!passed[start]) {
				traverseComp(start);
			}
		}
	}
	
	private void traverseComp(int s) throws NotADAGException {
		passed[s] = true;
		for (Iterator<Graph1.Arc> iArc = graph.arcs(s); iArc.hasNext(); ) {
			Graph1.Arc arc = iArc.next();
			int to = arc.to();
			if (!passed[to]) {
				if (marks[to] != -1) {
					throw new NotADAGException();
				}
				traverseComp(to);
			}
		}
		marks[s] = --curMark;
	}
}

