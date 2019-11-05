package com.es.phoneshop.model.product;

import java.util.List;
import java.util.stream.Collectors;

public class Sort {
    public List<Product> sortProductsList(List<Product> listToSort, String fieldToSort, String orderToSort) {
        if (fieldToSort == null && orderToSort == null)
            return listToSort;
        return fieldToSort.equals("description") ? getSortedListForDescription(listToSort, orderToSort)
                : getSortedListForPrice(listToSort, orderToSort);
    }

    private List<Product> getSortedListForDescription(List<Product> listToSort, String orderToSort) {
        return orderToSort.equals("asc") ? listToSort.stream().sorted((prod1, prod2) ->
                (prod1.getDescription().compareTo(prod2.getDescription()))).collect(Collectors.toList())
                : listToSort.stream().sorted((prod1, prod2) ->
                (prod2.getDescription().compareTo(prod1.getDescription()))).collect(Collectors.toList());
    }

    private List<Product> getSortedListForPrice(List<Product> listToSort, String orderToSort) {
        return orderToSort.equals("asc") ? listToSort.stream().sorted((prod1, prod2) ->
                prod1.getPrice().compareTo(prod2.getPrice())).collect(Collectors.toList())
                : listToSort.stream().sorted((prod1, prod2) ->
                prod2.getPrice().compareTo(prod1.getPrice())).collect(Collectors.toList());
    }
}
