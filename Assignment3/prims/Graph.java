package tools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Graph extends CustomLinkedList{
	private ArrayList<Node> adList = new ArrayList<>();
	private ArrayList<Node> mst = new ArrayList<>();
	private Map<String, Boolean> visited = new HashMap<>();
	private int vertexNumber;
	
	public Graph() {
		this.vertexNumber = 0;
	}
	
	public void constructGraph() {
		System.out.print("Enter the number of vertices in your graph: ");
		Scanner sc = new Scanner(System.in);
		this.vertexNumber = sc.nextInt();
		
		for(int i=0; i<vertexNumber; i++) {
			System.out.print("Enter name of any vertex: ");
			// step 1: creating a linked list of adjacent nodes
			String name = sc.next();
			var ll = new CustomLinkedList();
			ll.insertEnd(name, 0);
			
			System.out.printf("Enter number of vertices adjacent to %s: ",name);
			int numOfAdjacentVertices = sc.nextInt();
			System.out.printf("Enter name-weight of vertices adjacent to %s(space-separated): ",name);
			
			for(int j=0; j<numOfAdjacentVertices; j++) {
				String adjacentVertexName = sc.next();
				int weight = sc.nextInt();
				ll.insertEnd(adjacentVertexName, weight);
			}
			
			// step 2: adding the head of each created link list to arraylist
			adList.add(ll.getHead());
		}
	}
	
	public void displayAdjacencyList() {
		for(int i =0; i<adList.size(); i++) {
			displayLinkedList(adList.get(i));
		}
	}
	
	public void prims(String src) {
		
		int sum = 0;
		Map<String, String> prnt = new HashMap<>(); // parent
		for(Node n: adList) {
			visited.put(n.name, false);
			prnt.put(n.name, n.parent);
		}
		PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n->n.weight));
		pq.add(new Node(src, 0));
		while(!pq.isEmpty()) {
			Node curr = pq.poll();
			Node head = getHeadByName(curr.name);
			Node adj = head.next;
			int wt = curr.weight;
			
			if(visited.get(curr.name)==true) continue;
			
			visited.put(curr.name, true);
			sum += wt;
			
			// child, weight, parent
			if (!curr.parent.equals("no")) {
	            mst.add(new Node(curr.name, curr.weight, curr.parent));  
	        }
			
			while(adj != null) {
				if(visited.get(adj.name)==false) {
					pq.add(new Node(adj.name, adj.weight, curr.name));
				}
				adj = adj.next;
			}
				
		}
		System.out.println("Sum is: "+sum);
		displayMST();
	}
	
	private void displayMST() {
	    System.out.println("\nMinimum Spanning Tree:");
	    for (Node n : mst) {
	        System.out.println(n.parent + " -> " + n.name + " : " + n.weight);
	    }
	}
	
	public Node getHeadByName(String name) {
		for(Node n: adList) {
			if(name.equals(n.name)) return n;
		}
		return null;
	}
	
	public static void main(String args[]) {
		Graph g = new Graph();
		g.constructGraph();
		g.displayAdjacencyList();
		g.prims("A");
	}
}

/*
Enter the number of vertices in your graph: 5
Enter name of any vertex: A
Enter number of vertices adjacent to A: 2
Enter name-weight of vertices adjacent to A(space-separated): B 2 C 1
Enter name of any vertex: B
Enter number of vertices adjacent to B: 2
Enter name-weight of vertices adjacent to B(space-separated): A 2 C 1
Enter name of any vertex: C
Enter number of vertices adjacent to C: 4
Enter name-weight of vertices adjacent to C(space-separated): A 1 B 1 D 2 E 2
Enter name of any vertex: D
Enter number of vertices adjacent to D: 2
Enter name-weight of vertices adjacent to D(space-separated): C 2 E 1
Enter name of any vertex: E
Enter number of vertices adjacent to E: 2
Enter name-weight of vertices adjacent to E(space-separated): C 2 D 1
A:0 -> B:2 -> C:1 -> /
B:0 -> A:2 -> C:1 -> /
C:0 -> A:1 -> B:1 -> D:2 -> E:2 -> /
D:0 -> C:2 -> E:1 -> /
E:0 -> C:2 -> D:1 -> /
Sum is: 5

Minimum Spanning Tree:
A -> C : 1
C -> B : 1
C -> E : 2
E -> D : 1*/
