package solvers;

import java.util.Comparator;


public class SearchNodeFValueComparator implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		
			if (((SearchNode) o1).f()  < ((SearchNode) o2).f())
				return -1;
			if (((SearchNode) o1).f()  > ((SearchNode) o2).f())
				return 1;
			else
				return 0;
	}

}
