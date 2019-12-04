package review;

import java.util.List;

public class ReviewDaoService {

    private static ReviewDaoService reviewDaoService;

    private CustomersDaoReviews customersDaoReviews;

    private ReviewDaoService() {
        customersDaoReviews = CustomersDaoReviews.getInstance();
    }

    public static ReviewDaoService getInstance() {
        if (reviewDaoService == null) {
            reviewDaoService = new ReviewDaoService();
        }
        return reviewDaoService;
    }

    public void addComment(String name, String rate, String comment) {
     customersDaoReviews.addComment(name, rate, comment);
    }

    public List<ReviewItem> getList() {
        return customersDaoReviews.getReviewItemList();
    }

}
