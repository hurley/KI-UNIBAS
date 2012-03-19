package problems;

import java.util.ArrayList;


public abstract class SearchProblem {
	public abstract State init();
	public abstract boolean isGoal(State state);
	public abstract ArrayList<ActionStatePair> succ(State state);
	public abstract float cost(Action action);
}
