package nl.averageflow.springwarehouse.product.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.averageflow.springwarehouse.category.model.Category;
import nl.averageflow.springwarehouse.product.dto.AddProductsRequestItem;
import nl.averageflow.springwarehouse.product.dto.ProductImagesData;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Table(name = "products")
@Entity
public final class Product {
    public static final String[] ELEMENTS = new String[]{};

    @Id
    @GeneratedValue
    @Column(name = "uid", nullable = false)
    private UUID uid;

    @Column(name = "item_name", nullable = false)
    private String name;

    @Column(name = "image_urls")
    private String imageURLs;

    @Column(name = "price", nullable = false)
    private Double price;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_uid")
    private Category category;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ArticleAmountInProduct> articleProductRelation;


    public Product() {
    }

    public Product(final AddProductsRequestItem item, final Category category) {
        this.name = item.getName();
        this.price = item.getPrice();
        this.setImageURLs(item.getImageURLs());
        this.category = category;
    }

    public UUID getUid() {
        return this.uid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    public long getProductStock() {
        final List<Long> amountOfProductsPossibleList = new ArrayList<>();
        if (this.articleProductRelation == null || articleProductRelation.isEmpty()) {
            return 0L;
        }

        this.articleProductRelation.forEach(articleAmountInProduct -> {
            final long articleAmountNeeded = articleAmountInProduct.getAmountOf();
            final long articleStockPresent = articleAmountInProduct.getArticle().getStock();

            if (articleStockPresent >= articleAmountNeeded) {
                amountOfProductsPossibleList.add(articleStockPresent / articleAmountNeeded);
            }
        });

        if (amountOfProductsPossibleList.size() != this.articleProductRelation.size()) {
            return 0L;
        }

        final Optional<Long> minimumAmountPossible = amountOfProductsPossibleList.stream()
                .min(Comparator.naturalOrder());

        if (minimumAmountPossible.isEmpty()) {
            return 0L;
        }

        return minimumAmountPossible.get();
    }

    public Set<ArticleAmountInProduct> getArticles() {
        return this.articleProductRelation;
    }

    public Iterable<String> getImageURLs() {
        final ObjectMapper objectMapper = new ObjectMapper();

        try {
            final ProductImagesData images = objectMapper.readValue(this.imageURLs, ProductImagesData.class);
            return images.getUrls();
        } catch (final Exception e) {
            return List.of(ELEMENTS);
        }
    }

    public void setImageURLs(final Iterable<String> imageURLs) {
        final ObjectMapper objectMapper = new ObjectMapper();

        try {
            this.imageURLs = objectMapper.writeValueAsString(new ProductImagesData(imageURLs));
        } catch (final Exception e) {
            this.imageURLs = null;
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }
}