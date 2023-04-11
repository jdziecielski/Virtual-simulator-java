package pl.edu.pg.eti.oop.project2.GUI;

import pl.edu.pg.eti.oop.project2.Animal;
import pl.edu.pg.eti.oop.project2.Animals.Human;
import pl.edu.pg.eti.oop.project2.Organism;
import pl.edu.pg.eti.oop.project2.Plant;
import pl.edu.pg.eti.oop.project2.World;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    private int mapX, mapY;
    private JPanel windowContent;
    private JPanel bottomContent;
    private JPanel functionalityPanel;
    private MyButton[][] buttons;
    private World world;
    private JTextArea textArea = new JTextArea();
    private FunctionalityButtonListener functionalityButtonListener;
    private JButton functionalityButtonArray[];
    private GUI GUI;

    public Board(int mapX, int mapY, World world, GUI GUI) {
        this.mapX = mapX;
        this.mapY = mapY;
        this.world = world;
        this.GUI = GUI;
        this.windowContent = new JPanel(new GridLayout(2, 1));
        this.bottomContent = new JPanel(new GridLayout(1, 2));
        functionalityButtonListener = new FunctionalityButtonListener(this, world, GUI);
        this.functionalityButtonArray = new JButton[4];
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(mapY, mapX));

        //initialize board
        buttons = new MyButton[mapY][mapX];
        for (int y = 0; y < mapY; y++) {
            for (int x = 0; x < mapX; x++) {
                buttons[y][x] = new MyButton(x, y);
                buttons[y][x].setBackground(Color.BLACK);
                buttons[y][x].setText(" ");
                buttons[y][x].addActionListener(new ButtonInteraction(this, world, GUI));
                board.add(buttons[y][x]);
            }
        }
        Border border = BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder());
        board.setBorder(border);

        JScrollPane textBox = new JScrollPane(textArea);
        textArea = new JTextArea("Events are going to appear here. \n", 8, 30);
        textArea.setLineWrap(true);
        textArea.setLineWrap(true);
        textBox = new JScrollPane(textArea);
        textBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        textBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textBox.setBorder(border);


        functionalityPanel = new JPanel();
        functionalityPanel.setLayout(new GridLayout(mapX/4, mapY/2));
        functionalityPanel.setBackground(Color.BLACK);
        functionalityButtonArray[0] = new JButton("Start Game");
        functionalityButtonArray[1] = new JButton("Save Game");
        functionalityButtonArray[2] = new JButton("Load Game");
        functionalityButtonArray[3] = new JButton("End Game");
        for (JButton b: functionalityButtonArray) {
            b.setBackground(Color.pink);
            b.setBorder(border);
            b.addActionListener(functionalityButtonListener);
            functionalityPanel.add(b);
        }
        bottomContent.add(textBox);
        bottomContent.add(functionalityPanel);

        windowContent.add(board);
        windowContent.add(bottomContent);
        windowContent.requestFocus();
    }

    public int getCurrentTurnFromFile(){
        return functionalityButtonListener.getCurrentTurnFromFile();
    }

    public ArrayList<Organism> getOrganismArrayListFromFile() {
        return functionalityButtonListener.getOrganismArrayListFromFile();
    }

    public int getMaxTurnsFromLoadedFile() {
        return functionalityButtonListener.getMaxTurnsFromLoadedFile();
    }

    public JPanel getFunctionalityPanel() {
        return functionalityPanel;
    }

    public boolean ifElementInButtonArray(JButton element) {
        for (JButton b: functionalityButtonArray) {
            if (element == b) return true;
        }
        return false;
    }

    public JPanel getWindowContent() {
        return windowContent;
    }

    public void displayCommand(String command) {
        textArea.append(command);
    }

    public void clearTextArea() {
        textArea.setText(null);
    }

    public void drawPurificationArea() {
        world.setAdjacentPoints(world.getHuman().getPosX(), world.getHuman().getPosY());
        for (Point p: world.getAdjacentPoints()) {
            if (p.x >= 0 && p.y >= 0 && p.x < world.getWorldX() && p.y < world.getWorldY())
            buttons[p.y][p.x].setBackground(Color.pink);
        }

        windowContent.revalidate();
        windowContent.repaint();
    }

    public void updateBoard() {
        for (Organism organism : world.getOrganismArrayList()) {
            int posX = organism.getPosX();
            int posY = organism.getPosY();
            for (int y = 0; y < mapY; y++) {
                for (int x = 0; x < mapX; x++) {
                    if (x == posX && y == posY) {
                        buttons[y][x].setText(organism.draw());

                        if (world.getOrganismAtPosition(x, y) instanceof Animal) buttons[y][x].setForeground(Color.RED);
                        else if (world.getOrganismAtPosition(x, y) instanceof Plant) buttons[y][x].setForeground(Color.GREEN);

                        if (world.getOrganismAtPosition(x, y) instanceof Human) {
                            buttons[y][x].setForeground(Color.BLUE);
                        }
                    }
                }
            }
        }

        for (int y = 0; y < mapY; y++) {
            for (int x = 0; x < mapX; x++) {
                if (world.getOrganismAtPosition(x, y) == null)
                buttons[y][x].setText(" ");
                buttons[y][x].setBackground(Color.BLACK);
            }
        }
        windowContent.revalidate();
        windowContent.repaint();
    }
}

class MyButton extends JButton {
    private int posX, posY;
    private String organismName;

    public MyButton(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public MyButton(String organismName) {
        this.organismName = organismName;
    }

    public String getOrganismName() {
        return organismName;
    }

    public void setOrganismName(String organismName) {
        this.organismName = organismName;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
