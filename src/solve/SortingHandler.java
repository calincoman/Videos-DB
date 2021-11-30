package solve;

import common.Constants;
import fileio.ActionInputData;

import java.util.*;

public final class SortingHandler {

    private SortingHandler() {
    }

    public static ArrayList<Map.Entry<String, Double>> customSortEntries(final Map<String, Double> map,
                                              final String sortOrder) {

        ArrayList<Map.Entry<String, Double>> entryList = new ArrayList<>(map.entrySet());

        entryList.sort(new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> entry1, Map.Entry<String, Double> entry2) {
                Double val1 = entry1.getValue();
                Double val2 = entry2.getValue();

                int compareValue = val1.compareTo(val2);

                if (compareValue == 0) {
                    String key1 = entry1.getKey();
                    String key2 = entry2.getKey();

                    if (sortOrder.equals(Constants.DESCENDING)) {
                        return key2.compareTo(key1);
                    }

                    return key1.compareTo(key2);
                }

                if (sortOrder.equals(Constants.DESCENDING)) {
                    return val2.compareTo(val1);
                }
                return val1.compareTo(val2);
            }
        });

        return entryList;
    }
}
