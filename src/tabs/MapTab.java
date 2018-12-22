package tabs;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.text.Font;
import models.City;
import models.GraphBounds;

import java.util.List;

public class MapTab extends Tab {
    private final ObservableList<City> cities;
    private List<City> shortestRoute;

    public MapTab(ObservableList<City> cities, List<City> shortestRoute) {
        this.cities = cities;
        this.shortestRoute = shortestRoute;
        this.setText("Карта");
        this.setClosable(false);
        this.setOnSelectionChanged(e -> this.update());
    }

    private void update() {
        this.setContent(this.createLayout());
    }

    private Node createLayout() {
        if (this.cities.size() > 1) {
            return this.createGraph(this.cities);
        }

        return this.createFallback();
    }

    private Node createFallback() {
        Label label = new Label("Необходимо указать данные о более чем одном городе для построения карты");

        label.setWrapText(true);
        label.setFont(new Font("Arial", 18));
        label.setPadding(new Insets(10));

        return label;
    }

    private Node createGraph(ObservableList<City> cities) {
        final GraphBounds graphBounds = this.getGraphBounds(cities);
        final NumberAxis xAxis = new NumberAxis(graphBounds.getMinX(), graphBounds.getMaxX(), 1);
        final NumberAxis yAxis = new NumberAxis(graphBounds.getMinY(), graphBounds.getMaxY(), 1);
        final ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        final XYChart.Series seriesCity = new XYChart.Series();

        seriesCity.setName("Город");

        scatterChart.getXAxis().setTickMarkVisible(false);
        scatterChart.getYAxis().setTickMarkVisible(false);

        for (City city : cities) {
            final XYChart.Data data = new XYChart.Data(city.getPosX(), city.getPosY());

            data.setNode(new Label(city.getName()));

            seriesCity.getData().add(data);
        }

        scatterChart.getData().addAll(seriesCity);

        return scatterChart;
    }

    private GraphBounds getGraphBounds(ObservableList<City> cities) {
        Integer minX = Integer.MAX_VALUE;
        Integer maxX = Integer.MIN_VALUE;
        Integer minY = Integer.MAX_VALUE;
        Integer maxY = Integer.MIN_VALUE;
        final Integer padding = 2;

        for (City city : cities) {
            Integer posX = city.getPosX();
            Integer posY = city.getPosY();

            if (posX > maxX) {
                maxX = posX;
            }

            if (posX < minX) {
                minX = posX;
            }

            if (posY > maxY) {
                maxY = posY;
            }

            if (posY < minY) {
                minY = posY;
            }
        }

        return new GraphBounds(minX - padding, maxX + padding, minY - padding, maxY + padding);
    }
}
