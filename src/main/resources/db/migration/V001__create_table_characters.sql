create table characters (
  id bigint not null auto_increment,
  name varchar(100) not null,
  role varchar(100) not null,
  school varchar(100) not null,
  house varchar(100) not null,
  patronus varchar(100) not null,

  primary key (id)
) engine=InnoDB default charset=utf8;