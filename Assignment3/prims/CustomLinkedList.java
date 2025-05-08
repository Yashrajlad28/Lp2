package tools;

public class CustomLinkedList {
	private Node head;
	private Node tail;
	
	public CustomLinkedList() {
		this.head = null;
		this.tail = null;
	}
	
	public void insertEnd(String name, int weight) {
		if(head == null) {
			head = new Node(name, weight);
			tail = head;
		}else {
			tail.next = new Node(name, weight);
			tail = tail.next;
		}
	}
	
	public void displayLinkedList(Node strt) {
		/*
		 * while(strt != tail.next)
		 * This line generates NullPointerException*/
		while(strt != null) {
			System.out.print(strt.name+":"+strt.weight+" -> ");
			strt = strt.next;
		}
		System.out.println("/");
	}
	
	public Node getHead() {
		return this.head;
	}
}
