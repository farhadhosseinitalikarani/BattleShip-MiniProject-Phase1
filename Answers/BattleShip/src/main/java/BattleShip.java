import java.util.Random;
import java.util.Scanner;

public class BattleShip {

    static final int GRID_SIZE = 10;
    static char[][] player1Grid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player2Grid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player1TrackingGrid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player2TrackingGrid = new char[GRID_SIZE][GRID_SIZE];
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        initializeGrid(player1Grid);
        initializeGrid(player2Grid);
        initializeGrid(player1TrackingGrid);
        initializeGrid(player2TrackingGrid);

        placeShips(player1Grid);
        placeShips(player2Grid);

        boolean player1Turn = true;

        while (!isGameOver()) {
            if (player1Turn) {
                System.out.println("Player 1's  turn:");
                printGrid(player1TrackingGrid);
                playerTurn(player2Grid, player1TrackingGrid);
            } else {
                System.out.println("Player 2's  turn:");
                printGrid(player2TrackingGrid);
                playerTurn(player1Grid, player2TrackingGrid);
            }
            player1Turn = !player1Turn;
        }

        System.out.println("Game Over!");
    }

    static void initializeGrid(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = '~';
            }
        }
    }

    static void placeShips(char[][] grid) {
        int shipCount = 5;
        for (int i = 0; i < shipCount; i++) {
            int row = -1, col = -1;
            do {
                row = random.nextInt(GRID_SIZE);
                col = random.nextInt(GRID_SIZE);
            } while (grid[row][col] == 'S');
            grid[row][col] = 'S';
        }
    }

    static boolean canPlaceShip(char[][] grid, int row, int col, int size, boolean horizontal) {
        if (horizontal) {
            if (col + size > GRID_SIZE)
                return false;
            for (int i = 0; i < size; i++) {
                if (grid[row][col + i] == 'S')
                    return false;
            }
        } else {
            if (row + size > GRID_SIZE)
                return false;
            for (int i = 0; i < size; i++) {
                if (grid[row + i][col] == 'S')
                    return false;
            }
        }
        return true;
    }

    static void playerTurn(char[][] opponentGrid, char[][] trackingGrid) {
        int row = 0, col = 0;
        do {
            System.out.print("Enter coordinates (4 exampel A5): ");
            String input = scanner.next();
            if (input.length() != 2 || !isValidInput(input)) {
                System.out.println("Invalid input. Please enter a letter (A-J) followed by a number (0-9).");
                continue;
            }
            row = Character.toUpperCase(input.charAt(0)) - 'A';
            col = input.charAt(1) - '0';
        } while (row < 0 || row >= GRID_SIZE || col < 0 || col >= GRID_SIZE || trackingGrid[row][col] != '~');

        if (opponentGrid[row][col] == 'S') {
            System.out.println("Hit!");
            trackingGrid[row][col] = 'X';
            opponentGrid[row][col] = 'X';
        } else {
            System.out.println("Miss!");
            trackingGrid[row][col] = 'O';
        }
    }

    static boolean isGameOver() {
        return allShipsSunk(player1Grid) || allShipsSunk(player2Grid);
    }

    static boolean allShipsSunk(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 'S') {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean isValidInput(String input) {
        return input.matches("[A-Ja-j][0-9]");
    }


    static void printGrid(char[][] grid) {
        System.out.print("  ");
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
