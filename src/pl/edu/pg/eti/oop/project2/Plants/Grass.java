package pl.edu.pg.eti.oop.project2.Plants;

import pl.edu.pg.eti.oop.project2.GUI.Board;
import pl.edu.pg.eti.oop.project2.GUI.GUI;
import pl.edu.pg.eti.oop.project2.Plant;
import pl.edu.pg.eti.oop.project2.World;

public class Grass extends Plant {
    public Grass(int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(0 , posX, posY, world, board, GUI);
    }

    public Grass(int strength, int initiative, int age, int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(strength, initiative, age, posX, posY, world, board, GUI);
    }

    @Override
    public String draw() {
        return "Grass";
    }
}
