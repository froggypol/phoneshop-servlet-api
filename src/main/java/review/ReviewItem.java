package review;

public class ReviewItem {

    public ReviewItem(String customerName, String comment, String rate) {
        this.customerName = customerName;
        this.comment = comment;
        this.rate = rate;
    }

    private String customerName;

    private String comment;

    private String rate;

    public String getCustomerName() {
        return customerName;
    }

    public String getComment() {
        return comment;
    }

    public String getRate() {
        return rate;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
