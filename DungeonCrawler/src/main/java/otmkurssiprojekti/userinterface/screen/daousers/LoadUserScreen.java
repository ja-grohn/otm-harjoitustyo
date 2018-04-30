/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otmkurssiprojekti.userinterface.screen.daousers;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import otmkurssiprojekti.dataaccessobject.BasicFileDao;
import otmkurssiprojekti.userinterface.DungeonCrawler;
import otmkurssiprojekti.userinterface.screen.GameScreen;
import otmkurssiprojekti.userinterface.screen.LoadPlayerScreen;
import otmkurssiprojekti.userinterface.screen.MainMenuScreen;
import otmkurssiprojekti.userinterface.screen.VerticalMenuScreen;
import otmkurssiprojekti.dataaccessobject.FileDao;

/**
 *
 * @author Juho Gröhn
 */
public class LoadUserScreen extends VerticalMenuScreen {

    private final FileDao fudao;
    private final Path[] users;

//    private int pointer = 0;
    public LoadUserScreen(DungeonCrawler main) {
        super(main);
        fudao = new BasicFileDao(DungeonCrawler.USER_DIR);
        users = fudao.loadFiles().stream()
                .toArray(Path[]::new);
    }

    @Override
    protected void doEnterAction(int index) {
        if (index == 0) {
            switchTo(new NewUserScreen(main));
        } else {
            Path user = users[index - 1];
            main.getGameData().setUser(user.getFileName().toString());
            switchTo(new LoadPlayerScreen(main));
        }
    }

    @Override
    protected List<Object> getOptsList() {
        List<Object> optsList = new ArrayList<>();
        optsList.add("<new user>");
        for (Path user : users) {
            optsList.add(user.getFileName().toString());
        }
        return optsList;
    }

    @Override
    protected String getTitle() {
        return "Select user";
    }

    @Override
    protected GameScreen getReturnScreen() {
        return new MainMenuScreen(main);
    }

}
