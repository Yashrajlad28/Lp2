package tools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.Scanner;

public class Graph extends CustomLinkedList{
	private ArrayList<Node> adList = new ArrayList<>();
	private Map<String, Integer> dist = new HashMap<>();
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
	
	public Node getHeadByName(String name) {
		for(Node n: adList) {
			if(name.equals(n.name)) return n;
		}
		return null;
	}
	
	public void dijkstra(String source) {
		for(Node n: adList) {
			dist.put(n.name, Integer.MAX_VALUE);
		}
		dist.put(source, 0);
		
		PriorityQueue<Node> pq = 
				new PriorityQueue<>(Comparator.comparingInt(n->n.weight));
		pq.add(new Node(source, 0));
		while(!pq.isEmpty()) {
			Node curr = pq.poll();
			String u = curr.name;
			Node head = getHeadByName(u);
			Node neighbour = head.next;
			while(neighbour != null) {
				String v = neighbour.name;
				int weight = neighbour.weight;
				if(dist.get(v) > dist.get(u)+weight) {
					dist.put(v, dist.get(u)+weight);
					pq.add(new Node(v, dist.get(v)));
				}
				neighbour = neighbour.next;
			}
		}
		
	}
	
	// 0 4 4 7 8 5
	public void shortestPath() {
		for(Map.Entry<String,Integer> entry : dist.entrySet()) {
			System.out.println(entry.getKey()+" -> "+entry.getValue());
		}
	}
	
	public static void main(String args[]) {
		Graph g = new Graph();
		g.constructGraph();
		g.displayAdjacencyList();
		System.out.println("===============================");
		String src = "A";
		g.dijkstra(src);
		g.shortestPath();
		
	}
}

/*
Enter the number of vertices in your graph: 6
Enter name of any vertex: A
Enter number of vertices adjacent to A: 2
Enter name-weight of vertices adjacent to A(space-separated): B 4 C 4
Enter name of any vertex: B
Enter number of vertices adjacent to B: 2
Enter name-weight of vertices adjacent to B(space-separated): C 2 A 4
Enter name of any vertex: C
Enter number of vertices adjacent to C: 5
Enter name-weight of vertices adjacent to C(space-separated): A 4 B 2 D 3 F 1 E 6
Enter name of any vertex: D
Enter number of vertices adjacent to D: 2
Enter name-weight of vertices adjacent to D(space-separated): C 3 E 2
Enter name of any vertex: E
Enter number of vertices adjacent to E: 3
Enter name-weight of vertices adjacent to E(space-separated): C 6 F 3 D 2
Enter name of any vertex: F
Enter number of vertices adjacent to F: 2
Enter name-weight of vertices adjacent to F(space-separated): C 1 E 3
A:0 -> B:4 -> C:4 -> /
B:0 -> C:2 -> A:4 -> /
C:0 -> A:4 -> B:2 -> D:3 -> F:1 -> E:6 -> /
D:0 -> C:3 -> E:2 -> /
E:0 -> C:6 -> F:3 -> D:2 -> /
F:0 -> C:1 -> E:3 -> /
===============================
A -> 0
B -> 4
C -> 4
D -> 7
E -> 8
F -> 5
*/
