package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DisjointSet {
	Map<String, Integer> rank = new HashMap<>();
	Map<String, String> parent = new HashMap<>();
	
	// Initialization
	public DisjointSet(ArrayList<Edge> graph) {
		for(Edge e: graph) {
			rank.put(e.src, 0);
			rank.put(e.dest, 0);
			parent.put(e.src, e.src);
			parent.put(e.dest, e.dest);
		}
	}
	
//	public void display() {
//		System.out.println("Displaying Rank");
//		for(Map.Entry<String, Integer> r : rank.entrySet()) {
//			System.out.print(r.getKey()+":"+r.getValue()+", ");
//		}
//		
//		System.out.println("\nDisplaying Parent");
//		for(Map.Entry<String, String> p : parent.entrySet()) {
//			System.out.print(p.getKey()+":"+p.getValue()+", ");
//		}
//	}
	
	public String findUltimateParent(String nodeName) {
		String prnt = parent.get(nodeName);
		if(prnt.equals(nodeName)){
			return prnt;
		}
		
		String ultiPrnt = findUltimateParent(prnt);
		parent.put(prnt, ultiPrnt);
		return ultiPrnt;
	}
	
	public void findUnionByRank(String u, String v) {
		String ultiPrntU = findUltimateParent(u);
		String ultiPrntV = findUltimateParent(v);
		
		if(ultiPrntU.equals(ultiPrntV)) return;
		
		if(rank.get(ultiPrntU) < rank.get(ultiPrntV)) {
			parent.put(ultiPrntU, ultiPrntV);
		}else if(rank.get(ultiPrntV) < rank.get(ultiPrntU)) {
			parent.put(ultiPrntV, ultiPrntU);
		}else {
			// attaching V to U
			parent.put(ultiPrntV, ultiPrntU);
			// so rank of U will increase by 1
			int newRank = rank.get(ultiPrntU)+1;
			rank.put(ultiPrntU, newRank);
		}
	}
}
