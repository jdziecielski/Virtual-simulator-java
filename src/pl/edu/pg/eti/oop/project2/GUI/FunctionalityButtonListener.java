package pl.edu.pg.eti.oop.project2.GUI;

import pl.edu.pg.eti.oop.project2.Animals.*;
import pl.edu.pg.eti.oop.project2.Organism;
import pl.edu.pg.eti.oop.project2.Plants.*;
import pl.edu.pg.eti.oop.project2.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class FunctionalityButtonListener implements ActionListener {
    private Board board;
    private World world;
    private GUI GUI;
    private ArrayList<Organism> organismArrayListFromFile;
    private int maxTurnsFromLoadedFile;
    private int currentTurn;

    public FunctionalityButtonListener(Board board, World world, GUI GUI) {
        this.board = board;
        this.world = world;
        this.GUI = GUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        if (board.ifElementInButtonArray(clickedButton)) {
            if (clickedButton.getText() == "Start Game") {
                world.setGameStarted(true);
            }
            else if (clickedButton.getText() == "Load Game") {
                world.setGameStartedFromFile(true);
                organismArrayListFromFile = new ArrayList<>();
                FileDialog fd = new FileDialog(new JFrame());
                fd.setVisible(true);
                File[] f = fd.getFiles();
                String path = null;
                if(f.length > 0) {
                    path = fd.getFiles()[0].getAbsolutePath();
                }
                world.delay3s();

                File file = new File(path);

                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    int lineNum = 0;
                    while ((line = br.readLine()) != null) {
                        if (lineNum == 0) maxTurnsFromLoadedFile = Integer.parseInt(line);
                        else if (lineNum == 1) currentTurn = Integer.parseInt(line);
                        else {
                            String[] words = line.split(" ");
                            String organismName = words[0];
                            int newStr = Integer.parseInt(words[1]);
                            int newIni = Integer.parseInt(words[2]);
                            int newAge = Integer.parseInt(words[3]);
                            int newX = Integer.parseInt(words[4]);
                            int newY = Integer.parseInt(words[5]);
                            addOrganismFromFile(organismName, newStr, newIni, newAge, newX, newY);
                        }
                        lineNum++;
                    }
                }
                catch (Exception ignored) {}

            }
            else if (clickedButton.getText() == "Save Game"){
                //creating file to store data
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss");
                    LocalDateTime now = LocalDateTime.now();
                    String fileName = dtf.format(now);
                    String path = "saves/" + fileName + ".txt";
                    File f1 = new File(path);
                    if(!f1.exists()) {
                        f1.createNewFile();
                    }

                    // writing to file
                    Files.write(Paths.get(path), (world.getMaxTurns() + "\n" + world.getCurrentTurn() + "\n").getBytes(), StandardOpenOption.APPEND);
                    for (Organism o: world.getOrganismArrayList())
                        Files.write(Paths.get(path), (o.draw() + " " + o.getStrength() + " " + o.getInitiative() + " " + o.getAge() + " " + o.getPosX() + " " + o.getPosY() + "\n").getBytes(), StandardOpenOption.APPEND);

                } catch(IOException error) {
                    error.printStackTrace();
                }
            }
            else if (clickedButton.getText() == "End Game") {
                this.world.endGame();
            }
        }
    }

    public int getCurrentTurnFromFile() {
        return currentTurn;
    }

    public int getMaxTurnsFromLoadedFile() {
        return maxTurnsFromLoadedFile;
    }

    public ArrayList<Organism> getOrganismArrayListFromFile() {
        return organismArrayListFromFile;
    }

    public void addOrganismFromFile(String organismName, int str, int ini, int age, int x, int y) {
        if (Objects.equals(organismName, "Wolf")) { organismArrayListFromFile.add(new Wolf(str, ini, age, x, y, world, board, GUI)); }
        else if (Objects.equals(organismName, "Sheep")) { organismArrayListFromFile.add(new Sheep(str, ini, age, x, y, world, board, GUI)); }
        else if (Objects.equals(organismName, "Fox")) { organismArrayListFromFile.add(new Fox(str, ini, age, x, y, world, board, GUI)); }
        else if (Objects.equals(organismName, "Turtle")) { organismArrayListFromFile.add(new Turtle(str, ini, age, x, y, world, board, GUI)); }
        else if (Objects.equals(organismName, "Antelope")) { organismArrayListFromFile.add(new Antelope(str, ini, age, x, y, world, board, GUI));}
        else if (Objects.equals(organismName, "Grass")) { organismArrayListFromFile.add(new Grass(str, ini, age, x, y, world, board, GUI)); }
        else if (Objects.equals(organismName, "SowThistle")) { organismArrayListFromFile.add(new SowThistle(str, ini, age, x, y, world, board, GUI)); }
        else if (Objects.equals(organismName, "Guarana")) { organismArrayListFromFile.add(new Guarana(str, ini, age, x, y, world, board, GUI)); }
        else if (Objects.equals(organismName, "Belladonna")) { organismArrayListFromFile.add(new Belladonna(str, ini, age, x, y, world, board, GUI)); }
        else if (Objects.equals(organismName, "Hogweed")) { organismArrayListFromFile.add(new SosnowskysHogweed(str, ini, age, x, y, world, board, GUI)); }
        else if (Objects.equals(organismName, "HUMAN")) {
            Human human = new Human(str, ini, age, x, y, world, board, GUI);
            world.setHuman(human);
            organismArrayListFromFile.add(human);
        }
    }
}
