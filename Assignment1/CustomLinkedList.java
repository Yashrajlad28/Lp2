package tools;

public class CustomLinkedList {
	
	private Node head;
	private Node tail;
	
	public CustomLinkedList() {
		this.head = null;
		this.tail = null;
	}
	
	public void insertEnd(String data) {
		if(head == null) {
			head = new Node(data);
			tail = head;
		}else {
			tail.next = new Node(data);
			tail = tail.next;
		}
	}
	
	public void displayLinkedList(Node strt) {
		/*
		 * while(strt != tail.next)
		 * This line generates NullPointerException*/
		while(strt != null) {
			System.out.print(strt.data+"-> ");
			strt = strt.next;
		}
		System.out.println("/");
	}
	
	public Node getHead() {
		return this.head;
	}
}
