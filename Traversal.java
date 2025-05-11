import java.util.ArrayList;
import java.util.Scanner;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;

class Node{
    String name;
    Node next;

    public Node(String name) {
        this.name = name;
        this.next = null;
    }
}

class MyLinkedList{
    Node head;
    Node tail;

    public MyLinkedList(){
        this.head = null;
        this.tail = null;
    }

    public void insertEnd(String name){
        if(head == null){
            head = new Node(name);
            tail = head;
        }else{
            tail.next = new Node(name);
            tail = tail.next;
        }
    }

    public void displayLL(Node start){
        while(start != null){
            System.out.print(start.name+"->");
            start = start.next;
        }
        System.out.println("/");
    }

    public Node getHead(){
        return this.head;
    }
}

public class Traversal extends MyLinkedList{
    int vertices;
    ArrayList<Node> adList = new ArrayList<>();

    public Traversal(){
        this.vertices = 0;
    }

    public void constructGraph(){
        System.out.print("Enter number of vertices: ");
        Scanner sc = new Scanner(System.in);
        vertices = sc.nextInt();
        for(int i=0; i<vertices; i++){
            System.out.print("Enter data of any vertex: ");
            String name = sc.next();
            MyLinkedList ll = new MyLinkedList();
            ll.insertEnd(name);
            System.out.print("How many nodes are adjacent to "+name+": ");
            int adjNum = sc.nextInt();
            System.out.print("Enter name of adjacent nodes: ");
            for(int j=0; j<adjNum; j++){
                String adjName = sc.next();
                ll.insertEnd(adjName);
            }
            adList.add(ll.getHead());
        }
    }

    public void dfsRecCaller(String name){
        ArrayList<String> visited = new ArrayList<>();
        visited.add(name); // THIS IS IMPORTANT
        System.out.print("DFS Recursive: ");
        dfsRecursive(name, visited);
        System.out.println();
    }

    public void dfsRecursive(String name, ArrayList<String> visited){
        System.out.print(name+" ");
        Node head = getHeadByName(name);
        Node adj = head.next;
        while(adj != null){
            if(!visited.contains(adj.name)){
                visited.add(adj.name);
                dfsRecursive(adj.name, visited);
            }
            adj = adj.next;
        }
    }

    public void bfsNonRecursive(String name){
        Node head = getHeadByName(name);
        Queue<Node> q = new LinkedList<>();
        ArrayList<String> visited = new ArrayList<>();
        visited.add(name);
        q.add(head);
        System.out.print("BFS Non-Recursive: ");
        while(!q.isEmpty()){
            Node curr = q.poll();
            System.out.print(curr.name+" ");
            Node adj = curr.next;
            while(adj != null){
                if(!visited.contains(adj.name)){
                    visited.add(adj.name);
                    q.add(getHeadByName(adj.name));
                }
                adj = adj.next;
            }
        }
        System.out.println();
    }

    public void dfsNonRecursive(String name){
        Node head = getHeadByName(name);
        Stack<Node> s = new Stack<>();
        ArrayList<String> visited = new ArrayList<>();
        visited.add(name);
        s.add(head);
        System.out.print("DFS Non-Recursive: ");
        while(!s.isEmpty()){
            Node curr = s.pop();
            System.out.print(curr.name+" ");
            Node adj = curr.next;
            while(adj != null){
                if(!visited.contains(adj.name)){
                    visited.add(adj.name);
                    s.add(getHeadByName(adj.name));
                }
                adj = adj.next;
            }
        }
        System.out.println();
    }

    public void bfsRecCaller(String name){
        Node head = getHeadByName(name);
        Queue<Node> q = new LinkedList<>();
        ArrayList<String> visited = new ArrayList<>();
        visited.add(name);
        q.add(head);
        System.out.print("BFS Recursive: ");
        bfsRecursive(q, visited);
        System.out.println();
    }

    public void bfsRecursive(Queue<Node> q, ArrayList<String> visited){
        if(q.isEmpty()){
            return;
        }else{
            Node curr = q.poll();
            System.out.print(curr.name+" ");
            Node adj = curr.next;
            while(adj != null){
                if(!visited.contains(adj.name)){
                    visited.add(adj.name);
                    q.add(getHeadByName(adj.name));
                }
                adj = adj.next;
            }
            bfsRecursive(q, visited);
        }
    }

    public Node getHeadByName(String nodeName){
        for(Node n: adList){
            if(n.name.equals(nodeName)){
                return n;
            }
        }
        return null;
    }

    public void displayGraph(){
        for(Node n: adList){
            displayLL(n);
        }
    }

    public static void main(String args[]){
        Traversal t = new Traversal();
        t.constructGraph();
        t.displayGraph();
        t.dfsRecCaller("A");
        t.bfsRecCaller("A");
        t.dfsNonRecursive("A");
        t.bfsNonRecursive("A");
    }
}