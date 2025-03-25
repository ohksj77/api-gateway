# api-gateway

## 서버 별 역할

- api-gateway
  - 직접 라우팅하여 (ip 와 포트 등이 다른) 뒷단 서버에 요청 라우팅
  - 현재 구성은 module1, module2 에 요청 라우팅

- module1, module2
  - 각 다른 api를 가진 서버 모듈

## 구현 범위

- 구현 포함
  - 설정 파일을 읽어와 라우팅 대상 메모리 상에 저장
  - HTTP Method 와 요청 url기반 서버로의 라우팅
  - 서킷 브레이커 적용
- 구현 제외
  - 인증, 로그 수집, rate limiter 등등
  - API Gateway 본질적인 기능만 구현해보기 위함

## 실행 결과

- API Gateway를 8080 포트로, Module1은 8081, Module2는 8082 포트로 서버 기동
- 8080 포트로 원하는 API의 url로 요청 시 Module1, Module2 중 알맞은 서버로 라우팅 이후 사용자에게 response
