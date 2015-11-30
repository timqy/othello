import java.util.*;
import java.lang.*;

/**
 * This class is used to represent game positions. It uses a 2-dimensional char
 * array for the board and a Boolean to keep track of which player has the move.
 * 
 * @author Henrik Björklund
 */

public class OthelloPosition {

	/** For a normal Othello game, BOARD_SIZE is 8. */
	protected static final int BOARD_SIZE = 8;

	/** True if the first player (white) has the move. */
	protected boolean playerToMove;

	/**
	 * The representation of the board. For convenience, the array actually has
	 * two columns and two rows more that the actual game board. The 'middle' is
	 * used for the board. The first index is for rows, and the second for
	 * columns. This means that for a standard 8x8 game board,
	 * <code>board[1][1]</code> represents the upper left corner,
	 * <code>board[1][8]</code> the upper right corner, <code>board[8][1]</code>
	 * the lower left corner, and <code>board[8][8]</code> the lower left
	 * corner. In the array, the charachters 'E', 'W', and 'B' are used to
	 * represent empty, white, and black board squares, respectively.
	 */
	protected char[][] board;

	/** Creates a new position and sets all squares to empty. */
	public OthelloPosition() {
		board = new char[BOARD_SIZE + 2][BOARD_SIZE + 2];
		for (int i = 0; i < BOARD_SIZE + 2; i++)
			for (int j = 0; j < BOARD_SIZE + 2; j++)
				board[i][j] = 'E';

	}

	public OthelloPosition(String s) {
		if (s.length() != 65) {
			board = new char[BOARD_SIZE + 2][BOARD_SIZE + 2];
			for (int i = 0; i < BOARD_SIZE + 2; i++)
				for (int j = 0; j < BOARD_SIZE + 2; j++)
					board[i][j] = 'E';
		} else {
			board = new char[BOARD_SIZE + 2][BOARD_SIZE + 2];
			playerToMove = s.charAt(0) == 'W';
			for (int i = 1; i <= 64; i++) {
				char c;
				if (s.charAt(i) == 'E') {
					c = 'E';
				} else if (s.charAt(i) == 'O') {
					c = 'W';
				} else {
					c = 'B';
				}
				int column = ((i - 1) % 8) + 1;
				int row = (i - 1) / 8 + 1;
				board[row][column] = c;
			}
		}

	}

	/**
	 * Initializes the position by placing four markers in the middle of the
	 * board.
	 */
	public void initialize() {
		board[BOARD_SIZE / 2][BOARD_SIZE / 2] = board[BOARD_SIZE / 2 + 1][BOARD_SIZE / 2 + 1] = 'W';
		board[BOARD_SIZE / 2][BOARD_SIZE / 2 + 1] = board[BOARD_SIZE / 2 + 1][BOARD_SIZE / 2] = 'B';
		playerToMove = true;
	}

	/**
	 * Returns a linked list of <code>OthelloAction</code> representing all
	 * possible moves in the position. If the list is empty, there are no legal
	 * moves for the player who has the move.
	 */

	public List<OthelloAction> getMoves() {
		List<OthelloAction> moves = new LinkedList<>();

		for(int row = 1; IsWithinBoard(row);row++){
			for(int column = 1; IsWithinBoard(column);column++){
				if(board[row][column] == 'E'){
					int value = 0;
					/** check all 8 directions */
					for(int rowIncrement = -1; rowIncrement <= 1; rowIncrement++)
						for(int columnIncrement = -1; columnIncrement <= 1; columnIncrement++)
							value += EvaluateDirectionValue(row, column, rowIncrement, columnIncrement);

					if(value > 0){
						OthelloAction action = new OthelloAction(row,column);
						action.setValue(value);
						moves.add(action);
					}
				}
			}
		}

		return moves;

	}

	/** Returns true if the first player (white) has the move, otherwise false. */
	public boolean toMove() {
		return playerToMove;
	}

	/**
	 * Returns the position resulting from making the move <code>action</code>
	 * in the current position. Observe that this also changes the player to
	 * move next.
	 */
	public OthelloPosition makeMove(OthelloAction action)
			throws IllegalMoveException {

		/** Player should not be able to place an marker at occupied slot*/
		if(board[action.getRow()][action.getColumn()] != 'E')
			throw new IllegalMoveException(action);

		OthelloPosition boardAfterMove = clone();

		/** insert move */
		boardAfterMove.board[action.getRow()][action.getColumn()] = PlayerColor(playerToMove);

		/** Check all 8 directions at the action for pieces to flip */
		/**Check diagonally, Horizontally and Vertically*/
		for(int rowIncrement = -1; rowIncrement <= 1; rowIncrement++)
			for(int columnIncrement = -1; columnIncrement <= 1; columnIncrement++)
				boardAfterMove.CapturePiecesInDirection(
						action.getRow(), action.getColumn(), rowIncrement, columnIncrement);

		/** next players move */
		boardAfterMove.playerToMove = !boardAfterMove.playerToMove;

		return boardAfterMove;
	}

