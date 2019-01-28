import java.util.*;
import ilog.concert.*;
import ilog.cplex.*;

// input: .txt filename
public class ShortestPath{
  private Graph graph;
  
  public ShortestPath(String filename) throws java.io.FileNotFoundException{
    this.graph = new Graph(filename);
  }
  
  // solve by IloCplex  
  public void solveILP(){
    try {
      double startTime = System.currentTimeMillis();
      IloCplex cplex = new IloCplex();
      
      int n = graph.getNumberOfNodes();
      
      // define a matrix of binary variables for each pair of vertices
      IloNumVar[][] edgeVars = new IloNumVar[n][];
      for (int i = 0; i < n; i++) {
        edgeVars[i] = cplex.numVarArray(n, 0, 1,  IloNumVarType.Int);
      }
      
      // add objective function (explained in the report)
      IloLinearNumExpr expr1 = cplex.linearNumExpr(); 
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (graph.isEdge(i,j))
            expr1.addTerm(graph.getWeight(i,j),edgeVars[i][j]);       
        }
      }
      
      cplex.addMinimize(expr1);
      
      // add constraints
      for(int v=0; v < n; v++){
        IloLinearNumExpr expr2 = cplex.linearNumExpr();
        valueOfNode(edgeVars, graph, expr2, v);
        if (v == graph.getS())
          cplex.addEq(expr2, 1); // constraint for source node
        else if (v == graph.getT())
          cplex.addEq(expr2, -1); // constraint for destination node
        else
          cplex.addEq(expr2, 0); // constraint for the rest of the nodes
      }
      
      // solve the program
      cplex.solve();
      double endTime = System.currentTimeMillis();
      
      // output solution value
      System.out.println("Optimal value  = " + cplex.getObjValue()); 
      
      // output optimal solution
      System.out.println("Edges in optimal solution:");
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (graph.isEdge(i,j)){
            if(cplex.getValue(edgeVars[i][j])==1)        
              System.out.println((i + 1) + " " + (j + 1));
            // the given data count from node 1 to node n
            // while this program use node indices from 0 to n-1
          }
        }
      }
      System.out.println("Run time = " + (endTime - startTime) + "ms");
      cplex.end(); 
    }
    catch (IloException exc) {
      exc.printStackTrace();
    }
  }
  
  
  // update constraint expression for each node
  // the difference between total weight of outgoing edges and total weight of incoming edges
  public void valueOfNode(IloNumVar[][] edgeVars, Graph graph, IloLinearNumExpr expr, int v) throws IloException{
    ArrayList<Integer> firstNodes = graph.getNode(v).getFirstNodes();
    ArrayList<Integer> secondNodes = graph.getNode(v).getSecondNodes();
    
    for(int i:firstNodes){ // minus all incoming edges' weight
      expr.addTerm(-1,edgeVars[i][v]);
    }
    
    for(int j:secondNodes){ // add all outgoing edges' weight
      expr.addTerm(1,edgeVars[v][j]);
    }
  }
  
  
  // execution
  public static void main(String[] args)  throws java.io.FileNotFoundException {
    
    //run for instance1-5
    ShortestPath instance = new  ShortestPath(args[0]); 
    instance.solveILP();
    //instance.solveLP();
  }
 
  
  
  // LP Relaxation  
  public void solveLP(){
    try {
      double startTime = System.currentTimeMillis();
      IloCplex cplex = new IloCplex();
      
      int n = graph.getNumberOfNodes();
      
      // define a matrix of binary variables for each pair of vertices
      IloNumVar[][] edgeVars = new IloNumVar[n][];
      for (int i = 0; i < n; i++) {
        edgeVars[i] = cplex.numVarArray(n, 0, 1,  IloNumVarType.Float);
      }
      
      // add objective function 
      IloLinearNumExpr expr1 = cplex.linearNumExpr(); 
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (graph.isEdge(i,j))
            expr1.addTerm(graph.getWeight(i,j),edgeVars[i][j]);       
        }
      }
      
      cplex.addMinimize(expr1);
      
      // add constraints
      for(int v=0; v < n; v++){
        IloLinearNumExpr expr2 = cplex.linearNumExpr();
        valueOfNode(edgeVars, graph, expr2, v);
        if (v == graph.getS())
          cplex.addEq(expr2, 1); // constraint for source node
        else if (v == graph.getT())
          cplex.addEq(expr2, -1); // constraint for destination node
        else
          cplex.addEq(expr2, 0); // constraint for the rest of the nodes
      }
      
      // solve the program
      cplex.solve();
      double endTime = System.currentTimeMillis();
      
      // output solution value
      System.out.println("Optimal value  = " + cplex.getObjValue()); 
      
      // output optimal solution
      System.out.println("Edges in optimal solution:");
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (graph.isEdge(i,j)){
            if(cplex.getValue(edgeVars[i][j])==1)        
              System.out.println((i + 1) + " " + (j + 1));
            // the given data count from node 1 to node n
            // while this program use node indices from 0 to n-1
          }
        }
      }
      System.out.println("Run time = " + (endTime - startTime) + "ms");
      cplex.end(); 
    }
    catch (IloException exc) {
      exc.printStackTrace();
    }
  }
}
