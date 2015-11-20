import org.junit.Before
import org.junit.Test

/**
 * Tests the alpha beta search
 */
class AlphaBetaTest extends GroovyTestCase {

    OthelloPosition othelloBoard;
    OthelloAlgorithm algorithm;
    OthelloEvaluator evaluator;

    String board =  "W"+
            "EEEEEEEE" +
            "EEEEWEEE"+
            "EEEOWWEE"+
            "EEEOWEEE"+
            "EEEOOOEE"+
            "EEEEEEEE"+
            "EEEEEEEE"+
            "EEEEEEEE";

    @Before
    void setUp() {

        othelloBoard = new OthelloPosition(board);
        algorithm = new AlphaBeta(1);
        evaluator = new HeuristicEvaluator();
        algorithm.setEvaluator(evaluator);
    }

    @Test
    void testEvaluate() {
        algorithm.setSearchDepth(0);
        othelloBoard.illustrate();

        OthelloAction actionToMake = algorithm.evaluate(othelloBoard);
        println("actionToMake value : " + actionToMake.getValue());
        othelloBoard.makeMove(actionToMake).illustrate();

        algorithm.setSearchDepth(5);
        actionToMake = algorithm.evaluate(othelloBoard);
        println("actionToMake value : " + actionToMake.getValue());
        othelloBoard.makeMove(actionToMake).illustrate();

    }
}
