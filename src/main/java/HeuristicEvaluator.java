/**
 * Each player will most likely make the best possible move.
 */
public class HeuristicEvaluator implements OthelloEvaluator {

    public final int moveAbilityWeight = 40;
    public final int actionInCornerWeight = 10000;
    public final int actionAroundCornerWeight = -3000;
    public final int coinParityWeight = 100;
    public final int potentialMovesWeight = 40;

    public int evaluate(OthelloPosition position) {
        int value = 0;

        value += MovesComparison(position);
        value += coinParity(position);
        value += HasActionInCorner(position);
        //value += hasActionsAroundCorner(position);
        value += potentialMoves(position);

        if(!position.toMove())
            return value * -1;

        return value;
    }

    public int MovesComparison(OthelloPosition position){
        return position.getMoves().size() * moveAbilityWeight;
    }

    /***
     * gives a value of the amount of potential moves to be made
     * @param position the othello board
     * @return value given the number of potential moves and weight.
     */
    public int potentialMoves(OthelloPosition position){
        int potentialMoves = 0;

        for(int x = 1; position.IsWithinBoard(x); x++){
            for(int y = 1; position.IsWithinBoard(y);y++){
                if(position.board[x][y] == position.PlayerColor(!position.toMove())){
                    if(SearchForPotentialMoves(position,x,y))
                        potentialMoves++;
                }
            }
        }
        return potentialMoves * potentialMovesWeight;
    }


    /**
     * Is used to search around a position for Potential moves.
     * @param position othelloboard
     * @param x the x value of the piece to search around
     * @param y the y value of the piece to search around
     * @return a bool for if there's an empty slot around that piece.
     */
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

    /**
     * counts the difference between the amount of pieces each player has.
     * @param position othelloboard
     * @return an int containing weight.
     */
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
     * checks if the user has an action in the corner.
     * @param position othelloboard
     * @return the value from the evaluation.
     */
    public int HasActionInCorner(OthelloPosition position){
        for(OthelloAction action : position.getMoves()){
            int boardLow = 1;
            int boardHigh = OthelloPosition.BOARD_SIZE;

            if(action.getRow() == boardLow || action.getRow() == boardHigh)
                if(action.getColumn() == boardLow || action.getColumn() == boardHigh)
                    return actionInCornerWeight;
        }
        return 0;
    }
    
    /**
     *  If an action is oriented around a corner it gives the oponent
     * the opportunity to place a marker in corner.
     *
     * @param position othelloboard
     * @return the value from the evaluation.
     */
    public int hasActionsAroundCorner(OthelloPosition position){
        int boardLow = 1;
        int boardHigh = OthelloPosition.BOARD_SIZE;

        for(OthelloAction action : position.getMoves()) {
            if(action.getRow() == boardLow || action.getRow() == boardLow+1
               || action.getRow() == boardHigh || action.getRow() == boardHigh -1){
                if(action.getColumn() == boardLow || action.getColumn() == boardLow+1
                        || action.getColumn() == boardHigh || action.getColumn() == boardHigh -1){
                    if((action.getRow() == boardLow ||action.getRow() == boardHigh) &&
                            action.getColumn() == boardLow || action.getColumn() == boardHigh){
                        return actionAroundCornerWeight;
                    }
                }
            }
        }
        return 0;
    }
}
