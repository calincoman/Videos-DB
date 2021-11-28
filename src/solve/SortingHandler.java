package solve;

import common.Constants;
import fileio.ActionInputData;

import java.util.*;

public final class SortingHandler {

    private SortingHandler() {
    }

    public static Map<String, Double> sortMap(final Map<String, Double> map,
                                               final String sortOrder) {

        ArrayList<Map.Entry<String, Double>> entryList = new ArrayList<>(map.entrySet());

        Map<String, Double> sortedMap = new HashMap<String, Double>();

        MapComparator sortingComparator = new MapComparator();
        if (sortOrder.equals(Constants.ASCENDING)) {
            entryList.sort(sortingComparator);
        } else {
            entryList.sort(sortingComparator.reversed());
        }

        for (Map.Entry<String, Double> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
