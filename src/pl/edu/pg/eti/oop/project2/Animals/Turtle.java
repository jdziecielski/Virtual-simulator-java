package pl.edu.pg.eti.oop.project2.Animals;

import pl.edu.pg.eti.oop.project2.Animal;
import pl.edu.pg.eti.oop.project2.GUI.Board;
import pl.edu.pg.eti.oop.project2.GUI.GUI;
import pl.edu.pg.eti.oop.project2.Organism;
import pl.edu.pg.eti.oop.project2.Plants.Belladonna;
import pl.edu.pg.eti.oop.project2.Plants.Guarana;
import pl.edu.pg.eti.oop.project2.World;

import java.util.Objects;
import java.util.Random;

public class Turtle extends Animal {

    public Turtle(int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(2, 1, posX, posY, world, board, GUI);
    }

    public Turtle(int strength, int initiative, int age, int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(strength, initiative, age, posX, posY, world, board, GUI);
    }

    @Override
    public void action() {
        Random rand = new Random();
        int ifMakeMove = rand.nextInt(4);
        if (ifMakeMove == 1) {
            int n = rand.nextInt(4);
            switch (n)
            {
                case 0:         //go up
                    if (this.getPosY() > 0) {
                        if (getOrganismAtPosition(this.getPosX(), this.getPosY() - 1) != null) {
                            collision(this.getPosX(), this.getPosY() - 1);
                        }
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
    }

    @Override
    public void collision(int x, int y)  {
        Organism collidedWith = getOrganismAtPosition(x, y);
        if (Objects.equals(collidedWith.draw(), this.draw())) {
            int newX = x;
            int newY = y;
            boolean isThereEmptySpace = false;
            if (y > 0 && world.getOrganismAtPosition(x, y - 1) == null) {
                newY--;
                isThereEmptySpace = true;
            } else if (y < getWorldY() - 1 && world.getOrganismAtPosition(x, y + 1) == null) {
                newY++;
                isThereEmptySpace = true;
            } else if (x > 0 && world.getOrganismAtPosition(x - 1, y) == null) {
                newX--;
                isThereEmptySpace = true;
            } else if (x < getWorldX() - 1 && world.getOrganismAtPosition(x + 1, y) == null) {
                newX++;
                isThereEmptySpace = true; }

            if (isThereEmptySpace) breed(collidedWith, newX, newY);

        }
        else {
            if (collidedWith instanceof Guarana) {
                collidedWith.collision(this.getPosX(), this.getPosY());
            } else if (collidedWith instanceof Belladonna) {
                collidedWith.collision(this.getPosX(), this.getPosY());
            }
            else if (this.getStrength() >= collidedWith.getStrength()) {
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
    public String draw() {
        return "Turtle";
    }
}
