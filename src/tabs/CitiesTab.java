package tabs;

import controls.CityForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import models.City;
import enums.CityJsonFields;

public class CitiesTab extends Tab {
    private final Stage stage;
    private final HBox header;
    private final CityForm cityForm;
    private final TableView<City> table;
    private final FileChooser jsonChooser;
    private final ObservableList<City> cities;

    public CitiesTab(Stage stage, ObservableList<City> cities) {
        this.stage = stage;
        this.cities = cities;
        this.header = this.createHeader();
        this.table = this.createTable();
        this.cityForm = new CityForm(this.cities);
        this.jsonChooser = this.createJsonChooser();

        VBox layout = this.createLayout();

        layout.getChildren().addAll(this.header, this.table, this.cityForm);

        this.setText("Список городов");
        this.setClosable(false);
        this.setContent(layout);
    }

    private FileChooser createJsonChooser() {
        FileChooser jsonChooser = new FileChooser();
        ExtensionFilter extFilter = new ExtensionFilter("JSON файлы","*.json");

        jsonChooser.getExtensionFilters().add(extFilter);
        jsonChooser.setTitle("Выберите json файл");

        return jsonChooser;
    }

    private VBox createLayout() {
        VBox vBox = new VBox();
        Insets insets = new Insets(10, 10, 10, 10);


        vBox.setSpacing(10);
        vBox.setPadding(insets);

        return vBox;
    }

    private HBox createHeader() {
        HBox hBox = new HBox();
        Button delBtn = new Button("Удалить выбранный");
        Button loadCitiesBtn = new Button("Загрузить список");
        Button saveCitiesBtn = new Button("Сохранить список");

        delBtn.setOnAction(e -> this.removeSelectedCity());
        loadCitiesBtn.setOnAction(e -> this.loadCitiesFromFile());
        saveCitiesBtn.setOnAction(e -> this.saveCitiesToFile());

        hBox.setSpacing(10);
        hBox.getChildren().addAll(delBtn, loadCitiesBtn, saveCitiesBtn);

        return hBox;
    }

    private TableView createTable() {
        TableView table = new TableView();
        TableColumn cityNameColumn = new TableColumn("Название");
        TableColumn xPosColumn = new TableColumn("Координата X");
        TableColumn yPosColumn = new TableColumn("Координата Y");

        cityNameColumn.setPrefWidth(250);
        xPosColumn.setPrefWidth(105);
        yPosColumn.setPrefWidth(105);

        cityNameColumn.setCellValueFactory(new PropertyValueFactory<City, String>("name"));
        xPosColumn.setCellValueFactory(new PropertyValueFactory<City, String>("posX"));
        yPosColumn.setCellValueFactory(new PropertyValueFactory<City, String>("posY"));

        table.setEditable(true);
        table.setItems(cities);
        table.getColumns().addAll(cityNameColumn, xPosColumn, yPosColumn);

        return table;
    }

    private void removeSelectedCity() {
        City selectedCity = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(selectedCity);
    }

    private void saveCitiesToFile() {
        File file = this.jsonChooser.showSaveDialog(this.stage);

        if (file == null) {
            return;
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            String citiesJson = this.getCitiesJson();

            fileWriter.write(citiesJson);
            fileWriter.flush();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

    private void loadCitiesFromFile() {
        File file = this.jsonChooser.showOpenDialog(this.stage);

        if (file == null) {
            return;
        }

        try {
            JSONParser parser = new JSONParser();
            Object text = parser.parse(new FileReader(file));
            JSONArray json = (JSONArray) text;
            ObservableList<City> cities = this.readCitiesFromJson(json);

            this.cities.clear();
            this.cities.addAll(cities);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

    private String getCitiesJson() {
        JSONArray citiesJson = new JSONArray();

        for (City city : this.cities) {
            JSONObject cityJson = new JSONObject();

            cityJson.put(CityJsonFields.NAME.toString(), city.getName());
            cityJson.put(CityJsonFields.POS_X.toString(), city.getPosX());
            cityJson.put(CityJsonFields.POS_Y.toString(), city.getPosY());

            citiesJson.add(cityJson);
        }

        return citiesJson.toJSONString();
    }

    private ObservableList<City> readCitiesFromJson(JSONArray citiesJson) {
        ObservableList<City> cities = FXCollections.observableArrayList();

        for (JSONObject cityJson : (Iterable<JSONObject>) citiesJson) {
            String cityName = (String) cityJson.get(CityJsonFields.NAME.toString());
            Long cityPosX = (Long) cityJson.get(CityJsonFields.POS_X.toString());
            Long cityPosY = (Long) cityJson.get(CityJsonFields.POS_Y.toString());
            City city = new City(cityName, cityPosX.intValue(), cityPosY.intValue());

            cities.add(city);
        }

        return cities;
    }
}
