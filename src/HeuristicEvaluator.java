import java.util.LinkedList;

/**
 * Each player will most likely make the best possible move.
 */
public class HeuristicEvaluator implements OthelloEvaluator {
    @Override
    public int evaluate(OthelloPosition position) {
        LinkedList<OthelloAction> moves = position.getMoves();

        int maxValue = 0;
        for(OthelloAction action : moves){
            if(action.getValue() > maxValue){
                maxValue = action.getValue();
            }
        }

        if(!position.toMove())
            return maxValue * -1;

        return maxValue;
    }
    
    public int coinParity(OthelloPosition position){
        int blackCoin;
        int whiteCoin;
        
        for(int x = 1; position.IsWithinBoard(x);x++){
            for(int y = 1; position.IsWithinBoard(y);y++){
                if(position.board[x][y] == 'X')
                    blackCoin++;
                if(position.board[x][y] == 'O')
                    whiteCoin++;
            }
        }
        
        
        return 100* (whiteCoin /blackCoin);
    }
    
    /**
     * Corner positions are nice.
     */
    public boolean isInCorner(OthelloAction action){
        int actionX = action.getRow();
        int actionY = action.getColumn();
        
        int cornerX = 0+1;
        int cornerY = OthelloPosition.BOARD_SIZE;
        
        if(actionX == cornerX || actionX == cornerY)
            if(actionY == cornerX || actionY == cornerY)
                return true;
        
        return false;
    }
    
    /**
     *  If an action is oriented around a corner it gives the oponent
     * the opportunity to place a marker in corner.
     * 
     */
    public boolean isAroundCorner(OthelloAction action,OthelloPosition position){
        int actionX = action.getRow();
        int actionY = action.getColumn();
        
        int lowerSide = 0+1;
        int upperSide = OthelloPosition.BOARD_SIZE;
        
        if(actionX == lowerside || actionX == lowerSide+1 ||actionX == upperSide ||actionX == upperSide+1)
            if(actionY == lowerside || actionY == lowerSide+1 ||actionY == upperSide ||actionY == upperSide+1)
                if((actionX != lowerSide  && actionX != upperSide) && (actionY != lowerSide || actionY != upperSide))
                    return true;

        return false;
    }
}
