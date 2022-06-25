import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{
    MainPanel mainPanel;
    JPanel menuPanel;
    JButton start,play,pause,stop;
    int noCells;
    MainFrame(int size){
        this.noCells = size;
        mainPanel = new MainPanel(size);
        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());
        menuPanel.setBackground(Color.darkGray);

        start = new JButton("Start");
        start.setForeground(Color.white);
        start.setFocusable(false);
        pause = new JButton("Pause");
        pause.setForeground(Color.white);
        pause.setFocusable(false);
        play = new JButton("Play");
        play.setForeground(Color.white);
        play.setFocusable(false);
        stop = new JButton("Stop");
        stop.setForeground(Color.white);
        stop.setFocusable(false);

        start.setBackground(Color.black);
        pause.setBackground(Color.black);
        stop.setBackground(Color.black);
        play.setBackground(Color.black);


        start.addActionListener(new StartActionListener());
        stop.addActionListener(new StopActionListener());
        pause.addActionListener(new PauseActionListener());
        play.addActionListener(new PlayActionListener());

        menuPanel.add(start);
        menuPanel.add(pause);
        menuPanel.add(stop);
        menuPanel.add(play);


        add(mainPanel,BorderLayout.CENTER);
        add(menuPanel,BorderLayout.SOUTH);
        setSize(size*10, size*10);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }


    public void start(){
        mainPanel.start();
    }

    private class StartActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            mainPanel.start();
        }
    }

    private class StopActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            mainPanel.stop();
            mainPanel.clear();
        }
    }

    private class PlayActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            mainPanel.resume();
        }
    }

    private class PauseActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            mainPanel.stop();
        }
    }

}

