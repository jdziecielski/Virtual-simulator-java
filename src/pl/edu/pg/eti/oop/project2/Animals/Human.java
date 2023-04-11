package pl.edu.pg.eti.oop.project2.Animals;

import pl.edu.pg.eti.oop.project2.*;
import pl.edu.pg.eti.oop.project2.GUI.Board;
import pl.edu.pg.eti.oop.project2.Plants.Belladonna;
import pl.edu.pg.eti.oop.project2.Plants.Guarana;
import java.awt.*;

public class Human extends Animal {
    private int newX, newY;
    public Human(int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(5, 4, posX, posY, world, board, GUI);
    }

    public Human(int strength, int initiative, int age, int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(strength, initiative, age, posX, posY, world, board, GUI);
    }

    public void directionInTurn(int newX, int newY) {
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void action(){
        if (GUI.getIfMoveWasMade()) {
            if (newX >= 0 && newX <= getWorldX() - 1 && newY >= 0 && newY <= getWorldY() - 1) {
                if (world.getOrganismAtPosition(newX, newY) != null) {
                    this.collision(newX, newY);
                } else {
                    setPosY(newY);
                    setPosX(newX);
                }
            }
        }
    }

    @Override
    public void collision(int x, int y) {
        Organism collidedWith = getOrganismAtPosition(x, y);
        if (collidedWith instanceof Turtle && this.getStrength() < 5) {
            board.displayCommand(collidedWith.draw() + " has reflected attack of " + this.draw() + " at x: " + x + " y: " + y + "!\n");
        } else if (collidedWith instanceof Guarana) {
            collidedWith.collision(this.getPosX(), this.getPosY());
        } else if (collidedWith instanceof Belladonna) {
            collidedWith.collision(this.getPosX(), this.getPosY());
        } else if (this.getStrength() >= collidedWith.getStrength()) {
            board.displayCommand(this.draw() + " has killed " + collidedWith.draw() + " at x: " + x + " y: " + y + "!\n");
            world.deleteOrganism(collidedWith);
            this.setPosX(x);
            this.setPosY(y);
        } else if (this.getStrength() < collidedWith.getStrength()) {
            board.displayCommand(this.draw() + " has died from attacking " + collidedWith.draw() + " at x: " + x + " y: " + y + "!\n");
            world.deleteOrganism(this);
        }
    }

    public void usePurification() {
        int x = this.getPosX();
        int y = this.getPosY();
        int organismsPurified = 0;

        world.setAdjacentPoints(x, y);
        for (Point p: world.getAdjacentPoints()) {
            if (world.getOrganismAtPosition(p.x, p.y) != null) {
                world.deleteOrganism(world.getOrganismAtPosition(p.x, p.y));
                organismsPurified++;
            }
        }

        if (organismsPurified > 0)
            board.displayCommand("PURIFICATION HAS KILLED " + organismsPurified + " ORGANISM(s)!!! The effect will remain for " + (GUI.getSpecialAbilityTurnsRemaining() - 1) + " more turns.\n");
        GUI.requestFocus();
    }

    @Override
    public String draw() {
        return "HUMAN";
    }
}