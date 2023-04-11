package pl.edu.pg.eti.oop.project2;

import pl.edu.pg.eti.oop.project2.GUI.Board;
import pl.edu.pg.eti.oop.project2.GUI.GUI;
import pl.edu.pg.eti.oop.project2.Plants.*;

import java.util.Objects;
import java.util.Random;

public abstract class Plant extends Organism {
    public Plant(int strength, int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(strength, 0, posX, posY, world, board, GUI);
    }

    public Plant(int strength, int initiative, int age, int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(strength, initiative, age, posX, posY, world, board, GUI);
    }

    public void sow(int x, int y) {
         if (Objects.equals(this.draw(), "Grass")) { world.addOrganism(new Grass(x, y, world, board, GUI)); }
         else if (Objects.equals(this.draw(), "SowThistle")) { world.addOrganism(new SowThistle(x, y, world, board, GUI)); }
         else if (Objects.equals(this.draw(), "Guarana")) { world.addOrganism(new Guarana(x, y, world, board, GUI)); }
         else if (Objects.equals(this.draw(), "Belladonna")) { world.addOrganism(new Belladonna(x, y, world, board, GUI)); }
         else if (Objects.equals(this.draw(), "Hogweed")) { world.addOrganism(new SosnowskysHogweed(x, y, world, board, GUI)); }
        board.displayCommand(this.draw() + " has spread at x: " + x + " y: " + y + '\n');
    }

    @Override
    public void action() {
        Random rand = new Random();
        int n = rand.nextInt(10);
        if (n == 0) {          //10% to sow
            int newX = this.getPosX();
            int newY = this.getPosY();
            boolean isThereEmptySpace = false;
            if (this.getPosY() > 0 && world.getOrganismAtPosition(this.getPosX(), this.getPosY() - 1) == null) {
                newY--;
                isThereEmptySpace = true;
            } else if (this.getPosY() < getWorldY() - 1 && world.getOrganismAtPosition(this.getPosX(), this.getPosY() + 1) == null) {
                newY++;
                isThereEmptySpace = true;
            } else if (this.getPosX() > 0 && world.getOrganismAtPosition(this.getPosX() - 1, this.getPosY()) == null) {
                newX--;
                isThereEmptySpace = true;
            } else if (this.getPosX() < getWorldX() - 1 && world.getOrganismAtPosition(this.getPosX() + 1, this.getPosY()) == null) {
                newX++;
                isThereEmptySpace = true; }

            if (isThereEmptySpace) this.sow(newX, newY);
        }
    }

    @Override
    public void collision(int x, int y) {

    }

    @Override
    public String draw() {
        return null;
    }
}
