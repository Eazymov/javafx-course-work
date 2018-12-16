package models;

import java.util.HashMap;
import java.util.Map;

public class Ways {
    private final Map<String, Map<String, Boolean>> map;

    /**
     * Инициализирует поле map
     */
    public Ways() {
        this.map = new HashMap<>();
    }

    /**
     * Добавляет путь между двумя городами
     *
     * @param firstCityName  имя первого города
     * @param secondCityName имя второго города
     */
    public void addWay(String firstCityName, String secondCityName) {
        if (!map.containsKey(firstCityName)) {
            map.put(firstCityName, new HashMap<>());
        }

        if (!map.containsKey(secondCityName)) {
            map.put(secondCityName, new HashMap<>());
        }

        map.get(firstCityName).put(secondCityName, true);
        map.get(secondCityName).put(firstCityName, true);
    }

    /**
     * Проверяет есть ли путь между двумя городами
     *
     * @param firstCity  первый город
     * @param secondCity второй город
     * @return true если путь между двумя городами существует, иначе false
     */
    public boolean hasWay(City firstCity, City secondCity) {
        String firstCityName = firstCity.getName();
        String secondCityName = secondCity.getName();

        if (!map.containsKey(firstCityName)) {
            return false;
        }

        Map<String, Boolean> firstCityWays = map.get(firstCityName);

        return firstCityWays.containsKey(secondCityName);
    }
}
