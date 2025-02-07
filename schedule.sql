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