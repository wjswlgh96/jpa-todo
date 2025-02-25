# JPA-Todo

🌐 프로젝트 개요
- 내일배움캠프 숙련주차 과제 진행

***

## 트러블 슈팅 및 과제 회고
[📖 블로그 링크](https://velog.io/@wjswlgh96/TIL-CH-4-%EC%9D%BC%EC%A0%95-%EA%B4%80%EB%A6%AC-%EC%95%B1-%EB%A7%8C%EB%93%A4%EA%B8%B0-JPA-%EA%B3%BC%EC%A0%9C-%ED%9A%8C%EA%B3%A0-%EB%B0%8F-%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85)

## 🛠 사용한 기술 스택

<p>
    <img src="https://img.shields.io/badge/Java-b07219?style=flat-square&logoColor=white">
    <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat-square&logo=jpa&logoColor=white">
    <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white">
</p>

***
## 📖 API 문서
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

---

## 📖 SQL

```mysql
CREATE TABLE user (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    created_at DATETIME(6),
    modified_at DATETIME(6)
);

CREATE TABLE schedule (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(50) NOT NULL,
    contents LONGTEXT NOT NULL,
    created_at DATETIME(6),
    modified_at DATETIME(6),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE comment (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    contents VARCHAR(100) NOT NULL,
    created_at DATETIME(6),
    modified_at DATETIME(6),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (schedule_id) REFERENCES schedule(id) ON DELETE CASCADE
);
```

---

## 📖 ERD

![Image](https://github.com/user-attachments/assets/436b420c-ea51-43e5-819e-4843c9819999)