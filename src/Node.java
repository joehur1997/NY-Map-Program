
/*Joseph Hur
 * jhur3
 * Project 4
 */
import java.util.*;

public class Node implements Comparable<Node> {

	public double x;
	public double y;

	public String id;

	public LinkedList<Node> path;
	public HashMap<Node, Edge> connectedEdges;

	public boolean known;
	public double dist;
	public Node parent;

	public Node(String id, double x, double y) {
		this.id = id;
		this.x = x;
		this.y = y;

		path = new LinkedList<Node>();
		connectedEdges = new HashMap<Node, Edge>();

		known = false;
		dist = Integer.MAX_VALUE;
	}

	public Iterator<Edge> getAllEdges() {
		Collection<Edge> edges = connectedEdges.values();
		Iterator<Edge> iter = edges.iterator();

		return iter;
	}

	public Edge getEdge(Node node) {
		return connectedEdges.get(node);
	}

	public void addEdge(Node node, Edge edge) {
		connectedEdges.put(node, edge);
	}

	public LinkedList<Node> getPath() {
		return path;
	}

	public void addtoPath(Node node, Edge edge) {
		path.add(node);
		addEdge(node, edge);
	}

	public ListIterator<Node> getAllPath() {
		return path.listIterator();
	}

	@Override
	public String toString() {
		return id;
	}

	public int compareTo(Node node) {
		if (this.dist == node.dist) {
			return 0;
		} else if (this.dist < node.dist) {
			return -1;
		} else {
			return 1;
		}
	}

}
