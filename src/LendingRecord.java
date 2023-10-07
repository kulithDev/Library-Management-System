import java.util.Date;

public class LendingRecord {
    private int bookId;
    private int memberId;
    private Date checkoutDate;
    private Date dueDate;
    private Date returnDate;

    public LendingRecord(int bookId, int memberId, Date dueDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.dueDate = dueDate;
        this.checkoutDate = new Date();
    }
    public int getBookId() {
        return bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public Date getCheckoutDate(){
        return checkoutDate;
    }
}
