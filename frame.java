import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;


public class frame {
    private JFrame sudokuFrame = new JFrame("SUDOKU");
    public frame() {
        sudokuFrame.getContentPane().add(new grid(9));
        sudokuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // buildMenu();
        sudokuFrame.pack();
        sudokuFrame.setResizable(false);
        centerView();
        sudokuFrame.setVisible(true);
    }
    public void centerView() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = sudokuFrame.getSize();

        sudokuFrame.setLocation((screen.width - frameSize.width) >> 1,
                          (screen.height - frameSize.height) >> 1);
    }
}
