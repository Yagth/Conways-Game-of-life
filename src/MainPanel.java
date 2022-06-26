import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MainPanel extends JPanel {
    int size;
    boolean[][] cellMap;
    boolean[][] copyCellMap;
    JButton[][] cells;
    Timer timer;

    MainPanel(int size){
        this.size = size;
        cellMap = new boolean[size][size];
        copyCellMap = new boolean[size][size];
        cells = new JButton[size][size];

        setBackground(Color.black);
        setSize(500,500);
        setLayout(new GridLayout(size,size));
        addButtons();
    }

    public void generateRandomCells(){
        Random rnd = new Random();
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                try{
                    removeActionListener();
                }catch(Exception ex) {
                }
                cellMap[i][j] = rnd.nextInt(100)<30;
                copyCellMap[i][j] = cellMap[i][j];
            }
        }

    }

    void removeActionListener(){
        for(JButton[] jb : cells){
            for(JButton jb2 : jb){
                for(ActionListener al: jb2.getActionListeners()){
                    jb2.removeActionListener(al);
                }
            }
        }
    }

    void addActionListeners(){
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                cells[i][j].addActionListener(new CellActionListener());
            }
        }
    }

    private void generateCustomCells(){
        setCellBorder(Color.white);
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                cellMap[i][j] = !cells[i][j].getBackground().equals(Color.black);
                copyCellMap[i][j] = cellMap[i][j];
            }
        }
    }

    private void addButtons(){
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size;j++){
                JButton tmp = new JButton();
                tmp.setFocusable(false);
                tmp.setBorder(BorderFactory.createLineBorder(Color.black));

                if(cellMap[i][j])
                    tmp.setBackground(getRandomColor());
                else
                    tmp.setBackground(Color.black);
                add(tmp);
                cells[i][j] = tmp;
            }
        }
    }

     void addCustomButtons(){
        setCellBorder(Color.white);
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size;j++){
                cells[i][j].addActionListener(new CellActionListener());
            }
        }
    }

    public void setCellBorder(Color color){
        for(JButton[] jb: cells){
            for(JButton jb2: jb){
                jb2.setBorder(BorderFactory.createLineBorder(color));
            }
        }
    }
    public void randomStart(){
        setCellBorder(Color.black);
        generateRandomCells();
        timer = new Timer(100,new timerListener());
        timer.start();
    }

    public void customStart(){
        generateCustomCells();
        setCellBorder(Color.black);
        timer = new Timer(100,new timerListener());
        timer.start();
    }

    public void stop(){
        setCellBorder(Color.white);
        timer.stop();
    }

    public void clear(){
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                cellMap[i][j] = false;
            }
        }
        renderNextCells();
    }

    public void resume(){
        setCellBorder(Color.black);
        timer.start();
    }

    int countAliveNeighbors(int x, int y){
        int count  = 0;

        for(int i = x-1; i<=x+1; i++){
            for(int j = y-1; j<= y+1; j++){
                try{
                    if(cellMap[i][j])
                        count++;
                }catch(Exception e){
                }
            }
        }
        if(cellMap[x][y])
            count -- ;
        return count;
    }

    void renderNextCells(){
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                if(cellMap[i][j])
                    cells[i][j].setBackground(getRandomColor());
                else
                    cells[i][j].setBackground(Color.black);
            }
        }
    }

    Color getRandomColor(){
        Random random = new Random();
        Color color = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
        if(color == Color.black)
            color = getRandomColor();
        return color;
    }

    public void nextCellState(){
        boolean[][] tmp  = new boolean[size][size];
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                int count = countAliveNeighbors(i,j);
                if(cellMap[i][j]){
                    if(count<2)
                        tmp[i][j] = false;
                    if(count == 3 || count == 2)
                        tmp[i][j] = true;
                    if(count>3)
                        tmp[i][j] = false;
                }
                else{
                    if(count == 3)
                        tmp[i][j] = true;
                }
            }
        }
        cellMap = tmp;
        renderNextCells();
    }

    private class timerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            nextCellState();
        }
    }

    private class CellActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JButton jb = (JButton) actionEvent.getSource();
            if(jb.getBackground().equals(Color.black)){
                jb.setBackground(getRandomColor());
            }
            else{
                jb.setBackground(Color.black);
            }
        }
    }
}

