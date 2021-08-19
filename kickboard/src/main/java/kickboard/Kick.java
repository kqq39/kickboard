package kickboard;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Kick_table")
public class Kick {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long kickId;
    private Long kickStatus;
    private Long ticketId;
    private Long usingTime;

    @PostPersist
    public void onPostPersist(){

        // 킥보드 등록
        KickRented kickRented = new KickRented();
        BeanUtils.copyProperties(this, kickRented);
        kickRented.publishAfterCommit();

    }
    
    @PostUpdate
    public void onPostUpdate(){

        // 킥보드 렌트 
        // 등록 / 반납 상태의 유휴 킥보드만 렌트 가능
        if( this.getKickStatus().equals("Registered") || this.getKickStatus().equals("Returned") ) {
            boolean rslt = KickboardApplication.applicationContext.getBean(kickboard.external.TicketService.class)
                .chkTicketStatus(this.getTicketId(), this.getUsingTime());

            if (rslt) {
                KickRented kickRented = new KickRented();
                BeanUtils.copyProperties(this, kickRented);
                kickRented.publishAfterCommit();
            }
        }

        // 반납 
        // 렌트 상태만 반납 가능
        if( this.getKickStatus().equals("Rented") ) {
            KickReturned kickReturned = new KickReturned();
            BeanUtils.copyProperties(this, kickReturned);
            kickReturned.publishAfterCommit(); 
        }
    }

    @PreRemove
    public void onPreRemove(){

        // 삭제 
        // 렌트 상태는 삭제 불가 
        if ( !this.getKickStatus().equals("Rented") ) {
            KickDeleted kickDeleted = new KickDeleted();
            BeanUtils.copyProperties(this, kickDeleted);
            kickDeleted.publishAfterCommit();
        }

    }

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