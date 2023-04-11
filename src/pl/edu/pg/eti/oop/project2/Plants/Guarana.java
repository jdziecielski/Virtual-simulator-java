package pl.edu.pg.eti.oop.project2.Plants;

import pl.edu.pg.eti.oop.project2.GUI.Board;
import pl.edu.pg.eti.oop.project2.Organism;
import pl.edu.pg.eti.oop.project2.Plant;
import pl.edu.pg.eti.oop.project2.World;

public class Guarana extends Plant {
    public Guarana(int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(0, posX, posY, world, board, GUI);
    }

    public Guarana(int strength, int initiative, int age, int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(strength, initiative, age, posX, posY, world, board, GUI);
    }

    @Override
    public void collision(int x, int y) {
        Organism collidedWith = world.getOrganismAtPosition(x, y);
        collidedWith.setStrength(collidedWith.getStrength() + 3);
        board.displayCommand(collidedWith.draw() + " has eaten Guarana at x: " + this.getPosX() + " y: " + this.getPosY() + "! " + collidedWith.draw() + "'s strength is " + collidedWith.getStrength() + " now.\n");
        collidedWith.setPosX(this.getPosX());
        collidedWith.setPosY(this.getPosY());
        world.deleteOrganism(this);
    }

    public String draw() { return "Guarana"; }
}
