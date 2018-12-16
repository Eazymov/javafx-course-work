import enums.JsonFields;
import models.City;
import models.Ways;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class LongestPathFinder {

    private Ways ways;
    private City firstCity;
    private List<City> cities;

    /**
     * Инициализирует значения по умолчанию
     */
    public LongestPathFinder() {
        this.cities = new ArrayList<>();
        this.ways = new Ways();
    }

    /**
     * Задает поле cities
     *
     * @param cities список городов
     */
    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    /**
     * Задает поле waysList
     *
     * @param waysList набор всех путей между городами
     */
    public void setWays(Ways waysList) {
        this.ways = waysList;
    }

    /**
     * Задает поле firstCity
     *
     * @param firstCity город с которого начинается путешествие
     */
    public void setFirstCity(City firstCity) {
        this.firstCity = firstCity;
    }

    /**
     * Читает данные из файла в формате JSON
     *
     * @param src путь к файлу
     * @throws java.io.IOException                   возбуждается когда файл не найден
     * @throws org.json.simple.parser.ParseException возбуждается когда файл имеет некорректный формат
     */
    public void readFromFile(String src) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object text = parser.parse(new FileReader(src));
        JSONObject json = (JSONObject) text;

        try {
            readCitiesFromJson(json);
            readFirstCityFromJson(json);
            readWaysFromJson(json);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("json не содержит поля " + e.getMessage());
        }
    }

    /**
     * Читает список городов из JSON
     *
     * @param json данные в формате JSON которые содержат список городов
     */
    private void readCitiesFromJson(JSONObject json) {
        ArrayList<City> citiesList = new ArrayList<>();
        String jsonCityField = JsonFields.CITIES.toString();
        JSONArray citiesJson = (JSONArray) json.get(jsonCityField);

        if (citiesJson == null) {
            throw new IllegalArgumentException(jsonCityField);
        }

        for (JSONObject cityJson : (Iterable<JSONObject>) citiesJson) {
            String cityName = (String) cityJson.get("name");
            Integer cityPosX = (Integer) cityJson.get("posX");
            Integer cityPosY = (Integer) cityJson.get("posY");
            City city = new City(cityName, cityPosX, cityPosY);

            citiesList.add(city);
        }

        this.setCities(citiesList);
    }

    /**
     * Читает имя первого города из JSON
     *
     * @param json данные в формате JSON которые содержат имя первого города
     */
    private void readFirstCityFromJson(JSONObject json) {
        String jsonFirstCityNameField = JsonFields.FIRST_CITY_NAME.toString();
        String firstCityName = (String) json.get(jsonFirstCityNameField);

        if (firstCityName == null) {
            throw new IllegalArgumentException(jsonFirstCityNameField);
        }

        for (City city : this.cities) {
            if (city.getName().equals(firstCityName)) {
                this.setFirstCity(city);
            }
        }
    }

    /**
     * Читает список путей между городами из JSON
     *
     * @param json данные в формате JSON которые содержат список путей
     */
    private void readWaysFromJson(JSONObject json) {
        Ways ways = new Ways();
        String jsonWaysField = JsonFields.WAYS.toString();
        JSONArray waysListJson = (JSONArray) json.get(jsonWaysField);

        if (waysListJson == null) {
            throw new IllegalArgumentException(jsonWaysField);
        }

        Iterator<JSONArray> waysListIterator = waysListJson.iterator();

        if (waysListJson.isEmpty()) return;

        while (waysListIterator.hasNext()) {
            JSONArray citiesWayNames = waysListIterator.next();
            String firstCityName = (String) citiesWayNames.get(0);
            String secondCityName = (String) citiesWayNames.get(1);

            ways.addWay(firstCityName, secondCityName);
        }

        this.setWays(ways);
    }

    /**
     * Находит длиннейший возможный путь
     *
     * @return {@code models.City<List>}
     */
    public List<City> find() {
        List<City> cities = new ArrayList<>(this.cities);
        List<List<City>> allPaths = Helpers.getListPermutations(cities);
        List<List<City>> correctPaths = this.getCorrectPaths(allPaths);

        return this.getLongestPath(correctPaths);
    }

    /**
     * Возвращает длиннейший путь из списка переданных
     *
     * @param paths список путей
     * @return {@code List<models.City>}
     */
    private List<City> getLongestPath(List<List<City>> paths) {
        List<City> longestPath = new ArrayList<>();

        for (List<City> curPath : paths) {
            if (curPath.size() > longestPath.size()) {
                longestPath = curPath;
            }
        }

        return longestPath;
    }

    /**
     * Возвращает отфильтрованный список состоящий из корректных неразрывных
     * путей
     *
     * @param paths корректные пути
     * @return {@code List<List<models.City>>}
     */
    private List<List<City>> getCorrectPaths(List<List<City>> paths) {
        List<List<City>> correctPaths = new ArrayList<>();

        for (List<City> path : paths) {
            if (path.isEmpty()) {
                continue;
            }

            if (path.get(0) != firstCity) {
                continue;
            }

            List<City> correctedPath = this.correctPath(path);
            City lastCity = correctedPath.get(correctedPath.size() - 1);

            if (ways.hasWay(lastCity, firstCity)) {
                correctedPath.add(firstCity);
                correctPaths.add(correctedPath);
            }
        }

        return correctPaths;
    }

    /**
     * Возвращает путь обрезанный на точке разрыва
     *
     * @param path путь
     * @return {@code List<models.City>}
     */
    private List<City> correctPath(List<City> path) {
        for (int idx = 1; idx < path.size(); idx++) {
            City currentCity = path.get(idx);
            City previousCity = path.get(idx - 1);

            boolean hasWay = this.ways.hasWay(previousCity, currentCity);

            if (!hasWay) {
                return path.subList(0, idx);
            }
        }

        return path;
    }
}
