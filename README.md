# 공유 킥보드 서비스

## 시나리오

### 기능적 요구 사항

1. 고객이 이용권을 구매한다.
2. 고객이 이용권을 결제한다.
3. 고객이 이용권을 사용하여 킥보드를 대여한다.
4. 고객이 킥보드를 대여하면 알림 메시지를 전송한다.
5. 고객이 킥보드 이용을 마치면 반납을 한다.
6. 고객이 킥보드를 반납하면 알림 메시지를 전송한다.
7. 관리자는 킥보드를 관리하여 등록/삭제할 수 있다.

### 비기능적 요구 사항

1. 트랜잭션 
   - 고객은 이용권 결제가 완료되어야 킥보드 대여가 가능하다.(Sync)

2. 장애격리
   - 킥보드 대여/반납을 24시간 가능하다.(Async 호출-event-driven)
   - 결제 시스템이 과중되면 결제를 받지 않고 결제를 잠시 후에 하도록 유도한다. (Circuit breaker, fallback)

3. 성능
   - 고객이 구매한 이용권의 잔여를 확인할 수 있다 (CQRS)
   - 킥보드 대여 상태가 변경되면 고객에게 알림 메시지를 전송한다 (Event Driven)

### As-Is 조직구조 – Horizontal
![image](https://user-images.githubusercontent.com/87048759/130189294-e7a97d1e-de5f-49af-84c4-c02235119c5f.png)

### To-Be 조직구조 – Vertical
![image](https://user-images.githubusercontent.com/87048759/130189510-a583a7a1-63b6-4ce5-93a1-3e12d2677814.png)

### 이벤트스토밍 - Event
![image](https://user-images.githubusercontent.com/87048759/130189887-9a17b234-0bca-485d-9e0d-8dc193cffeec.png)

### 이벤트스토밍 - 비적격 Event 제거
![image](https://user-images.githubusercontent.com/87048759/130189918-34fbdd66-6b15-4b18-8904-042ebc31d43f.png)

### 이벤트스토밍 - Actor, Command
![image](https://user-images.githubusercontent.com/87048759/130190257-71b5f09c-628c-4542-a741-8f1f11549e8b.png)

### 이벤트스토밍 - Aggregate
![image](https://user-images.githubusercontent.com/87048759/130190445-9d479f8e-33ed-45c7-8e9c-4d244d9c6bb6.png)

### 이벤트스토밍 - Bounded Context
![image](https://user-images.githubusercontent.com/87048759/130190875-acf76f26-a540-4db3-a13d-6b927867dfb7.png)

### 이벤트스토밍 - Policy
![image](https://user-images.githubusercontent.com/87048759/130191232-aec783cc-7cfa-4572-beca-5ae08e274d39.png)

### 이벤트스토밍 - Context Mapping
![image](https://user-images.githubusercontent.com/87048759/130191559-6e969f88-0f36-44e3-93ad-dc7a36ef866f.png)

### 이벤트스토밍 - 완성된 모형
http://www.msaez.io/#/storming/bc8D3KeQEkRS3CyNNBP02KrrNrE2/d36c03850878e3b6ebfce15eb6bf9ed8

![image](https://user-images.githubusercontent.com/87048759/130191648-066779ee-5be0-49f9-bba3-a29cfc18288a.png)

### 이벤트스토밍 - 기능 요구사항 Coverage Check
![image](https://user-images.githubusercontent.com/87048759/130192639-164bd7b0-1683-465c-aa08-95efe443a8da.png)

1. 고객이 이용권을 구매하고, 결제를 하면 알림 메시지가 전송되고 이용권 상태가 변경된다.
2. 고객이 킥보드는 대여/반납하면 알림 메시지가 전송되고 이용권 상태가 변경된다.
3. 관리자가 킥보드를 등록/취소 한다.

### 이벤트스토밍 - 비기능 요구사항 Coverage Check
![image](https://user-images.githubusercontent.com/87048759/130193450-ba5f2ebd-80a2-4142-ac3c-52bd551f715e.png)

1. 고객은 이용권 결제가 완료되어야 킥보드 대여가 가능하다.
2. 킥보드 대여/반납은 24시간 가능하다. (서비스 분리)
3. 결제 시스템이 과중되면 결제를 받지 않고 결제를 잠시 후에 하도록 한다. (서비스 분리)
4. 고객이 구매한 이용권의 잔여를 확인할 수 있다 (view)
5. 킥보드 대여 상태가 변경되면 고객에게 알림 메시지를 전송한다 (알림)

### 헥사고날 아키텍처
![image](https://user-images.githubusercontent.com/87048759/130193521-9431f23a-dbee-4b24-8794-011880a99773.png)
*****

## 구현
분석/설계 단계에서 도출된 헥사고날 아키텍처에 따라, 각 BC별로 대변되는 마이크로 서비스들을 스프링부트로 구현하였다. 구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다 (서비스 포트는 8081, 8082, 8083, 8084, 8085 이다)

```
cd ticket
mvn spring-boot:run

cd payment
mvn spring-boot:run

cd kickboard
mvn spring-boot:run

cd message
mvn spring-boot:run

cd viewpage
mvn spring-boot:run

```
****

### 동기식 호출과 Fallback 처리

분석단계에서의 조건 중 하나로 이용권 구매 (ticket) --> 이용권 결제 (payment) 간의 호출은 동기식 일관성을 유지하는 트랜잭션으로
처리한다. 호출 프로토콜은 Rest Repository 에 의해 노출되어있는 REST 서비스를 FeignClient를 이용하여 호출한다.

1. 결제서비스를 호출하기 위해 FeignClient를 이용해 Service 대행 인터페이스 구현

#### PaymentService.java

```
@FeignClient(name="payment", url="http://payment:8080")
public interface PaymentService {
    @RequestMapping(method= RequestMethod.GET, path="/payTicket")
    public boolean payTicket(@RequestParam("ticketId") Long ticketId, 
                             @RequestParam("ticketAmt") Long ticketAmt);
}
```

2. 이용권을 받은 직후(@PostPersist) 결제 요청하도록 처리

#### ticket.java

```
    @PostPersist
    public void onPostPersist(){
        Long ticketAmount = Long.decode(this.getTicketType() == "1"?"1000":"2000");

        boolean result = TicketApplication.applicationContext.getBean(kickboard.external.PaymentService.class)
            .payTicket(this.getTicketId(), ticketAmount);
        
        if(result) {
            TicketPurchased ticketPurchased = new TicketPurchased();
            ticketPurchased.setTicketId(this.getTicketId());
            ticketPurchased.setTicketStatus("ticketPurchased");
            ticketPurchased.setTicketType(this.getTicketType());
            ticketPurchased.setBuyerPhoneNum(this.getBuyerPhoneNum());

            BeanUtils.copyProperties(this, ticketPurchased);
            ticketPurchased.publishAfterCommit();
        }
    }
```    

3. 동기식 호출에서는 호출 시간에 따른 타임 커플링이 발생하며, 결제 시스템이 장애가 나면 주문도 못받는다는 것을 확인
   (Payment 서비스 다운 후 티켓 구매)

```    
root@siege:/#  http ticket:8080/tickets ticketType=1 ticketStatus="ReadyForPay"
HTTP/1.1 500 
Connection: close
Content-Type: application/json;charset=UTF-8
Date: Thu, 02 Sep 2021 11:36:43 GMT
Transfer-Encoding: chunked

{
    "error": "Internal Server Error",
    "message": "Could not commit JPA transaction; nested exception is javax.persistence.RollbackException: Error while committing the transaction",
    "path": "/tickets",
    "status": 500,
    "timestamp": "2021-09-02T11:36:44.953+0000"
}

```

### DDD의 적용

1. 각 서비스내에 도출된 핵심 Aggregate Root 객체를 Entity 로 선언하였다. (예시는 ticket 마이크로 서비스 )

#### Ticket.java

```
@Entity
@Table(name="Ticket_table")
public class Ticket {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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

```

2. Entity Pattern 과 Repository Pattern 을 적용하여 JPA 를 통하여 다양한 데이터소스 유형 (RDB or NoSQL) 에 대한 별도의 처리가 없도록 데이터 접근 어댑터를 자동 생성하기 위하여 Spring Data REST 의 RestRepository 를 적용하였다

#### TicketRepository.java

```
@RepositoryRestResource(collectionResourceRel="tickets", path="tickets")
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long>{
}

```

3. 적용 후 REST API 의 테스트

```
1. 이용권 구매
http ticket:8080/tickets ticketType=1 ticketStatus="ReadyForPay"

2. 킥보드 등록
http kickboard:8080/kicks kickId="1" kickStatus="Registered"

3. 킥보드 대여
http PATCH kickboard:8080/kicks/1 ticketId="1" usingTime="60"

4. 이용권 상태 변경 확인 (ticketStatus가 ticketUsed로 변경되었는지 확인)
http GET ticket:8080/tickets/1

```
****

### Correlation

PolicyHandler에서 처리 시 어떤 건에 대한 처리인지를 구별하기 위한 Correlation-key 구현을 이벤트 클래스 안의 변수로 전달받아 서비스간 연관 처리를 구현 
(이용권 구매와 킥보드 대여시 이용권 상태, 킥보드 상태 변경)

- 이용권 구매

```
root@siege:/# http GET ticket:8080/tickets/1
HTTP/1.1 200 
Content-Type: application/hal+json;charset=UTF-8
Date: Thu, 02 Sep 2021 14:45:54 GMT
Transfer-Encoding: chunked

{
    "_links": {
        "self": {
            "href": "http://ticket:8080/tickets/1"
        },
        "ticket": {
            "href": "http://ticket:8080/tickets/1"
        }
    },
    "buyerPhoneNum": null,
    "ticketStatus": "ticketAvailable",
    "ticketType": "1"
}
```

- 킥보드 등록

```
root@siege:/# http GET kickboard:8080/kicks/1
HTTP/1.1 200 
Content-Type: application/hal+json;charset=UTF-8
Date: Thu, 02 Sep 2021 14:48:39 GMT
Transfer-Encoding: chunked

{
    "_links": {
        "kick": {
            "href": "http://kickboard:8080/kicks/1"
        },
        "self": {
            "href": "http://kickboard:8080/kicks/1"
        }
    },
    "kickStatus": "Registered",
    "ticketId": null,
    "usingTime": null
}
```

- 킥보드 대여 (1번 킥보드 상태 Rented로 변경)

```
root@siege:/# http GET kickboard:8080/kicks/1
HTTP/1.1 200 
Content-Type: application/hal+json;charset=UTF-8
Date: Thu, 02 Sep 2021 14:51:32 GMT
Transfer-Encoding: chunked

{
    "_links": {
        "kick": {
            "href": "http://kickboard:8080/kicks/1"
        },
        "self": {
            "href": "http://kickboard:8080/kicks/1"
        }
    },
    "kickStatus": "Rented",
    "ticketId": 1,
    "usingTime": 60
}
```

- 킥보드 대여 (1번 이용권 상태 Used로 변경)

```
root@siege:/# http GET ticket:8080/tickets/1
HTTP/1.1 200 
Content-Type: application/hal+json;charset=UTF-8
Date: Thu, 02 Sep 2021 14:52:32 GMT
Transfer-Encoding: chunked

{
    "_links": {
        "self": {
            "href": "http://ticket:8080/tickets/1"
        },
        "ticket": {
            "href": "http://ticket:8080/tickets/1"
        }
    },
    "buyerPhoneNum": null,
    "ticketStatus": "ticketUsed",
    "ticketType": "1"
}
```

### 비동기식 호출

1. 킥보드를 대여한 후 이용권 상태가 변경되고, 킥보드를 대여했다는 메시지가 전송되는 시스템과의 통신은 비동기식으로 처리한다.

```
    @PostUpdate
    public void onPostUpdate(){

        if( this.getKickStatus().equals("Registered") || this.getKickStatus().equals("Returned") ) {
            boolean rslt = KickboardApplication.applicationContext.getBean(kickboard.external.TicketService.class)
                .chkTicketStatus(this.getTicketId(), this.getUsingTime());

            if (rslt) {
                KickRented bicycleRented = new KickRented();
                BeanUtils.copyProperties(this, kickRented);
                kickRented.publishAfterCommit();
            }
        }
```

2. Message 서비스는 Ticket과 Kickboard 서비스와 분리되어 이벤트 수신에 따라 처리하기 때문에 Message 서비스가 다운되어도 
   이용권 구매와 킥보드 대여는 문제 없이 사용할 수 있다.

- message 서비스 미동작
```
root@labs--1860204849:/home/project/kickboard/kickboard# kubectl get pods
NAME                         READY   STATUS             RESTARTS   AGE
gateway-7cf6fcfb7b-42gfx     1/1     Running            0          177m
kickboard-66fd9859b8-pj99f   1/1     Running            0          63m
message-bbf6bcd66-jv4vh      0/1     CrashLoopBackOff   82         7h12m
my-kafka-0                   1/1     Running            1          9h
my-kafka-zookeeper-0         1/1     Running            0          9h
payment-6b45b46848-ztcrk     1/1     Running            0          65m
siege                        1/1     Running            0          9h
ticket-c84f858f4-rj2jg       1/1     Running            0          66m
viewpage-66bc678b84-fn8gr    1/1     Running            0          3h
```

- 이용권 구매

```
root@siege:/# http ticket:8080/tickets ticketType=1 ticketStatus="ReadyForPay"
HTTP/1.1 200 
Content-Type: application/hal+json;charset=UTF-8
Date: Thu, 02 Sep 2021 15:25:23 GMT
Transfer-Encoding: chunked

{
    "_links": {
        "self": {
            "href": "http://ticket:8080/tickets/2"
        },
        "ticket": {
            "href": "http://ticket:8080/tickets/2"
        }
    },
    "buyerPhoneNum": null,
    "ticketStatus": "ticketAvailable",
    "ticketType": "1"
}
```

- 킥보드 대여

```
root@siege:/# http PATCH kickboard:8080/kicks/2 ticketId="2" usingTime="60"
HTTP/1.1 200 
Content-Type: application/hal+json;charset=UTF-8
Date: Thu, 02 Sep 2021 15:29:32 GMT
Transfer-Encoding: chunked

{
    "_links": {
        "kick": {
            "href": "http://kickboard:8080/kicks/2"
        },
        "self": {
            "href": "http://kickboard:8080/kicks/2"
        }
    },
    "kickStatus": "Rented",
    "ticketId": 2,
    "usingTime": 60
}
```

- 킥보드 상태 확인

```
root@siege:/# http GET kickboard:8080/kicks/2
HTTP/1.1 200 
Content-Type: application/hal+json;charset=UTF-8
Date: Thu, 02 Sep 2021 15:31:38 GMT
Transfer-Encoding: chunked

{
    "_links": {
        "kick": {
            "href": "http://kickboard:8080/kicks/2"
        },
        "self": {
            "href": "http://kickboard:8080/kicks/2"
        }
    },
    "kickStatus": "Rented",
    "ticketId": 2,
    "usingTime": 60
}
```

### 서킷브레이킹

1. Spring FeignClient + Hystrix 옵션을 사용하여 구현

2. ticket -> payment가 Request/Response 로 연동하여 구현이 되어있고, 요청이 과도할 경우 CB를 통하여 장애격리

3. Hystrix 설정 : thread에서 처리 시간이 600 밀리가 넘어서기 시작하여 어느정도 유지되면 CB 회로가 닫히도록 (요청을 빠르게 실패처리, 차단) 설정


### 오토스케일아웃

1. replica를 동적으로 늘려 주도록 HPA를 설정한다. CPU 사용량이 20%를 넘으면 replica를 10개까지 늘려준다.

```
root@labs--1860204849:/home/project/kickboard/ticket# kubectl autoscale deployment ticket --cpu-percent=20 --min=1 --max=10
horizontalpodautoscaler.autoscaling/ticket autoscaled
root@labs--1860204849:/home/project/kickboard/ticket# kubectl autoscale deployment payment --cpu-percent=20 --min=1 --max=10
horizontalpodautoscaler.autoscaling/payment autoscaled
root@labs--1860204849:/home/project/kickboard/ticket# kubectl get all
NAME                             READY   STATUS             RESTARTS   AGE
pod/gateway-7cf6fcfb7b-42gfx     1/1     Running            0          3h33m
pod/kickboard-66fd9859b8-pj99f   1/1     Running            0          99m
pod/my-kafka-0                   1/1     Running            1          10h
pod/my-kafka-zookeeper-0         1/1     Running            0          10h
pod/payment-6b45b46848-ztcrk     1/1     Running            0          100m
pod/siege                        1/1     Running            0          9h
pod/ticket-c84f858f4-rj2jg       1/1     Running            0          102m
pod/viewpage-66bc678b84-fn8gr    1/1     Running            0          3h36m

.......

NAME                                          REFERENCE            TARGETS         MINPODS   MAXPODS   REPLICAS   AGE
horizontalpodautoscaler.autoscaling/payment   Deployment/payment   <unknown>/20%   1         10        0          11s
horizontalpodautoscaler.autoscaling/ticket    Deployment/ticket    <unknown>/20%   1         10        1          70s
```

2. 부하 테스트 진행

```
HTTP/1.1 200     0.03 secs:     349 bytes ==> GET  /tickets
HTTP/1.1 200     0.03 secs:     349 bytes ==> GET  /tickets
HTTP/1.1 200     0.04 secs:     349 bytes ==> GET  /tickets
HTTP/1.1 200     0.03 secs:     349 bytes ==> GET  /tickets
HTTP/1.1 200     0.08 secs:     349 bytes ==> GET  /tickets
HTTP/1.1 200     0.02 secs:     349 bytes ==> GET  /tickets
HTTP/1.1 200     0.02 secs:     349 bytes ==> GET  /tickets
HTTP/1.1 200     0.01 secs:     349 bytes ==> GET  /tickets
HTTP/1.1 200     0.04 secs:     349 bytes ==> GET  /tickets
HTTP/1.1 200     0.04 secs:     349 bytes ==> GET  /tickets

Lifting the server siege...
Transactions:                  11000 hits
Availability:                 100.00 %
Elapsed time:                  29.51 secs
Data transferred:               3.66 MB
Response time:                  0.08 secs
Transaction rate:             372.76 trans/sec
Throughput:                     0.12 MB/sec
Concurrency:                   29.80
Successful transactions:       11000
Failed transactions:               0
Longest transaction:            0.57
Shortest transaction:           0.00
```

3. HPA 삭제

```
root@labs--1860204849:/home/project/kickboard/ticket# kubectl delete hpa --all
horizontalpodautoscaler.autoscaling "payment" deleted
horizontalpodautoscaler.autoscaling "ticket" deleted
```

## 운영