	/**
	 * Checks wether or not a direction will be capturing any pieces.
	 *
	 * @param row the row in which the move is made
	 * @param column the column in which the move is made
	 * @param incRow What to increment the row with.
	 * @param incColumn increment of Column
	 * @return the value of the move.
	 */
	private int EvaluateDirectionValue(int row, int column, int incRow, int incColumn){
		row += incRow;
		column += incColumn;

		int PiecesCaptured = 0;

		while(IsWithinBoard(row) && IsWithinBoard(column)){
			if(board[row][column] == 'E')
				return 0;

			if(board[row][column] == PlayerColor(!playerToMove))
				PiecesCaptured++;

			if(board[row][column] == PlayerColor(playerToMove))
					return PiecesCaptured;

			row += incRow;
			column += incColumn;
		}

		return 0;
	}

	/**
	 * Checks if the direction has an marker of current player
	 * if so, it flips all markers in between.
	 *
	 * @param row the row in which the move is made
	 * @param column the column in which the move is made
	 * @param incRow What to increment the row with.
	 * @param incColumn increment of Column
	 */
	public void CapturePiecesInDirection(int row, int column, int incRow, int incColumn) {
		if(EvaluateDirectionValue(row, column, incRow, incColumn) == 0)
			return;

		row += incRow;
		column += incColumn;

		while(board[row][column] == PlayerColor(!playerToMove)){
			if(!IsWithinBoard(row) && !IsWithinBoard(column))
				return;

			board[row][column] = PlayerColor(playerToMove);

			row += incRow;
			column += incColumn;
		}
	}

	/**
	 * Check if the position is within board limits
	 *
	 * @param position the position to evaluate
	 * @return true if position is within borders else false.
	 */
	public boolean IsWithinBoard(int position){
		return 0 < position && position <= BOARD_SIZE;
	}

	/**
	 * Gets the color of the marker of the current player
	 * @return The first letter of the color (W)hite or (B)lack.
	 */
	public char PlayerColor(boolean playerToMove){
		/**if player white return W */
		if(playerToMove)
			return 'W';

		/** else it's blacks turn*/
		return 'B';
	}

	/**
	 * Returns a new <code>OthelloPosition</code>, identical to the current one.
	 */
	protected OthelloPosition clone() {
		OthelloPosition newPosition = new OthelloPosition();
		newPosition.playerToMove = playerToMove;
		for (int i = 0; i < BOARD_SIZE + 2; i++)
			for (int j = 0; j < BOARD_SIZE + 2; j++)
				newPosition.board[i][j] = board[i][j];
		return newPosition;
	}

	/* illustrate and other output functions */

	/**
	 * Draws an ASCII representation of the position. White squares are marked
	 * by '0' while black squares are marked by 'X'.
	 */
	public void illustrate() {
		System.out.print("   ");
		for (int i = 1; i <= BOARD_SIZE; i++)
			System.out.print("| " + i + " ");
		System.out.println("|");
		printHorizontalBorder();
		for (int i = 1; i <= BOARD_SIZE; i++) {
			System.out.print(" " + i + " ");
			for (int j = 1; j <= BOARD_SIZE; j++) {
				if (board[i][j] == 'W') {
					System.out.print("| 0 ");
				} else if (board[i][j] == 'B') {
					System.out.print("| X ");
				} else {
					System.out.print("|   ");
				}
			}
			System.out.println("| " + i + " ");
			printHorizontalBorder();
		}
		System.out.print("   ");
		for (int i = 1; i <= BOARD_SIZE; i++)
			System.out.print("| " + i + " ");
		System.out.println("|\n");
	}

	private void printHorizontalBorder() {
		System.out.print("---");
		for (int i = 1; i <= BOARD_SIZE; i++) {
			System.out.print("|---");
		}
		System.out.println("|---");
	}

	public String toString() {
		String s = "";
		char c, d;
		if (playerToMove) {
			s += "W";
		} else {
			s += "B";
		}
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				d = board[i][j];
				if (d == 'W') {
					c = 'O';
				} else if (d == 'B') {
					c = 'X';
				} else {
					c = 'E';
				}
				s += c;
			}
		}
		return s;
	}

}
