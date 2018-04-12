/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otmkurssiprojekti.Level;

import otmkurssiprojekti.Level.GameObjects.Dependencies.Direction;
import otmkurssiprojekti.Level.GameObjects.Dependencies.Coords;
import otmkurssiprojekti.Level.GameObjects.PlayerCharacter;
import otmkurssiprojekti.Level.GameObjects.NonPlayerCharacter;
import otmkurssiprojekti.Level.GameObjects.ImmutableObject;
import java.util.*;
import otmkurssiprojekti.Level.GameObjects.*;

/**
 *
 * @author gjuho
 */
public class BasicGameLevel implements GameLevel {

    public static final Coords DIMENSIONS = new Coords(16, 16, 8); //The level is a box from origin to DIMENSIONS Coords exclusive. Ie. The level has a size 16x16x8.
    private final String levelName;
    private PlayerCharacter player;
    private final List<NonPlayerCharacter> npcs;
    private final List<ImmutableObject> blocks;
    private final List<InteractiveObject> interactives;
    private final List<LinkObject> levelLinks;
    private final List<PointsObject> points;

    public BasicGameLevel() {
        this.levelName = null;
        this.player = null;
        this.npcs = null;
        this.blocks = null;
        this.interactives = null;
        this.levelLinks = null;
        this.points = null;
    }

    public BasicGameLevel(String levelName, PlayerCharacter player, List<NonPlayerCharacter> npcs, List<ImmutableObject> blocks, List<InteractiveObject> interactives, List<LinkObject> levelLinks, List<PointsObject> points) {
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

    //Setter
    @Override
    public void setPlayer(PlayerCharacter player) {
        this.player = player;
    }

    //Others
    private static Boolean containsCoords(Coords coords) {
        return coords.greaterThanOrEqualTo(new Coords(0, 0, 0)) && coords.lesserThan(DIMENSIONS);
    }
    
    private Boolean hasSolidBlockAt(Coords coords){
        List<GameObject> possiblySolidBlocks = new ArrayList<>();
        possiblySolidBlocks.addAll(blocks);
        possiblySolidBlocks.addAll(interactives);
        return possiblySolidBlocks.stream()
                .filter(b -> b.getCoords().equals(coords))
                .anyMatch(b -> b.isSolid());
    }

    @Override
    public void movePlayer(Direction dir) {
        Coords playerCoords = this.player.getCoords();
        Coords newCoords = playerCoords.sum(dir.getCoords());
        if (BasicGameLevel.containsCoords(newCoords) && !this.hasSolidBlockAt(newCoords)) {
            player.move(dir);
        }
    }

    @Override
    public GameObject[][][] getLevelData() {
        GameObject[][][] levelData = new GameObject[DIMENSIONS.z][DIMENSIONS.y][DIMENSIONS.x];
        //Everything in one big list.
        List<GameObject> allObjects = new ArrayList<>();
        allObjects.add(player);
        allObjects.addAll(npcs);
        allObjects.addAll(blocks);
        allObjects.addAll(interactives);
        allObjects.addAll(levelLinks);
        allObjects.addAll(points);
        //Convert list into matrix.
        for (GameObject gobj : allObjects) {
            Coords gobjc = gobj.getCoords();
            if (BasicGameLevel.containsCoords(gobjc)) {
                levelData[gobjc.z][gobjc.y][gobjc.x] = gobj;
            }
        }
        return levelData;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.levelName);
        hash = 53 * hash + Objects.hashCode(this.player);
        hash = 53 * hash + Objects.hashCode(this.npcs);
        hash = 53 * hash + Objects.hashCode(this.blocks);
        hash = 53 * hash + Objects.hashCode(this.interactives);
        hash = 53 * hash + Objects.hashCode(this.levelLinks);
        hash = 53 * hash + Objects.hashCode(this.points);
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BasicGameLevel other = (BasicGameLevel) obj;
        if (!Objects.equals(this.levelName, other.levelName)) {
            return false;
        }
        if (!Objects.equals(this.player, other.player)) {
            return false;
        }
        if (!Objects.equals(this.npcs, other.npcs)) {
            return false;
        }
        if (!Objects.equals(this.blocks, other.blocks)) {
            return false;
        }
        if (!Objects.equals(this.interactives, other.interactives)) {
            return false;
        }
        if (!Objects.equals(this.levelLinks, other.levelLinks)) {
            return false;
        }
        if (!Objects.equals(this.points, other.points)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.levelName;
    }

}
