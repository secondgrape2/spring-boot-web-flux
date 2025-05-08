## Spring boot Webflux

이 프로젝트는 Spring Boot에 포함된 리액티브(Reactive) 웹 프레임워크인 **Spring WebFlux**를 기반으로 개발되었습니다. 기존의 Spring MVC 대신 WebFlux를 선택한 주요 이유는 다음과 같습니다.

**효율적인 자원 사용:**
  * 논블로킹 I/O 모델은 스레드당 요청 모델에 비해 스레드 수를 적게 사용하므로, 스레드 컨텍스트 스위칭 비용을 줄이고 메모리 사용량 측면에서 이점을 가질 수 있습니다.


## Branching Strategy

This project follows the **GitHub Flow** branching strategy.

* The `main` branch always represents the latest stable and deployable version of the project.
* Once work on a feature branch is complete and verified, it is merged back into the `main` branch, preferably via a Pull Request (PR) for review, even if working solo.
* Feature branches are typically deleted after they have been successfully merged into `main`.

## 코드 빌드, 테스트, 실행 방법

### 로컬 환경 실행 (Gradle 사용)

```bash
./gradlew bootRun
```

### 로컬 환경 테스트 (Gradle 사용)

```bash
./gradlew test
```

### 컨테이너 환경 실행 (Docker 사용)

```bash
docker compose up --build app
```

### 컨테이너 환경 테스트 (Docker 사용)

```bash
docker compose up --build test
```


## 구현범위에대한설명

### 1. 예외처리

예외처리는 예외처리 모듈을 만들어서 처리하였습니다.

예외처리 모듈은 다음과 같은 기능을 가지고 있습니다:

- `GlobalExceptionHandler`를 통한 중앙집중식 예외 처리
- HTTP 상태 코드(4xx, 5xx)에 따른 일관된 오류 응답 형식 제공
- 4xx 상태 코드를 세분화하기 위한 커스텀 에러 코드 시스템 (예: E2000)
- 모든 예외는 `ErrorResponse` 형식으로 변환되어 클라이언트에게 전달
- 도메인별 에러 코드 범위 지정 (예: 브랜드 관련 오류는 2000-2099)

### 2. API 문서

API 문서는 Swagger를 사용하여 작성되었습니다.

- Swagger UI: http://localhost:6001/docs


### 3. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API

- 카테고리가 추가 될 수 있어 array로 응답하도록 구현
- 카테고리 별 lowestProduct 정보를 응답합니다.
- lowestProduct는 price와 brand외에 추가 정보가 있을 수 있으므로 nested 형태로 응답합니다.
- lowestProduct의 brand는 brand name외에 추가 정보가 있을 수 있으므로 nested 형태로 응답합니다.
- 총액은 backend에서 계산하여 root에 포함하여 응답합니다.

### 4. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API

- 응답값이 정해져 있으므로 인터페이스에 맞춰서 내려줍니다.

### 5.  카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API

- 요청값과 응답값이 정해져있으므로 인터페이스에 맞춰서 내려줍니다.
- 카테고리 ID를 사용한 API 설계 변경 이유:
  - 다국어 지원 용이: 카테고리명은 언어별로 다르게 표현되므로 ID를 사용하면 언어 독립적인 API 구현 가능
  - 유지보수성 향상: 카테고리명이 변경되어도 ID는 유지되므로 API 호출 방식 변경 불필요

### 6. 브랜드 및 상품을 추가 / 업데이트 / 삭제하는 API

- brand와 product api를 분리하여 구현하였습니다.
- brand api는 브랜드 추가, 수정, 삭제를 할 수 있습니다.
- product api는 상품 추가, 수정, 삭제를 할 수 있습니다.
- 삭제는 soft delete를 사용하였습니다.

### 7. Localization
- category name을 한국어로 반환하도록 구현하였습니다.
- 추후 다른 언어로 반환할 경우 추가 구현 필요

### 8. PriceFormatter
- 가격을 쉼표로 구분하여 포맷팅하는 유틸 클래스를 구현하였습니다.
- 추후 다른 포맷팅 방식이 필요할 경우 추가 구현 필요