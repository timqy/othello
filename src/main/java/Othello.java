/**
 * Main class
 */
public class Othello {


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

        int depth = calculateDepth(Float.parseFloat(args[1]));

        OthelloPosition othelloBoard = new OthelloPosition(args[0]);
        OthelloAlgorithm alphaBetaSearch = new AlphaBeta(depth);
        alphaBetaSearch.setEvaluator(new HeuristicEvaluator());

        alphaBetaSearch.evaluate(othelloBoard).print();
    }

    /**
     *
     * @param time the time given
     * @return searchDepth
     */
    public static int calculateDepth(float time){
        if(time < 2)
            return 3;
        if(time < 3)
            return 5;
        if(time < 30)
            return 6;
        if(time < 61)
            return 7;
        if(time < 399)
            return 8;

        return 9;
    }
}
