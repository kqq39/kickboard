package kickboard;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Message_table")
public class Message {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long messageId;
    private Long ticketId;
    private String ticketStatus;
    private String buyerPhoneNum;
    private Long kickId;
    private String kickStatus;
    private Long paymentId;
    private String paymentStatus;


    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
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
    public String getBuyerPhoneNum() {
        return buyerPhoneNum;
    }

    public void setBuyerPhoneNum(String buyerPhoneNum) {
        this.buyerPhoneNum = buyerPhoneNum;
    }
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
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}