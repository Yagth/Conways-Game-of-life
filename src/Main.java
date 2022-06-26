import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    JFrame frame = new JFrame("Game of life");
    JPanel panel = new JPanel();

    JRadioButton hundredTwentyEight;
    JRadioButton sixteen;
    JRadioButton thirtyTwo;
    JRadioButton sixtyFour;
    JButton next;
    ButtonGroup group = new ButtonGroup();
    final int[] size = {0};
    Main(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(250,180);

         sixteen = new JRadioButton("16 x 16");
         thirtyTwo = new JRadioButton("32 x 32");
         sixtyFour = new JRadioButton("64 x 64");
        hundredTwentyEight = new JRadioButton("128 x 128");


        next = new JButton("Next");
         next.addActionListener(new NextButtonListener());

         hundredTwentyEight.addActionListener(new radioButtonListener());
        sixteen.addActionListener(new radioButtonListener());
        thirtyTwo.addActionListener(new radioButtonListener());
        sixtyFour.addActionListener(new radioButtonListener());

        group.add(sixteen);
        group.add(thirtyTwo);
        group.add(sixtyFour);
        group.add(hundredTwentyEight);

        JLabel label = new JLabel("Choose size");

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(label);
        panel.add(sixteen);
        panel.add(thirtyTwo);
        panel.add(sixtyFour);
        panel.add(hundredTwentyEight);
        panel.add(next);

        frame.add(panel,BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }
    class radioButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getSource().equals(hundredTwentyEight)){
                size[0] = 128;
            }
            else if(actionEvent.getSource().equals(sixteen)){
                size[0] = 16;
            }
            else if(actionEvent.getSource().equals(thirtyTwo)){
                size[0] = 32;
            } else if (actionEvent.getSource().equals(sixtyFour)) {
                size[0] = 64;
            }
        }
    }

    class NextButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            frame.dispose();
            new MainFrame(size[0]);
        }
    }
}
