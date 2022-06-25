import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{
    MainPanel mainPanel;
    JPanel menuPanel;
    JButton startStop;
    JButton playPause;
    int noCells;
    MainFrame(int size){
        this.noCells = size;
        mainPanel = new MainPanel(size);
        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());
        menuPanel.setBackground(Color.darkGray);

        startStop = new JButton("Start");
        startStop.setForeground(Color.white);
        startStop.setFocusable(false);
        playPause = new JButton("Pause");
        playPause.setForeground(Color.white);
        playPause.setFocusable(false);


        startStop.setBackground(Color.black);
        playPause.setBackground(Color.black);


        startStop.addActionListener(new StartStopActionListener());
        playPause.addActionListener(new PlayPauseActionListener());

        menuPanel.add(startStop);
        menuPanel.add(playPause);


        add(mainPanel,BorderLayout.CENTER);
        add(menuPanel,BorderLayout.SOUTH);
        setSize(size*10, size*10);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }


    public void start(){
        mainPanel.start();
    }

    private class StartStopActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JButton jb = (JButton) actionEvent.getSource();
            if(jb.getText().equals("Start")){
                mainPanel.start();
                jb.setText("Stop");
            }
            else if(jb.getText().equals("Stop")){
                mainPanel.stop();
                mainPanel.clear();
                jb.setText("Start");
            }
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

