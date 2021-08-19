package kickboard;

import kickboard.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired MessageRepository messageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverKickRented_RentMessage(@Payload KickRented kickRented, KickRented kickReturned){

        if(!kickRented.validate()) return;

        System.out.println("\n\n##### listener RentMessage : " + kickRented.toJson() + "\n\n");

        // 메세지는 계속 신규 생성
        Message message = new Message(); 
        message.setKickId(kickRented.getKickId());
        message.setKickStatus(kickReturned.getKickStatus());
        message.setTicketId(kickRented.getTicketId());
        messageRepository.save(message);

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverKickReturned_ReturnMessage(@Payload KickReturned kickReturned){

        if(!kickReturned.validate()) return;

        System.out.println("\n\n##### listener ReturnMessage : " + kickReturned.toJson() + "\n\n");

        Message message = new Message();
        message.setKickId(kickReturned.getKickId());
        message.setKickStatus(kickReturned.getKickStatus());
        message.setTicketId(kickReturned.getTicketId());
        messageRepository.save(message);

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentApproved_BuyTicketMessage(@Payload PaymentApproved paymentApproved){

        if(!paymentApproved.validate()) return;

        System.out.println("\n\n##### listener BuyTicketMessage : " + paymentApproved.toJson() + "\n\n");

        Message message = new Message();
        message.setPaymentId(paymentApproved.getPaymentId());
        message.setPaymentStatus(paymentApproved.getPaymentStatus());
        message.setTicketId(paymentApproved.getTicketId());
        messageRepository.save(message);

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCancelled_RefundTicketMessage(@Payload PaymentCancelled paymentCancelled){

        if(!paymentCancelled.validate()) return;

        System.out.println("\n\n##### listener RefundTicketMessage : " + paymentCancelled.toJson() + "\n\n");

        Message message = new Message();
        message.setPaymentId(paymentCancelled.getPaymentId());
        message.setPaymentStatus(paymentCancelled.getPaymentStatus());
        message.setTicketId(paymentCancelled.getTicketId());
        messageRepository.save(message);

    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
