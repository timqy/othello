/**
 * Main class
 */
public class Othello {

    public static float time;

    public static void main(String[] args) {
        if(args.length != 2){
            System.err.println("Othello runs on two arguments");
            System.exit(-1);
        }


        if(args[0].length() != 65 &&
                (args[0].charAt(0) == 'W' || args[1].charAt(0) == 'B')){
            System.err.println("First input should contain 65 characters and start with W or B");
            System.exit(-1);
        }

        time = Float.parseFloat(args[1]);

        OthelloPosition othelloBoard = new OthelloPosition(args[0]);
        OthelloAlgorithm alphaBetaSearch = new AlphaBeta((int)time);
        alphaBetaSearch.setEvaluator(new HeuristicEvaluator());

        alphaBetaSearch.evaluate(othelloBoard).print();
    }
}
