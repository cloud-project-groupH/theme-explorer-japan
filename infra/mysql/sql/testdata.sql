CREATE TABLE members (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nickname VARCHAR(255) NOT NULL UNIQUE,
                         email VARCHAR(255) NOT NULL,
                         profile_image VARCHAR(255),
                         allowance BOOLEAN,
                         role VARCHAR(10) NOT NULL,
                         deleted_at DATETIME,
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                         updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE addresses (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           city VARCHAR(255) NOT NULL,
                           district VARCHAR(255) NOT NULL,
                           street VARCHAR(255) NOT NULL,
                           created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                           updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE places (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        address_id BIGINT NOT NULL,
                        title VARCHAR(255) NOT NULL,
                        likes INT NOT NULL DEFAULT 0,
                        visited INT NOT NULL DEFAULT 0,
                        latitude VARCHAR(50) NOT NULL,
                        longitude VARCHAR(50) NOT NULL,
                        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                        updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE CASCADE
);

CREATE TABLE likes (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       member_id BIGINT NOT NULL,
                       place_id BIGINT NOT NULL,
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT fk_likes_member FOREIGN KEY (member_id) REFERENCES members (id) ON DELETE CASCADE,
                       CONSTRAINT fk_likes_place FOREIGN KEY (place_id) REFERENCES places (id) ON DELETE CASCADE,
                       CONSTRAINT uc_like UNIQUE (member_id, place_id)
);

CREATE TABLE visited (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         member_id BIGINT NOT NULL,
                         place_id BIGINT NOT NULL,
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                         updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         CONSTRAINT fk_visited_member FOREIGN KEY (member_id) REFERENCES members (id) ON DELETE CASCADE,
                         CONSTRAINT fk_visited_place FOREIGN KEY (place_id) REFERENCES places (id) ON DELETE CASCADE,
                         CONSTRAINT uc_visited UNIQUE (member_id, place_id)
);

CREATE TABLE categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            title VARCHAR(255) NOT NULL,
                            created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                            updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE sub_categories (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                category_id BIGINT NOT NULL,
                                title VARCHAR(255) NOT NULL,
                                created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);

CREATE TABLE preferences (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             member_id BIGINT NOT NULL,
                             subcategory_id BIGINT NOT NULL,
                             created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                             updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             CONSTRAINT fk_preferences_member FOREIGN KEY (member_id) REFERENCES members (id) ON DELETE CASCADE,
                             CONSTRAINT fk_preferences_subcategory FOREIGN KEY (subcategory_id) REFERENCES sub_categories (id) ON DELETE CASCADE,
                             CONSTRAINT uc_preference UNIQUE (member_id, subcategory_id)
);

CREATE TABLE chat_rooms (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            title VARCHAR(255) NOT NULL,
                            travel_date DATETIME,
                            created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                            updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE participants (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              chatroom_id BIGINT NOT NULL,
                              member_id BIGINT NOT NULL,
                              created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                              updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              CONSTRAINT fk_participants_chatroom FOREIGN KEY (chatroom_id) REFERENCES chat_rooms (id) ON DELETE CASCADE,
                              CONSTRAINT fk_participants_member FOREIGN KEY (member_id) REFERENCES members (id) ON DELETE CASCADE,
                              CONSTRAINT uc_chatroom_member UNIQUE (chatroom_id, member_id)
);



CREATE TABLE messages (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          chatroom_id BIGINT NOT NULL,
                          sender BIGINT NOT NULL,
                          content TEXT NOT NULL,
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          CONSTRAINT fk_messages_chatroom FOREIGN KEY (chatroom_id) REFERENCES chat_rooms (id) ON DELETE CASCADE,
                          CONSTRAINT fk_messages_sender FOREIGN KEY (sender) REFERENCES members (id) ON DELETE CASCADE
);


INSERT INTO members (nickname, email, profile_image, allowance, role, deleted_at) VALUES
                                                                                      ('User1', 'user1@example.com', 'https://example.com/image1.png', TRUE, 'USER', NULL),
                                                                                      ('User2', 'user2@example.com', 'https://example.com/image2.png', TRUE, 'ADMIN', NULL),
                                                                                      ('User3', 'user3@example.com', 'https://example.com/image3.png', FALSE, 'USER', NULL),
                                                                                      ('User4', 'user4@example.com', 'https://example.com/image4.png', TRUE, 'USER', NULL),
                                                                                      ('User5', 'user5@example.com', 'https://example.com/image5.png', FALSE, 'USER', NULL),
                                                                                      ('User6', 'user6@example.com', 'https://example.com/image6.png', TRUE, 'USER', NULL),
                                                                                      ('User7', 'user7@example.com', 'https://example.com/image7.png', FALSE, 'USER', NULL),
                                                                                      ('User8', 'user8@example.com', 'https://example.com/image8.png', TRUE, 'USER', NULL),
                                                                                      ('User9', 'user9@example.com', 'https://example.com/image9.png', TRUE, 'ADMIN', NULL),
                                                                                      ('User10', 'user10@example.com', 'https://example.com/image10.png', FALSE, 'USER', NULL);


INSERT INTO categories (title) VALUES
                                   ('이색 관광지'),
                                   ('자연, 힐링'),
                                   ('맛집 탐방'),
                                   ('역사 문화 탐방'),
                                   ('유명 관광지');

INSERT INTO sub_categories (category_id, title) VALUES
                                                    (1, '신카이 마코토'),
                                                    (1, '최애의 아이'),
                                                    (2, '온천'),
                                                    (2, '공원'),
                                                    (3, '일식'),
                                                    (3, '양식'),
                                                    (3, '중식'),
                                                    (3, '한식'),
                                                    (4, '신사'),
                                                    (4, '절'),
                                                    (4, '성'),
                                                    (5, '건물'),
                                                    (5, '거리');

INSERT INTO chat_rooms (title, travel_date) VALUES
                                                ('Trip to Tokyo', '2024-12-15 09:00:00'),
                                                ('Weekend Hiking', '2024-12-22 07:30:00'),
                                                ('Beach Getaway', '2024-12-29 10:00:00'),
                                                ('Museum Visit', '2024-12-10 14:00:00'),
                                                ('Food Tour', '2024-12-18 12:00:00');

INSERT INTO preferences (member_id, subcategory_id) VALUES
                                                        (1, 1), (1, 3), (1, 5),
                                                        (2, 2), (2, 6),
                                                        (3, 4), (3, 9), (3, 10),
                                                        (4, 7), (4, 12),
                                                        (5, 8), (5, 13);

INSERT INTO messages (chatroom_id, sender, content) VALUES
                                                        (1, 1, 'Hello, how are you?'),
                                                        (1, 2, 'I am fine, thank you! How about you?'),
                                                        (2, 3, 'Are we still on for the meeting?'),
                                                        (2, 4, 'Yes, let’s meet at 3 PM.'),
                                                        (3, 5, 'Don’t forget to bring the files.'),
                                                        (3, 1, 'Got it, will bring them.');

INSERT INTO addresses (city, district, street) VALUES
                                                   ('Tokyo', 'Chiyoda', 'Marunouchi'),
                                                   ('Tokyo', 'Minato', 'Roppongi'),
                                                   ('Tokyo', 'Shinjuku', 'Kabukicho'),
                                                   ('Tokyo', 'Shibuya', 'Harajuku'),
                                                   ('Tokyo', 'Taito', 'Asakusa'),
                                                   ('Tokyo', 'Sumida', 'Ryogoku'),
                                                   ('Tokyo', 'Chuo', 'Ginza'),
                                                   ('Tokyo', 'Ota', 'Kamata'),
                                                   ('Tokyo', 'Setagaya', 'Shimokitazawa'),
                                                   ('Tokyo', 'Koto', 'Odaiba');

INSERT INTO places (address_id, title, likes, visited, latitude, longitude) VALUES
                                                                                (1, 'Imperial Palace', 100, 500, '35.6852', '139.7528'),
                                                                                (2, 'Tokyo Tower', 250, 1200, '35.6586', '139.7454'),
                                                                                (3, 'Shinjuku Gyoen', 180, 800, '35.6850', '139.7107'),
                                                                                (4, 'Meiji Shrine', 300, 1500, '35.6764', '139.6993'),
                                                                                (5, 'Sensoji Temple', 400, 2000, '35.7148', '139.7967');

INSERT INTO likes (member_id, place_id) VALUES
                                            (1, 1), (1, 3), (1, 5),
                                            (2, 2), (2, 4),
                                            (3, 1), (3, 3), (3, 5),
                                            (4, 2), (4, 4),
                                            (5, 1), (5, 3), (5, 5);

INSERT INTO visited (member_id, place_id) VALUES
                                              (1, 1), (1, 2),
                                              (2, 3), (2, 4),
                                              (3, 5), (3, 2),
                                              (4, 1), (4, 3),
                                              (5, 4), (5, 2);

INSERT INTO participants (chatroom_id, member_id) VALUES
                                                      (1, 1), (1, 2), (1, 3),
                                                      (2, 2), (2, 4),
                                                      (3, 1), (3, 3), (3, 5);