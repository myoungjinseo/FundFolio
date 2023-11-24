<p>
<img src="https://img.shields.io/github/issues-pr-closed/myoungjinseo/FundFolio?color=blueviolet"/>
<img src="https://img.shields.io/github/issues/myoungjinseo/FundFolio?color=inactive"/>
<img src="https://img.shields.io/github/issues-closed/myoungjinseo/FundFolio"/> 
</p>

## 요구 사항

<details>
<summary>A.유저</summary>
<div align="left">

### 사용자 회원가입(API)

- 본 서비스에서는 유저 고유 정보가 크게 사용되지 않아 간단히 구현합니다.
- `계정명` , `패스워드` 입력하여 회원가입

### 사용자 로그인(API)

- `계정`, `비밀번호` 로 로그시 `JWT` 가 발급됩니다.
- 이후 모든 API 요청 Header 에 `JWT` 가 항시 포함되며, `JWT` 유효성을 검증합니다.
</div>
</details>

<details>
<summary>B. 예산설정 및 설계</summary>
<div align="left">

### 카테고리

- 카테고리는 `식비` , `교통` 등 일반적인 지출 카테고리를 의미합니다.
- 자유롭게 구성하여 생성하세요.

### 카테고리 목록(API)

- 유저가 예산설정에 사용할 수 있도록 모든 카테고리 목록을 반환합니다.

### 예산 설정(API)

- 해당 기간 별 설정한 `예산` 을 설정합니다. 예산은 `카테고리` 를 필수로 지정합니다.
    - ex) `식비` : 40만원, `교통` : 20만원
- 사용자는 언제든지 위 정보를 변경할 수 있습니다.

### 예산 설계 (=추천) (API)

- 카테고리 별 예산 설정에 어려움이 있는 사용자를 위해 예산 비율 추천 기능이 존재합니다.
- `카테고리` 지정 없이 총액 (ex. 100만원) 을 입력하면, `카테고리` 별 예산을 자동 생성합니다.
- 자동 생성된 예산은, 기존 이용중인 `유저` 들이 설정한 평균 값 입니다.
    - 유저들이 설정한 카테고리 별 예산을 통계하여, 평균적으로 40% 를 `식비`에, 30%를 `주거` 에 설정 하였다면 이에 맞게 추천.
    - 10% 이하의 카테고리들은 모두 묶어 `기타` 로 제공한다.(8% 문화, 7% 레져 라면 15% 기타로 표기)
    - **위 비율에 따라 금액이 입력됩니다.**
        - **ex) 식비 40만원, 주거 30만원, 취미 13만원 등.**

> **추가설명**

유저는 예산 설정 페이지 에서 카테고리별로 `예산을 설정` 합니다. 
이를 지정하기 어려운 유저들은 `예산 추천 기능`을 사용하고 클릭 시, 자동으로 페이지 상 카테고리 별 예산이 입력됩니다.
유저는 입력 된 값들을 필요에 따라 수정(API 가 아닌 화면에서) 한 뒤 이를 `저장(=예산설정 API)`합니다

</div>
</details>

<details>
<summary>C. 지출 기록</summary>
<div align="left">

### 지출

- `지출 일시`, `지출 금액`, `카테고리` 와 `메모` 를 입력하여 생성합니다
    - 추가적인 필드 자유롭게 사용

### 지출 CRUD (API)

- 지출을 `생성`, `수정`, `읽기(상세)`, `읽기(목록)`, `삭제` , `합계제외` 할 수 있습니다.
- `생성한 유저`만 위 권한을 가집니다.
- `읽기(목록)` 은 아래 기능을 가지고 있습니다.
    - 필수적으로 `기간` 으로 조회 합니다.
    - 조회된 모든 내용의 `지출 합계` , `카테고리 별 지출 합계` 를 같이 반환합니다.
    - 특정 `카테고리` 만 조회.
    - `최소` , `최대` 금액으로 조회.
        - ex) 0~10000원 / 20000원 ~ 100000원
- `합계제외` 처리한 지출은 목록에 포함되지만, 모든 `지출 합계`에서 제외됩니다.

</div>
</details>


<details>
<summary>D. 지출 컨설팅</summary>
<div align="left">

### 오늘 지출 추천(API)

- 설정한 `월별` 예산을 만족하기 위해 오늘 지출 가능한 금액을 `총액` 과 `카테고리 별 금액` 으로 제공합니다.
    - ex) 11월 9일 지출 가능 금액 총 30,000원, 식비 15,000 … 으로 페이지에 노출 예정.
