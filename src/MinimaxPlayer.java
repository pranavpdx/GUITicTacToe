import java.util.Random;

public class MinimaxPlayer {
    private int piece;
    private int opponent;

    public MinimaxPlayer(int piece) {
        this.piece = piece;
        opponent = (piece == 1) ? 2 : 1;
    }

    public int[] makeMove(int[][] board) {
        int[] bestMove = findBestMove(board);
        return bestMove;
    }
    private static class Move {
        int row, col;
    }

    private int opponent() {
        return (piece == 1) ? 2 : 1;
    }

    private boolean isMovesLeft(int board[][]) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == 0)
                    return true;
        return false;
    }

    private int evaluate(int b[][]) {
        for (int row = 0; row < 3; row++) {
            if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
                if (b[row][0] == piece) return +10;
                else if (b[row][0] == opponent) return -10;
            }
        }
    
        for (int col = 0; col < 3; col++) {
            if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
                if (b[0][col] == piece) return +10;
                else if (b[0][col] == opponent) return -10;
            }
        }
    
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == piece) return +10;
            else if (b[0][0] == opponent) return -10;
        }
    
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == piece) return +10;
            else if (b[0][2] == opponent) return -10;
        }
    
        return 0;
    }
    
    
    private int minimax(int board[][], int depth, boolean isMax) {
        int score = evaluate(board);
    
        // If Maximizer has won the game
        // return his/her evaluated score
        if (score == 10 || score == -10) {
            return score;
        }
    
        // If there are no more moves and
        // no winner then it is a tie
        if (!isMovesLeft(board)) {
            return 0;
        }
    
        // If this maximizer's move
        if (isMax) {
            int best = Integer.MIN_VALUE;
    
            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j] == 0) {
                        // Make the move
                        board[i][j] = piece;
    
                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best, minimax(board, depth + 1, !isMax));
    
                        // Undo the move
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
    
            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j] == 0) {
                        // Make the move
                        board[i][j] = opponent;
    
                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best, minimax(board, depth + 1, !isMax));
    
                        // Undo the move
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        }
    }

    private Move getRandomEmptyPosition(int[][] board) {
        Random random = new Random();
        Move randomMove = new Move();

        do {
            randomMove.row = random.nextInt(3);
            randomMove.col = random.nextInt(3);
        } while (board[randomMove.row][randomMove.col] != 0);

        return randomMove;
    }
    

    private int[] findBestMove(int board[][]) {
        int bestVal = (piece == 1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] bestMove = {-1, -1};
        int[] copyBest = bestMove;
        
        // Check for a winning move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = piece;
                    if (evaluate(board) == 10) {
                        // Winning move found
                        bestMove[0] = i;
                        bestMove[1] = j;
                        return bestMove;
                    }
                    board[i][j] = 0;
                }
            }
        }

        // Block opponent from winning
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = opponent;
                    if (evaluate(board) == -10) {
                        // Blocking opponent's winning move
                        bestMove[0] = i;
                        bestMove[1] = j;
                        return bestMove;
                    }
                    board[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = piece;
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = 0;

                    if ((piece == 1 && moveVal > bestVal) || (piece == 2 && moveVal < bestVal)) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        if(bestMove[0] == -1 || bestMove[1] == -1){
            Move m = getRandomEmptyPosition(board);
            System.out.println("random move");
            int[] ret = {m.row, m.col};
            return ret;
        }
        return bestMove;
    }
    
}
