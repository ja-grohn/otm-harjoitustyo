/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otmkurssiprojekti.dataaccessobject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import otmkurssiprojekti.level.BasicGameLevel;
import otmkurssiprojekti.level.GameLevel;
import otmkurssiprojekti.level.gameobjects.archetypes.NonPlayerCharacterArchetype;
import otmkurssiprojekti.level.gameobjects.archetypes.PlayerCharacterArchetype;
import otmkurssiprojekti.level.gameobjects.gamecharacter.nonplayercharacter.HostileNonPlayerCharacter;
import otmkurssiprojekti.level.gameobjects.gamecharacter.playercharacter.PlayerCharacter;
import otmkurssiprojekti.level.gameobjects.interfaces.NonPlayerCharacter;
import otmkurssiprojekti.level.gameobjects.location.Coords;
import otmkurssiprojekti.level.gameobjects.location.Direction;

/**
 *
 * @author gjuho
 */
public class TextFileLevelDaoTest {

    private Path directory;
    private TextFileLevelDao tfld;

    public TextFileLevelDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException {
        directory = Files.createTempDirectory("levelsTest");
        tfld = new TextFileLevelDao(directory);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSLL0() {
        GameLevel gl = new BasicGameLevel(
                "testLevel",
                new PlayerCharacter(PlayerCharacterArchetype.THIEF, new Coords(0, 0, 0), Direction.DOWN),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
        testSaveLoadLevel(gl);
    }

    @Test
    public void testSLL1() {
        List<NonPlayerCharacter> npcs = new ArrayList<>();
        npcs.add(new HostileNonPlayerCharacter(NonPlayerCharacterArchetype.RAT, new Coords(), Direction.DOWN));

        GameLevel gl = new BasicGameLevel(
                "testLevel",
                new PlayerCharacter(PlayerCharacterArchetype.THIEF, new Coords(0, 0, 0), Direction.DOWN),
                npcs,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
        testSaveLoadLevel(gl);
    }

    public void testSaveLoadLevel(GameLevel gl) {
        tfld.saveLevel(gl);
        GameLevel gl2 = tfld.loadLevel(Paths.get(directory.toString(), gl.toString()));

        assertTrue("Expected " + gl.toString() + " to be equal with " + gl2.toString(), gl.equals(gl2));
    }

}