- 고려사항 1. 앞선 일자에서 과다 소비하였다 해서 오늘 예산을 극히 줄이는것이 아니라, 이후 일자에 부담을 분배한다.
    - 앞선 일자에서 사용가능한 금액을 1만원 초과했다 하더라도, 오늘 예산이 1만원 주는것이 아닌 남은 기간 동안 분배해서 부담(10일 남았다면 1천원 씩).
- 고려사항 2. 기간 전체 예산을 초과 하더라도 `0원` 또는 `음수` 의 예산을 추천받지 않아야 한다.
    - 지속적인 소비 습관을 생성하기 위한 서비스이므로 예산을 초과하더라도 적정한 금액을 추천받아야 합니다.
    - `최소 금액`을 자유롭게 설정하세요.
- 유저의 상황에 맞는 1 문장의 `멘트` 노출.
    - 잘 아끼고 있을 때, 적당히 사용 중 일 때, 기준을 넘었을때, 예산을 초과하였을 때 등 유저의 상황에 맞는 메세지를 같이 노출합니다.
    - 조건과 기준은 자유롭게 설정하세요.
    - ex) “절약을 잘 실천하고 계세요! 오늘도 절약 도전!” 등
- 15333원 과 같은 값이라면 백원 단위 반올림 등으로 사용자 친화적이게 변환.
- **선택 구현 기능)** 매일 08:00 시 알림 발송
    - Scheduler 까지만 구현하셔도 좋습니다.
    - Discord webhook, 이메일, 카카오톡 등 실제 알림까지 진행하셔도 좋습니다.

### 오늘 지출 안내(API)

- 오늘 지출한 내용을 `총액` 과 `카테고리 별 금액` 을 알려줍니다.
- `월별`설정한 예산 기준 `카테고리 별` 통계 제공
    - 일자기준 오늘 `적정 금액` : 오늘 기준 사용했으면 적절했을 금액
    - 일자기준 오늘 `지출 금액` : 오늘 기준 사용한 금액
    - `위험도` : 카테고리 별 적정 금액, 지출금액의 차이를 위험도로 나타내며 %(퍼센테이지) 입니다.
        - ex) 오늘 사용하면 적당한 금액 10,000원/ 사용한 금액 20,000원 이면 200%
- **선택 구현 기능)** 매일 20:00 시 알림 발송
    - Scheduler 까지만 구현하셔도 좋습니다.
    - Discord webhook, 이메일, 카카오톡 등 실제 알림까지 진행하셔도 좋습니다.

> 본 기능은 명확한 요구사항이 존재하지 않습니다.위와 같이 유저들이 지속적으로 건강한 소비 습관을 생성하기 위한 목적을 이해하시고 자유롭게 해석 및 구현하세요.
</div>
</details> 


 
<details>
<summary>E. 지출 통계</summary>
<div align="left">

### Dummy 데이터 생성

- 사용자의 통계데이터 생성을 위해 Dummy 데이터를 생성합니다.

### 지출 통계 (API)

- `지난 달` 대비 `총액`, `카테고리 별` 소비율.
    - 오늘이 10일차 라면, 지난달 10일차 까지의 데이터를 대상으로 비교
    - ex) `식비` 지난달 대비 150%
- `지난 요일` 대비 소비율
    - 오늘이 `월요일` 이라면 지난 `월요일` 에 소비한 모든 기록 대비 소비율
    - ex) `월요일` 평소 대비 80%
- `다른 유저` 대비 소비율
    - 오늘 기준 다른 `유저` 가 예산 대비 사용한 평균 비율 대비 나의 소비율
    - 오늘기준 다른 유저가 소비한 지출이 평균 50%(ex. 예산 100만원 중 50만원 소비중) 이고 나는 60% 이면 120%.
    - ex) `다른 사용자` 대비 120%

</div>
</details> 

## 기술스택
### Language
<img src="https://img.shields.io/badge/JAVA-FFF000?style=for-the-badge&logo=JAVA&logoColor=white"/>


### Web Frameworks & related technologies

<p>
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"/>
<img src="https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=JPA&logoColor=white">
<img src="https://img.shields.io/badge/SPRING SECURITY-6DB33F?style=for-the-badge&logo=SPRING SECURITY&logoColor=white">
<img src="https://img.shields.io/badge/JWT-6DB33F?style=for-the-badge&logo=JWT&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/QueryDSL-0285c9?style=for-the-badge&logo=qeurydsl&logoColor=white">
</p>

### Database
<p>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=orange">
</p>


## ERD 다이어그램
![image](https://github.com/myoungjinseo/FundFolio/assets/80959635/a6d188eb-97b9-45d3-9ee0-a9cb8abb4af1)

