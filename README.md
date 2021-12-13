# Ex3_Graph_Algorithms <br>
## Sivan Cohen & Hodaya Turgeman <br>

in this assigment we were required to implement and to design data structures and algorithms on graphs.  <br>
In addition,  we were required to creat a graphical interface that allows you to load, save and draw graphs and run the algorithms on them. <br>
We were required to implement the following interfaces:   
1. GeoLocation - this interface represents a geo location <x,y,z>. <br>
   so we make those 3 field: coordinate of x, y and z. <br>
3. NodeData - this interface represents the set of operations applicable on a node (vertex) in a (directional) weighted graph. <br>
   for each node we save this information: its unique id, its GeoLocation, weight, info and tag. <br>
5. EdgeData - this interface represents the set of operations applicable on a directional edge(src,dest) in a (directional) weighted graph. <br>
   for every edge we save: its source and destination node, its weight, info and tag. <br>
7. DirectedWeightedGraph - this interface represents a Directional Weighted Graph. <br>
   each graph has set of nodes, set of edges and count which is the amount of nodes and edges in the graph. <br>
   we represent the nodes by an hashmap which its key is the node's id and its value is the object Node which belong to this id. <br>
   for the edges we use 3 hashmap for getting better running time in some operations: <br>
   first hashmap: its key is a point2D which its x coordinate is the node src key, and its y coordinateis the node dest key. <br>
   the hashmap value is the the object Edge which belong to this nodes key. <br>
   second hashmap: its key is the node src key, its value is an hashmap which its key is the dest node key and the value is the object Edge which belong to this nodes key. <br>
   (key is the node src key and its value is all the edge which go out of this node). <br>
   third hashmap: its key is the node dest, its value is a hash map which its key is the src node and the value is the object Edge which belong to this nodes key. <br>
   (key is the node dest key and its value is all the edge which go in to this node). <br>
9. DirectedWeightedGraphAlgorithms - This interface represents a Directed (positive) Weighted Graph Theory Algorithms including: <br>
   1. #### clone(copy) <br>
   2. #### init(graph) <br>
   4. #### isConnected - strongly (all ordered pairs connected) <br>
       we implement this method by using Kosaraju’s BFS algorithm. <br>
   6. #### shortestPathDist(int src, int dest) <br>
       we implement this method by using Dijkstra algorithm for finding the shortest path between two nodes so the sum of edge weight is minimal. <br>
   8. #### List<NodeData> shortestPath(int src, int dest) <br>
       we used the algorithm above and save the nodes in the path in the returned list. <br>
   9. #### NodeData center <br> 
       this method finds the Node which minimizes the max distance to all the other nodes
   10. #### List<NodeData> tsp(List<NodeData> cities); <br>
        we used a greedy algorithem which compute the shortest path by Dijkstra algorithm. <br>
   11. #### save(file) <br>
   12. #### load(file) <br>
  
 when we implement the interfaces we make sure to do Junit and check our functions. <br>
 We tested them on graphs which we created manually by ourselves and after implementing the load, we tested the functions on the graphs which were provided to us. <br>
  
## running time

|                  | G1  | G2  | G3  | 1000 | 10000  | 100000 |
| :---             |:---:|---: |:---:| ---: | :---:  |---:    |
|center            |  15  ms |  32 ms  |  63 ms  |  8.1 sec    |        |        |
| shortestPathDist |   15 ms  |   16 ms  |  18 ms   |   23 ms   |   47 ms     |   298 ms  |
| shortestPathList |   15 ms  |   16 ms  |   18 ms  |  23 ms    |      77 ms   |   1164  ms  |
| tsp              |    15 ms  |  15 ms    |  16 ms    |  78 ms    |   462 ms     |   9509 ms     |
| IsConnected      |  15 ms   |   15 ms  |  16 ms   |   32 ms   |  10000 ms      |    6579 ms    |
| load | 32 ms    |   48 ms  |  87 ms   |  234 ms    |    687 ms    |  9 sec 44 ms      |

  
 ## How to use our program
- Download The jar from here.  <br>
- open the folder of the jar and run the cmd from its address.  <br>
- write this Line in the cmd: java -jar Ex2.jar json_file_name.json  <br>

The program window will be open, click the zoom out and the window will be displayed in full screen with the graph from the file. <br>
Note that in the file button there is an option to load a file, save a file of the graph which you were created and exit the program. <br>
for the another oporations: <br>
Click the fit button you want and follow this instructions. for: <br>
- Add Node \ Delete Node \ Get Node Information: <br>
   click the desired node by the mouse. <br>
- Add edge \ Delete edge \ Get edge Info \ Find the shortest distance between two nodes: <br>
   click the two desired nodes. The first click defines the source node, and the second the destination node. <br>
- To check if the graph IsConnected or to get the center of the graph:  <br>
   click the appropriate button. <br>
   Note that the node that return will be colored for a few seconds in different color. <br>
- To check if the graph IsConnected or to get the center of the graph:  <br>
   click the appropriate button. <br>
- For the tsp:  <br>
   click startTsp and begin to select the nodes which you want to find a route between them. at the end of the selection click endTsp. <br>
Note that the nodes that return from tsp and shortestPathList will be colored for a few seconds in different color. <br>
  
  ## UML
  
  [uml.pdf](https://github.com/HTUR5/Ex3_Graph_Algorithms/files/7697379/uml.pdf)

  
  
  
  
  
  
