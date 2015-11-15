import org.junit.*;

/**
 * Tests othelloPositions
 *
 * @author Tim
 *
 */
class OthelloPositionTest {

    OthelloPosition othelloBoard;
    String board =
                "EEEEEEEE" +
                "EEEEBEEE"+
                "EEEOBBEE"+
                "EEEOBEEE"+
                "EEEOOOEE"+
                "EEEEEEEE"+
                "EEEEEEEE"+
                "EEEEEEEEE";

    @Before
    void setUp() {

        othelloBoard = new OthelloPosition(board);
    }

    @Test
    void testMakeMove(){
        othelloBoard.illustrate();
        for(action in othelloBoard.getMoves()){
            println("MOVE VALUE : " + ((OthelloAction)action).getValue());
            othelloBoard.makeMove((OthelloAction)action).illustrate();
        }

    }
}
