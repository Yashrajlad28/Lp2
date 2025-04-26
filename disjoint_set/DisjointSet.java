package tools;

import java.util.HashMap;
import java.util.Map;

public class DisjointSet {
	Map<String, Integer> rank = new HashMap<>();
	Map<String, String> parent = new HashMap<>(); // node - parent
	
	// public DisjointSet(ArrayList<Node> adList) {
		// initialize all vertices with rank 0
		// initialize all vertices with parent as themselves
		
	// }
	
	public DisjointSet() {
		String arr[] = {"A","B","C","D","E","F","G"};
		for(String s: arr) {
			rank.put(s, 0);
			parent.put(s, s);
		}
		
		
	}
	
	public void display() {
		System.out.println("\nDisplaying Parent");
		for(Map.Entry<String, String> p : parent.entrySet()) {
			System.out.print(p.getKey()+":"+p.getValue()+", ");
		}
	}
	
	public String findUltimateParent(String nodeName) {
		String prnt = parent.get(nodeName);
		if(prnt.equals(nodeName)) {
			return nodeName;
		}
		/*
		      A
		     / \ 
		    E   B
		         \
		          C
		   if given node is C, then ultimate parent of C is not B but it is A
		   to obtain that we again need to recursively call findUltimateParent("B")
		*/
		String ultParent = findUltimateParent(prnt);
		parent.put(prnt, ultParent);
		return ultParent;
	}
	
	public void unionByRank(String u, String v) {
		String ultPrntU = findUltimateParent(u);
		String ultPrntV = findUltimateParent(v);
		
		if(ultPrntU.equals(ultPrntV)) return; // belong to same component
		
		if(rank.get(ultPrntU) < rank.get(ultPrntV)) {
			// always attach smaller rank to bigger rank
			parent.put(ultPrntU, ultPrntV);
		}else if(rank.get(ultPrntV) < rank.get(ultPrntU)) {
			parent.put(ultPrntV, ultPrntU);
		}else {
			// if both have same rank
			parent.put(ultPrntV, ultPrntU);
			int newRank = rank.get(ultPrntU)+1;
			rank.put(ultPrntU, newRank);
		}
	}
	
	public static void main(String args[]) {
		DisjointSet dj = new DisjointSet();
		dj.unionByRank("A", "B");
		dj.unionByRank("B", "C");
		System.out.println(dj.findUltimateParent("C")); // A
		System.out.println(dj.findUltimateParent("B")); // A
		dj.unionByRank("D", "E");
		dj.unionByRank("F", "G");
		dj.unionByRank("E", "F");
		System.out.println(dj.findUltimateParent("G")); // D 
		System.out.println(dj.findUltimateParent("E")); // D
		dj.unionByRank("C", "F");
		System.out.println(dj.findUltimateParent("C"));
		System.out.println(dj.findUltimateParent("F"));
		System.out.println(dj.findUltimateParent("A"));
		dj.display();
	}
	
}
/*
A
A
D
D
D
D
D

Displaying Parent
A:D, B:A, C:A, D:D, E:D, F:D, G:F, 
*/
