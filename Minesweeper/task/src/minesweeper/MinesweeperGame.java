package minesweeper;

import java.util.*;

public class MinesweeperGame {
    private static final int SIZE = 9;
    private static boolean gameOver;
    private final Set<Location> mines;
    private final Map<Location, Integer> hints;
    private final Set<Location> dugSpaces;
    private static final char EMPTY_SPACE = '.';
    private static final char DUG_SPACE = '/';
    private static final char MINE = 'X';
    private static final char FLAG = '*';

    private final char[][] mineField;

    public MinesweeperGame() {
        mineField = createMineField();
        dugSpaces = new HashSet<>();
        mines = new HashSet<>();
        hints = new HashMap<>();
        gameOver = false;
    }

    //create array
    public void start() {
        //get start location
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? ");
        int numberOfMines = Integer.parseInt(scanner.nextLine());
        displayGrid();
        while (!gameOver) {

            System.out.print("Set/unset mines marks or claim a cell as free: ");
            String[] input = scanner.nextLine().trim().toLowerCase().split(" ");
            int row = Integer.parseInt(input[0]) - 1;
            int column = Integer.parseInt(input[1]) - 1;
            Location playerMove = new Location(column, row);

            //choice of dig or flag
            switch (input[2]) {
                case "free":
                    //if first move
                    if (dugSpaces.isEmpty()) {
                        generateMines(numberOfMines, playerMove);
                        generateHints(mines);
                    }
                    //trying to dig same spot
                    if (!isValidMove(playerMove)) {
                        break;
                    }
                    dig(playerMove);
                    break;
                case "mine":
                    setFlag(playerMove);
                    break;
                default:
                    System.out.println("Error");
                    gameOver = true;
                    return;
            }
            displayGrid();
            gameOver = isGameOver();
        }
    }

    private char[][] createMineField() {
        char[][] mineField = new char[SIZE][SIZE];
        for (char[] row : mineField) {
            Arrays.fill(row, EMPTY_SPACE);
        }
        return mineField;
    }

    //generate mines
    private void generateMines(int numberOfMines, Location startingLocation) {
        Random random = new Random();
        for (int i = 0; i < numberOfMines; i++) {
            int column = random.nextInt(SIZE);
            int row = random.nextInt(SIZE);
            Location mine = new Location(column, row);
            if (!mines.contains(mine) && !mine.equals(startingLocation)) {
                mines.add(mine);
            } else {
                i--;
            }
        }
    }

    private void generateHints(Set<Location> locationOfMines) {
        //make a map of spaces with number of hints associated
        for (Location mine : locationOfMines) {
            Location[] spaces = emptySpacesAroundMine(mine);
            for (Location space : spaces) {
                // IF the space is not a mine ELSE SKIP
                if (!locationOfMines.contains(space)) {
                    //if my map already has the space marked
                    if (hints.containsKey(space)) {
                        hints.put(space, hints.get(space) + 1);
                    } else {
                        hints.put(space, 1);
                    }
                }
            }
        }

    }

    private Location[] emptySpacesAroundMine(Location mine) {
        List<Location> emptySpaces = new ArrayList<>();

        int startOnRow = Math.max(mine.getColumn() - 1, 0);
        int startOnColumn = Math.max(mine.getRow() - 1, 0);
        int endOnRow = Math.min(mine.getColumn() + 1, mineField.length - 1);
        int endOnColumn = Math.min(mine.getRow() + 1, mineField[0].length - 1);

        for (int i = startOnRow; i <= endOnRow; i++) {
            for (int j = startOnColumn; j <= endOnColumn; j++) {
                char currentSpace = mineField[i][j];
                if (currentSpace == EMPTY_SPACE) {
                    Location emptySpace = new Location(i, j);
                    emptySpaces.add(emptySpace);
                }
            }
        }
        return emptySpaces.toArray(new Location[0]);
    }

