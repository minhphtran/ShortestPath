import java.util.*;
import java.io.*;

public class Graph{
  
  // class for nodes
  // containing a list of adjacent outgoing nodes and a list of adjacent incoming nodes
  public class Node{
    private ArrayList<Integer> firstNodes;
    private ArrayList<Integer> secondNodes;
    
    public Node(){
      this.firstNodes = new ArrayList<Integer>();
      this.secondNodes = new ArrayList<Integer>();
    }        
    
    // add node u into the list of incoming nodes
    public void addFirstNode(int u){
      this.firstNodes.add(u);
    }
    
    // add node v into the list of outgoing nodes
    public void addSecondNode(int v){
      this.secondNodes.add(v);
    }
    
    // return the above-mentioned lists
    public ArrayList<Integer> getFirstNodes(){
      return this.firstNodes;
    }
    public ArrayList<Integer> getSecondNodes(){
      return this.secondNodes;
    }  
  }
  
  
  private int numberOfNodes;         
  private int numberOfEdges;
  private int source;
  private int dest;
  
  private double[][] weights;
  private ArrayList<Node> nodes;
  
  // read data of graph from file
  // build a matrix of existing edges' weights and a list of nodes
  public Graph(String filename) 
    throws java.io.FileNotFoundException{
    File file = new File(filename);
    Scanner input = new Scanner (file);
    
    input.next(); // Instance name
    this.numberOfNodes = input.nextInt();
    this.numberOfEdges = input.nextInt();
    this.source = input.nextInt()-1;
    this.dest = input.nextInt()-1;
    // minus one as the given data starts counting from node 1
    // while this program use node indices from 0 to (n-1)
    
    this.weights = new double[this.numberOfNodes][this.numberOfNodes];
    
    for (int i=0;i<this.numberOfNodes;i++){
      for (int j=0;j<this.numberOfNodes;j++){
        weights[i][j] = 0; 
        // if there is no edge from i to j, the weight would stay at 0
      }
    }
    
    // use the array index as node ID
    // n nodes are counted from node 0 to node (n-1)
    this.nodes = new ArrayList<Node>();
    for(int i=0; i<this.numberOfNodes; i++){
      nodes.add(new Node());
    }
    
    for (int i=0;i<this.numberOfEdges;i++){
      // arc form u to v has weight w
      int u = input.nextInt()-1;
      int v = input.nextInt()-1;
      double w = input.nextDouble();
      
      // add weight value to existing edge uv
      this.weights[u][v] = w;
      
      this.nodes.get(u).addSecondNode(v); // v is added onto the outgoing nodes list of u 
      this.nodes.get(v).addFirstNode(u); // u is added onto the incoming nodes list of v
    } 
    input.close(); 
  }
  
  // returns the number of nodes of this graph
  public int getNumberOfNodes(){
    return this.numberOfNodes; 
  }
  
  // returns the node at index v
  public Node getNode(int v){
    return this.nodes.get(v);
  }
  
  // return the index of source node
  public int getS(){
    return this.source;
  }
  
  // return the index of destination node
  public int getT(){
    return this.dest;
  }
  
  // returns whether there is an edge from node i to node j in this graph
  public boolean isEdge(int i, int j){
    return (this.weights[i][j]!=0); 
  }
  
  // return the weight of the edge from node i to node j
  public double getWeight(int i, int j){
    return (this.weights[i][j]);
  }
  
}