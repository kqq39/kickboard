package kickboard;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="KickboardView_table")
public class KickboardView {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long ticketId;
        private String ticketStatus;
        private String ticketType;
        private String buyerPhoneNum;
        private Long paymentId;
        private String paymentStatus;
        private Long kickId;
        private String kickStatus;
        private Long usingTime;


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
        public Long getUsingTime() {
            return usingTime;
        }

        public void setUsingTime(Long usingTime) {
            this.usingTime = usingTime;
        }

}
