package ttt;

import framework.Game;

public class TttAi {
    private char aiPlayer;
    private char opponentPlayer;

    public TttAi(char aiPlayer, char opponentPlayer) {
        this.aiPlayer = aiPlayer;
        this.opponentPlayer = opponentPlayer;
    }

    public int[] findBestMove(Game game) {
        int[] bestMove = minimax(game, aiPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new int[]{bestMove[1], bestMove[2]};
    }

    private int[] minimax(Game game, char currentPlayer, int alpha, int beta) {
        if (game.hasEnded()) { 
            if (game.getWinner() == null) {
                return new int[]{0, 0, 0};
            } else if (game.getWinner().getSymbol() == aiPlayer) {
                return new int[]{1, 0, 0};
            } else if (game.getWinner().getSymbol() == opponentPlayer) {
                return new int[]{-1, 0, 0};
            }
        }
        int[] bestMove = {currentPlayer == aiPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE, -1, -1};

        for (int row = 0; row < game.getBoard().getBoardHeight(); row++) {
            for (int col = 0; col < game.getBoard().getBoardWidth(); col++) {
                if (game.getBoard().get(col, row) == 0) {
                    game.getBoard().set(col, row, currentPlayer);
                    int[] score = minimax(game, (currentPlayer == aiPlayer) ? opponentPlayer : aiPlayer, alpha, beta);
                    game.getBoard().set(col, row, (char) 0);

                    if (currentPlayer == aiPlayer) {
                        if (score[0] > bestMove[0]) {
                            bestMove[0] = score[0];
                            bestMove[1] = row;
                            bestMove[2] = col;
                        }
                        alpha = Math.max(alpha, score[0]);
                    } else {
                        if (score[0] < bestMove[0]) {
                            bestMove[0] = score[0];
                            bestMove[1] = row;
                            bestMove[2] = col;
                        }
                        beta = Math.min(beta, score[0]);
                    }

                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }

        return bestMove;
    }
}