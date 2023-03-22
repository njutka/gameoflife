package hu.progmasters;

import java.util.*;

public class GameOfLife {

    Set<Cell> livingCells = new HashSet<>();
    int round = 1;

    public void start() {

        initBoard();
        printBoard();

        while (!livingCells.isEmpty()) {
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            createNextGeneration();
            System.out.println(round++);
        }
    }

    public void initBoard() {
//oscillator
//        livingCells.add(new Cell(0,7));
//        livingCells.add(new Cell(0,8));
//        livingCells.add(new Cell(0,9));


// spaceship
//        livingCells.add(new Cell(0,2));
//        livingCells.add(new Cell(1,0));
//        livingCells.add(new Cell(1,2));
//        livingCells.add(new Cell(2,1));
//        livingCells.add(new Cell(2,2));


//pentadecathlon
        livingCells.add(new Cell(0,7));
        livingCells.add(new Cell(1,0));
        livingCells.add(new Cell(1,1));
        livingCells.add(new Cell(1,3));
        livingCells.add(new Cell(1,4));
        livingCells.add(new Cell(1,5));
        livingCells.add(new Cell(1,6));
        livingCells.add(new Cell(1,8));
        livingCells.add(new Cell(1,9));
        livingCells.add(new Cell(0,2));
        livingCells.add(new Cell(2,2));
        livingCells.add(new Cell(2,7));
    }

    public boolean isCellAlive(Cell cell) {
        return livingCells.contains(cell);
    }

    public Set<Cell> getNeighbourCells(Cell centerCell) {
        Set<Cell> neighbourCells = new HashSet<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                neighbourCells.add(new Cell(centerCell.getX() + i, centerCell.getY() + j));
            }
        }
        neighbourCells.remove(centerCell);
        return neighbourCells;
    }

    public int countAliveNeighbourCells(Cell cell) {
        Set<Cell> neighbourCells = getNeighbourCells(cell);
        int numOfLivingNeighbours = 0;
        for (Cell currentNeighbour : neighbourCells) {

            if (isCellAlive(currentNeighbour)) {
                numOfLivingNeighbours++;
            }
        }

        return numOfLivingNeighbours;
    }

    public void createNextGeneration() {
        Set<Cell> newLivingCells = new HashSet<>();
        Set<Cell> possibleNewCells = new HashSet<>();

        //check living cells

        for (Cell currentCell : livingCells) {

            if (countAliveNeighbourCells(currentCell) == 2 || countAliveNeighbourCells(currentCell) == 3) {
                newLivingCells.add(currentCell);
            }

            possibleNewCells.addAll(getNeighbourCells(currentCell));
        }
        possibleNewCells.removeAll(livingCells);

        //check surrounding cells if become alive
        for (Cell currentPossibleCell : possibleNewCells) {
            if (countAliveNeighbourCells(currentPossibleCell) == 3) {
                newLivingCells.add(currentPossibleCell);
            }
        }
        livingCells = newLivingCells;
        if (!livingCells.isEmpty()) {
            printBoard();
        }
    }

    public void printBoard() {
        //print actual size
//        int[] edgesArray = calculateEdgesOfBoard();
//        int xMin = edgesArray[0];
//        int xMax = edgesArray[1];
//        int yMin = edgesArray[2];
//        int yMax = edgesArray[3];
//
//        for (int i = xMin - 1; i < xMax + 1; i++) {
//            for (int j = yMin - 1; j < yMax + 1; j++) {
//                if (livingCells.contains(new Cell(i, j))) {
//                    System.out.print("¤ ");
//                } else {
//                    System.out.print("  ");
//                }
//            }
//            System.out.println();
//        }

        // print fix size
        for (int i = -3; i < 7; i++) {
            for (int j = -2; j < 16; j++) {
                if (livingCells.contains(new Cell(i, j))) {
                    System.out.print("¤ ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println("=========================");

    }

    public int[] calculateEdgesOfBoard() {
        List<Integer> xValues = new ArrayList<>();
        List<Integer> yValues = new ArrayList<>();

        for (Cell cell : livingCells) {
            xValues.add(cell.getX());
            yValues.add(cell.getY());
        }
        Collections.sort(xValues);
        Collections.sort(yValues);

        return new int[]{xValues.get(0),
                xValues.get(xValues.size() - 1),
                yValues.get(0),
                yValues.get(yValues.size() - 1)};
    }

}
