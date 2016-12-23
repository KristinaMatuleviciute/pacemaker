# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table my_activity (
  id                            bigint not null,
  user_id                       bigint not null,
  type                          varchar(255),
  location                      varchar(255),
  distance                      double,
  constraint pk_my_activity primary key (id)
);
create sequence my_activity_seq;

create table my_location (
  id                            bigint not null,
  activity_id                   bigint not null,
  latitude                      float,
  longitude                     float,
  constraint pk_my_location primary key (id)
);
create sequence my_location_seq;

create table my_user (
  id                            bigint not null,
  firstname                     varchar(255),
  lastname                      varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  constraint pk_my_user primary key (id)
);
create sequence my_user_seq;

alter table my_activity add constraint fk_my_activity_user_id foreign key (user_id) references my_user (id) on delete restrict on update restrict;
create index ix_my_activity_user_id on my_activity (user_id);

alter table my_location add constraint fk_my_location_activity_id foreign key (activity_id) references my_activity (id) on delete restrict on update restrict;
create index ix_my_location_activity_id on my_location (activity_id);


# --- !Downs

alter table my_activity drop constraint if exists fk_my_activity_user_id;
drop index if exists ix_my_activity_user_id;

alter table my_location drop constraint if exists fk_my_location_activity_id;
drop index if exists ix_my_location_activity_id;

drop table if exists my_activity;
drop sequence if exists my_activity_seq;

drop table if exists my_location;
drop sequence if exists my_location_seq;

drop table if exists my_user;
drop sequence if exists my_user_seq;

