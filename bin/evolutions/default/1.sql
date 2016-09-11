# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table item (
  id                        bigint not null,
  title                     varchar(100) not null,
  description               varchar(500),
  price                     bigint,
  picture_uri               varchar(100),
  create_date               timestamp not null,
  constraint pk_item primary key (id))
;

create sequence item_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists item;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists item_seq;

