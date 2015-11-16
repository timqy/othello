/**
 * Main class
 */
public class Othello {

    public static void main(String[] args) {
        OthelloAlgorithm algorithm = new AlphaBeta();
        OthelloEvaluator evaluator = new HeuristicEvaluator();
        algorithm.setEvaluator(evaluator);
        algorithm.setSearchDepth(5);

    }
}
