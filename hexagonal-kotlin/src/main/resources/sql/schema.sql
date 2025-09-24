CREATE TABLE users (
                        id SERIAL PRIMARY KEY,
                        email VARCHAR(300) NOT NULL,
                        password VARCHAR(200) NOT NULL,
                        user_name VARCHAR(50),
                        phone_number VARCHAR(50),
                        age INTEGER,
                        gender VARCHAR(10),
                        address VARCHAR(300),
                        is_active BOOLEAN,
                        created_at TIMESTAMP(6),
                        updated_at TIMESTAMP(6)
);

CREATE TABLE post (
                        id SERIAL PRIMARY KEY,
                        title VARCHAR(200) NOT NULL,
                        content TEXT NOT NULL,
                        writer VARCHAR(50) NOT NULL, -- userName
                        delYn CHAR(1) DEFAULT 'N' CHECK (delYn IN ('Y', 'N')),
                        view_count INTEGER DEFAULT 0, -- 조회수
                        created_at TIMESTAMP(6),
                        updated_at TIMESTAMP(6),
                        user_id INTEGER REFERENCES users(id) -- FK
);