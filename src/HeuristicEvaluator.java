/**
 * Each player will most likely make the best possible move.
 */
public class HeuristicEvaluator implements OthelloEvaluator {
    @Override
    public int evaluate(OthelloPosition position) {
        float value;

        value = MovesComparison(position);
        value *= coinParity(position);
        value *= HasActionInCorner(position);
        value *= hasActionsAroundCorner(position);


        if(!position.toMove())
            return (int)value * -1;

        return (int)value;
    }

    public float MovesComparison(OthelloPosition position){
        int numberOfMoves = position.getMoves().size();
        numberOfMoves %= 10;

        return numberOfMoves;
    }

    public int coinParity(OthelloPosition position){
        int blackCoin = 0;
        int whiteCoin = 0;
        
        for(int x = 1; position.IsWithinBoard(x);x++){
            for(int y = 1; position.IsWithinBoard(y);y++){
                if(position.board[x][y] == 'B')
                    blackCoin++;
                if(position.board[x][y] == 'W')
                    whiteCoin++;
            }
        }
        
        return 100* (whiteCoin/blackCoin);
    }
    
    /**
     * Corner positions are nice.
     */
    public float HasActionInCorner(OthelloPosition position){

        for(OthelloAction action : position.getMoves()){
            int actionX = action.getRow();
            int actionY = action.getColumn();

            int cornerX = 1;
            int cornerY = OthelloPosition.BOARD_SIZE;

            if(actionX == cornerX || actionX == cornerY)
                if(actionY == cornerX || actionY == cornerY)
                    return 3.0f;

        }

        return 1.0f;
    }
    
    /**
     *  If an action is oriented around a corner it gives the oponent
     * the opportunity to place a marker in corner.
     * OZXXZO
     * ZZXXZZ
     * YY##YY
     * ZZXXZZ
     * OZXXZO
     *
     */
    public float hasActionsAroundCorner(OthelloPosition position){
        for(OthelloAction action : position.getMoves()) {
            int actionX = action.getRow();
            int actionY = action.getColumn();

            int lowerSide = 1;
            int upperSide = OthelloPosition.BOARD_SIZE;

            if (actionX == lowerSide || actionX == lowerSide + 1 || actionX == upperSide || actionX == upperSide - 1)
                if (actionY == lowerSide || actionY == lowerSide + 1 || actionY == upperSide || actionY == upperSide - 1)
                    if(actionX == lowerSide || actionX == upperSide)
                        if(actionY == lowerSide || actionY == upperSide)
                            return 0.3f;
        }
        return 1;
    }
}
