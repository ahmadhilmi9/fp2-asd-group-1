/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026221016 - Arjuna Ahmad Dewangga Aljabbar
 * 2 - 5026221126 -  Nur Ghulam Musthafa Al Kautsar
 * 3 - 5026221147 -  Ahmad Hilmi Dwi Setiawan
 */

package tictactoe;

public class AIPlayerMinimax extends AIPlayer {

    public AIPlayerMinimax(Board board) {
        super(board);
    }

    @Override
    public int[] move() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        // Flatten the board to a 1D array for easier handling
        Seed[] flatBoard = flattenBoard();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    // Simulate the AI move
                    flatBoard[row * COLS + col] = mySeed;
                    cells[row][col].content = mySeed;

                    // Evaluate the board using Minimax
                    int moveScore = minimax(flatBoard, oppSeed, false);

                    // Revert the move
                    flatBoard[row * COLS + col] = Seed.NO_SEED;
                    cells[row][col].content = Seed.NO_SEED;

                    // Update the best move if this move is better
                    if (moveScore > bestScore) {
                        bestScore = moveScore;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }

        System.out.printf("AI selected move: (%d, %d) with BestScore: %d\n", bestMove[0], bestMove[1], bestScore);
        return bestMove;
    }

    private int minimax(Seed[] board, Seed currentPlayer, boolean isMaximizing) {
        // Base cases for win, lose, or draw
        if (checkWin(board, mySeed)) return 1;
        if (checkWin(board, oppSeed)) return -1;
        if (checkTie(board)) return 0;

        // Recursive case: Maximize or Minimize score
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < ROWS * COLS; i++) {
            if (board[i] == Seed.NO_SEED) {
                board[i] = currentPlayer; // Simulate move

                int currentScore = minimax(board, isMaximizing ? oppSeed : mySeed, !isMaximizing);

                board[i] = Seed.NO_SEED; // Revert move

                if (isMaximizing) {
                    bestScore = Math.max(bestScore, currentScore);
                } else {
                    bestScore = Math.min(bestScore, currentScore);
                }
            }
        }

        return bestScore;
    }

    private Seed[] flattenBoard() {
        Seed[] flatBoard = new Seed[ROWS * COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                flatBoard[row * COLS + col] = cells[row][col].content;
            }
        }
        return flatBoard;
    }

    private boolean checkWin(Seed[] board, Seed player) {
        return (board[0] == player && board[1] == player && board[2] == player) ||
                (board[3] == player && board[4] == player && board[5] == player) ||
                (board[6] == player && board[7] == player && board[8] == player) ||
                (board[0] == player && board[3] == player && board[6] == player) ||
                (board[1] == player && board[4] == player && board[7] == player) ||
                (board[2] == player && board[5] == player && board[8] == player) ||
                (board[0] == player && board[4] == player && board[8] == player) ||
                (board[2] == player && board[4] == player && board[6] == player);
    }

    private boolean checkTie(Seed[] board) {
        for (Seed cell : board) {
            if (cell == Seed.NO_SEED) return false;
        }
        return true;
    }
}
