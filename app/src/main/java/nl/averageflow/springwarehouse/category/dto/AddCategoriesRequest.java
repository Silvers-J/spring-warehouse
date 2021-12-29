package nl.averageflow.springwarehouse.category.dto;

public final class AddCategoriesRequest {
    private Iterable<AddCategoriesRequestItem> items;

    public Iterable<AddCategoriesRequestItem> getItems() {
        return this.items;
    }

}