import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private ObservableList<City> cities = FXCollections.observableArrayList(
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

        MapTab mapTab = new MapTab();
        HelpTab helpTab = new HelpTab();
        RoutesTab routesTab = new RoutesTab();
        CitiesTab citiesTab = new CitiesTab(this.cities);

        tabPane.getTabs().addAll(citiesTab, mapTab, routesTab, helpTab);

        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        borderPane.setCenter(tabPane);

        root.getChildren().add(borderPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
