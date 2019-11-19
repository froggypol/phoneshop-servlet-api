package service;

import com.es.phoneshop.cart.Cart;
import validation.ErrorMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionService {

    private static SessionService sessionService;

    private HttpSession session;

    private ErrorMap errorMap;

    private SessionService() {

    }

    public static SessionService getInstance() {
        if (sessionService == null) {
            sessionService = new SessionService();
        }
        return sessionService;
    }

    public void setCart(Cart cart, HttpServletRequest request, HttpServletResponse response) {
        session = request.getSession();
        session.setAttribute("cart", cart);
    }

    public Cart getCart(HttpServletRequest request, HttpServletResponse response) {
        session = request.getSession();
        return (Cart) session.getAttribute("cart");
    }

    public HttpSession getSession(HttpServletRequest request, HttpServletResponse response) {
        return request.getSession();
    }

    public void setErrorMapInSessionAttribute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("errorMap", errorMap);
    }

    public ErrorMap getErrorMapFromSession(HttpServletRequest request, HttpServletResponse response) {
        if ((ErrorMap) request.getSession().getAttribute("errorMap") == null) {
            return new ErrorMap();
        }
        return errorMap;
    }

}
