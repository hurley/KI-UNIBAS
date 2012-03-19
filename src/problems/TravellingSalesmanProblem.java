package problems;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class TravellingSalesmanProblem extends SearchProblem {
	private int size;
	private float[][] dist;
	private int initial;
	
	public TravellingSalesmanProblem(String problemPath) {
		loadfromFile(problemPath);
	}

	public TravellingSalesmanProblem(boolean random, int size) {
		generateProblem(random, size);
	}

	private void generateProblem(boolean random, int size) {
		this.size=size;
		dist= new float[size][size];
		for (int i = 0; i < size; i++) {
			dist[i][i]=0;
		}
		Random generator=null;
		if (random) {
			generator=new Random();
		} else {
			generator=new Random(4713);			
		}
		for (int i = 0; i < size-1; i++) {
			for (int j = i+1; j < size; j++) {
				dist[i][j]=generator.nextInt(211);
				dist[j][i]=dist[i][j];
			}
		}
	}

	// reads in and sets size and dist[][] from a file
	// first line of file : #cities
	// following (#cities)^2 lines : distances 
	// cities with no connection should have a distance of -1
	private void loadfromFile(String problemPath) {
		 try{
			  DataInputStream in = new DataInputStream(new FileInputStream(problemPath));
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String line;
			  boolean firstLine=true;
			  int i=0;
			  int j=0;
			  while ((line = br.readLine()) != null)   {
				  if (line.length() > 0 && !(line.startsWith("//") || line.startsWith(" ") || line.startsWith(System.getProperty("line.separator")))) {
					  if (firstLine) {
						  size=Integer.parseInt(line);
						  dist= new float[size][size];
						  firstLine=false;
					  } else {
						  if (j>=size) {
							  j=0;
							  i++;
						  }
						  dist[i][j]=Float.parseFloat(line);
						  j++;
					  }
				  }
			  }
			  in.close();
			  } catch (Exception e){
				  System.out.println("Error reading problemfile");
				  e.printStackTrace();
				  System.exit(1);
			  }
	}
	
	@Override
	public
	State init() {
		initial = 0;
		return new TSPState(0);
	}

	@Override
	public
	boolean isGoal(State state) {
		if (((TSPState) state).getVisited().size()==size+1) {
			return true;
		}
		return false;
	}

	@Override
	public
	ArrayList<ActionStatePair> succ(State state) {
		ArrayList<ActionStatePair> successors=new ArrayList<ActionStatePair>();
		int current=((TSPState) state).getCurrent();
		ArrayList visited = ((TSPState) state).getVisited();
		for (int j = 0; j < size; j++) {
				if (dist[current][j] > 0) {
				if (allowed(visited,j)) {
				TSPAction action=new TSPAction(current,j);
				ArrayList<Integer> newVisited = new ArrayList<Integer>(visited);
				newVisited.add(Integer.valueOf(j));
				TSPState newState=new TSPState(j,newVisited);
				successors.add(new ActionStatePair(action, newState));
				}
			}
		}
		return successors;
	}



	private boolean allowed(ArrayList visited, int destination) {
		if (visited.contains(Integer.valueOf(destination))) {
			if (destination == initial && visited.size() == size) {
				return true;
			}
			return false;
		}
		return true;
	}

	@Override
	public
	float cost(Action action) {
		return dist[((TSPAction) action).from][((TSPAction) action).to];
	}

}
