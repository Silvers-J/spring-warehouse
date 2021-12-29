package nl.averageflow.springwarehouse.article.dto;

public final class AddArticlesRequest {
    private Iterable<AddArticlesRequestItem> inventory;

    public Iterable<AddArticlesRequestItem> getInventory() {
        return this.inventory;
    }

}