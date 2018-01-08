/*Joseph Hur
 * jhur3
 * Project 4
 */
public class Edge implements Comparable<Edge> {

	public String node1;
	public String node2;

	public String id;
	public double dist;

	public Edge(String id, String node1, String node2) {
		this.id = id;
		this.node1 = node1;
		this.node2 = node2;
		dist = 0;
	}

	public Edge getEdge(String node1, String node2) {
		if (node1.equals(this.node1) && node2.equals(this.node2)) {
			return this;
		}
		return null;
	}

	public boolean isSameDest(Edge edge) {
		if (this.node2.equals(edge.node2)) {
			return true;
		}
		return false;
	}

	public boolean isSameEdge(Edge edge) {
		if (node1.equals(edge.node2) && node2.equals(edge.node1)) {
			return true;
		}

		return false;
	}

	@Override
	public int compareTo(Edge edge) {
		if (this.dist == edge.dist) {
			return 0;
		} else if (this.dist < edge.dist) {
			return -1;
		} else {
			return 1;
		}
	}

	@Override
	public String toString() {
		return node1 + " to " + node2;
	}

}
