package pl.edu.pg.eti.oop.project2.GUI;

import pl.edu.pg.eti.oop.project2.World;

import javax.swing.*;
import java.awt.event.*;

public class GUI extends JFrame implements KeyListener, ActionListener {
    private int windowWidth, windowHeight, worldX, worldY;
    private World world;
    private Board board;
    private boolean ifMoveWasMade = true;
    private boolean wasSpecialAbilityUsed = false;
    private int specialAbilityTurnsRemaining;
    private int specialAbilityCooldown = 0;

    public GUI(int worldX, int worldY, int windowWidth, int windowHeight, World world) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.world = world;
        this.board = new Board(worldX, worldY, world, this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(windowWidth, windowHeight);
        this.setLayout(null);
        this.addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        this.setVisible(true);

        this.setContentPane(board.getWindowContent());
        this.setVisible(true);
        this.requestFocus();
    }

    public Board getBoard() {
        return board;
    }

    public void displayCommand(String command) {
        board.displayCommand(command);
    }

    public void updateBoard() {
        board.updateBoard();
    }

    public void clearTextArea() {
        board.clearTextArea();
    }

    public void drawPurificationArea() {
        board.drawPurificationArea();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!ifMoveWasMade) {
            this.requestFocus();
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    //go left
                    world.getHuman().directionInTurn(world.getHuman().getPosX() - 1,world.getHuman().getPosY());
                    setMoveWasMadeToTrue();
                    break;
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    //go up
                    world.getHuman().directionInTurn(world.getHuman().getPosX(), world.getHuman().getPosY() - 1);
                    setMoveWasMadeToTrue();
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    //go right
                    world.getHuman().directionInTurn(world.getHuman().getPosX() + 1, world.getHuman().getPosY());
                    setMoveWasMadeToTrue();
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    //go down
                    world.getHuman().directionInTurn(world.getHuman().getPosX(),world.getHuman().getPosY() + 1);
                    setMoveWasMadeToTrue();
                    break;
                case KeyEvent.VK_ENTER:
                    if (!wasSpecialAbilityUsed && specialAbilityCooldown <= 0) {
                        board.displayCommand("Human has used special ability: P U R I F I C A T I O N!!!\n");
                        specialAbilityTurnsRemaining = 5;
                        wasSpecialAbilityUsed = true;
                    } else
                        board.displayCommand("Special ability has already been used!\n");

                    break;
            }
        }
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setSpecialAbilityCooldown(int specialAbilityCooldown) {
        this.specialAbilityCooldown = specialAbilityCooldown;
    }

    public int getSpecialAbilityCooldown() {
        return specialAbilityCooldown;
    }

    public void setWasSpecialAbilityUsed(boolean wasSpecialAbilityUsed) { this.wasSpecialAbilityUsed = wasSpecialAbilityUsed; }

    public boolean wasSpecialAbilityUsed() { return wasSpecialAbilityUsed; }

    public int getSpecialAbilityTurnsRemaining() { return specialAbilityTurnsRemaining; }

    public void setSpecialAbilityTurnsRemaining(int specialAbilityTurnsRemaining) { this.specialAbilityTurnsRemaining = specialAbilityTurnsRemaining; }

    public void setMoveWasMadeToFalse() { ifMoveWasMade = false; }

    public void setMoveWasMadeToTrue() { ifMoveWasMade = true; }

    public boolean getIfMoveWasMade() { return ifMoveWasMade;}

    public void resetGUI() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public void closeGUI() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}