package solvers;

import problems.Action;
import problems.State;

public class SearchNode {
	State state;
	float cost;
	SearchNode parent;
	Action action;
	public SearchNode(State initialState) {
		this.state=initialState;
		cost=0;
		parent=null;
		action=null;
	}
	public SearchNode(State state, float cost, SearchNode parent,Action action) {
		this.state=state;
		this.cost=cost;
		this.parent=parent;
		this.action=action;
	}
	public float cost() {
		return cost;
	}
	public State getState() {
		return state;
	}
	public String getDescription() {
		if (parent!=null) {
			return action.getDescription()+" -> "+state.getDescription()+" cost: "+cost;
			} else {
			return	"initial state: "+state.getDescription();
			}		
	}

}
