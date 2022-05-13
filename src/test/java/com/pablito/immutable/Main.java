package com.pablito.immutable;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Long id = 25L;
        List<String> categories = new ArrayList<>();

        Product product = new Product(id, categories);

        System.out.println(product);

        id = 100L;
        categories.add("DUPA");

        System.out.println(product);

        product.getCategories().add("LALALa");
    }
}
