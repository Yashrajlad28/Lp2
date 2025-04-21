package tools;

public class Node {
	public String name;
	public int weight;
	public String parent;
	public Node next;
	
	public Node(String name, int weight) {
		this.name = name;
		this.weight = weight;
		this.parent="no";
		this.next = null;
	}
	
	public Node(String name, int weight, String parent) {
		this.name = name;
		this.weight = weight;
		this.parent= parent;
		this.next = null;
	}
}
