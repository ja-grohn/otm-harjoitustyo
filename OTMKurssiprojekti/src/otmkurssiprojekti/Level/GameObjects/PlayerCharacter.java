/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otmkurssiprojekti.Level.GameObjects;

import otmkurssiprojekti.Level.GameObjects.Dependencies.Coords;
import otmkurssiprojekti.Level.GameObjects.Dependencies.Direction;

/**
 *
 * @author Juho Gröhn
 */
public class PlayerCharacter implements GameCharacter {

    private static final char ID = '@';
    private static final boolean TRANSPARENT = true;
    private static final boolean SOLID = false;
    private final Coords coords;

    private final int str;
    private final int end;

    private Direction direction;
    private int hp;

    public PlayerCharacter(Coords coords, int str, int end, Direction direction, int hp) {
        this.coords = coords;
        this.str = str;
        this.end = end;
        this.direction = direction;
        this.hp = hp;
    }

    @Override
    public void takeDamage(int dmg) {
        //Endurance reduces damage taken.
        dmg -= end;
        if (dmg > 0) {
            hp -= end;
        }
    }

    @Override
    public int getAttackStrength() {
        //This code might later become more complex if eg. weapons get added to the game.
        return str;
    }

    @Override
    public boolean isDead() {
        return hp <= 0;
    }

    @Override
    public char getId() {
        return ID;
    }

    @Override
    public boolean isTransparent() {
        return TRANSPARENT;
    }

    @Override
    public boolean isSolid() {
        return SOLID;
    }

    @Override
    public Coords getCoords() {
        return coords;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void move(Direction dir) {
        this.coords.add(dir.getCoords());
    }

    @Override
    public void turn(Direction direction) {
        this.direction = direction;
    }

}