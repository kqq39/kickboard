package kickboard;

public class KickReturned extends AbstractEvent {

    private Long kickId;
    private String kickStatus;
    private Long ticketId;
    private Long usingTime;

    public Long getKickId() {
        return kickId;
    }

    public void setKickId(Long kickId) {
        this.kickId = kickId;
    }
    public String getKickStatus() {
        return kickStatus;
    }

    public void setKickStatus(String kickStatus) {
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