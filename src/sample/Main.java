package sample;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.event.ActionEvent;

public class Main extends Application {
    private final ObservableList<City> cities = FXCollections.observableArrayList(
            new City("City 1", 0, 0),
            new City("City 2", 10, 10),
            new City("City 3", 20, 20)
    );

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shortest path finder");
        Group root = new Group();
        Scene scene = new Scene(root, 700, 450, Color.WHITE);
        TabPane tabPane = new TabPane();
        BorderPane borderPane = new BorderPane();

        this.addCitiesTab(tabPane);
        this.addMapTab(tabPane);
        this.addRouteTab(tabPane);
        this.addHelpTab(tabPane);

        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addCitiesTab(TabPane tabPane) {
        Tab tab = new Tab("Cities list");
        TableView table = new TableView();
        TableColumn cityNameColumn = new TableColumn("City name");
        TableColumn xPosColumn = new TableColumn("X coordinate");
        TableColumn yPosColumn = new TableColumn("Y coordinate");

        cityNameColumn.setCellValueFactory(new PropertyValueFactory<City, String>("name"));
        xPosColumn.setCellValueFactory(new PropertyValueFactory<City, String>("posX"));
        yPosColumn.setCellValueFactory(new PropertyValueFactory<City, String>("posY"));

        Label label = new Label("List of all cities");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        table.setItems(cities);
        table.getColumns().addAll(cityNameColumn, xPosColumn, yPosColumn);



        TextField cityNameField = new TextField();
        cityNameField.setPromptText("City Name");
        cityNameField.setMaxWidth(250);

        TextField xPosField = new TextField();
        xPosField.setMaxWidth(90);
        xPosField.setPromptText("X coordinate");
        xPosField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    xPosField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        TextField yPosField = new TextField();
        yPosField.setMaxWidth(90);
        yPosField.setPromptText("Y coordinate");
        yPosField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    yPosField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Button addCityButton = new Button("Add city");
        addCityButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                cities.add(new City(
                        cityNameField.getText(),
                        Integer.parseInt(xPosField.getText()),
                        Integer.parseInt(yPosField.getText())
                ));
                cityNameField.clear();
                xPosField.clear();
                yPosField.clear();
            }
        });




        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(cityNameField, xPosField, yPosField, addCityButton);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, table, hBox);

        tab.setContent(vbox);
        tab.setClosable(false);

        tabPane.getTabs().add(tab);
    }

    public void addMapTab(TabPane tabPane) {
        Tab tab = new Tab("Map");

        tab.setClosable(false);

        tabPane.getTabs().add(tab);
    }

    public void addRouteTab(TabPane tabPane) {
        Tab tab = new Tab("Route");

        tab.setClosable(false);

        tabPane.getTabs().add(tab);
    }

    public void addHelpTab(TabPane tabPane) {
        Tab tab = new Tab("Help");

        tab.setClosable(false);

        tabPane.getTabs().add(tab);
    }
}
