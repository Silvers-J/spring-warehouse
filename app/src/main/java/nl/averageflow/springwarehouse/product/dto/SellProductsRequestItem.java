package nl.averageflow.springwarehouse.product.dto;

import java.util.UUID;

public final class SellProductsRequestItem {
    private UUID itemUid;
    private long amountOf;


    public UUID getItemUid() {
        return itemUid;
    }

    public long getAmountOf() {
        return amountOf;
    }
}
