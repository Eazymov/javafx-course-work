import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CitiesTab extends Tab {
    private ObservableList<City> cities;

    public CitiesTab(ObservableList<City> cities) {
        this.cities = cities;

        VBox layout = this.createLayout();

        this.setText("Cities list");
        this.setClosable(false);
        this.setContent(layout);
    }

    private VBox createLayout() {
        VBox vBox = new VBox();
        Label label = this.createLabel();
        TableView table = this.createTable();
        CityForm cityForm = new CityForm(this.cities);
        Insets insets = new Insets(10, 10, 10, 10);

        vBox.setSpacing(10);
        vBox.setPadding(insets);
        vBox.getChildren().addAll(label, table, cityForm);

        return vBox;
    }

    private Label createLabel() {
        Label label = new Label("List of all cities");
        label.setFont(new Font("Arial", 20));

        return label;
    }

    private TableView createTable() {
        TableView table = new TableView();
        TableColumn cityNameColumn = new TableColumn("City name");
        TableColumn xPosColumn = new TableColumn("X coordinate");
        TableColumn yPosColumn = new TableColumn("Y coordinate");

        cityNameColumn.setPrefWidth(250);
        xPosColumn.setPrefWidth(100);
        yPosColumn.setPrefWidth(100);

        cityNameColumn.setCellValueFactory(new PropertyValueFactory<City, String>("name"));
        xPosColumn.setCellValueFactory(new PropertyValueFactory<City, String>("posX"));
        yPosColumn.setCellValueFactory(new PropertyValueFactory<City, String>("posY"));

        table.setEditable(true);
        table.setItems(cities);
        table.getColumns().addAll(cityNameColumn, xPosColumn, yPosColumn);

        return table;
    }
}
