package tabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import models.City;
import lib.ShortestRouteFinder;

import java.util.List;

public class RouteTab extends Tab {
    private final ObservableList<City> cities;
    private City firstCity;
    private City lastCity;
    private List<City> route;

    public RouteTab(ObservableList<City> cities, List<City> route) {
        this.cities = cities;
        this.route = route;
        this.setText("Маршрут");
        this.setClosable(false);
        this.setContent(this.createLayout());
        this.setOnSelectionChanged(e -> this.update());
    }

    private void update() {
        this.setContent(this.createLayout());
    }

    private Node createLayout() {
        HBox hBox = new HBox();
        ObservableList<String> options = this.getOptions(this.cities);
        ComboBox firstCitySelect = new ComboBox(options);
        ComboBox lastCitySelect = new ComboBox(options);
        Button submitBtn = new Button("Построить маршрут");

        submitBtn.setOnAction(e -> this.createRoute());

        firstCitySelect.setPromptText("Начальный город");
        lastCitySelect.setPromptText("Конечный город");

        if (this.firstCity != null) {
            firstCitySelect.setValue(this.firstCity.getName());
        }

        if (this.lastCity != null) {
            lastCitySelect.setValue(this.lastCity.getName());
        }

        firstCitySelect.setOnAction(e -> this.handleFirstCityChange(firstCitySelect));
        lastCitySelect.setOnAction(e -> this.handleLastCityChange(lastCitySelect));

        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));
        hBox.getChildren().addAll(firstCitySelect, lastCitySelect, submitBtn);

        return hBox;
    }

    private void handleFirstCityChange(ComboBox comboBox) {
        Integer index = comboBox.getSelectionModel().getSelectedIndex();
        City selectedCity = this.cities.get(index);

        if (selectedCity == null) {
            return;
        }

        this.firstCity = selectedCity;
    }

    private void handleLastCityChange(ComboBox comboBox) {
        Integer index = comboBox.getSelectionModel().getSelectedIndex();
        City selectedCity = this.cities.get(index);

        if (selectedCity == null) {
            return;
        }

        this.lastCity = selectedCity;
    }

    private ObservableList<String> getOptions(ObservableList<City> cities) {
        ObservableList<String> options = FXCollections.observableArrayList();

        for (City city : cities) {
            options.add(city.getName());
        }

        return options;
    }

    private void createRoute() {
        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");

        if (this.firstCity == null) {
            alert.setContentText("Не выбран начальный город");
            alert.show();

            return;
        }

        if (this.lastCity == null) {
            alert.setContentText("Не выбран конечный город");
            alert.show();

            return;
        }

        ShortestRouteFinder routeFinder = new ShortestRouteFinder();

        routeFinder.setCities(this.cities);
        routeFinder.setFirstCity(this.firstCity);
        routeFinder.setLastCity(this.lastCity);

        this.route.clear();
        this.route.addAll(routeFinder.find());
    }
}
