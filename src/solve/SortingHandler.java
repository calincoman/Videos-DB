package solve;

import common.Constants;
import fileio.ActionInputData;

import java.util.*;

/**
 * Class used for sorting
 */
public final class SortingHandler {

    private SortingHandler() {
    }

    /**
     * Sorts a (String, Double) Map first by value, then by key in the specified order
     * @param map map to be sorted
     * @param sortOrder sorting order (ascending / descending)
     * @return array list containing the sorted entries of the map
     */
    public static ArrayList<Map.Entry<String, Double>> customSortEntries(final Map<String, Double> map,
                                              final String sortOrder) {

        ArrayList<Map.Entry<String, Double>> entryList = new ArrayList<>(map.entrySet());

        if (sortOrder.equals(Constants.DESCENDING)) {
            entryList.sort(Comparator.comparing(Map.Entry<String, Double>::getValue)
                    .thenComparing(Map.Entry<String, Double>::getKey).reversed());
        } else {
            entryList.sort(Comparator.comparing(Map.Entry<String, Double>::getValue)
                    .thenComparing(Map.Entry<String, Double>::getKey));
        }

        return entryList;
    }
}
