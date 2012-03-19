package solvers;

import java.util.ArrayList;
import java.util.PriorityQueue;

import problems.ActionStatePair;
import problems.SearchProblem;
import problems.State;


public class UniformCostSearch {
	private SearchProblem problem;
	private SearchNode current;
	private PriorityQueue<SearchNode> frontier;
	
	public UniformCostSearch(SearchProblem problem) {
		this.problem=problem;
		this.current=new SearchNode(problem.init());
		frontier=new PriorityQueue<SearchNode>(50, new SearchNodeComparator());
		frontier.add(current);
	}
	public void solve() {
		while (true) {
		current=frontier.poll();
		if (current==null) {
			failure();
			return;
		}
		if (problem.isGoal(current.getState())) {
			solution(current);
			return;
		}
		ArrayList<ActionStatePair> expansion=problem.succ(current.getState());
		insertExpansion(expansion);
		}
	}
	private void insertExpansion(ArrayList<ActionStatePair> expansion) {
		for (int i=0; i<expansion.size();i++) {
			ActionStatePair pair=expansion.get(i);
			State newState=pair.state;
			float newCost=current.cost()+problem.cost(pair.action);
			SearchNode newNode=new SearchNode(newState,newCost,current,pair.action);
			frontier.add(newNode);
		}
	}
	private void solution(SearchNode endNode) {
		System.out.println("found solution with total cost "+endNode.cost);
		System.out.println("states and actions in REVERSE order:");	
		SearchNode n=endNode;
		while (n!=null) {
			System.out.println(n.getDescription());
			n=n.parent;
		}
	}
	private void failure() {
		System.out.println("no solution found");	
	}

}
 