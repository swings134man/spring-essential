create table kor(
                    id SERIAL primary key,
                    name varchar(50) not null,
                    age int not null
);

create table board(
                      id SERIAL primary key,
                      title varchar(100) not null,
                      content text not null,
                      created_at timestamp(6) ,
                      updated_at timestamp(6),
                      kor_id bigint references kor(id) on delete cascade on update cascade
);

create table dsl(
                    id SERIAL primary key,
                    name varchar(100) not null,
                    address varchar(100) not null,
                    created_at timestamp(6) ,
                    updated_at timestamp(6)
);


-- insert data
insert into kor(name, age, is_active, created_at, updated_at) values('홍길동', 30, true, now(), now());
insert into kor(name, age, is_active, created_at, updated_at) values('강봄', 5, true, now(), now());
insert into kor(name, age, is_active, created_at, updated_at) values('루카스', 30, true, now(), now());

insert into dsl(name, address, created_at, updated_at) values('강루카스', '서울시 강남구', now(), now());
insert into dsl(name, address, created_at, updated_at) values('강봄', '서울시 서초구', now(), now());
insert into dsl(name, address, created_at, updated_at) values('오리동동', '서울시 동작구', now(), now());
insert into dsl(name, address, created_at, updated_at) values('루길동', '서울시 송파구', now(), now());

-- FIXME : fk
insert into board(title, content, created_at, updated_at, kor_id) values('게시글1', '게시글 내용1', now(), now(), 1);
insert into board(title, content, created_at, updated_at, kor_id) values('게시글2', '게시글 내용2', now(), now(), 2);
insert into board(title, content, created_at, updated_at, kor_id) values('게시글3', '게시글 내용3', now(), now(), 3);