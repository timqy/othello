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
            "EEEEBEEE"+
            "EEEOBBEE"+
            "EEEOBEEE"+
            "EEEOOOEE"+
            "EEEEEEEE"+
            "EEEEEEEE"+
            "EEEEEEEE";

    @Before
    void setUp() {

        othelloBoard = new OthelloPosition(board);
        algorithm = new AlphaBeta();
        evaluator = new HeuristicEvaluator();
        algorithm.setEvaluator(evaluator);
        algorithm.setSearchDepth(1);
    }

    @Test
    void testEvaluate() {
        algorithm.setSearchDepth(0);
        assertEquals(3,algorithm.evaluate(othelloBoard).getValue());


        algorithm.setSearchDepth(5);
        printf("value : " + algorithm.evaluate(othelloBoard).getValue());


    }
}
