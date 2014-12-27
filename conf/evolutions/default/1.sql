# --- !Ups

create table competition (
  id                        bigint not null,
  creation_date             timestamp not null,
  last_update_date          timestamp not null,
  name                      varchar(255) not null,
  description               varchar(255),
  admin_id                  bigint,
  constraint pk_competition primary key (id))
;

create table match (
  id                        bigint not null,
  creation_date             timestamp not null,
  last_update_date          timestamp not null,
  home_team_name            varchar(255) not null,
  away_team_name            varchar(255) not null,
  home_team_score           integer default -1,
  away_team_score           integer default -1,
  start_date                timestamp not null,
  status                    varchar(19) not null,
  competition_id            bigint not null,
  constraint ck_match_status check (status in ('OPEN_FOR_PREDICTION','PREDICTION_CLOSED','RESULT_AVAILABLE')),
  constraint pk_match primary key (id))
;

create table prediction (
  id                        bigint not null,
  creation_date             timestamp not null,
  last_update_date          timestamp not null,
  home_team_score           integer not null,
  away_team_score           integer not null,
  points                    integer default 0,
  match_id                  bigint not null,
  predictor_id              bigint not null,
  constraint pk_prediction primary key (id))
;

create table predictor (
  id                        bigint not null,
  creation_date             timestamp not null,
  last_update_date          timestamp not null,
  login                     varchar(255) not null,
  password                  varchar(255) not null,
  registration_date         timestamp not null,
  constraint uq_login_predictor unique (login),
  constraint pk_predictor primary key (id))
;

create table predictor_points (
  id                        bigint not null,
  creation_date             timestamp not null,
  last_update_date          timestamp not null,
  predictor_id              bigint not null,
  competition_id            bigint not null,
  points                    integer default 0 not null,
  constraint pk_predictor_points primary key (id))
;

create sequence competition_seq;

create sequence match_seq;

create sequence prediction_seq;

create sequence predictor_seq;

create sequence predictor_points_seq;

alter table competition add constraint fk_competition_admin_1 foreign key (admin_id) references predictor (id);
create index ix_competition_admin_1 on competition (admin_id);
alter table match add constraint fk_match_competition_2 foreign key (competition_id) references competition (id);
create index ix_match_competition_2 on match (competition_id);
alter table prediction add constraint fk_prediction_match_3 foreign key (match_id) references match (id);
create index ix_prediction_match_3 on prediction (match_id);
alter table prediction add constraint fk_prediction_predictor_4 foreign key (predictor_id) references predictor (id);
create index ix_prediction_predictor_4 on prediction (predictor_id);
alter table predictor_points add constraint fk_predictor_points_predictor_5 foreign key (predictor_id) references predictor (id);
create index ix_predictor_points_predictor_5 on predictor_points (predictor_id);
alter table predictor_points add constraint fk_predictor_points_competitio_6 foreign key (competition_id) references competition (id);
create index ix_predictor_points_competitio_6 on predictor_points (competition_id);



# --- !Downs

drop table if exists competition cascade;

drop table if exists match cascade;

drop table if exists prediction cascade;

drop table if exists predictor cascade;

drop table if exists predictor_points cascade;

drop sequence if exists competition_seq;

drop sequence if exists match_seq;

drop sequence if exists prediction_seq;

drop sequence if exists predictor_seq;

drop sequence if exists predictor_points_seq;

