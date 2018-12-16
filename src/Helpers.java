import java.util.ArrayList;
import java.util.List;

class Helpers {

    /**
     * Возвращает список всех возможных вариаций расстановки элементов списка
     *
     * @param <T>  Тип элемента в массиве
     * @param list массив элементов
     * @return {@code List<List<T>}
     */
    public static <T> List<List<T>> getListPermutations(List<T> list) {
        List<List<T>> result = new ArrayList<>();

        if (list.isEmpty()) {
            result.add(new ArrayList<>());

            return result;
        }

        T firstElement = list.remove(0);
        List<List<T>> permutations = getListPermutations(list);

        permutations.forEach((List<T> smallerPermutated) -> {
            for (int idx = 0; idx <= smallerPermutated.size(); idx++) {
                List<T> temp = new ArrayList<>(smallerPermutated);

                temp.add(idx, firstElement);
                result.add(temp);
            }
        });

        return result;
    }
}
