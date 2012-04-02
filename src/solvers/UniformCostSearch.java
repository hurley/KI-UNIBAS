package solvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import problems.ActionStatePair;
import problems.SearchProblem;
import problems.State;

public class UniformCostSearch {
	private SearchProblem problem;
	private SearchNode current;
	private PriorityQueue<SearchNode> frontier; // open nodes, sorted according to path-cost
	private HashMap<String, SearchNode> idMap; // open nodes, used for identifying duplicates, (the String is a unique id for a state)
	private int totalNodeCounter; // only for debugging/stats
	private int visitedNodeCounter; //only for debugging/stats
	private int distinctNodeCounter; //only for debugging/stats
	private int duplicateCounter; //only for debugging/stats

	public UniformCostSearch(SearchProblem problem) {
		this.problem = problem;
		SearchNode init = new SearchNode(problem.init()); //searchNode with initial state of problem
		frontier = new PriorityQueue<SearchNode>(50, new SearchNodeCostComparator()); 
		idMap = new HashMap<String, SearchNode>();
		frontier.add(init);
		idMap.put(init.state.getId(), init);
		this.totalNodeCounter = 1;
		this.distinctNodeCounter = 1;
		this.duplicateCounter = 0;
		this.visitedNodeCounter = 0;
	}

	public void solve() {
		while (true) {
			current = frontier.poll(); //retrieve the node with minimal path-cost
			idMap.remove(current.state.getId()); //keep idMap synchronized with frontier
			visitedNodeCounter++;
			if (current == null) {
				failure();
				return;
			}
			if (problem.isGoal(current.getState())) {
				solution(current);
				return;
			}
			// expand the current node
			ArrayList<ActionStatePair> expansion = problem.succ(current
					.getState());
			insertExpansion(expansion); //insert expanded nodes in frontier (if not duplicates)
			totalNodeCounter += expansion.size();
		}
	}
	
	// inserts expanded nodes in frontier and idMap, (if not duplicates)
	private void insertExpansion(ArrayList<ActionStatePair> expansion) {
		for (int i = 0; i < expansion.size(); i++) {
			ActionStatePair pair = expansion.get(i);
			State newState = pair.state;
			float newCost = current.cost() + problem.cost(pair.action); //compute new cost
			// new node with state, cost, pointer to parent node (=current), and action
			SearchNode newNode = new SearchNode(newState, newCost, current, 
					pair.action);
			// check if the new state has been seen before
			if (idMap.containsKey(newState.getId())) {
				SearchNode oldNode=idMap.get(newState.getId());
				// if newNode has smalller cost than oldNode, update cost, else do nothing/throw away newNode
				if (oldNode.cost() > newNode.cost()) {
					//instead of updating the cost of oldNode, we delete oldNode, and insert newNode,in order to keep fronier sorted
					frontier.remove(oldNode);
					idMap.remove(newState.getId());
					frontier.add(newNode);
					idMap.put(newState.getId(), newNode);
				}
				duplicateCounter++;
				// no duplicate -> just add newNode
			} else {
				frontier.add(newNode);
				idMap.put(newState.getId(), newNode);
				distinctNodeCounter++;
			}
		}
	}

	//prints stats for a solution
	private void solution(SearchNode endNode) {
		System.out.println("found solution with total cost " + endNode.cost);
		System.out.println("total generated Nodes: " + totalNodeCounter);
		System.out.println("# duplicates: " + duplicateCounter);
		System.out.println("# distinct Nodes: " + distinctNodeCounter);
		System.out.println("# visited Nodes: " + visitedNodeCounter);
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
		System.out.println("# duplicates: " + duplicateCounter);
		System.out.println("# distinct Nodes: " + distinctNodeCounter);
		System.out.println("# visited Nodes: " + visitedNodeCounter);

	}

}
