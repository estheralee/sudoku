public class solver{
    public solver(){

    }
    public boolean solve(int[][] board){
        int[] emptyCoords = findEmpty(board);
        if (emptyCoords == null){  // The board does not have an empty space meaning that a solution was found
            return true;
        }

        int row = emptyCoords[0];
        int col = emptyCoords[1];

        // Try every single number to see if it is valid (9x9 sudoku board you'd have 9 options: 1-9)
        for (int i = 1; i < 10; i++){
            if (valid(board, i, emptyCoords)){  
                board[row][col] = i;

                if (solve(board)){  // For each potentially valid number, see if the board is solvable with that new value added to the board
                    return true;
                }
                board[row][col] = 0;    // If the number ends up not being valid then reset it to 0 and try a different number
            }
        }

        // The board is not solvable
        return false;   

    }
    public boolean valid(int[][] board, int num, int[] coords){
        int i;
        // Checks if each number in a row of sudoku board is unique
        for (i = 0; i < board[0].length; i++){
            if (board[coords[0]][i] == num && coords[1] != i){  // check each element in the row and see if it's equal to num 
                return false;                                   //but if it's the position we just inserted a number into then we'll ignore it
            }
        }
        // Checks if each number in a column of sudoku board is unique
        for (i = 0; i < board.length; i++){
            if (board[i][coords[1]] == num && coords[0] != i){  // check each element in the column and see if it's equal to num 
                return false;                                   //but if it's the position we just inserted a number into then we'll ignore it
            }
        }

        // Checks if each number in a box of sudoku board is unique
        int xCoordOfBox = coords[1] / 3;    // Gets which row of boxes we are looking at (9x9 sudoku board has 3 rows of boxes)
        int yCoordOfBox = coords[0] / 3;    // Gets which column of boxes we are looking at  (9x9 sudoku board has 3 boxes in each row)

        int [] currentPos = {0, 0};
        for (i = yCoordOfBox * 3; i < (yCoordOfBox * 3) + 3; i++){
            for (int j = xCoordOfBox * 3; j < (xCoordOfBox * 3) + 3; j++){
                currentPos[0] = i;
                currentPos[1] = j;
                if (board[i][j] == num && !currentPos.equals(coords)){    // check each element in the box and see if it's equal to num 
                    return false;                                         //but if it's the position we just inserted a number into then we'll ignore it
                }
            }
        }
        // There are no duplicates in the row, column, or box so the number is a valid entry
        return true;
    }
    public int[] findEmpty(int[][] board){
        int [] coords = {0, 0};
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                if (board[i][j] == 0){
                    coords[0] = i;
                    coords[1] = j;
                    return coords;
                }
            }
        }
        return null;
    }
    public void printBoard(int[][] board){
        System.out.println(" ---------------------------");
        for (int i = 0; i < board.length; i++){
            if (i % 3 == 0 && i != 0){
                System.out.println(" ---------------------------");
            }
            for (int j = 0; j < board[i].length; j++){
                if (j % 3 == 0){
                    System.out.print(" | ");
                }
                if (j == 8){
                    System.out.println(board[i][j] + " | ");
                }else{
                    System.out.print(board[i][j] + " ");
                }
                
            }
        }
        System.out.println(" ---------------------------");
    }
    
}