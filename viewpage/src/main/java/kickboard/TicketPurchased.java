package kickboard;

public class TicketPurchased extends AbstractEvent {

    private Long ticketId;
    private String ticketStatus;
    private String ticketType;
    private String buyerPhoneNum;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
    public String getBuyerPhoneNum() {
        return buyerPhoneNum;
    }

    public void setBuyerPhoneNum(String buyerPhoneNum) {
        this.buyerPhoneNum = buyerPhoneNum;
    }
}