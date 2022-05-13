package com.pablito.immutable;

import java.util.Collections;
import java.util.List;

public final class Product {

    private final Long id;
    private final List<String> categories;

    public Product(final Long id, final List<String> categories) {
        this.id = id;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public List<String> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", categories=" + categories +
                '}';
    }
}
