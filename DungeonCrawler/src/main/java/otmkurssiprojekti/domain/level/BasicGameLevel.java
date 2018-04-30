/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otmkurssiprojekti.domain.level;

import otmkurssiprojekti.domain.gameobject.location.Direction;
import otmkurssiprojekti.domain.gameobject.location.Coords;
import otmkurssiprojekti.domain.gameobject.gameinanimates.InteractiveObject;
import otmkurssiprojekti.domain.gameobject.gameinanimates.PointsBall;
import otmkurssiprojekti.domain.gameobject.gameinanimates.ImmutableObject;
import otmkurssiprojekti.domain.gameobject.gameinanimates.LinkObject;
import otmkurssiprojekti.domain.gameobject.gamecharacter.playercharacter.BasicPlayerCharacter;
import otmkurssiprojekti.domain.gameobject.interfaces.GameObject;
import java.util.*;
import otmkurssiprojekti.domain.gameobject.interfaces.Mobile;
import otmkurssiprojekti.domain.gameobject.interfaces.derivatives.NonPlayerCharacter;

/**
 * The most basic implementation of a GameLevel.
 *
 * @author gjuho
 */
public class BasicGameLevel implements GameLevel {

    /**
     * The level is a box from origin to DIMENSIONS Coords exclusive. Ie. The
     * level has a size 16x16x8.
     */
    public static final Coords DIMENSIONS = new Coords(16, 16, 8);

    private final String levelName;
    private BasicPlayerCharacter player;
    private final List<NonPlayerCharacter> npcs;
    private final List<ImmutableObject> blocks;
    private final List<InteractiveObject> interactives;
    private final List<LinkObject> levelLinks;
    private final List<PointsBall> points;

    public BasicGameLevel() {
        this.levelName = null;
        this.player = null;
        this.npcs = null;
        this.blocks = null;
        this.interactives = null;
        this.levelLinks = null;
        this.points = null;
    }

    public BasicGameLevel(String levelName, BasicPlayerCharacter player, List<NonPlayerCharacter> npcs, List<ImmutableObject> blocks, List<InteractiveObject> interactives, List<LinkObject> levelLinks, List<PointsBall> points) {
        this.levelName = levelName;
        this.player = player;
        this.npcs = npcs;
        this.blocks = blocks;
        this.interactives = interactives;
        this.levelLinks = levelLinks;
        this.points = points;
    }

    //Getters
    @Override
    public String getLevelName() {
        return levelName;
    }

    @Override
    public BasicPlayerCharacter getPlayer() {
        return this.player;
    }

    public List<NonPlayerCharacter> getNpcs() {
        return npcs;
    }

    public List<ImmutableObject> getBlocks() {
        return blocks;
    }

    public List<InteractiveObject> getInteractives() {
        return interactives;
    }

    public List<LinkObject> getLevelLinks() {
        return levelLinks;
    }

    public List<PointsBall> getPoints() {
        return points;
    }

    //Setter
    @Override
    public void setPlayer(BasicPlayerCharacter player) {
        this.player = player;
    }

    //Others
    @Override
    public boolean isInaccessible(Coords coords) {
        return !BasicGameLevel.hasCoords(coords) || this.hasSolidBlockAt(coords);
    }

    protected static Boolean hasCoords(Coords coords) {
        return coords.greaterThanOrEqualTo(new Coords(0, 0, 0)) && coords.lesserThan(DIMENSIONS);
    }

    protected Boolean hasSolidBlockAt(Coords coords) {
        List<GameObject> possiblySolidBlocks = new ArrayList<>();
        possiblySolidBlocks.addAll(blocks);
        possiblySolidBlocks.addAll(interactives);
        return possiblySolidBlocks.stream()
                .filter(b -> b.getCoords().toFlatCoords().equals(coords.toFlatCoords()))
                .anyMatch(b -> b.isSolid());
    }

    @Override
    public void movePlayer(Direction dir) {
        moveMobile(player, dir);
    }

    public void moveMobile(Mobile mobile, Direction dir) {
        Coords mobileCoords = mobile.getCoords();
        Coords newCoords = mobileCoords.sum(dir.getCoords());
        if (!this.isInaccessible(newCoords)) {
            mobile.move(dir);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.levelName);
        hash = 37 * hash + Objects.hashCode(this.player);
        hash = 37 * hash + Objects.hashCode(this.blocks);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof GameLevel) {
            return ((GameLevel) obj).getLevelName().equals(this.getLevelName())
                    && ((GameLevel) obj).getGameObjects().containsAll(this.getGameObjects());

        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.levelName;
    }

    @Override
    public boolean doGameTick() {
        npcs.forEach(npc -> npc.act(this));
        npcs.removeIf(npc -> npc.isDead());
        return this.player.isDead();
    }

    @Override
    public List<GameObject> getGameObjects() {
        List<GameObject> all = new ArrayList<>();
        all.add(player);
        all.addAll(blocks);
        all.addAll(interactives);
        all.addAll(levelLinks);
        all.addAll(npcs);
        all.addAll(points);
        return all;
    }

    @Override
    public void playerAttack(Direction dir) {
        Coords pcCoords = this.player.getCoords();
        Coords coords = this.player.coordsAt(dir);
        this.npcs.stream()
                .filter(npc -> npc.getCoords().equals(pcCoords) || npc.getCoords().equals(coords))
                .forEach(npc -> this.player.hurt(npc));
        this.npcs.removeIf(npc -> npc.isDead());
    }

}
