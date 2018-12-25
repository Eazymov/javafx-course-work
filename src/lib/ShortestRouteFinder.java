package lib;

import models.City;

import java.util.ArrayList;
import java.util.List;

public class ShortestRouteFinder {
    private City firstCity;
    private City lastCity;
    private List<City> cities;

    public ShortestRouteFinder() {
        this.cities = new ArrayList<>();
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public void setFirstCity(City firstCity) {
        this.firstCity = firstCity;
    }

    public void setLastCity(City lastCity) {
        this.lastCity = lastCity;
    }

    public List<City> find() {
        List<City> cities = new ArrayList<>(this.cities);
        List<List<City>> allPaths = Helpers.getListPermutations(cities);
        List<List<City>> filteredPaths = this.filterPaths(allPaths);

        return this.getShortestPath(filteredPaths);
    }

    private List<City> getShortestPath(List<List<City>> paths) {
        List<City> shortestPath = null;
        Integer lowestCost = Integer.MAX_VALUE;

        for (List<City> currentPath : paths) {
            Integer cost = this.getPathCost(currentPath);

            if (cost < lowestCost) {
                lowestCost = cost;
                shortestPath = currentPath;
            }
        }

        return shortestPath;
    }

    private Integer getPathCost(List<City> path) {
        Integer cost = 0;

        for (Integer idx = 1; idx < path.size(); idx++) {
            City prevCity = path.get(idx - 1);
            City curCity = path.get(idx);

            if (prevCity == null) {
                return cost;
            }

            double distance = this.getDistanceBetween(prevCity, curCity);

            cost += (int) distance;
        }

        return cost;
    }

    private double getDistanceBetween(City firstCity, City secondCity) {
        Integer x1 = firstCity.getPosX();
        Integer y1 = firstCity.getPosY();
        Integer x2 = secondCity.getPosX();
        Integer y2 = secondCity.getPosY();

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private List<List<City>> filterPaths(List<List<City>> paths) {
        List<List<City>> filteredPaths = new ArrayList<>();

        for (List<City> path : paths) {
            if (path.isEmpty()) {
                continue;
            }

            if (path.get(0) != this.firstCity) {
                continue;
            }

            City lastCity = path.get(path.size() - 1);

            if (lastCity != this.lastCity) {
                continue;
            }

            filteredPaths.add(path);
        }

        return filteredPaths;
    }
}
