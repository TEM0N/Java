package lab12.strategy;

import lab12.app.Export;

import java.util.List;

public interface FilterExportByNameStrategy {
    List<Export> filterByName(List<Export> exports, String name);

    String calculateTotalQuantity(List<Export> exports);
}
