# JPA-Todo

🌐 프로젝트 개요
- 내일배움캠프 숙련주차 과제 진행

## 🛠 사용한 기술 스택

<img src="https://img.shields.io/badge/Java-b07219?style=flat-square&logoColor=white" />
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white" />
<img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat-square&logo=jpa&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white" />

***
## API

[Swagger 링크](https://wjswlgh96.github.io/swagger-docs/#/Author%20API/createAuthor)

### Auth

|Method|Endpoint|Description|Parameters| Request Body                                                 |Response| Status Code |
|---|---|---|---|--------------------------------------------------------------|---|-------------|
|`POST`|`/auth/signup`|회원 가입|없음|`{ "username": string, "email": string, "password": string }`|`{ "id": long, "username": string, "email": string, "createdAt": string, "modifiedAt": string }`|`200 OK`|
|`POST`|`/auth/login`|로그인|없음|`{ "email": string, "password": string}`|없음|`200 OK`|
|`POST`|`/auth/logout`|로그아웃|없음|없음|없음|`200 OK`|

### User
| Method |Endpoint| Description |Parameters| Request Body                                                 | Response                                                                                            | Status Code |
|-----|---|-------------|---|--------------------------------------------------------------|-----------------------------------------------------------------------------------------------------|-------------|
|`GET`|`/users`|유저 목록 조회|없음|없음| `[ { "id": long, "username": string, "email": string, "createdAt": string, "modifiedAt": string} ]` |`200 OK`|
|`GET`|`/users/{id}`|유저 단건 조회|Path:<br/>-`id`|없음|`{ "id": long, "username": string, "email": string, "createdAt": string, "modifiedAt": string}`|`200 OK`|
|`PATCH`|`/users/{id}`|비밀번호 변경|Path:<br/>-`id`|`{ "old_password": string, "new_password": string }`|없음|`200 OK`|