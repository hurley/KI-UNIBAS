package problems;

import java.util.ArrayList;
import java.util.Iterator;


public class TSPState extends State {
	private ArrayList<Integer> visited;
	private int current;
	
	public TSPState(int initialCity) {
		this.current=initialCity;
		visited=new ArrayList<Integer>();
		visited.add(Integer.valueOf(current));
	}
	
	public TSPState(int current, ArrayList visited) {
		this.current=current;
		this.visited=visited;
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

}
