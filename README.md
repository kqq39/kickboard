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

## 운영

