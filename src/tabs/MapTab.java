package tabs;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.text.Font;
import models.City;
import models.GraphBounds;

import java.util.List;

public class MapTab extends Tab {
    private final ObservableList<City> cities;
    private final List<City> route;

    public MapTab(ObservableList<City> cities, List<City> route) {
        this.cities = cities;
        this.route = route;
        this.setText("Карта");
        this.setClosable(false);
        this.setOnSelectionChanged(e -> this.update());
    }

    private void update() {
        this.setContent(this.createLayout());
    }

    private Node createLayout() {
        if (this.cities.size() > 1) {
            return this.createGraph(this.cities, this.route);
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

    private Node createGraph(ObservableList<City> cities, List<City> route) {
        final GraphBounds graphBounds = this.getGraphBounds(cities);
        final NumberAxis xAxis = new NumberAxis(graphBounds.getMinX(), graphBounds.getMaxX(), 1);
        final NumberAxis yAxis = new NumberAxis(graphBounds.getMinY(), graphBounds.getMaxY(), 1);
        final LineChart<Number, Number> lineChart = new LineChart(xAxis, yAxis);
        final XYChart.Series seriesCities = new XYChart.Series();
        final Font labelFont = new Font("Arial", 12);

        seriesCities.setName("Город");

        lineChart.getXAxis().setTickMarkVisible(false);
        lineChart.getYAxis().setTickMarkVisible(false);
        lineChart.setHorizontalZeroLineVisible(false);
        lineChart.setVerticalZeroLineVisible(false);
        lineChart.setLegendVisible(false);
        lineChart.getData().addAll(seriesCities);
        lineChart.lookup(".default-color0.chart-series-line").setStyle("-fx-stroke: transparent");

        for (int idx = 1; idx < route.size(); idx++) {
            City prevCity = route.get(idx - 1);
            City curCity = route.get(idx);
            XYChart.Series series = new XYChart.Series();
            XYChart.Data firstDot = new XYChart.Data(prevCity.getPosX(), prevCity.getPosY());
            XYChart.Data secondDot = new XYChart.Data(curCity.getPosX(), curCity.getPosY());
            String selector = ".default-color" + idx + ".chart-series-line";

            series.getData().addAll(firstDot, secondDot);
            lineChart.getData().add(series);
            lineChart.lookup(selector).setStyle("-fx-stroke: green");
        }

        for (City city : cities) {
            final XYChart.Data data = new XYChart.Data(city.getPosX(), city.getPosY());
            final Label label = new Label(city.getName());

            label.setFont(labelFont);
            data.setNode(label);
            data.getNode().toFront();

            seriesCities.getData().add(data);
        }

        return lineChart;
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
