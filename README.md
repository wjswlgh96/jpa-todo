# JPA-Todo

🌐 프로젝트 개요
- 내일배움캠프 숙련주차 과제 진행

## 🛠 사용한 기술 스택

<p>
    <img src="https://img.shields.io/badge/Java-b07219?style=flat-square&logoColor=white">
    <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat-square&logo=jpa&logoColor=white">
    <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white">
</p>

***
## 📖 **API 문서**
[📌 Swagger 문서 보기](https://wjswlgh96.github.io/swagger-docs/#/Author%20API/createAuthor)

---

## 🛠 Auth API
| Method  | Endpoint      | Description | Parameters | Request Body                                                         | Response | Status Code |
|---------|--------------|-------------|------------|----------------------------------------------------------------------|----------|-------------|
| `POST`  | `/auth/signup` | 회원 가입 | 없음 | ```json { "username": string, "email": string, "password": string } ``` | ```json { "id": long, "username": "string", "email": "string", "createdAt": "string", "modifiedAt": "string" } ``` | `200 OK` |
| `POST`  | `/auth/login`  | 로그인     | 없음 | ```json { "email": string, "password": string } ```                 | 없음 | `200 OK` |
| `POST`  | `/auth/logout` | 로그아웃   | 없음 | 없음                                                                   | 없음 | `200 OK` |

---

## 🛠 User API
| Method   | Endpoint      | Description  | Parameters | Request Body | Response | Status Code |
|----------|--------------|--------------|------------|--------------|----------|-------------|
| `GET`    | `/users`      | 유저 목록 조회 | 없음 | 없음 | ```json [ { "id": long, "username": string, "email": string, "createdAt": string, "modifiedAt": string } ] ``` | `200 OK` |
| `GET`    | `/users/{id}` | 유저 단건 조회 | Path:<br/>- `id` | 없음 | ```json { "id": long, "username": string, "email": string, "createdAt": string, "modifiedAt": string } ``` | `200 OK` |
| `PATCH`  | `/users/{id}` | 비밀번호 변경 | Path:<br/>- `id` | ```json { "oldPassword": string, "newPassword": string } ``` | 없음 | `200 OK` |
| `DELETE` | `/users/{id}` | 회원 탈퇴 | Path:<br/>- `id` | 없음 | 없음 | `200 OK` |

---

## 🛠 Schedule API
| Method   | Endpoint      | Description  | Parameters                                                                         | Request Body                                        | Response | Status Code |
|----------|--------------|--------------|------------------------------------------------------------------------------------|-----------------------------------------------------|----------|-------------|
| `POST`   | `/schedules`  | 할일 등록 | 없음                                                                                 | ```json { "title": string, "contents": string } ``` | ```json { "id": long, "userId": long, "title": string, "contents": string, "comments": [], "createdAt": string, "modifiedAt": string } ``` | `200 OK` |
| `GET`    | `/schedules`  | 모든 할일 조회 (페이지네이션) | Query:<br/>- `page(default: 1)`<br/>- `size(default: 10)`<br/>- `sort(default: modifiedAt)` | 없음                                                  | ```json { "content": [ { "id": long, "userId": long, "title": string, "contents": string, "comments": [], "createdAt": string, "modifiedAt": string } ], "size": int, "number": int, "totalElements": long, "totalPages": int } ``` | `200 OK` |
| `GET`    | `/schedules/{id}` | 특정 할일 조회 | Path:<br/>- `id`                                                                 | 없음                                                  | ```json { "id": long, "userId": long, "title": string, "contents": string, "comments": [], "createdAt": string, "modifiedAt": string } ``` | `200 OK` |
| `PATCH`  | `/schedules/{id}` | 할일 제목 & 내용 수정 | Path:<br/>- `id`                                                                      | ```json { "title": string, "contents": string } ``` | 없음 | `200 OK` |
| `DELETE` | `/schedules/{id}` | 할일 삭제 | Path:<br/>- `id`                                                                      | 없음                                                  | 없음 | `200 OK` |


---

## 🛠 Comment API
| Method   | Endpoint      | Description  | Parameters | Request Body | Response | Status Code |
|----------|--------------|--------------|------------|--------------|----------|-------------|
| `POST`   | `/comments`  | 댓글 등록 | 없음 | ```json { "schedule_id": long, "contents": string } ``` | ```json { "id": long, "userId": long, "scheduleId": long, "contents": string, "createdAt": string, "modifiedAt": string } ``` | `200 OK` |
| `GET`    | `/comments`  | 모든 댓글 조회 | 없음 | 없음 | ```json [ { "id": long, "userId": long, "scheduleId": long, "contents": string, "createdAt": string, "modifiedAt": string } ] ``` | `200 OK` |
| `GET`    | `/comments/{id}` | 특정 댓글 조회 | Path:<br/>- `id` | 없음 | ```json { "id": long, "userId": long, "scheduleId": long, "contents": string, "createdAt": string, "modifiedAt": string } ``` | `200 OK` |
| `PATCH`  | `/comments/{id}` | 댓글 수정 | Path:<br/>- `id` | ```json { "contents": string } ``` | 없음 | `200 OK` |
| `DELETE` | `/comments/{id}` | 댓글 삭제 | Path:<br/>- `id` | 없음 | 없음 | `200 OK` |
