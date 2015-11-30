import org.junit.Before
import org.junit.Test

/**
 * Heuristic evaluator test.
 */
class HeuristicEvaluatorTest {

    OthelloPosition othelloBoard;
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
        evaluator = new HeuristicEvaluator();
    }

    @Test
    void testEvaluate() {
        othelloBoard.illustrate();
        println("Evaluation of board : " + evaluator.evaluate(othelloBoard));
    }
}
