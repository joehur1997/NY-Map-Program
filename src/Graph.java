
/*Joseph Hur
 * jhur3
 * Project 4
 */
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JPanel;

public class Graph {

	public HashMap<String, Node> graph;
	public LinkedList<Edge> edgeList;

	public double max = Integer.MAX_VALUE;

	public LinkedList<Edge> spEdgeList;
	public LinkedList<Node> spNodeList;

	public Graph() { //graph of hashmap, with linkedlists to store roads and nodes
		graph = new HashMap<String, Node>();
		edgeList = new LinkedList<Edge>();
		spEdgeList = new LinkedList<Edge>();
		spNodeList = new LinkedList<Node>();

	}

	public void insert(Node node) {
		graph.put(node.id, node);
	}

	public boolean isConnected(Node node1, Node node2) { //checks if connected
		ListIterator<Node> pathNodes = node1.getAllPath();
		while (pathNodes.hasNext()) {
			if (pathNodes.next().id.equals(node2.id)) {
				return true;
			}
		}
		return false;
	}

	public Node getNode(String id) { //returns node from string id
		return graph.get(id);
	}

	public void addEdgeConnection(Edge edge) { //adds node connections to edge
		Node node1 = graph.get(edge.node1);
		Node node2 = graph.get(edge.node2);

		node1.addtoPath(node2, edge);
		node2.addtoPath(node1, edge);

		calEdgeWeights(edge);
		edgeList.add(edge);

	}

	public double calDist(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	public void calEdgeWeights(Edge edge) {//calculates weights of edges, ie distance

		double x1;
		double x2;
		double y1;
		double y2;

		Node node1;
		Node node2;

		node1 = getNode(edge.node1);
		node2 = getNode(edge.node2);

		x1 = node1.x;
		y1 = node1.y;

		x2 = node2.x;
		y2 = node2.y;

		edge.dist = calDist(x1, y1, x2, y2);
	}

	public JPanel drawGraph() {
		return new GraphView();
	}

	public class GraphView extends JPanel { //not used in code but was to test if graph could be drawn

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			ListIterator<Edge> list = edgeList.listIterator();

			Edge current;
			int x1;
			int y1;
			int x2;
			int y2;

			Node node1;
			Node node2;

			while (list.hasNext()) {
				current = list.next();
				node1 = getNode(current.node1);
				node2 = getNode(current.node2);

				x1 = (int) node1.x;
				y1 = (int) node1.y;
				x2 = (int) node2.x;
				y2 = (int) node2.y;

				g.drawLine(x1, y1, x2, y2);
			}
		}
	}

	public JPanel drawShortestPath() {
		return new SPView();
	}

	public class SPView extends JPanel { //graphics for shortest path/graph
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ListIterator<Edge> map = edgeList.listIterator();

			Edge current;
			int x1;
			int y1;
			int x2;
			int y2;

			Node node1;
			Node node2;

			while (map.hasNext()) {
				current = map.next();

				node1 = getNode(current.node1);
				node2 = getNode(current.node2);

				x1 = (int) node1.x;
				y1 = (int) node1.y;
				x2 = (int) node2.x;
				y2 = (int) node2.y;

				if (spEdgeList.contains(current)) {
					g.setColor(Color.red);
				} else {
					g.setColor(Color.black);
				}
				g.drawLine(x1, y1, x2, y2);
			}
		}
	}

	public Edge findMinEdge() { //find the minimum weighted edge
		ListIterator<Edge> roads = edgeList.listIterator();
		Edge minRoad = roads.next();
		Edge current;

		while (roads.hasNext()) {
			current = roads.next();
			if (current.dist < minRoad.dist) {
				minRoad = current;
			}
		}
		return minRoad;
	}

	public void DijAlg(Node source) { //Dijstra's algorithm implementation
		Node sourceNode = graph.get(source.id);
		PriorityQueue<Node> queue = new PriorityQueue<Node>();
		LinkedList<Node> nodeList = new LinkedList<Node>();
		nodeList.addAll(graph.values());

		int unknownCount = 0;

		for (Node current : nodeList) {
			if (current.id.equals(sourceNode.id)) {
				current.dist = 0;
			} else {
				current.dist = Integer.MAX_VALUE;
			}
			current.known = false;
			unknownCount++;
			current.parent = null;
			queue.add(current);
		}

		while (unknownCount > 0) {
			Node currentNode = graph.get(queue.poll().id);
			currentNode.known = true;
			unknownCount--;

			LinkedList<Node> adjNodes = currentNode.getPath();
			for (Node adjNode : adjNodes) {
				if (adjNode.known == false) {
					double edgeCost = currentNode.getEdge(adjNode).dist;
					if (currentNode.dist + edgeCost < adjNode.dist) {
						adjNode.dist = currentNode.dist + edgeCost;
						adjNode.parent = currentNode;
						queue.remove(adjNode);
						queue.add(adjNode);
					}
				}
			}
		}
	}

	public void printPath(Node source) {//prints path of nodes
		if (source.parent != null) {
			printPath(source.parent);
			System.out.print(" to ");
		}

		System.out.print(source.id);
		spNodeList.add(source);
	}

	public void getShortestPath() { //constructs list of node for shortest path for graphics
		Node source = spNodeList.poll();
		Node current = spNodeList.poll();

		if (source != null && current != null) {
			Edge road = source.getEdge(current);
			spEdgeList.add(road);
			while (spNodeList.isEmpty() == false) {
				Node next = spNodeList.poll();
				road = current.getEdge(next);
				spEdgeList.add(road);
				current = next;
			}
		}
	}

}
