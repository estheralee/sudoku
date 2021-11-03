import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

public class cell implements KeyListener {

    private final grid sudokuGrid;

    public cell (grid sudokuGrid) {
        this.sudokuGrid = sudokuGrid;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        JTextField textField = (JTextField) e.getSource();
        
        switch (c) {
            case 'a':
            case 'A':
            case 's':
            case 'S':
            case 'd':
            case 'D':
            case 'w':
            case 'W':
                e.consume();
                sudokuGrid.moveCursor(textField, c);
        }
        
        String s = "" + sudokuGrid.getDimension();
        int digits = s.length();
        
        if (textField.getText().length() >= digits) {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
