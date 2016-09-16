package algos.coinOnTheTable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by viveksrivastava on 12/04/16.
 */

class CoinOnTheTableDPSolver {
    private enum MOVEMENT {
        N, U, D, L, R, S
    }

    private class Cell {
        private MOVEMENT defaultMovement;
        private Set<MOVEMENT> movementCompleteSet = new HashSet<>();
        private int changes = 0;
        private Map<MOVEMENT, Integer> movementTimeCount = new HashMap<>();

        public Set<MOVEMENT> getMovementCompleteSet() {
            return movementCompleteSet;
        }

        public void setMovementCompleteSet(MOVEMENT movement) {
            this.movementCompleteSet.add(movement);
        }

        public int getChanges() {
            return changes;
        }

        public void setChanges(int changes) {
            this.changes = changes;
        }

        public Map<MOVEMENT, Integer> getMovementTimeCount() {
            return movementTimeCount;
        }

        public void setMovementTimeCount(MOVEMENT movement, int time) {
            this.movementTimeCount.put(movement, time);
        }

        public MOVEMENT getDefaultMovement() {
            return defaultMovement;
        }

        public void setDefaultMovement(MOVEMENT defaultMovement) {
            this.defaultMovement = defaultMovement;
        }
    }

    private String[][] board;
    private Cell[][] cells;
    private Cell nullCell;

    public CoinOnTheTableDPSolver(String[][] board) {
        this.board = board;
        initCells(board);
        nullCell = new Cell();
        nullCell.setChanges(Integer.MAX_VALUE);
    }

    private void initCells(String[][] board) {
        cells = new Cell[board.length][];
        for (int i = 0; i < board.length; i++) {
            cells[i] = new Cell[board[i].length];
            for (int j = 0; j < board[i].length; j++) {
                Cell cell = new Cell();
                MOVEMENT movement = getMovement(board[i][j]);
                cell.setDefaultMovement(movement);
                cells[i][j] = cell;
            }
        }
    }

    private MOVEMENT getMovement(String s) {
        MOVEMENT movement = MOVEMENT.S;
        switch (s) {
            case "U":
                movement = MOVEMENT.U;
                break;
            case "D":
                movement = MOVEMENT.D;
                break;
            case "L":
                movement = MOVEMENT.L;
                break;
            case "R":
                movement = MOVEMENT.R;
        }
        return movement;
    }

    public void explore(Cell cell, int time) {
        if (time <= 0)
            return;
    }
}

public class Solution {
}
