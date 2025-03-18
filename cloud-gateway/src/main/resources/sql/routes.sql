--  Maria DB - WIP ......
create table routes (
    id bigint primary key auto_increment,
    route_id varchar(255) not null,
    uri varchar(300) not null,
    predicates varchar(255),
    filters varchar(255),
    order int,
    enabled boolean not null default true,
    created_by VARCHAR(125),
    updated_by VARCHAR(125),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);