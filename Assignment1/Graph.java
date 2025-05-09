package tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Graph extends CustomLinkedList{
	// representing the graph using adjacency list
	// linked list will be used to store a vertex and all of its adjacent nodes
	// head of each linked list will be stored in arraylists
	
	private ArrayList<Node> adList = new ArrayList<>();
	private int vertexNumber;
	
	public Graph() {
		// super() is present by default you don't need to mention it explicitly
		super(); // writing this just for better understanding of inheritance
		this.vertexNumber = 0;
	}
	
	public void constructGraph() {
		System.out.print("Enter the number of vertices in your graph: ");
		Scanner sc = new Scanner(System.in);
		this.vertexNumber = sc.nextInt();
		
		for(int i=0; i<vertexNumber; i++) {
			System.out.print("Enter data of any vertex: ");
			// step 1: creating a linked list of adjacent nodes
			String data = sc.next();
			var ll = new CustomLinkedList();
			ll.insertEnd(data);
			
			System.out.print("Enter number of vertices adjacent to "+data+": ");
			int numOfAdjacentVertices = sc.nextInt();
			System.out.print("Enter data of vertices adjacent to "+data+": ");
			for(int j=0; j<numOfAdjacentVertices; j++) {
				String adjacentVertexData = sc.next();
				ll.insertEnd(adjacentVertexData);
			}
			
			// step 2: adding the head of each created link list to arraylist
			adList.add(ll.getHead());
		}
	}
	
	public void bfsNonRecursive(String dataOfStartNode) {
		
		Node node = findNodeByData(dataOfStartNode);
		if(node == null) {
			System.out.println("Node with given data cannot be found.");
			return;
		}
		
	    Queue<Node> queue = new LinkedList<>();
	    // Use Integer to track visited nodes by their data
	    ArrayList<String> visited = new ArrayList<>();


	    queue.add(node);
	    visited.add(node.data);

	    while (!queue.isEmpty()) {
	        Node current = queue.poll(); // Remove and process the front of the queue
	        System.out.print(current.data + " ");

	        Node adjacent = current.next; // Start from the next node in the adjacency list
	        while (adjacent != null) {
	            if (!visited.contains(adjacent.data)) {
	                visited.add(adjacent.data); // Mark as visited
	                queue.add(findNodeByData(adjacent.data)); // Add the corresponding node to the queue
	            }
	            adjacent = adjacent.next; // Move to the next adjacent node
	        }
	    }
	}
	
	public void dfsNonRecursive(String dataOfStartNode) {
		
		Node node = findNodeByData(dataOfStartNode);
		if(node == null) {
			System.out.println("Node with given data cannot be found.");
			return;
		}

		Stack<Node> s = new Stack<>();
		ArrayList<String> visited = new ArrayList<>();
		
		s.add(node);
		visited.add(node.data);
		
		while(!s.isEmpty()) {
			Node current = s.pop();
			System.out.print(current.data+" ");
			
			Node adjacent = current.next;
			while(adjacent!=null) {
				if(!visited.contains(adjacent.data)) {
					visited.add(adjacent.data);
					s.push(findNodeByData(adjacent.data));
				}
				adjacent = adjacent.next;
			}
			
		}
	}
	
	public void dfsRecCaller(String startNodeData) {
		ArrayList<String> visited = new ArrayList<>();
		dfsRecursive(startNodeData, adList, visited);
	}
	
	// main dfs Recursive function
	private void dfsRecursive(String val, ArrayList<Node> adjList, ArrayList<String> vis) {
		vis.add(val);
		System.out.print(val+" ");
		Node head = findNodeByData(val);
		
		while(head!=null) {
			if(!vis.contains(head.data)) {
				dfsRecursive(head.data, adjList, vis);
			}
			head = head.next;
		}
	}
	
	public void bfsRecCaller(String dataOfStartNode) {
		Node node = findNodeByData(dataOfStartNode);
		if(node == null) {
			System.out.println("Node with given data cannot be found.");
			return;
		}
		
	    Queue<Node> queue = new LinkedList<>();
	    // Use Integer to track visited nodes by their data
	    ArrayList<String> visited = new ArrayList<>();


	    queue.add(node);
	    visited.add(node.data);
	    bfsRecursive(queue, visited);
	    
	}
	
	private void bfsRecursive(Queue<Node> que, ArrayList<String> vis) {
		if(que.isEmpty()) {
			return;
		}else {
			Node current = que.poll();
			System.out.print(current.data+" ");
			Node adjacent = current.next;
			while(adjacent != null) {
				if (!vis.contains(adjacent.data)) {
	                vis.add(adjacent.data); // Mark as visited
	                que.add(findNodeByData(adjacent.data)); // Add the corresponding node to the queue
	            }
	            adjacent = adjacent.next;
			}
			bfsRecursive(que, vis);
		}
	}

	// Helper method to find a node by its data
	private Node findNodeByData(String data) {
	    for (Node n : adList) {
	        if (n.data.equals(data)) {
	            return n;
	        }
	    }
	    return null;
	}

	
	public void displayAdjacencyList() {
		for(int i =0; i<adList.size(); i++) {
			displayLinkedList(adList.get(i));
		}
	}
	
	public static void main(String[] args) {
		var gh = new Graph();
		gh.constructGraph();
		gh.displayAdjacencyList();
		System.out.print("BFS Recursive: ");
		gh.bfsRecCaller("A");
		System.out.print("\nDFS Recursive: ");
		gh.dfsRecCaller("A");
		System.out.print("\nBFS Non-Recursive: ");
		gh.bfsNonRecursive("A");
		System.out.print("\nDFS Non-Recursive: ");
		gh.dfsNonRecursive("A");
	}
}
/*
Enter the number of vertices in your graph: 6
Enter data of any vertex: A
Enter number of vertices adjacent to A: 2
Enter data of vertices adjacent to A: B C
Enter data of any vertex: B
Enter number of vertices adjacent to B: 2
Enter data of vertices adjacent to B: A C
Enter data of any vertex: C
Enter number of vertices adjacent to C: 5
Enter data of vertices adjacent to C: A B D E F
Enter data of any vertex: D
Enter number of vertices adjacent to D: 2
Enter data of vertices adjacent to D: C E
Enter data of any vertex: E
Enter number of vertices adjacent to E: 3
Enter data of vertices adjacent to E: C D F
Enter data of any vertex: F
Enter number of vertices adjacent to F: 2
Enter data of vertices adjacent to F: C E
A-> B-> C-> /
B-> A-> C-> /
C-> A-> B-> D-> E-> F-> /
D-> C-> E-> /
E-> C-> D-> F-> /
F-> C-> E-> /
BFS Recursive: A B C D E F 
DFS Recursive: A B C D E F 
BFS Non-Recursive: A B C D E F 
DFS Non-Recursive: A C F E D B 
*/

