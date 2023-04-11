package pl.edu.pg.eti.oop.project2;

import pl.edu.pg.eti.oop.project2.GUI.Board;
import pl.edu.pg.eti.oop.project2.GUI.GUI;

abstract public class Organism {
    protected int strength, initiative, posX, posY, age;
    protected World world;
    protected Board board;
    protected pl.edu.pg.eti.oop.project2.GUI.GUI GUI;
    protected boolean ifJustBorn = false;

    public Organism(int strength, int initiative, int posX, int posY, World world, Board board, GUI GUI) {
        this.strength = strength;
        this.initiative = initiative;
        this.posX = posX;
        this.posY = posY;
        this.world = world;
        this.board = board;
        this.GUI = GUI;
        this.age = 0;
    }

    public Organism(int strength, int initiative, int age, int posX, int posY, World world, Board board, GUI GUI) {
        this.strength = strength;
        this.initiative = initiative;
        this.age = age;
        this.posX = posX;
        this.posY = posY;
        this.world = world;
        this.board = board;
        this.GUI = GUI;
    }

    public void setIfJustBorn(boolean ifJustBorn) { this.ifJustBorn = ifJustBorn; }

    public int getWorldX() { return world.getWorldX();}

    public int getWorldY() { return world.getWorldY();}

    public int getPosX() { return posX; }

    public int getPosY() { return posY; }

    public int getStrength() { return strength; }

    public int getInitiative() { return initiative; }

    public void setPosX(int posX) { this.posX = posX; }

    public void setPosY(int posY) { this.posY = posY; }

    public void setStrength(int strength) { this.strength = strength; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public Organism getOrganismAtPosition(int x, int y) { return world.getOrganismAtPosition(x, y); }

    public abstract void action();
    public abstract void collision(int x, int y);
    public abstract String draw();
}
