
import javax.swing.*;
class gui{
    public static void main (String args[]) {
       JFrame frame = new JFrame("SportsLabs");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(300,300);
       JButton button = new JButton("Start test");
       frame.getContentPane().add(button); // Adds Button to content pane of frame
       frame.setVisible(true);
    }
}
