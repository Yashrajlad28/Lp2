package tools;

public class Node {
	public String name;
	public int weight;
	public Node next;
	
	public Node(String name, int weight) {
		this.name = name;
		this.weight = weight;
		this.next = null;
	}
}
