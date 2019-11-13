package com.es.phoneshop.model.product;

import java.util.List;
import java.util.stream.Collectors;

public class Sort {

    private List<Product> listToSort;

    public Sort(List<Product> listToSort) {
        this.listToSort = listToSort;
    }

    public List<Product> sortProductsList(String fieldToSort, String orderToSort) {
        return fieldToSort.equals("description") ?
                           sortingOnDescription(orderToSort) :
                           sortingOnPrice(orderToSort);
    }

    private List<Product> sortingOnPrice(String order) {
                     return listToSort.stream().sorted((prod1, prod2) -> {
                         return order.equals("asc") ?
                                      prod1.getPrice().compareTo(prod2.getPrice()) :
                                      prod2.getPrice().compareTo(prod1.getPrice());
                     })
                                     .collect(Collectors.toList());
    }

    private List<Product> sortingOnDescription(String order) {
        return listToSort.stream().sorted((prod1, prod2) -> {
            return order.equals("asc") ?
                         prod1.getDescription().compareTo(prod2.getDescription()) :
                         prod2.getDescription().compareTo(prod1.getDescription());
                    })   .collect(Collectors.toList());
    }
}
