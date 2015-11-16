import javafx.geometry.Pos;

/**
 * Alpha beta search
 */
public class AlphaBeta implements OthelloAlgorithm {

    private OthelloEvaluator evaluator;
    private int maxDepth;

    @Override
    public void setEvaluator(OthelloEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public OthelloAction evaluate(OthelloPosition position) {
        OthelloAction bestAction = null;
        int bestValue = Integer.MIN_VALUE;

        try {
            for(OthelloAction action : position.getMoves()) {
                int alphabetaValue = alphaBetaSearch(position.makeMove(action), 0, 0);
                System.out.println("alphabeta value : " + alphabetaValue);
                if (bestValue < alphabetaValue) {
                    bestAction = action;
                    bestValue = alphabetaValue;
                }
            }
        } catch (IllegalMoveException e) {
                e.printStackTrace();
            }
        return bestAction;
    }

    private int alphaBetaSearch(OthelloPosition position,int depth,int value) throws IllegalMoveException {
        if(depth >= maxDepth)
            return value;

        int bestValue = evaluator.evaluate(position);
        for(OthelloAction action : position.getMoves()){
            if(Math.abs(bestValue) == action.getValue()){
                value += alphaBetaSearch(position.makeMove(action),depth++,bestValue);
            }

        }
        return value;
    }

    /**
    private int MaxValue(OthelloPosition position,int depth,int alpha,int beta) throws IllegalMoveException {
        if(depth >= maxDepth)
            return alpha;

        int value = Integer.MIN_VALUE;
        for(OthelloAction action : position.getMoves()){
            value = Math.max(value,MinValue(position.makeMove(action),depth++,alpha,beta));
            if(value >= beta)
                return value;

            alpha = Math.max(alpha,value);
        }
        return value;
    }

    private int MinValue(OthelloPosition position,int depth, int alpha,int beta) throws IllegalMoveException {
        if(depth >= maxDepth)
            return beta;

        int value = Integer.MAX_VALUE;
        for(OthelloAction action : position.getMoves()){
            value = Math.min(value,MaxValue(position.makeMove(action),depth++,alpha,beta));
            if(value <= alpha)
                return value;
            beta = Math.min(beta,value);
        }
        return value;
    }
    */

    @Override
    public void setSearchDepth(int depth) {
        this.maxDepth = depth;
    }
}
