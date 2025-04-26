package tools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Graph {
	int edges;
	ArrayList<Edge> graph = new ArrayList<>();
	ArrayList<Edge> mst = new ArrayList<>();
	int cost;
	
	public Graph() {
		this.edges = 0;
		this.cost = 0;
	}
	
	public void constructGraph() {
		System.out.print("How many edges are present in Graph: ");
		Scanner sc = new Scanner(System.in);
		edges = sc.nextInt();
		for(int i=0; i<edges; i++) {
			System.out.print("Enter source-weight-destination (space-separated): ");
			String src = sc.next();
			int weight = sc.nextInt();
			String dest = sc.next();
			graph.add(new Edge(src, weight, dest));
		}
	}
	
	public void kruskals() {
		// sort the edges as per weight
		graph.sort(Comparator.comparingInt(e -> e.weight));
		
		DisjointSet ds = new DisjointSet(graph);
		
		for(Edge e: graph) {
			String u = e.src;
			String v = e.dest;
			int wt = e.weight;
			
			String ultiPrntU = ds.findUltimateParent(u);
			String ultiPrntV = ds.findUltimateParent(v);
			
			if(!ultiPrntU.equals(ultiPrntV)) {
				mst.add(new Edge(u, wt, v));
				ds.findUnionByRank(u, v);
			}
		}
		displayMST();
	}
	
	private void displayMST() {
		System.out.println("Displaying the MST");
		for(Edge e: mst) {
			System.out.println(e.src+" -> "+e.dest+" : "+e.weight);
			cost += e.weight;
		}
		System.out.println("Cost of MST: "+cost);
	}
	
	public static void main(String args[]) {
		Graph g = new Graph();
		g.constructGraph();
		g.kruskals();
	}
}
/*
How many edges are present in Graph: 6
Enter source-weight-destination (space-separated): A 2 B
Enter source-weight-destination (space-separated): A 1 C
Enter source-weight-destination (space-separated): C 1 B
Enter source-weight-destination (space-separated): C 2 E
Enter source-weight-destination (space-separated): E 1 D
Enter source-weight-destination (space-separated): C 2 D
Displaying the MST
A -> C : 1
C -> B : 1
E -> D : 1
C -> E : 2
Cost of MST: 5
*/
