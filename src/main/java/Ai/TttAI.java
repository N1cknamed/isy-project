package Ai;

import Games.Tictactoe;

import java.awt.*;

public class TttAI {
    private char aiPlayer;
    private char opponentPlayer;

    public TttAI(char aiPlayer, char opponentPlayer) {
        this.aiPlayer = aiPlayer;
        this.opponentPlayer = opponentPlayer;
    }

    public int[] findBestMove(Tictactoe game) {
        int[] bestMove = minimax(game, aiPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new int[] {bestMove[1], bestMove[2]};
    }

    private int[] minimax(Tictactoe game, char currentPlayer, int alpha, int beta) {
        if (game.checkWin(aiPlayer)) {
            return new int[]{1, 0, 0};
        } else if (game.checkWin(opponentPlayer)) {
            return new int[]{-1, 0, 0};
        } else if (game.isBoardFull()) {
            return new int[]{0, 0, 0};
        }

        int[] bestMove = {currentPlayer == aiPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE, -1, -1};

        for (int row = 0; row < game.getBoardHeight(); row++) {
            for (int col = 0; col < game.getBoardWidth(); col++) {
                if (game.getBoard()[row][col] == 0) {
                    game.getBoard()[row][col] = currentPlayer;
                    int[] score = minimax(game, (currentPlayer == aiPlayer) ? opponentPlayer : aiPlayer, alpha, beta);
                    game.getBoard()[row][col] = 0;

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