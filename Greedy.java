import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Comparator;

class Node{
    String name;
    int weight;
    String parent;
    Node next;

    public Node(String name, int weight) {
        this.name = name;
        this.weight = weight;
        this.parent = "no";
        this.next = null;
    }

    public Node(String name, int weight, String parent){
        this.name = name;
        this.weight = weight;
        this.parent = parent;
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

    public void insertEnd(String name, int weight){
        if(head == null){
            head = new Node(name, weight);
            tail = head;
        }else{
            tail.next = new Node(name, weight);
            tail = tail.next;
        }
    }

    public void displayLL(Node start){
        while(start != null){
            System.out.print(start.name+":"+start.weight+"->");
            start = start.next;
        }
        System.out.println("/");
    }

    public Node getHead(){
        return this.head;
    }
}

public class Greedy extends MyLinkedList{
    int vertices;
    ArrayList<Node> adList = new ArrayList<>();

    Map<String, Integer> dist = new HashMap<>(); // for dijkstra

    Map<String, Boolean> visited = new HashMap<>(); // for prims
    ArrayList<Node> mst = new ArrayList<>();

    public Greedy(){
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
            ll.insertEnd(name, 0);
            System.out.print("How many nodes are adjacent to "+name+": ");
            int adjNum = sc.nextInt();
            System.out.print("Enter name-weight of adjacent nodes: ");
            for(int j=0; j<adjNum; j++){
                String adjName = sc.next();
                int wt = sc.nextInt();
                ll.insertEnd(adjName, wt);
            }
            adList.add(ll.getHead());
        }
    }

    public void prims(String src){

        for(Node n: adList){
            visited.put(n.name, false);
        }
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n->n.weight));
        pq.add(new Node(src, 0));

        int cost = 0;

        while(!pq.isEmpty()){
            Node curr = pq.poll();
            if(visited.get(curr.name)) continue;

            visited.put(curr.name, true);// ONLY CHANGE VISITED OVER HERE
            cost+=curr.weight;

            if(!curr.parent.equals("no")){
                mst.add(new Node(curr.name, curr.weight, curr.parent));
            }

            Node head = getHeadByName(curr.name);
            Node adj = head.next;

            while(adj != null){
                if(!visited.get(adj.name)){
                    pq.add(new Node(adj.name, adj.weight, curr.name));
                }
                adj = adj.next;
            }
        }

        for(Node n: mst){
            System.out.println(n.name+"->"+n.parent+": "+n.weight);
        }
    }

    public void dijkstra(String src){
        for(Node n: adList){
            dist.put(n.name, Integer.MAX_VALUE);
        }
        dist.put(src, 0);

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n->n.weight));
        pq.add(new Node(src, 0));

        while(!pq.isEmpty()){
            Node curr = pq.poll();
            String u = curr.name;
            Node head = getHeadByName(u);
            Node adj = head.next;
            while(adj != null){
                String v = adj.name;
                int weight = adj.weight;
                if(dist.get(v) > dist.get(u)+weight){
                    dist.put(v, dist.get(u)+weight);
                    pq.add(new Node(v, dist.get(v)));
                }
                adj = adj.next;
            }
        }
        for(Map.Entry<String, Integer> d: dist.entrySet()){
            System.out.println(d.getKey()+" "+d.getValue());
        }
    }


    public void displayGraph(){
        for(Node n: adList){
            displayLL(n);
        }
    }

    public Node getHeadByName(String nm){
        for(Node n: adList){
            if(n.name.equals(nm)){
                return n;
            }
        }
        return null;
    }

    public static void main(String args[]){
        Greedy g = new Greedy();
        g.constructGraph();
        g.prims("A");
        g.dijkstra("A");
    }
}