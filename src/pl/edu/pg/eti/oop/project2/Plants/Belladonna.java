package pl.edu.pg.eti.oop.project2.Plants;

import pl.edu.pg.eti.oop.project2.GUI.Board;
import pl.edu.pg.eti.oop.project2.GUI.GUI;
import pl.edu.pg.eti.oop.project2.Organism;
import pl.edu.pg.eti.oop.project2.Plant;
import pl.edu.pg.eti.oop.project2.World;

public class Belladonna extends Plant {
    public Belladonna(int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(99, posX, posY, world, board, GUI);
    }

    public Belladonna(int strength, int initiative, int age, int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(strength, initiative, age, posX, posY, world, board, GUI);
    }

    @Override
    public void collision(int x, int y) {
        Organism collidedWith = getOrganismAtPosition(x, y);
        board.displayCommand(collidedWith.draw() + " has been eaten by " + this.draw() + " at x: " + this.getPosX() + " y: " + this.getPosY() + "!\n");
        world.deleteOrganism(collidedWith);
    }

    @Override
    public String draw() { return "Belladonna"; }
}
