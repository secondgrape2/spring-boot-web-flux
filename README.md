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

### 로컬 환경 (Gradle 사용)

```bash
./gradlew bootRun
```

### 컨테이너 환경 (Docker 사용)

```bash
docker compose up --build app
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
