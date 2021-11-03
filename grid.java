import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class grid extends JPanel {
    private static final Font FONT = new Font("Verdana", 
                                              Font.CENTER_BASELINE, 
                                              20);

    private final JTextField[][] sudokuGrid;
    private final Map<JTextField, Point> mapFieldToCoordinates = new HashMap<>();

    private final int dimension;
    private final JPanel gridPanel;
    private final JPanel buttonPanel;
    private final JButton solveButton;
    private final JButton clearButton;
    private final JPanel[][] minisquarePanels;
    public int[][] board = {
        {7, 8, 0, 4, 0, 0, 1, 2, 0},
        {6, 0, 0, 0, 7, 5, 0, 0, 9},
        {0, 0, 0, 6, 0, 1, 0, 7, 8},
        {0, 0, 7, 0, 4, 0, 2, 6, 0},
        {0, 0, 1, 0, 5, 0, 9, 3, 0},
        {9, 0, 4, 0, 6, 0, 0, 0, 5},
        {0, 7, 0, 3, 0, 0, 0, 1, 2},
        {1, 2, 0, 0, 0, 7, 4, 0, 0},
        {0, 4, 9, 2, 0, 6, 0, 0, 7}
    };
    public int[][] origBoard = {
        {7, 8, 0, 4, 0, 0, 1, 2, 0},
        {6, 0, 0, 0, 7, 5, 0, 0, 9},
        {0, 0, 0, 6, 0, 1, 0, 7, 8},
        {0, 0, 7, 0, 4, 0, 2, 6, 0},
        {0, 0, 1, 0, 5, 0, 9, 3, 0},
        {9, 0, 4, 0, 6, 0, 0, 0, 5},
        {0, 7, 0, 3, 0, 0, 0, 1, 2},
        {1, 2, 0, 0, 0, 7, 4, 0, 0},
        {0, 4, 9, 2, 0, 6, 0, 0, 7}
    };;

    public grid(int dimension) {
        this.sudokuGrid = new JTextField[dimension][dimension];
        this.dimension = dimension;

        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                JTextField field = new JTextField();
                field.setText(board[y][x] + "");
                if (board[y][x] != 0){
                    field.setEditable(false);
                }
                field.addKeyListener(new cell(this));
                mapFieldToCoordinates.put(field, new Point(x, y));
                sudokuGrid[y][x] = field;
            }
        }

        this.gridPanel   = new JPanel();
        this.buttonPanel = new JPanel();

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        Dimension fieldDimension = new Dimension(30, 30);

        class PopupMenuListener implements ActionListener {

            private final JTextField field;
            private final int number;
            
            PopupMenuListener(JTextField field, int number) {
                this.field  = field;
                this.number = number; 
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                field.setText("" + number);
            }
        }
        
        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                JTextField field = sudokuGrid[y][x];
                field.setBorder(border);
                field.setFont(FONT);
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setPreferredSize(fieldDimension);
                
                JPopupMenu menu = new JPopupMenu();
                
                for (int i = 0; i <= dimension; ++i) {
                    JMenuItem item = new JMenuItem("" + i);
                    menu.add(item);
                    item.addActionListener(new PopupMenuListener(field, i));
                }
                
                field.add(menu);
                field.setComponentPopupMenu(menu);
            }
        }

        int minisquareDimension = (int) Math.sqrt(dimension);
        this.gridPanel.setLayout(new GridLayout(minisquareDimension,
                                                minisquareDimension));

        this.minisquarePanels = new JPanel[minisquareDimension]
                                          [minisquareDimension];

        Border minisquareBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int y = 0; y < minisquareDimension; ++y) {
            for (int x = 0; x < minisquareDimension; ++x) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(minisquareDimension,
                                               minisquareDimension));
                panel.setBorder(minisquareBorder);
                minisquarePanels[y][x] = panel;
                gridPanel.add(panel);
            }
        }

        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                int minisquareX = x / minisquareDimension;
                int minisquareY = y / minisquareDimension;

                minisquarePanels[minisquareY][minisquareX].add(sudokuGrid[y][x]);
            }
        }

        this.gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 
                                                                2));
        this.clearButton = new JButton("Clear");
        this.solveButton = new JButton("Solve");

        this.buttonPanel.setLayout(new BorderLayout());
        this.buttonPanel.add(clearButton, BorderLayout.WEST);
        this.buttonPanel.add(solveButton, BorderLayout.EAST);

        this.setLayout(new BorderLayout());
        this.add(gridPanel,   BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);  

        clearButton.addActionListener((ActionEvent e) -> {
            clearAll();
        });

        solveButton.addActionListener((ActionEvent e) -> {
            solve();
        });
    }
    public void clearAll() {
        // for (JTextField[] row : sudokuGrid) {
        //     for (JTextField field : row) {
        //         field.setText("");
        //     }
        // }
        for (int r = 0; r < 9; r++){
            for (int c = 0; c < 9; c++){
                sudokuGrid[r][c].setText(origBoard[r][c] + "");
            }
        }
    }
    public void solve(){
        solver solution = new solver();
        if (solution.solve(board)){
            for (int r = 0; r < 9; r++){
                for (int c = 0; c < 9; c++){
                    sudokuGrid[r][c].setText(board[r][c] + "");
                }
            }
        }

    }
    public int getDimension() {
        return dimension;
    }
    void moveCursor(JTextField field, char c) {
        Point coordinates = mapFieldToCoordinates.get(field);
        field.setBackground(Color.WHITE);

        switch (c) {
            case 'w':
            case 'W':

                if (coordinates.y > 0) {
                    sudokuGrid[coordinates.y - 1][coordinates.x].requestFocus();
                    JTextField f = (sudokuGrid[coordinates.y - 1][coordinates.x]);
                    if (f.getText().isEmpty()) {
                        f.setText(" ");
                    }
                }

                break;

            case 'd':
            case 'D':

                if (coordinates.x < dimension - 1) {
                    sudokuGrid[coordinates.y][coordinates.x + 1].requestFocus();
                    JTextField f = (sudokuGrid[coordinates.y][coordinates.x + 1]);
                    if (f.getText().isEmpty()) {
                        f.setText(" ");
                    }
                }

                break;

            case 's':
            case 'S':

                if (coordinates.y < dimension - 1) {
                    sudokuGrid[coordinates.y + 1][coordinates.x].requestFocus();
                    JTextField f = (sudokuGrid[coordinates.y + 1][coordinates.x]);
                    if (f.getText().isEmpty()) {
                        f.setText(" ");
                    }
                }

                break;

            case 'a':
            case 'A':

                if (coordinates.x > 0) {
                    sudokuGrid[coordinates.y][coordinates.x - 1].requestFocus();
                    JTextField f = sudokuGrid[coordinates.y][coordinates.x - 1];
                    if (f.getText().isEmpty()) {
                        f.setText(" ");
                    }
                }

                break;
        }
    }

}
