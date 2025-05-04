## Spring boot Webflux

이 프로젝트는 Spring Boot에 포함된 리액티브(Reactive) 웹 프레임워크인 **Spring WebFlux**를 기반으로 개발되었습니다. 기존의 Spring MVC 대신 WebFlux를 선택한 주요 이유는 다음과 같습니다.

**효율적인 자원 사용:**
  * 논블로킹 I/O 모델은 스레드당 요청 모델에 비해 스레드 수를 적게 사용하므로, 스레드 컨텍스트 스위칭 비용을 줄이고 메모리 사용량 측면에서 이점을 가질 수 있습니다.


## Branching Strategy

This project follows the **GitHub Flow** branching strategy.

* The `main` branch always represents the latest stable and deployable version of the project.
* Once work on a feature branch is complete and verified, it is merged back into the `main` branch, preferably via a Pull Request (PR) for review, even if working solo.
* Feature branches are typically deleted after they have been successfully merged into `main`.

## How to Run in local
```bash
./gradlew bootRun
```

## How to run with container
```bash
docker compose up --build app
```
