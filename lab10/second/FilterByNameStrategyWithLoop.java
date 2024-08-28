package lab10.second;

import java.util.ArrayList;
import java.util.List;

public class FilterByNameStrategyWithLoop implements FilterExportByNameStrategy {
    @Override
    public List<Export> filterByName(List<Export> exports, String name) {
        List<Export> result = new ArrayList<>();
        for (Export export : exports) {
            if (export.getName().equals(name)) {
                result.add(export);
            }
        }
        return result;
    }

    @Override
    public String calculateTotalQuantity(List<Export> exports) {
        int result = 0;
        for (Export export : exports) {
            result += export.getQuantity();
        }
        return String.valueOf(result);
    }
}
