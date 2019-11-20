package service;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.RecentlyViewedProductsModel;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Queue;

public class RecentlyViewedProductsService {

    private Queue<Product> recentlyViewedList;

    private static RecentlyViewedProductsService recentlyViewedProducts;

    private RecentlyViewedProductsModel recentlyViewedListModel;

    private RecentlyViewedProductsService() {
        recentlyViewedList = new LinkedList<>();
    }

    public static RecentlyViewedProductsService getInstance() {
        if (recentlyViewedProducts == null) {
            recentlyViewedProducts = new RecentlyViewedProductsService();
        }
        return recentlyViewedProducts;
    }

    public void setRecentlyViewedProducts(HttpServletRequest request) {
        request.getSession().setAttribute("recentlyViewedProducts", recentlyViewedList);
    }

    public void addProductToViewedList(Product product) {
        if(recentlyViewedListModel == null) {
            recentlyViewedListModel = new RecentlyViewedProductsModel();
        }
        recentlyViewedListModel.addProductToViewedList(product);
        recentlyViewedList = recentlyViewedListModel.getProductList();
    }

    public Queue<Product> getRecentlyViewedProducts(HttpServletRequest request) {
        recentlyViewedList = (Queue<Product>) request.getSession().getAttribute("recentlyViewedProducts");
        return  recentlyViewedList;
    }
}
