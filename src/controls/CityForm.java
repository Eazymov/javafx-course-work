package controls;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import models.City;

public class CityForm extends HBox {
    private final ObservableList<City> cities;
    private final TextField xPosField = new NumberField();
    private final TextField yPosField = new NumberField();
    private final TextField cityNameField = new TextField();
    private final Button addCityButton = new Button("Добавить город");

    public CityForm(ObservableList<City> cities) {
        this.cities = cities;
        this.setSpacing(10);
        this.getChildren().addAll(cityNameField, xPosField, yPosField, addCityButton);

        cityNameField.setPromptText("Название города");
        xPosField.setPromptText("Координата X");
        yPosField.setPromptText("Координата Y");

        cityNameField.setPrefWidth(250);
        xPosField.setPrefWidth(95);
        yPosField.setPrefWidth(95);

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
