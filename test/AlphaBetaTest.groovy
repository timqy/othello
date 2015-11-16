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

        for(int i = 1; i < 2; i++){
            algorithm.setSearchDepth(i);
            printf("value : " + algorithm.evaluate(othelloBoard).getValue());

        }

    }
}
