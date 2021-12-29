package nl.averageflow.springwarehouse.product.dto;

public final class AddProductRequest {

    private Iterable<AddProductsRequestItem> products;

    public Iterable<AddProductsRequestItem> getProducts() {
        return this.products;
    }

}
