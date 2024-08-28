package lab10.second;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FilterByNameStrategyWithStream implements FilterExportByNameStrategy {
    @Override
    public List<Export> filterByName(List<Export> exports, String name) {
        return exports.stream()
                .filter(export -> export.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public String calculateTotalQuantity(List<Export> exports) {
        AtomicInteger total = new AtomicInteger();
        exports.forEach(export -> total.addAndGet(export.getQuantity()));
        return String.valueOf(total);
    }
}
