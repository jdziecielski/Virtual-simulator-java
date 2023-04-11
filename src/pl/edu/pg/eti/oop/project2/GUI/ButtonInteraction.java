package pl.edu.pg.eti.oop.project2.GUI;

import pl.edu.pg.eti.oop.project2.Animals.*;
import pl.edu.pg.eti.oop.project2.Organism;
import pl.edu.pg.eti.oop.project2.Plants.*;
import pl.edu.pg.eti.oop.project2.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class ButtonInteraction implements ActionListener {
    private Board board;
    private World world;
    private GUI GUI;
    private JFrame organismFrame;
    private MyButton availableOrganismsToCreate[] = new MyButton[10];
    public ButtonInteraction(Board board, World world, GUI GUI) {
        this.board = board;
        this.world = world;
        this.GUI = GUI;
        availableOrganismsToCreate[0] = new MyButton("Wolf");
        availableOrganismsToCreate[1] = new MyButton("Sheep");
        availableOrganismsToCreate[2] = new MyButton("Fox");
        availableOrganismsToCreate[3] = new MyButton("Antelope");
        availableOrganismsToCreate[4] = new MyButton("Turtle");
        availableOrganismsToCreate[5] = new MyButton("Grass");
        availableOrganismsToCreate[6] = new MyButton("Hogweed");
        availableOrganismsToCreate[7] = new MyButton("SowThistle");
        availableOrganismsToCreate[8] = new MyButton("Guarana");
        availableOrganismsToCreate[9] = new MyButton("Belladonna");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //hold button's coordinates
        MyButton clickedButton = (MyButton) e.getSource();
        int clickedButtonPosX = clickedButton.getPosX();
        int clickedButtonPosY = clickedButton.getPosY();
        if (world.getOrganismAtPosition(clickedButtonPosX, clickedButtonPosY) == null) {
            organismFrame = new JFrame("Add new organism");

            organismFrame.setSize(200, 600);
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(10, 1));
            addOrganismFromButton addOrganismFromButton = new addOrganismFromButton(organismFrame, world, board, GUI, panel, clickedButtonPosX, clickedButtonPosY);
            for (MyButton b: availableOrganismsToCreate) {
                b.setText(b.getOrganismName());
                b.addActionListener(addOrganismFromButton);
                b.setBackground(Color.pink);
                b.setForeground(Color.black);
                panel.add(b);
            }
            organismFrame.requestFocusInWindow();
            organismFrame.add(panel);
            organismFrame.setVisible(true);
        }
    }
}

class addOrganismFromButton implements ActionListener {

    private World world;
    private Board board;
    private GUI GUI;
    private JPanel panel;
    private int clickedX, clickedY;
    private JFrame addOrganismFrame;

    public addOrganismFromButton(JFrame addOrganismFrame, World world, Board board, GUI GUI, JPanel panel, int x, int y) {
        this.addOrganismFrame = addOrganismFrame;
        this.world = world;
        this.board = board;
        this.GUI = GUI;
        this.panel = panel;
        this.clickedX = x;
        this.clickedY = y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MyButton clickedButton = (MyButton) e.getSource();
        String organismName = clickedButton.getOrganismName();

        if (Objects.equals(organismName, "Wolf")) { addOrganismFromClickedButton(new Wolf(clickedX, clickedY, world, board, GUI));}
        else if (Objects.equals(organismName, "Sheep")) { addOrganismFromClickedButton(new Sheep(clickedX, clickedY, world, board, GUI)); }
        else if (Objects.equals(organismName, "Fox")) { addOrganismFromClickedButton(new Fox(clickedX, clickedY, world, board, GUI)); }
        else if (Objects.equals(organismName, "Turtle")) { addOrganismFromClickedButton(new Turtle(clickedX, clickedY, world, board, GUI)); }
        else if (Objects.equals(organismName, "Antelope")) { addOrganismFromClickedButton(new Antelope(clickedX, clickedY, world, board, GUI)); }
        else if (Objects.equals(organismName, "Grass")) { addOrganismFromClickedButton(new Grass(clickedX, clickedY, world, board, GUI)); }
        else if (Objects.equals(organismName, "SowThistle")) { addOrganismFromClickedButton(new SowThistle(clickedX, clickedY, world, board, GUI)); }
        else if (Objects.equals(organismName, "Guarana")) { addOrganismFromClickedButton(new Guarana(clickedX, clickedY, world, board, GUI)); }
        else if (Objects.equals(organismName, "Belladonna")) { addOrganismFromClickedButton(new Belladonna(clickedX, clickedY, world, board, GUI)); }
        else if (Objects.equals(organismName, "Hogweed")) { addOrganismFromClickedButton(new SosnowskysHogweed(clickedX, clickedY, world, board, GUI)); }

        addOrganismFrame.dispatchEvent(new WindowEvent(addOrganismFrame, WindowEvent.WINDOW_CLOSING));
        board.updateBoard();
//        GUI.requestFocus();
    }

    private void addOrganismFromClickedButton(Organism newOrganism) {
        world.getOrganismArrayList().add(newOrganism);
        world.sortOrganisms();
    }
}