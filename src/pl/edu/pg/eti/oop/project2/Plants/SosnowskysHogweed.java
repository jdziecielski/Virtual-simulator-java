package pl.edu.pg.eti.oop.project2.Plants;

import pl.edu.pg.eti.oop.project2.*;
import pl.edu.pg.eti.oop.project2.GUI.Board;

public class SosnowskysHogweed extends Plant {
    public SosnowskysHogweed(int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(10, posX, posY, world, board, GUI);
    }

    public SosnowskysHogweed(int strength, int initiative, int age, int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(strength, initiative, age, posX, posY, world, board, GUI);
    }

    @Override
    public void action() {
        int x = this.getPosX();
        int y = this.getPosY();
        boolean isThereAnimalNearby = false;
        Organism organismNearby;
        if (y > 0 && world.getOrganismAtPosition(x, y - 1) instanceof Animal) {   //upper cell
            y--;
            isThereAnimalNearby = true;
        } else if (x > 0 && world.getOrganismAtPosition(x - 1, y) instanceof Animal) { //left cell
            x--;
            isThereAnimalNearby = true;
        } else if (y < getWorldY() - 1 && world.getOrganismAtPosition(x, y + 1) instanceof Animal) {    //lower cell
            y--;
            isThereAnimalNearby = true;
        } else if (x < getWorldX() - 1 && world.getOrganismAtPosition(x + 1, y) instanceof Animal) {    //right cell
            x++;
            isThereAnimalNearby = true;
        }

        if (isThereAnimalNearby) {
            organismNearby = getOrganismAtPosition(x, y);
            if (organismNearby != null) {
                board.displayCommand(this.draw() + " has killed " + organismNearby.draw() + " at x: " + this.getPosX() + " y: " + this.getPosY() + "!\n");
                world.deleteOrganism(organismNearby);
            }
        }
    }

    @Override
    public void collision(int x, int y) {
        Organism collidedWith = getOrganismAtPosition(x, y);
        board.displayCommand(collidedWith.draw() + " has died from " + this.draw() + " at x: " + this.getPosX() + " y: " + this.getPosY() + "!\n");
        world.deleteOrganism(collidedWith);
    }

    @Override
    public String draw() { return "Hogweed"; }
}
