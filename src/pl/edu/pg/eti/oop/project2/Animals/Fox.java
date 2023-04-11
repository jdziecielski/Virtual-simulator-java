package pl.edu.pg.eti.oop.project2.Animals;

import pl.edu.pg.eti.oop.project2.Animal;
import pl.edu.pg.eti.oop.project2.GUI.Board;
import pl.edu.pg.eti.oop.project2.GUI.GUI;
import pl.edu.pg.eti.oop.project2.World;

import java.util.Random;

public class Fox extends Animal {

    public Fox(int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(3, 7, posX, posY, world, board, GUI);
    }

    public Fox(int strength, int initiative, int age, int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(strength, initiative, age, posX, posY, world, board, GUI);
    }

    @Override
    public void action() {
        Random rand = new Random();
        int n = rand.nextInt(4);
        switch (n)
        {
            case 0:         //go up
                if (this.getPosY() > 0) {
                    if (getOrganismAtPosition(this.getPosX(), this.getPosY() - 1) != null && getOrganismAtPosition(this.getPosX(), this.getPosY() - 1).getStrength() <= this.getStrength()) {
                        collision(this.getPosX(), this.getPosY() - 1);
                    }
                    else
                        this.setPosY(this.getPosY() - 1);
                }
                break;
            case 1:         //go right
                if (this.getPosX() < getWorldX() - 1) {
                    if (getOrganismAtPosition(this.getPosX() + 1, this.getPosY()) != null && getOrganismAtPosition(this.getPosX() + 1, this.getPosY()).getStrength() <= this.getStrength())
                        collision(this.getPosX() + 1, this.getPosY());
                    else
                        this.setPosX(this.getPosX() + 1);
                }
                break;
            case 2:         //go down
                if (this.getPosY() < getWorldY() - 1) {
                    if (getOrganismAtPosition(this.getPosX(), this.getPosY() + 1) != null && getOrganismAtPosition(this.getPosX(), this.getPosY() + 1).getStrength() <= this.getStrength())
                        collision(this.getPosX(), this.getPosY() + 1);
                    else
                        this.setPosY(this.getPosY() + 1);
                }
                break;
            case 3:         //go left
                if (this.getPosX() > 0) {
                    if (getOrganismAtPosition(this.getPosX() - 1, this.getPosY()) != null && getOrganismAtPosition(this.getPosX() - 1, this.getPosY()).getStrength() <= this.getStrength())
                        collision(this.getPosX() - 1, this.getPosY());
                    else
                        this.setPosX(getPosX() - 1);
                }
                break;
        }
    }

    @Override
    public String draw() {
        return "Fox";
    }
}
