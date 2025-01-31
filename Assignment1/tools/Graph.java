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
			int val = sc.nextInt();
			var ll = new CustomLinkedList();
			ll.insertEnd(val);
			
			System.out.printf("Enter number of vertices adjacent to %d: ",val);
			int numOfAdjacentVertices = sc.nextInt();
			System.out.printf("Enter data of vertices adjacent to %d(space-separated): ",val);
			for(int j=0; j<numOfAdjacentVertices; j++) {
				int adjacentVertexData = sc.nextInt();
				ll.insertEnd(adjacentVertexData);
			}
			
			// step 2: adding the head of each created link list to arraylist
			adList.add(ll.getHead());
		}
	}
	
	public void bfsNonRecursive(int dataOfStartNode) {
		
		Node node = findNodeByData(dataOfStartNode);
		if(node == null) {
			System.out.println("Node with given data cannot be found.");
			return;
		}
		
	    Queue<Node> queue = new LinkedList<>();
	    // Use Integer to track visited nodes by their data
	    ArrayList<Integer> visited = new ArrayList<>();


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
	
	public void dfsNonRecursive(int dataOfStartNode) {
		
		Node node = findNodeByData(dataOfStartNode);
		if(node == null) {
			System.out.println("Node with given data cannot be found.");
			return;
		}

		Stack<Node> s = new Stack<>();
		ArrayList<Integer> visited = new ArrayList<>();
		
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
	
	public void dfsRecCaller(int startNodeData) {
		ArrayList<Integer> visited = new ArrayList<>();
		dfsRecursive(startNodeData, adList, visited);
	}
	
	// main dfs Recursive function
	private void dfsRecursive(int val, ArrayList<Node> adjList, ArrayList<Integer> vis) {
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
	
	public void bfsRecCaller(int dataOfStartNode) {
		Node node = findNodeByData(dataOfStartNode);
		if(node == null) {
			System.out.println("Node with given data cannot be found.");
			return;
		}
		
	    Queue<Node> queue = new LinkedList<>();
	    // Use Integer to track visited nodes by their data
	    ArrayList<Integer> visited = new ArrayList<>();


	    queue.add(node);
	    visited.add(node.data);
	    bfsRecursive(queue, visited);
	    
	}
	
	private void bfsRecursive(Queue<Node> que, ArrayList<Integer> vis) {
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
	private Node findNodeByData(int data) {
	    for (Node n : adList) {
	        if (n.data == data) {
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
		gh.bfsRecCaller(1);
	}
}

