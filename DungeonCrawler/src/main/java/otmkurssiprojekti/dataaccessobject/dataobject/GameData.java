/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otmkurssiprojekti.dataaccessobject.dataobject;

import otmkurssiprojekti.domain.level.GameLevel;

/**
 * A GameData contains knowledge of three things: the user, the player, and the
 * GameLevel.
 *
 * @author Juho Gröhn
 */
public interface GameData {

    //Getters
    public String getUser();

    public String getPlayer();

    public GameLevel getGameLevel();

    //Setters
    public void setUser(String user);

    public void setPlayer(String player);

    public void setGameLevel(GameLevel gameLevel);

}
