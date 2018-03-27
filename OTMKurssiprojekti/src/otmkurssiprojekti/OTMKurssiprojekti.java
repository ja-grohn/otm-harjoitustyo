/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otmkurssiprojekti;

import java.util.Arrays;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import otmkurssiprojekti.Level.Direction;
import otmkurssiprojekti.Level.GameLevel;
import otmkurssiprojekti.Level.GameLevelObject;
import otmkurssiprojekti.Level.GameObjects.GameCharacters.PlayerCharacter;

/**
 *
 * @author gjuho
 */
public class OTMKurssiprojekti extends Application {

    private GameLevel gamelvl;
    private Scene mainScene;
    private InputController inControl;
    private OutputController outControl;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        gamelvl = new GameLevel();
        mainScene = new Scene(new BorderPane());
        outControl = new OutputController(mainScene, gamelvl);
        mainScene.setRoot(outControl.getLevelTextRepresentation());
        inControl = new InputController(outControl);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Adventure Game");
        //Näppäimistösyöte.
        mainScene.setOnKeyPressed(e -> inControl.handleKeyEvent(e));
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

}
