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
        int maxValue = Integer.MIN_VALUE;

        try {
            for(OthelloAction action : position.getMoves()) {
                int alphabetaValue = alphaBetaSearch(position.makeMove(action), 0,Integer.MIN_VALUE,Integer.MAX_VALUE);
                System.out.println("Action (" +action.getRow()+ "," + action.getColumn() + ") " + alphabetaValue);
                if (maxValue < alphabetaValue) {
                    bestAction = action;
                    maxValue = alphabetaValue;
                }
            }
        } catch (IllegalMoveException e) {
                e.printStackTrace();
            }
        return bestAction;
    }

    private int alphaBetaSearch(OthelloPosition position,int depth,int alpha,int beta) throws IllegalMoveException {
        if(depth == maxDepth || position.getMoves().isEmpty()){
            return evaluator.evaluate(position);
        }

        int value;
        if(position.toMove()){
            /** Max player */
            value = Integer.MIN_VALUE;

            for(OthelloAction action : position.getMoves()) {
                value = Math.max(value,alphaBetaSearch(position.makeMove(action),depth+1,alpha,beta));
                alpha = Math.max(alpha,value);

                /** beta cutoff */
                if(alpha >= beta)
                    break;
            }


        } else {
            /** Min Player */
            value = Integer.MAX_VALUE;

            for(OthelloAction action : position.getMoves()) {
                value = Math.min(value,alphaBetaSearch(position.makeMove(action),depth+1, alpha,beta));
                beta = Math.min(beta,value);

                /** alpha cutoff */
                if(beta <= alpha)
                    break;
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
