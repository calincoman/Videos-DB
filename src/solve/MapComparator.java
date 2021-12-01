package solve;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public final class MapComparator implements Comparator<Map.Entry<String, Double>> {

    @Override
    public int compare(final Map.Entry<String, Double> entry1,
                       final Map.Entry<String, Double> entry2) {
        Double value1 = entry1.getValue();
        Double value2 = entry2.getValue();

        int compareValues = value1.compareTo(value2);

        if (compareValues == 0) {
            String key1 = entry1.getKey();
            String key2 = entry2.getKey();

            return key1.compareTo(key2);
        }

        return compareValues;
    }
}
