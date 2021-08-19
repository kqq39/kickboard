package kickboard;

public class PaymentCancelled extends AbstractEvent {

    private Long paymentId;
    private Long ticketId;
    private Long ticketAmt;
    private Boolean addPaymentYN;
    private String paymentStatus;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    public Long getTicketAmt() {
        return ticketAmt;
    }

    public void setTicketAmt(Long ticketAmt) {
        this.ticketAmt = ticketAmt;
    }
    public Boolean getAddPaymentYn() {
        return addPaymentYN;
    }

    public void setAddPaymentYn(Boolean addPaymentYN) {
        this.addPaymentYN = addPaymentYN;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}