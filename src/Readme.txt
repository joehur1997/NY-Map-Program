Joseph Hur
jhur3
Class ID: 67

WRITEUP:

This project involved making a graph and a program that takes a file input and draws a map representation, with the ability to find the
shortest path between two points. I used a HashMap<String, Node>, where the String id corresponded to a Node with the data. I also used
three linkedlists to store data, for all roads, and the shortest path for roads and intersections. For both my Node and Edge classes I 
implemented them from the java Comparable, so I could compare values within them more easily. The GraphReader class is the main class for
the program, and it takes a file, constructs a string array, then reads the string array to construct an instance of my Graph Class. My
implementation of Dijstra's algorithm is in my Graph class, and I utilized a priority like we went over in class. It takes a node as input,
and finds the shortest path from that node to all other nodes. By using the algorithm within the constructor for my GraphReader class, 
which as a source node and a destination node as part of the input, the algorithm takes the source node and finds the shortest path to the 
destination node. One thing about the constructor for GraphReader, the main class that is used in StreetMap for the project, is that it
has three booleans, representing cases for the command line arguments. Based on whether "--show" or "--directions" or none of them were
input, the StreetMap class initilizes an instance of GraphReader with the booleans based on the command line input. For printing the
shortest path, I used the code from Lab 11. It takes the final node after D's algorithm is run on it, and prints each parent until it
reaches a node with no parent.

RUNTIME ANALYSIS:

Using a HashMap for my graph means that inserting and retrieving data is in constant time, O(1) runtime. For the actual drawing of the map,
since it draws every node and road from prexisting data structures, it will take the size of the data, or O(N) runtime. In terms of 
Dijstra's algorithm, since I used a priority queue, it can be said that the runtime has a O(log n), which meant it scaled well for the
larger files. The extra time the program takes in running for monroe.txt and nys.txt is due to it drawing the graph, which has the
aforementioned O(N) runtime.

INSTRUCTIONS TO RUN PROJECT:

StreetMap is the primary class to run the project.
Running from a command prompt, once the appropiate directory is indicated, commands in the format of:

java StreetMap mapFilename.txt --show --directions startIntersection endIntersection
java StreetMap ur.txt --show --directions HOYT MOREY

should be entered. Running StreetMap directly out of eclipse usually returns an error.
Entering "java StreetMap ur.txt --show --directions HOYT MOREY" should return the list of nodes traveled, total miles traveled, and 
of course the graph itself.

