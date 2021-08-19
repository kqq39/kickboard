package kickboard;

public class KickRented extends AbstractEvent {

    private Long kickId;
    private Long kickStatus;
    private Long ticketId;
    private Long usingTime;

//    public KickRented(){
//        super();
//    }

    public Long getKickId() {
        return kickId;
    }

    public void setKickId(Long kickId) {
        this.kickId = kickId;
    }
    public Long getKickStatus() {
        return kickStatus;
    }

    public void setKickStatus(Long kickStatus) {
        this.kickStatus = kickStatus;
    }
    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    public Long getUsingTime() {
        return usingTime;
    }

    public void setUsingTime(Long usingTime) {
        this.usingTime = usingTime;
    }
}
