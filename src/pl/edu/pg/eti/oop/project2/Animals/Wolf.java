package pl.edu.pg.eti.oop.project2.Animals;

import pl.edu.pg.eti.oop.project2.Animal;
import pl.edu.pg.eti.oop.project2.GUI.Board;
import pl.edu.pg.eti.oop.project2.GUI.GUI;
import pl.edu.pg.eti.oop.project2.World;

public class Wolf extends Animal {

    public Wolf(int posX, int posY, World world, Board board, GUI GUI) {
        super(9, 5, posX, posY, world, board, GUI);
    }

    public Wolf(int strength, int initiative, int age, int posX, int posY, World world, Board board, pl.edu.pg.eti.oop.project2.GUI.GUI GUI) {
        super(strength, initiative, age, posX, posY, world, board, GUI);
    }

    @Override
    public String draw() {
        return "Wolf";
    }
}