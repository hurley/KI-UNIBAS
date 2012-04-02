import problems.SearchProblem;
import problems.TravellingSalesmanProblem;
import solvers.AStarSearch;
import solvers.UniformCostSearch;


public class Main {

	/**
	 * @param args[0] 	'-f' read problem from file 
	 * 					|| '-r' generate random TSP instance 
	 * 					|| '-d' generate deterministic TSP instance (for better comparability of different instances of different sizes)
	 * 						(by deterministic we mean, that the problems generated are always the same problems)
	 * @param args[1] 	if args[0]=='-f'
	 * 						path to problem file
	 * 					if args[0]== ('-r' || '-d')
	 * 						# of cities
	 */
	public static void main(String[] args) {
		SearchProblem tsp=null;
		if (args[0].equals("-f")) {
			tsp = new TravellingSalesmanProblem(args[1]);
		} else if (args[0].equals("-r")) {
			tsp = new TravellingSalesmanProblem(true,Integer.parseInt(args[1]));
		}else if (args[0].equals("-d")) {
			tsp = new TravellingSalesmanProblem(false,Integer.parseInt(args[1]));
		}
	//	UniformCostSearch ucs = new UniformCostSearch(tsp);
		AStarSearch aStar = new AStarSearch(tsp);
		
		long t1=System.currentTimeMillis();
		aStar.solve();
		//ucs.solve();
		long dt=System.currentTimeMillis()-t1;
		System.out.println(dt/1000.);
		
		
	}

}
