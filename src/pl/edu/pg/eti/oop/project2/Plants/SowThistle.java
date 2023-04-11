package pl.edu.pg.eti.oop.project2.Plants;

import pl.edu.pg.eti.oop.project2.GUI.Board;
import pl.edu.pg.eti.oop.project2.Plant;
import pl.edu.pg.eti.oop.project2.World;

import java.util.Random;

public class SowThistle extends Plant {
    public SowThistle(int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(0, posX, posY, world, board, GUI);
    }

    public SowThistle(int strength, int initiative, int age, int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(strength, initiative, age, posX, posY, world, board, GUI);
    }

    @Override
    public void action() {
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
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
    }

    @Override
    public String draw() { return "SowThistle"; }
}
