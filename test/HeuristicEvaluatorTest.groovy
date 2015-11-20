import org.junit.Before
import org.junit.Test

/**
 * Heuristic evaluator test.
 */
class HeuristicEvaluatorTest extends GroovyTestCase {

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
        assertEquals(3,evaluator.evaluate(othelloBoard));
    }
}
