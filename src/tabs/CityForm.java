package tabs;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import models.City;
import controls.NumberField;

public class CityForm extends HBox {
    private ObservableList<City> cities;
    private TextField xPosField = new NumberField();
    private TextField yPosField = new NumberField();
    private TextField cityNameField = new TextField();
    private Button addCityButton = new Button("Add city");

    public CityForm(ObservableList<City> cities) {
        this.cities = cities;
        this.setSpacing(10);
        this.getChildren().addAll(cityNameField, xPosField, yPosField, addCityButton);

        cityNameField.setPromptText("models.City Name");
        xPosField.setPromptText("X coordinate");
        yPosField.setPromptText("Y coordinate");

        cityNameField.setPrefWidth(250);
        xPosField.setPrefWidth(90);
        yPosField.setPrefWidth(90);

        addCityButton.setOnAction(e -> {
            City newCity = createCity();

            if (newCity != null) {
                cities.add(newCity);
                reset();
            }
        });
    }

    private void reset() {
        cityNameField.clear();
        xPosField.clear();
        yPosField.clear();
    }

    private City createCity() {
        String cityName = cityNameField.getText();
        String xPosStr = xPosField.getText();
        String yPosStr = yPosField.getText();

        if (cityName.isEmpty() || xPosStr.isEmpty() ||yPosStr.isEmpty()) {
            return null;
        }

        try {
            Integer xPos = Integer.parseInt(xPosStr);
            Integer yPos = Integer.parseInt(yPosStr);

            return new City(cityName, xPos, yPos);
        } catch (Exception ex) {
            return null;
        }
    }
}
