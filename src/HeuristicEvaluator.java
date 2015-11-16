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
}
