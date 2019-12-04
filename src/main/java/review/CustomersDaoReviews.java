package review;

import java.util.ArrayList;
import java.util.List;

public class CustomersDaoReviews implements ReviewDao {

    private static CustomersDaoReviews customersDaoReviews;

    private List<ReviewItem> reviewItemList;

    private CustomersDaoReviews() {
        reviewItemList = new ArrayList<>();
    }

    public static CustomersDaoReviews getInstance() {
        if (customersDaoReviews == null) {
            customersDaoReviews = new CustomersDaoReviews();
        }
        return customersDaoReviews;
    }

    @Override
    public void addComment(String name, String rate, String comment) {
        ReviewItem reviewItem = new ReviewItem(name, rate, comment);
        reviewItemList.add(reviewItem);
    }

    public List<ReviewItem> getReviewItemList() {
        return reviewItemList;
    }
}
