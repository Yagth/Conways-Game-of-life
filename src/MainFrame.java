import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class MainFrame extends JFrame{
    MainPanel mainPanel;
    JPanel menuPanel;
    JButton startStop;
    JButton playPause;
    JButton stepForward;
    JMenuBar menuBar;
    JMenu menu,newCells;
    JMenuItem save, load, random, custom, changeSize;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    ImageIcon playImage = new ImageIcon("IconImages/playIcon.png");
    ImageIcon pauseImage = new ImageIcon("IconImages/pauseIcon.png");
    ImageIcon stopImage = new ImageIcon("IconImages/stopIcon.png");
    ImageIcon nextImage = new ImageIcon("IconImages/nextIcon.png");

    int noCells;
    boolean isCustom = false;
    MainFrame(int size){
        setSize(800, 800);

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

        menuBar = new JMenuBar();
        menuBar.setBackground(Color.DARK_GRAY);

        menu = new JMenu("File");
        menu.setForeground(Color.white);
        newCells = new JMenu("New Cells");
        newCells.setForeground(Color.white);

        save = new JMenuItem("Save");
        load = new JMenuItem("Load");
        random = new JMenuItem("Random");
        custom = new JMenuItem("Custom");
        changeSize = new JMenuItem("Change size");

        save.addActionListener(new SaveActionListener());
        load.addActionListener(new LoadActionListener());
        random.addActionListener(new RandomActionListener());
        custom.addActionListener(new CustomActionListener());
        changeSize.addActionListener(new ChangeSizeListener());

        menu.add(save);
        menu.add(load);
        newCells.add(random);
        newCells.add(custom);
        newCells.add(changeSize);

        menuBar.add(menu);
        menuBar.add(newCells);

        setBackground(Color.black);
        setTitle("Game of Life");
        this.setJMenuBar(menuBar);
        add(mainPanel,BorderLayout.CENTER);
        add(menuPanel,BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class StartStopActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JButton jb = (JButton) actionEvent.getSource();
            if(jb.getText().equals("Start")){
                if(isCustom){
                    mainPanel.customStart();
                }
                else{
                    mainPanel.randomStart();
                }
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

    private class SaveActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showSaveDialog(mainPanel);
            try {
                FileOutputStream outputFileStream = new FileOutputStream(fileChooser.getSelectedFile());
                outputStream = new ObjectOutputStream(outputFileStream);
                outputStream.writeObject(mainPanel.copyCellMap);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(mainPanel,"Couldn't save the file",null,JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class LoadActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(mainPanel);
            try {
                FileInputStream fileInputStream = new FileInputStream(fileChooser.getSelectedFile());
                inputStream = new ObjectInputStream(fileInputStream);
                mainPanel.cellMap = (boolean[][]) inputStream.readObject();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(mainPanel,"Couldn't Open the file",null,JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class RandomActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            isCustom = false;
            startStop.setText("Start");
            startStop.setIcon(null);
            mainPanel.generateRandomCells();
        }
    }

    private class CustomActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            isCustom = true;
            mainPanel.stop();
            mainPanel.clear();
            startStop.setText("Start");
            startStop.setIcon(null);
            mainPanel.addCustomButtons();
        }
    }

    private class ChangeSizeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new Main();
            MainFrame.this.dispose();
        }
    }

}

