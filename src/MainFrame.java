import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{
    MainPanel mainPanel;
    JPanel menuPanel;
    JButton start,playPause,stop;
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
        playPause = new JButton("Pause");
        playPause.setForeground(Color.white);
        playPause.setFocusable(false);
        stop = new JButton("Stop");
        stop.setForeground(Color.white);
        stop.setFocusable(false);

        start.setBackground(Color.black);
        stop.setBackground(Color.black);
        playPause.setBackground(Color.black);


        start.addActionListener(new StartActionListener());
        stop.addActionListener(new StopActionListener());
        playPause.addActionListener(new PlayPauseActionListener());

        menuPanel.add(start);
        menuPanel.add(playPause);
        menuPanel.add(stop);


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

    private class PlayPauseActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JButton jb = (JButton) actionEvent.getSource();
            if(jb.getText().equals("Pause")){
                mainPanel.stop();
                jb.setText("Play");
            }
            else if(jb.getText().equals("Play")) {
                mainPanel.resume();
                jb.setText("Pause");
            }
        }
    }

}

