package solvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import problems.ActionStatePair;
import problems.SearchProblem;
import problems.State;

public class AStarSearch {
	private SearchProblem problem;
	private SearchNode current;
	private PriorityQueue<SearchNode> frontier; // open nodes, sorted according to fValues
	private HashMap<String, SearchNode> closed;
	private int totalNodeCounter; // only for debugging/stats
	private int visitedNodeCounter; //only for debugging/stats
	private int expandedNodeCounter; //only for debugging/stats

	public AStarSearch(SearchProblem problem) {
		this.problem = problem;
		SearchNode init = new SearchNode(problem.init()); //searchNode with initial state of problem
		frontier = new PriorityQueue<SearchNode>(50, new SearchNodeFValueComparator()); 
		frontier.add(init);
		closed = new HashMap<String, SearchNode>();
		this.totalNodeCounter = 1;
		this.expandedNodeCounter = 0;
		this.visitedNodeCounter = 0;
	}

	public void solve() {
		while (frontier.size() > 0) {
			current = frontier.poll(); //retrieve the node with minimal fValue
			visitedNodeCounter++; //just for debugging/stats
			if (!closed.containsKey(current.state.getId())) {
				closed.put(current.state.getId(), current);
				expandedNodeCounter++; //just for debugging/stats
				if (problem.isGoal(current.getState())) {
					solution(current);
					return;
				}
				ArrayList<ActionStatePair> expansion = problem.succ(current.getState());
				// insert expanded nodes in frontier (if h < inf)
				insertExpansion(expansion);
			}	
		}
		failure();
	}
	
	// inserts expanded nodes in frontier (if h < inf)
	private void insertExpansion(ArrayList<ActionStatePair> expansion) {
		for (int i = 0; i < expansion.size(); i++) {
			ActionStatePair pair = expansion.get(i);
			State newState = pair.state;
			float h = problem.getH(pair.state);
			if (h >= 0) { // assumes infinity to be encoded with values < 0
				float newCost = current.cost() + problem.cost(pair.action); //compute new cost
				float  f = newCost + h;
				// new node with state, cost, fValue, pointer to parent node (=current), and action
				SearchNode newNode = new SearchNode(newState, newCost, f, current, pair.action);
				frontier.add(newNode);
			}
			totalNodeCounter++; //just for debugging/stats
		}
	}

	//prints stats for a solution
	private void solution(SearchNode endNode) {
		System.out.println("found solution with total cost " + endNode.cost);
		System.out.println("total generated Nodes: " + totalNodeCounter);
		System.out.println("# visited Nodes: " + visitedNodeCounter);
		System.out.println("# expanded Nodes: " + expandedNodeCounter);
		System.out.println("states and actions in REVERSE order:");
		SearchNode n = endNode;
		while (n != null) {
			System.out.println(n.getDescription());
			n = n.parent;
		}
	}

	//prints stats for a failure
	private void failure() {
		System.out.println("no solution found");
		System.out.println("total generated Nodes: " + totalNodeCounter);
		System.out.println("# expanded Nodes: " + expandedNodeCounter);
		System.out.println("# visited Nodes: " + visitedNodeCounter);

	}

}
