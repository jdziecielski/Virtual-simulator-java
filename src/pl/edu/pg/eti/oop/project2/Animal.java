package pl.edu.pg.eti.oop.project2;

import pl.edu.pg.eti.oop.project2.Animals.Fox;
import pl.edu.pg.eti.oop.project2.Animals.Sheep;
import pl.edu.pg.eti.oop.project2.Animals.Turtle;
import pl.edu.pg.eti.oop.project2.Animals.Wolf;
import pl.edu.pg.eti.oop.project2.GUI.Board;
import pl.edu.pg.eti.oop.project2.Plants.Belladonna;
import pl.edu.pg.eti.oop.project2.Plants.Guarana;

import java.util.Objects;
import java.util.Random;

public abstract class Animal extends Organism {

    public Animal(int strength, int initiative, int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(strength, initiative, posX, posY, world, board, GUI); }

    public Animal(int strength, int initiative, int age, int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(strength, initiative, age, posX, posY, world, board, GUI);
    }

    @Override
    public void action(){
        Random rand = new Random();
        int n = rand.nextInt(4);
        switch (n)
        {
            case 0:         //go up
                if (this.getPosY() > 0) {
                    if (getOrganismAtPosition(this.getPosX(), this.getPosY() - 1) != null)
                        collision(this.getPosX(), this.getPosY() - 1);
                    else
                        this.setPosY(this.getPosY() - 1);
                }
                break;
            case 1:         //go right
                if (this.getPosX() < getWorldX() - 1) {
                    if (getOrganismAtPosition(this.getPosX() + 1, this.getPosY()) != null)
                        collision(this.getPosX() + 1, this.getPosY());
                    else
                        this.setPosX(this.getPosX() + 1);
                }
                break;
            case 2:         //go down
                if (this.getPosY() < getWorldY() - 1) {
                    if (getOrganismAtPosition(this.getPosX(), this.getPosY() + 1) != null)
                        collision(this.getPosX(), this.getPosY() + 1);
                    else
                        this.setPosY(this.getPosY() + 1);
                }
                break;
            case 3:         //go left
                if (this.getPosX() > 0) {
                    if (getOrganismAtPosition(this.getPosX() - 1, this.getPosY()) != null)
                        collision(this.getPosX() - 1, this.getPosY());
                    else
                        this.setPosX(getPosX() - 1);
                }
                break;
        }
    }

    public void breed(Organism collidedWith, int x, int y) {
        if (Objects.equals(collidedWith.draw(), "Wolf")) { world.addOrganism(new Wolf(x, y, world, board, GUI)); }
        else if (Objects.equals(collidedWith.draw(), "Sheep")) { world.addOrganism(new Sheep(x, y, world, board, GUI)); }
        else if (Objects.equals(collidedWith.draw(), "Fox")) { world.addOrganism(new Fox(x, y, world, board, GUI)); }
        else if (Objects.equals(collidedWith.draw(), "Turtle")) { world.addOrganism(new Fox(x, y, world, board, GUI)); }
        else if (Objects.equals(collidedWith.draw(), "Antelope")) { world.addOrganism(new Fox(x, y, world, board, GUI)); }

        board.displayCommand("New " + this.draw() + " was born at x: " + x + " y: " + y + '\n');
    }

    @Override
    public void collision(int x, int y)
    {
        Organism collidedWith = getOrganismAtPosition(x, y);
        //breeding section
        if (Objects.equals(collidedWith.draw(), this.draw())) {
            int newX = x;
            int newY = y;
            boolean isThereEmptySpace = false;
            if (y > 0 && world.getOrganismAtPosition(x, y - 1) == null) {
                newY--;
                isThereEmptySpace = true;
            }
            else if (y < getWorldY() - 1 && world.getOrganismAtPosition(x, y + 1) == null) {
                newY++;
                isThereEmptySpace = true;
            }
            else if (x > 0 && world.getOrganismAtPosition(x - 1, y) == null) {
                newX--;
                isThereEmptySpace = true;
            }
            else if (x < getWorldX() - 1 && world.getOrganismAtPosition(x + 1, y) == null) {
                newX++;
                isThereEmptySpace = true; }

            if (isThereEmptySpace) breed(collidedWith, newX, newY);

        }
        else {
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
    }

    @Override
    public abstract String draw();
}