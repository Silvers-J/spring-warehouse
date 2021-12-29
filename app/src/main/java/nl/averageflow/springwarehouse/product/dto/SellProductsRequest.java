package nl.averageflow.springwarehouse.product.dto;

public final class SellProductsRequest {
    private Iterable<SellProductsRequestItem> wantedItemsForSale;

    public Iterable<SellProductsRequestItem> getWantedItemsForSale() {
        return this.wantedItemsForSale;
    }
}
