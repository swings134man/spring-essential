create table kor(
                    id SERIAL primary key,
                    name varchar(50) not null,
                    is_active boolean,
                    age int not null,
                    created_at timestamp(6) ,
                    updated_at timestamp(6)
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


