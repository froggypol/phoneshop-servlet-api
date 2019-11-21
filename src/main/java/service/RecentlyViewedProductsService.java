package service;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.RecentlyViewedProductsModel;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Queue;

public class RecentlyViewedProductsService {

    private static RecentlyViewedProductsService recentlyViewedProductsService;

    private RecentlyViewedProductsService() {}

    public static RecentlyViewedProductsService getInstance() {
        if (recentlyViewedProductsService == null) {
            recentlyViewedProductsService = new RecentlyViewedProductsService();
        }
        return recentlyViewedProductsService;
    }

    private void setRecentlyViewedProducts(HttpServletRequest request, Queue<Product> listoSet) {
        request.getSession().setAttribute("recentlyViewedProducts", listoSet);
    }

    public void addProductToViewedList(Product product, HttpServletRequest request) {
        RecentlyViewedProductsModel recentlyViewedProductsModel = new RecentlyViewedProductsModel();
        Queue<Product> recentlyViewedProductsList = getRecentlyViewedProducts(request);
        recentlyViewedProductsModel.setRecentlyViewedList(recentlyViewedProductsList);
        recentlyViewedProductsModel.addProductToViewedList(product);
        setRecentlyViewedProducts(request, recentlyViewedProductsList);
    }

    private Queue<Product> getRecentlyViewedProducts(HttpServletRequest request) {
        Queue<Product> recentlyViewedProductList = (Queue<Product>) request.getSession().getAttribute("recentlyViewedProducts");
        if (recentlyViewedProductList != null) {
            return recentlyViewedProductList;
        }
        return new LinkedList<>();
    }
}
