import java.util.List;

/**
 * Alpha beta search
 */
public class AlphaBeta implements OthelloAlgorithm {

    private OthelloEvaluator evaluator;
    private int maxDepth;
    public AlphaBeta(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void setEvaluator(OthelloEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public OthelloAction evaluate(OthelloPosition position) {
        OthelloAction bestAction = null;
        int maxValue = Integer.MIN_VALUE;

        if(!position.toMove())
            maxValue = Integer.MAX_VALUE;

        for(OthelloAction action : position.getMoves()) {

            try {
                action.setValue(alphaBetaSearch(position.makeMove(action), 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
            } catch (IllegalMoveException e) {
                /** Continue */
                e.printStackTrace();
            }

            if (position.toMove() && maxValue < action.getValue()) {
                bestAction = action;
                maxValue = action.getValue();
            } else if (!position.toMove() && maxValue > action.getValue()) {
                bestAction = action;
                maxValue = action.getValue();
            }
        }

        if(bestAction != null)
            return bestAction;

        /** Pass */
        return new OthelloAction(1,1,true);
    }

    private int alphaBetaSearch(OthelloPosition position,int depth,int alpha,int beta) throws IllegalMoveException {
        List<OthelloAction> moves;

        if(depth == maxDepth || (moves = position.getMoves()).isEmpty()){
            return evaluator.evaluate(position);
        }

        int value;
        if(position.toMove()){
            /** Max player */
            value = Integer.MIN_VALUE;

            for(OthelloAction action : moves) {
                value = Math.max(value,alphaBetaSearch(position.makeMove(action),depth+1,alpha,beta));

                /** beta cutoff */
                if(value >= beta)
                    break;

                alpha = Math.max(alpha,value);
            }
        } else {
            /** Min Player */
            value = Integer.MAX_VALUE;

            for(OthelloAction action : moves) {
                value = Math.min(value, alphaBetaSearch(position.makeMove(action), depth + 1, alpha, beta));

                /** alpha cutoff */
                if(value <= alpha)
                    break;

                beta = Math.min(beta, value);
            }
        }
        return value;
    }

    @Override
    public void setSearchDepth(int depth) {
        this.maxDepth = depth;
    }
}