    private void dig(Location location) {
        // if the space is a mine
        if (mines.contains(location)) {
            mineField[location.getColumn()][location.getRow()] = MINE;
            return;
        }

        // if the space is a number
        if (hints.containsKey(location)) {
            mineField[location.getColumn()][location.getRow()] = Character.forDigit(hints.get(location), 10);
            dugSpaces.add(new Location(location.getColumn(), location.getRow()));
            return;
        }

        // if the space is already dug up
        if (dugSpaces.contains(location)) {
            return;
        }

        //dig the current space
        mineField[location.getColumn()][location.getRow()] = DUG_SPACE;
        dugSpaces.add(new Location(location.getColumn(), location.getRow()));

        //dig up the spaces around the space
        int startOnRow = Math.max(location.getColumn() - 1, 0);
        int startOnColumn = Math.max(location.getRow() - 1, 0);
        int endOnRow = Math.min(location.getColumn() + 1, mineField.length - 1);
        int endOnColumn = Math.min(location.getRow() + 1, mineField[0].length - 1);

        for (int i = startOnRow; i <= endOnRow; i++) {
            for (int j = startOnColumn; j <= endOnColumn; j++) {
                Location surroundingSpace = new Location(i, j);
                dig(surroundingSpace);
            }
        }
    }

    private void placeFlag(Location playerMove) {
        mineField[playerMove.getColumn()][playerMove.getRow()] = FLAG;
    }

    private void removeFlag(Location playerMove) {
        mineField[playerMove.getColumn()][playerMove.getRow()] = EMPTY_SPACE;
    }

    private void setFlag(Location playerMove) {
        if (mineField[playerMove.getColumn()][playerMove.getRow()] == EMPTY_SPACE) {
            placeFlag(playerMove);
        } else if (mineField[playerMove.getColumn()][playerMove.getRow()] == FLAG) {
            removeFlag(playerMove);
        } else {
            // trying to flag a number
            System.out.println("Cannot flag a number");
        }
    }
    //method to look at that spot

    private void displayGrid() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");

        for (int i = 0; i < mineField.length; i++) {
            System.out.printf("%d|", i + 1);
            System.out.print(mineField[i]);
            System.out.println("|");
        }

        System.out.println("-|---------|");
    }

    private boolean isValidMove(Location playerMove) {
        if (dugSpaces.contains(playerMove)) {
            System.out.println("Space has been dug up already");
            return false;
        }
        return true;
    }

    private boolean isGameOver() {
        //code to check for loss
        boolean gameWin = isGameWon();
        boolean gameLoss = isGameLost();

        if (gameWin) {
            System.out.println("Congratulations! You found all mines!");
        } else if (gameLoss) {
            System.out.println("You stepped on a mine and failed!");
        }
        return gameWin || gameLoss;
    }

    private boolean isGameWon() {
        if (mines == null) {
            return false;
        }

        int numberOfMines = mines.size();
        int emptySpaces = 0;
        int numberOfFlags = 0;
        int correctlyPlacedFlags = 0;

        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                Location currentLocation = new Location(i, j);
                if (mineField[i][j] == FLAG) {
                    numberOfFlags++;
                    if (mines.contains(currentLocation)) {
                        correctlyPlacedFlags++;
                    }
                } else {
                    if (mineField[i][j] == EMPTY_SPACE) {
                        emptySpaces++;
                    } else if (mineField[i][j] == MINE) {
                        return false;
                    }
                }
            }
        }

        return correctlyPlacedFlags == numberOfMines && numberOfFlags == correctlyPlacedFlags ||
                emptySpaces == numberOfMines && numberOfFlags == 0;
    }

    private boolean isGameLost() {
        for (char[] row : mineField) {
            for (char space : row) {
                if (space == MINE) {
                    return true;
                }
            }
        }
        return false;
    }


    private static class Location {
        private final int column;
        private final int row;

        public Location(int column, int row) {
            this.column = column;
            this.row = row;
        }

        public int getColumn() {
            return column;
        }

        public int getRow() {
            return row;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Location location = (Location) o;
            return column == location.column && row == location.row;
        }

        @Override
        public int hashCode() {
            return Objects.hash(column, row);
        }
    }
}
