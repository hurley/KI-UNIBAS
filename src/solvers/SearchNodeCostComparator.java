package solvers;

import java.util.Comparator;


public class SearchNodeCostComparator implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		
			if (((SearchNode) o1).cost()  < ((SearchNode) o2).cost())
				return -1;
			if (((SearchNode) o1).cost()  > ((SearchNode) o2).cost())
				return 1;
			else
				return 0;
	}

}
