package problems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;


public class TSPState extends State {
	private ArrayList<Integer> visited;
	private int current;
	private String id;
	
	public TSPState(int initialCity) {
		this.current=initialCity;
		visited=new ArrayList<Integer>();
		visited.add(Integer.valueOf(current));
		createId();
	}

	public TSPState(int current, ArrayList visited) {
		this.current=current;
		this.visited=visited;
		createId();
	}

	private void createId() {
		id=""+current;
		PriorityQueue<Integer> p=new PriorityQueue<Integer>();
		for (int i=0; i<visited.size();i++) {
			p.add(visited.get(i));
		}
		Integer city=p.poll();
		while (city!=null) {
			id+="."+city.toString();
			city=p.poll();	
		}
	}
	
	public ArrayList getVisited() {
		return visited;
	}

	public int getCurrent() {
		return current;
	}

	@Override
	public String getDescription() {
		String desc="State: current city: "+current+" visited: ";
		for (int i = 0; i < visited.size(); i++) {
			desc+= " "+visited.get(i);
		}
		return desc;
	}

	@Override
	public String getId() {
		return id;
	}

}
