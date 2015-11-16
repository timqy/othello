/**
 * Main class
 */
public class Othello {

    public static float time;

    public static void main(String[] args) {
        if(args.length != 3){
            System.err.println("Othello runs on two arguments");
            System.exit(-1);
        }


        if(args[1].length() != 65 &&
                (args[1].charAt(0) == 'W' || args[1].charAt(0) == 'B')){
            System.err.println("First input should contain 65 characters and start with W or B");
            System.exit(-1);
        }

        OthelloAlgorithm alphaBetaSearch = new AlphaBeta();
        OthelloEvaluator heuristicEvaluator = new HeuristicEvaluator();
        alphaBetaSearch.setEvaluator(heuristicEvaluator);

        time = Float.parseFloat(args[2]);

        OthelloPosition othelloBoard = new OthelloPosition(args[1]);

        alphaBetaSearch.evaluate(othelloBoard);

    }
}
