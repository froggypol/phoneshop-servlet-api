package com.es.phoneshop.web;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
public class ProductListPageServlet extends HttpServlet {

    ArrayListProductDao productListDao;
    public ProductListPageServlet() {
        productListDao = new ArrayListProductDao();
        try {
            this.init();
        }
        catch(ServletException e){
            System.exit(-1);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("products", getSampleProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    private List<Product> getSampleProducts(){
        return productListDao.getProductListDao();
    }
}
