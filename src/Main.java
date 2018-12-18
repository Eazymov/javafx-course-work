import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.City;
import tabs.CitiesTab;
import tabs.HelpTab;
import tabs.MapTab;
import tabs.RoutesTab;

public class Main extends Application {
    private final ObservableList<City> cities = FXCollections.observableArrayList();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Найти кратчайший маршрут");
        Group root = new Group();
        Scene scene = new Scene(root, 700, 450, Color.WHITE);
        TabPane tabPane = new TabPane();
        BorderPane borderPane = new BorderPane();

        MapTab mapTab = new MapTab(this.cities);
        HelpTab helpTab = new HelpTab();
        RoutesTab routesTab = new RoutesTab();
        CitiesTab citiesTab = new CitiesTab(primaryStage, this.cities);

        tabPane.getTabs().addAll(citiesTab, mapTab, routesTab, helpTab);

        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        borderPane.setCenter(tabPane);

        root.getChildren().add(borderPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
