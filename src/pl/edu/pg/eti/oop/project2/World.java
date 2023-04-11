package pl.edu.pg.eti.oop.project2;

import pl.edu.pg.eti.oop.project2.Animals.*;
import pl.edu.pg.eti.oop.project2.GUI.Board;
import pl.edu.pg.eti.oop.project2.GUI.GUI;
import pl.edu.pg.eti.oop.project2.Plants.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class World {
    private final int worldX;
    private final int worldY;
    private ArrayList<Organism> organismArrayList = new ArrayList<Organism>();
    private ArrayList<Point> adjacentPoints = new ArrayList<Point>();
    private GUI GUI;
    private Human human;
    private final Comparator<Organism> organismComparator = Comparator.comparing(Organism::getInitiative).thenComparing(Organism::getAge);
    private boolean wereOrganismsAdded = false;
    private final int maxTurns;
    private Board board;
    private boolean gameStarted = false;
    private boolean gameStartedFromFile = false;
    private int currentTurn;

    public World(int worldX, int worldY, int maxTurns) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.GUI = new GUI(worldX, worldY, 1200, 800, this);
        this.board = GUI.getBoard();
        this.maxTurns = maxTurns;
        startGame();
    }

    public int getCurrentTurn() { return currentTurn; }

    public void setHuman(Human human) {
        this.human = human;
    }

    public void setGUI(pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        this.GUI = GUI;
    }

    private void initOrganisms() {
        this.human = new Human(4, 5, this, board, GUI);
        addOrganism(human);
        //animals
        addOrganism(new Turtle(2, 8,this, board, GUI));
        addOrganism(new Turtle(0, 4,this, board, GUI));
        addOrganism(new Fox(1, 4, this, board, GUI));
        addOrganism(new Fox(1, 5, this, board, GUI));
        addOrganism(new Fox(6, 0, this, board, GUI));
        addOrganism(new Antelope(2, 7, this, board, GUI));
        addOrganism(new Antelope(1, 7, this, board, GUI));
        addOrganism(new Sheep(8, 3, this, board, GUI));
        addOrganism(new Sheep(7, 3, this, board, GUI));
        addOrganism(new Wolf(8, 6, this, board, GUI));
        addOrganism(new Wolf(8, 7, this, board, GUI));
        addOrganism(new Wolf(7, 7, this, board, GUI));

        //plants
        addOrganism(new Grass(0,0, this, board, GUI));
        addOrganism(new Grass(4,4, this, board, GUI));
        addOrganism(new Grass(1, 0, this, board, GUI));
        addOrganism(new SowThistle(4, 0, this, board, GUI));
        addOrganism(new Guarana(6, 5, this,board,GUI));
        addOrganism(new Belladonna(6, 6, this, board, GUI));
        addOrganism(new SosnowskysHogweed(4, 7, this, board, GUI));
        sortOrganisms();
    }

    public int getMaxTurns() { return maxTurns; }

    public int getWorldX() { return worldX; }

    public int getWorldY() { return worldY; }

    public Human getHuman() { return human; }

    public boolean isHumanAlive() {
        for (Organism it: organismArrayList) {
            if (it instanceof Human) return true;
        }
        return false;
    }

    public ArrayList<Point> getAdjacentPoints() {
        return adjacentPoints;
    }

    public void setAdjacentPoints(int x, int y) {
        adjacentPoints.clear();
        adjacentPoints.add(new Point(x, y-1));
        adjacentPoints.add(new Point(x+1, y-1));
        adjacentPoints.add(new Point(x+1, y));
        adjacentPoints.add(new Point(x+1, y+1));
        adjacentPoints.add(new Point(x, y+1));
        adjacentPoints.add(new Point(x-1, y+1));
        adjacentPoints.add(new Point(x-1, y));
        adjacentPoints.add(new Point(x-1, y-1));
    }

    public ArrayList<Organism> getOrganismArrayList() { return organismArrayList; }

    public void sortOrganisms() { organismArrayList.sort(organismComparator.reversed()); }

    public void addOrganism(Organism newOrganism) {
        organismArrayList.add(newOrganism);
        newOrganism.setIfJustBorn(true);
        wereOrganismsAdded = true;
    }

    public void setGameStartedFromFile(boolean gameStartedFromFile) {
        this.gameStartedFromFile = gameStartedFromFile;
    }

    public void setIfJustBornToFalse() {
        for (Organism it: organismArrayList)
            it.setIfJustBorn(false);
    }

    public void deleteOrganism(Organism toDelete) {
        for (int i = 0; i < organismArrayList.size(); i++) {
            if (organismArrayList.get(i) == toDelete) {
                organismArrayList.remove(i);
                break;
            }
        }
    }

    public Organism getOrganismAtPosition(int x, int y) {
        if (x >= 0 && y >= 0 && x <= worldX - 1 && y <= worldY - 1) {
            for (Organism organism: organismArrayList) {
                if (organism.getPosX() == x && organism.getPosY() == y)
                    return organism;
            }
            return null;
        }
        return null;
    }

    public void delay3s () {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makeTurn() {
        GUI.setMoveWasMadeToFalse();
        GUI.requestFocus();
        GUI.displayCommand("Make your move!\n\n");
        delay3s();
        if (GUI.getSpecialAbilityCooldown() > 0) {
            GUI.setSpecialAbilityCooldown(GUI.getSpecialAbilityCooldown() - 1);
            GUI.displayCommand("Special ability is on cooldown for " + GUI.getSpecialAbilityCooldown() + " more turns...\n");
        }

        for (int i = 0; i < organismArrayList.size(); i++) {
            Organism temp = organismArrayList.get(i);
            if (temp != null && !temp.ifJustBorn) {
                temp.setAge(temp.getAge() + 1);
                temp.action();
                if (temp instanceof Human && GUI.wasSpecialAbilityUsed() && GUI.getSpecialAbilityTurnsRemaining() == 0) {
                    GUI.setSpecialAbilityTurnsRemaining(5);
                    GUI.setWasSpecialAbilityUsed(false);
                }
            }
            else continue;
        }
    }

    public void endGame() {
        this.GUI.closeGUI();
    }

    public void startGame() {
        delay3s();
        delay3s();
        if (gameStarted) {
            currentTurn = 0;
            initOrganisms();
            GUI.updateBoard();
            GUI.displayCommand("Initial state of the game.\n");
            delay3s();
            GUI.clearTextArea();
            game(maxTurns);
        } else if (gameStartedFromFile) {
            organismArrayList = board.getOrganismArrayListFromFile();
            currentTurn = board.getCurrentTurnFromFile();
            sortOrganisms();
            GUI.updateBoard();
            GUI.displayCommand("Initial state of the game.\n");
            delay3s();
            GUI.clearTextArea();
            game(board.getMaxTurnsFromLoadedFile());
        }
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    private void game(int maxTurns) {
        wereOrganismsAdded = false;
        setIfJustBornToFalse();
        while (currentTurn < maxTurns){
            GUI.displayCommand("Turn: " + (currentTurn+1) + "/" + maxTurns + '\n');
            GUI.updateBoard();
            makeTurn();

            //if purification
            if (GUI.wasSpecialAbilityUsed() && GUI.getSpecialAbilityTurnsRemaining() > 0) {
                GUI.updateBoard();
                delay3s();
                human.usePurification();
                GUI.setSpecialAbilityTurnsRemaining(GUI.getSpecialAbilityTurnsRemaining() - 1);
                if (GUI.getSpecialAbilityTurnsRemaining() == 0) {
                    GUI.displayCommand("The special ability has ended!\n");
                    GUI.setSpecialAbilityCooldown(5);
                }
                GUI.updateBoard();
                if (GUI.wasSpecialAbilityUsed()) GUI.drawPurificationArea();
            } else {
                GUI.updateBoard();
                delay3s();
            }

            //end of turn
            GUI.displayCommand("\nEnd of turn: " + (currentTurn+1) + "/" + maxTurns + '\n');
            delay3s();

            if (!isHumanAlive()) {
                GUI.clearTextArea();
                GUI.displayCommand("Human has died! The game ends now ;-((...\n");
                delay3s();
                GUI.closeGUI();
                return;
            }

            if (wereOrganismsAdded) sortOrganisms();
            GUI.clearTextArea();

            if (currentTurn == maxTurns) {
                GUI.displayCommand("The game has ended!\n");
                delay3s();
            }
            currentTurn++;
        }
        endGame();
    }
}