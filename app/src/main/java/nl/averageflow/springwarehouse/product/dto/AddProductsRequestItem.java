package nl.averageflow.springwarehouse.product.dto;

import java.util.UUID;

public final class AddProductsRequestItem {
    private String name;
    private Double price;
    private UUID categoryUid;
    private Iterable<AddProductsRequestArticleItem> containArticles;
    private Iterable<String> imageURLs;

    public Double getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }

    public Iterable<AddProductsRequestArticleItem> getContainArticles() {
        return containArticles;
    }

    public Iterable<String> getImageURLs() {
        return imageURLs;
    }

    public UUID getCategoryUid() {
        return categoryUid;
    }
}
