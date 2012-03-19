package problems;

public class TSPAction extends Action {
	public int from;
	public int to;
	public TSPAction(int from, int to) {
		this.from=from;
		this.to=to;
	}
	@Override
	public String getDescription() {
		return "Action: from: "+from+" to: "+to;
	}

}
