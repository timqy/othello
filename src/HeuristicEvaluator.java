import javax.print.attribute.standard.PDLOverrideSupported;

/**
 * Each player will most likely make the best possible move.
 */
public class HeuristicEvaluator implements OthelloEvaluator {

    private static int moveabilityWeight = 4;
    private static int actionInCornerWeight = 10000;
    private static int actionAroundCornerWeight = -400;
    private static int coinParityWeight = 100;


    @Override
    public int evaluate(OthelloPosition position) {
        int value = 0;

        value += MovesComparison(position);
        value += coinParity(position);
        value += HasActionInCorner(position);
        value += hasActionsAroundCorner(position);

        if(!position.toMove())
            return value * -1;

        return value;
    }

    public int MovesComparison(OthelloPosition position){
        return position.getMoves().size() * moveabilityWeight;
    }

    public int PotentialMoves(OthelloPosition position){
        int potentialMoves = 0;

        for(int x = 1; position.IsWithinBoard(x); x++){
            for(int y = 1; position.IsWithinBoard(y);y++){
                if(position.board[x][y] == position.PlayerColor(!position.toMove())){
                    if(SearchForPotentialMoves(position,x,y))
                        potentialMoves++;
                }
            }
        }
        return potentialMoves;
    }


    private boolean SearchForPotentialMoves(OthelloPosition position,int x, int y){
        for(int i = x -1; i <= 1; i++){
            for (int k = y -1; k <=1; k++){
                if(position.IsWithinBoard(i) && position.IsWithinBoard(k)){
                    if(position.board[i][k] == 'E'){
                        return true;
                    }
                }
            }
        }
        return false;
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

        if(blackCoin == 0 && position.toMove())
            return 0;
        if(whiteCoin == 0 && !position.toMove())
            return 0;

        if(position.toMove()){
            return coinParityWeight * (whiteCoin/blackCoin);

        }

        return coinParityWeight * (blackCoin/whiteCoin);
    }
    
    /**
     * Corner positions are nice.
     */
    public int HasActionInCorner(OthelloPosition position){

        for(OthelloAction action : position.getMoves()){
            int cornerX = 1;
            int cornerY = OthelloPosition.BOARD_SIZE;

            if(action.getRow() == cornerX || action.getRow() == cornerY)
                if(action.getColumn() == cornerX || action.getColumn() == cornerY)
                    return actionInCornerWeight;
        }

        return 0;
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
    public int hasActionsAroundCorner(OthelloPosition position){
        for(OthelloAction action : position.getMoves()) {
            int actionX = action.getRow();
            int actionY = action.getColumn();

            int low = 1;
            int high = OthelloPosition.BOARD_SIZE;

            if (actionX == low || actionX == (low + 1) || actionX == high || actionX == (high - 1)){
                if (actionY == low || actionY == (low + 1) || actionY == high || actionY == (high - 1)) {
                    if (!((actionX == low || actionX == high) && (actionY == low || actionY == high))) {
                        return actionAroundCornerWeight;
                    }
                }
            }
        }
        return 0;
    }
}
