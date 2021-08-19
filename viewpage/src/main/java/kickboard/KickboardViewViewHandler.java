package kickboard;

import kickboard.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class KickboardViewViewHandler {


    @Autowired
    private KickboardViewRepository kickboardViewRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenTicketPurchased_then_CREATE_1 (@Payload TicketPurchased ticketPurchased) {
        try {

            if (!ticketPurchased.validate()) return;

            // view 객체 생성
            KickboardView kickboardView = new KickboardView();
            // view 객체에 이벤트의 Value 를 set 함
            kickboardView.setTicketId(ticketPurchased.getTicketId());
            kickboardView.setTicketType(ticketPurchased.getTicketType());
            kickboardView.setTicketStatus(ticketPurchased.getTicketStatus());
            kickboardView.setBuyerPhoneNum(ticketPurchased.getBuyerPhoneNum());
            // view 레파지 토리에 save
            kickboardViewRepository.save(kickboardView);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaymentApproved_then_CREATE_2 (@Payload PaymentApproved paymentApproved) {
        try {

            if (!paymentApproved.validate()) return;

            // view 객체 생성
            KickboardView kickboardView = new KickboardView();
            // view 객체에 이벤트의 Value 를 set 함
            kickboardView.setPaymentId(paymentApproved.getPaymentId());
            kickboardView.setTicketId(paymentApproved.getTicketId());
            kickboardView.setPaymentStatus(paymentApproved.getPaymentStatus());
            // view 레파지 토리에 save
            kickboardViewRepository.save(kickboardView);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaymentCancelled_then_UPDATE_1(@Payload PaymentCancelled paymentCancelled) {
        try {
            if (!paymentCancelled.validate()) return;
                // view 객체 조회

                    List<KickboardView> kickboardViewList = kickboardViewRepository.findByPaymentId(paymentCancelled.getPaymentId());
                    for(KickboardView kickboardView : kickboardViewList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    kickboardView.setPaymentStatus(paymentCancelled.getPaymentStatus());
                // view 레파지 토리에 save
                kickboardViewRepository.save(kickboardView);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenKickReturned_then_UPDATE_2(@Payload KickReturned kickReturned) {
        try {
            if (!kickReturned.validate()) return;
                // view 객체 조회
            Optional<KickboardView> kickboardViewOptional = kickboardViewRepository.findByTicketId(kickReturned.getKickId());

            if( kickboardViewOptional.isPresent()) {
                 KickboardView kickboardView = kickboardViewOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
                 kickboardView.setKickStatus(kickReturned.getKickStatus());
                // view 레파지 토리에 save
                 kickboardViewRepository.save(kickboardView);
                }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenKickRented_then_UPDATE_3(@Payload KickRented kickRented) {
        try {
            if (!kickRented.validate()) return;
                // view 객체 조회
            Optional<KickboardView> kickboardViewOptional = kickboardViewRepository.findByTicketId(kickRented.getKickId());

            if( kickboardViewOptional.isPresent()) {
                 KickboardView kickboardView = kickboardViewOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
                 kickboardView.setKickStatus(kickRented.getKickStatus());
                // view 레파지 토리에 save
                 kickboardViewRepository.save(kickboardView);
                }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenKickReturned_then_UPDATE_4(@Payload KickReturned kickReturned) {
        try {
            if (!kickReturned.validate()) return;
                // view 객체 조회
            Optional<KickboardView> kickboardViewOptional = kickboardViewRepository.findByTicketId(kickReturned.getTicketId());

            if( kickboardViewOptional.isPresent()) {
                 KickboardView kickboardView = kickboardViewOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
                 kickboardView.setPaymentStatus(kickReturned.getKickStatus());
                // view 레파지 토리에 save
                 kickboardViewRepository.save(kickboardView);
                }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

