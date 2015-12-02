import org.junit.Before
import org.junit.Test

import static junit.framework.Assert.assertEquals

/**
 * Created by Aous on 2015-12-01.
 */
class OthelloTest {

    Othello othello;

    @Before
    void Setup(){
        othello = new Othello();
    }

    @Test
    void testCalculateDepth() {
        int depth;
        depth = othello.calculateDepth(1);
        assertEquals(depth,3);
        depth = othello.calculateDepth(3);
        assertEquals(depth,6);
        depth = othello.calculateDepth(5);
        assertEquals(depth,6);
        depth = othello.calculateDepth(35);
        assertEquals(depth,7);
    }
}
