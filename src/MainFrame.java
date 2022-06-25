import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{
    MainPanel mainPanel;
    JPanel menuPanel;
    JButton startStop;
    JButton playPause;
    JButton stepForward;
    ImageIcon playImage = new ImageIcon("IconImages/playIcon.png");
    ImageIcon pauseImage = new ImageIcon("IconImages/pauseIcon.png");
    ImageIcon stopImage = new ImageIcon("IconImages/stopIcon.png");
    ImageIcon nextImage = new ImageIcon("IconImages/nextIcon.png");

    int noCells;
    MainFrame(int size){
        this.noCells = size;
        mainPanel = new MainPanel(size);
        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());
        menuPanel.setBackground(Color.darkGray);

        startStop = new JButton("Start");
        startStop.setForeground(Color.BLACK);
        startStop.setFocusable(false);
        startStop.setPreferredSize(new Dimension(70,20));
        playPause = new JButton(pauseImage);
        playPause.setFocusable(false);
        playPause.setPreferredSize(new Dimension(20,20));

        stepForward = new JButton(nextImage);
        stepForward.setFocusable(false);
        stepForward.setPreferredSize(new Dimension(20,20));

        startStop.setBackground(Color.white);
        playPause.setBackground(Color.white);
        stepForward.setBackground(Color.white);

        startStop.addActionListener(new StartStopActionListener());
        playPause.addActionListener(new PlayPauseActionListener());
        stepForward.addActionListener(new StepForwardActionListener());

        menuPanel.add(startStop);
        menuPanel.add(playPause);
        menuPanel.add(stepForward);

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
                jb.setIcon(stopImage);
                jb.setText("");
            }
            else if(jb.getIcon().equals(stopImage)){
                mainPanel.stop();
                mainPanel.clear();
                jb.setIcon(null);
                jb.setText("Start");
                playPause.setIcon(pauseImage);
            }
        }
    }

    private class PlayPauseActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JButton jb = (JButton) actionEvent.getSource();
            try {
                if (jb.getIcon().equals(pauseImage)) {
                    mainPanel.stop();
                    jb.setIcon(playImage);
                } else if (jb.getIcon().equals(playImage)) {
                    mainPanel.resume();
                    jb.setIcon(pauseImage);
                }
            }catch (Exception ex){}
        }
    }

    private class StepForwardActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JButton jb = playPause;
            try {
                if (jb.getIcon().equals(pauseImage))
                    jb.setIcon(playImage);
                mainPanel.stop();
                mainPanel.nextCellState();
            }catch(Exception e){}
        }
    }

}

