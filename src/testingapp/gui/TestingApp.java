package testingapp.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import testingapp.Question;
import testingapp.Test;
import testingapp.workwithdata.IniFiles;

import java.util.concurrent.Flow;

public class TestingApp extends Application {
    Stage window;
    Scene scene;
    BorderPane layout;
    String username;
    int score;
    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setTitle("Testing Application");
        layout = new BorderPane();

        TableView table = new TableView<>();
        TableColumn<Test, String> name = new TableColumn<>("Название теста");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Test selectedTest = (Test) table.getSelectionModel().getSelectedItem();
                selectTest(selectedTest);
            }
        });

        table.setItems(getTests());
        table.getColumns().add(name);

        Label label = new Label("Выберите любой тест, " + username);




        BorderPane.setAlignment(table, Pos.CENTER);
        BorderPane.setAlignment(label, Pos.CENTER);
        BorderPane.setMargin(table, new Insets(10,10,10,10));
        layout.setLeft(table);
        layout.setCenter(label);
        scene = new Scene(layout, 1000, 600);
        window.setScene(scene);
        window.show();
    }

    public void setUser(String name){
        this.username = name;
    }

    private void selectTest(Test testObject){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        Label test = new Label(testObject.getName());
        test.setFont(new Font(30.0));

        Label q = new Label("Число вопросов: "+testObject.getQuestionsNum());
        q.setFont(new Font (24.0));

        Button btn = new Button("Начать тест!");
        btn.setOnAction(e->{
            startSession(testObject);
        });
        GridPane.setConstraints(test, 0,0);
        GridPane.setConstraints(q,0,1);
        GridPane.setConstraints(btn, 0,2);

        grid.getChildren().addAll(test,q,btn);
        grid.setAlignment(Pos.CENTER);
        layout.setCenter(grid);

    }

    private void startSession(Test Test){

        ObservableList<Question> q = FXCollections.observableArrayList();
        IniFiles.parseQuestions(q, Test.getQuestionsIncluded(), Test.getQuestionsNum());
        score = 0;
        nextQ(q, 0, score);

    }

    private void nextQ(ObservableList<Question> q, int counter, int score){
            int result = score;
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10,10,10,10));
            grid.setVgap(8);
            grid.setHgap(10);

            Question currQ = q.get(counter);

            Label label = new Label(currQ.getQuestionText());
            label.setFont(new Font(30.0));
            Label slug = new Label(currQ.getSlug());

            Button btn = new Button("GO NEXT");
        int finalScore = score;
        btn.setOnAction(e->{
                if(counter+1 < q.size()) {
                    int next_q = counter + 1;
                    int next_score = finalScore + 1;
                    nextQ(q, next_q, next_score);
                }
                else{
                    finishTest(result);
                }
            });

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10);
            if (!currQ.getHM()) {
                ToggleGroup tg = new ToggleGroup();
                RadioButton rb1 = new RadioButton(currQ.getA());
                RadioButton rb2 = new RadioButton(currQ.getB());
                RadioButton rb3 = new RadioButton(currQ.getC());
                RadioButton rb4 = new RadioButton(currQ.getD());
                rb1.setToggleGroup(tg);
                rb2.setToggleGroup(tg);
                rb3.setToggleGroup(tg);
                rb4.setToggleGroup(tg);


                root.getChildren().addAll(rb1, rb2, rb3, rb4);
                String VOTE = "";
                if (tg.getSelectedToggle()!=null) {
                    VOTE = tg.getSelectedToggle().getUserData().toString();
                    boolean[] temp = currQ.getAnswers();
                    if (VOTE.equals(currQ.getA()) && temp[0] == true) {
                        score++;
                    }
                    if (VOTE.equals(currQ.getB()) && temp[1] == true) {
                        score++;
                    }
                    if (VOTE.equals(currQ.getB()) && temp[2] == true) {
                        score++;
                    }
                    if (VOTE.equals(currQ.getB()) && temp[3] == true) {
                        score++;
                    }
                }
            }
            else {
                ToggleGroup tg = new ToggleGroup();
                CheckBox rb1 = new CheckBox(currQ.getA());
                CheckBox rb2 = new CheckBox(currQ.getB());
                CheckBox rb3 = new CheckBox(currQ.getC());
                CheckBox rb4 = new CheckBox(currQ.getD());
                root.getChildren().addAll(rb1, rb2, rb3, rb4);
            }

            GridPane.setConstraints(label, 0,0);
            GridPane.setConstraints(root, 0,1);
            GridPane.setConstraints(slug,0,2);
            GridPane.setConstraints(btn, 0,3);

            grid.getChildren().addAll(label,slug,btn, root);
            grid.setAlignment(Pos.CENTER);
            layout.setCenter(grid);

    }

    private void finishTest(int score){
        GridPane grid = new GridPane();
        Label label = new Label("Вы завершили тест. Ваш результат: "+score);
        label.setFont(new Font(30.0));
        grid.getChildren().add(label);
        grid.setAlignment(Pos.CENTER);
        layout.setCenter(grid);
    }

    private ObservableList<Test> getTests() {
        ObservableList<Test> tests = FXCollections.observableArrayList();
        IniFiles.parseTests(tests);
        return tests;
    }
}

