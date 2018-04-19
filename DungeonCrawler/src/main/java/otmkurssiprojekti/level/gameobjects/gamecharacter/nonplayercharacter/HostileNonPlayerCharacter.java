/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otmkurssiprojekti.level.gameobjects.gamecharacter.nonplayercharacter;

import otmkurssiprojekti.level.GameLevel;
import otmkurssiprojekti.level.gameobjects.archetypes.NonPlayerCharacterArchetype;
import otmkurssiprojekti.level.gameobjects.location.Coords;
import otmkurssiprojekti.level.gameobjects.location.Direction;
import otmkurssiprojekti.utilityclasses.AI;

public class HostileNonPlayerCharacter extends BasicNonPlayerCharacter {

    public HostileNonPlayerCharacter(char id, int lvl, int hp, int str, int per, int end, int agl, Coords coords, Direction direction) {
        super(id, lvl, hp, str, per, end, agl, coords, direction);
    }

    public HostileNonPlayerCharacter(NonPlayerCharacterArchetype npca, Coords coords, Direction direction) {
        super(npca, coords, direction);
    }

    @Override
    public void act(GameLevel gameLevel) {
        AI.hunt(this, gameLevel);
    }
}
