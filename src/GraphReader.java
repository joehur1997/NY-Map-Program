
/*Joseph Hur
 * jhur3
 * Project 4
 */
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.util.Map.Entry;

public class GraphReader {

	public LinkedList<Node> intersections;
	public LinkedList<Edge> roads;

	public LinkedList<String> interData;
	public LinkedList<String> roadData;

	public LinkedList<String> fileData;

	public int numOfNodes;

	public Graph graph;
	double minx = Integer.MAX_VALUE;
	double miny = Integer.MAX_VALUE;
	double maxx = Integer.MIN_VALUE;
	double maxy = Integer.MIN_VALUE;

	public void addToList(boolean isNode, String currentLine) { //adds to either node list or edge list
		String readLine = "";
		for (int i = 0; i < currentLine.length(); i++) {
			if (!(Character.isWhitespace(currentLine.charAt(i)))) {
				readLine = readLine + currentLine.charAt(i);
			} else {
				if (isNode == true) {
					interData.add(readLine);
				} else {
					roadData.add(readLine);
				}
				readLine = "";
			}
		}

		if (!(readLine.equals(""))) {
			if (isNode == true) {
				interData.add(readLine);
			} else {
				roadData.add(readLine);
			}
		}
	}

	public GraphReader(String file, String source, String dest, boolean showOnlyMap, boolean showDirect,
			boolean onlyDirect) { //constructor for graphreader and main code for project

		String currentLine = null;
		numOfNodes = 0;
		intersections = new LinkedList<Node>();

		roads = new LinkedList<Edge>();
		roadData = new LinkedList<String>();
		interData = new LinkedList<String>();

		fileData = new LinkedList<String>();
		graph = new Graph();

		String[] data = readFile(file);

		for (int i = 0; i < data.length; i++) {
			currentLine = data[i];
			if (currentLine.startsWith("i")) {
				numOfNodes++;
				addToList(true, currentLine);
			} else {
				addToList(false, currentLine);
			}
		}

		createIntersections();
		createRoads();

		double rangelat = maxy - miny;
		double rangelon = maxx - minx;

		JFrame frame = new JFrame();
		frame.setTitle("Street Map");
		frame.setSize(1000, 1000);

		for (Entry<String, Node> entry : graph.graph.entrySet()) {
			entry.getValue().x = ((entry.getValue().x - minx) / rangelon) * 800;
			entry.getValue().y = ((entry.getValue().y - maxy) / rangelat) * (-1) * 800;

		}

		if (showOnlyMap == true || showDirect == true || onlyDirect == true) {

			if (showDirect == true || onlyDirect == true) {
				System.out.println("Computing shortest path from " + source + " to " + dest + "...");
				graph.DijAlg(graph.getNode(source));

				if (graph.getNode(dest).dist > 100000000) {
					System.out.println("There was an error. Nodes may not be connected!");
				} else {
					System.out.println("Printing shortest path as list of intersections: ");
					graph.printPath(graph.getNode(dest));
					System.out.println();

					System.out.print("Total miles traveled: ");
					System.out.println(graph.getNode(dest).dist * 65 + " miles.");
					System.out.println();

					graph.getShortestPath();
				}
			}

			if (onlyDirect == false) {
				JPanel viewShortestPath = graph.drawShortestPath();
				JTabbedPane tabbedPane = new JTabbedPane();
				tabbedPane.addTab("Shortest Path in Red!", viewShortestPath);
				frame.add(tabbedPane);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			}
		} else {
			System.out.println("Graph created!");
		}
	}

	public void createIntersections() { //creates intersections based on data
		String id;
		double x;
		double y;
		Node intersection;
		while (interData.isEmpty() == false) {
			if (interData.peek().equals("i")) {
				interData.poll();
				id = interData.poll();
				y = Double.parseDouble(interData.poll());
				x = Double.parseDouble(interData.poll());
				intersection = new Node(id, x, y);
				graph.insert(intersection);

				if (x < minx) {
					minx = x;
				}
				if (x > maxx) {
					maxx = x;
				}
				if (y < miny) {
					miny = y;
				}
				if (y > maxy) {
					maxy = y;
				}
			}
		}
	}

	public void createRoads() { //creates roads based on data
		Edge road;
		String node1;
		String node2;
		String id;

		while (roadData.isEmpty() == false) {
			if (roadData.peek().equals("r")) {
				roadData.poll();
				id = roadData.poll();
				node1 = roadData.poll();
				node2 = roadData.poll();
				road = new Edge(id, node1, node2);
				graph.addEdgeConnection(road);
			}
		}
	}

	public static String readFile(String file, Charset charset) throws IOException { //reads file helper function
		byte[] bytearray = Files.readAllBytes(Paths.get(file));
		return new String(bytearray, charset);
	}

	public String[] readFile(String file) { //takes file data and returns a string array of data
		String fileInput = "";
		String[] fileData;
		try {
			fileInput = readFile(file, Charset.defaultCharset());
		} catch (IOException e1) {
			System.out.println("Cannot read file!");
		}
		fileData = fileInput.split("\\r?\\n");
		return fileData;
	}

	public static void main(String[] args) { //just several test cases i did to make sure code worked
		// GraphReader test = new GraphReader("nys.txt", "i89", "i171", false, true,
		// false);
		// GraphReader test2 = new GraphReader("monroe.txt", "i50", "i53", false, true,
		// false);
		// GraphReader test3 = new GraphReader("ur.txt", "GOERGEN-ATHLETIC", "CSB",
		// false, true, false);
	}

}
