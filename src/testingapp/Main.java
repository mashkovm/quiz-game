package testingapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import testingapp.gui.TestingApp;
import testingapp.gui.notifications.ConfirmBox;
import testingapp.workwithdata.IniFiles;

import java.io.IOException;

public class Main extends Application{

    Stage window;
    Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setTitle("Testing Application");
        window.setOnCloseRequest(e->{
            e.consume();
            onClose();
        });

        window.setResizable(false);
        window.setWidth(1000);
        window.setHeight(600);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label usernameLabel = new Label("Имя");
        GridPane.setConstraints(usernameLabel, 0,0);
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput,1, 0);
        usernameInput.setPromptText("Введите имя");
        Button loginButton = new Button("Login");
        //action of submit button
        loginButton.setOnAction(e -> {
            try {
                loginFormSubmit(usernameInput.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        GridPane.setConstraints(loginButton, 1,2 );

        grid.getChildren().addAll(usernameInput, usernameLabel, loginButton);
        grid.setAlignment(Pos.CENTER);
        scene = new Scene(grid, 1000, 600);
        window.setScene(scene);
        window.show();
    }

    private void loginFormSubmit(String name) throws Exception {
        TestingApp ta = new TestingApp();
        ta.setUser(name);
        ta.start(window);
    }

    private void onClose(){
        if(ConfirmBox.display("Confirm", "Are you sure you want to close?")){
            window.close();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
