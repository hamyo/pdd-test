/*==============================================================*/
/* Table: USR                                                   */
/*==============================================================*/
create table USR (
   U_ID                 SERIAL not null,
   U_LOGIN              VARCHAR(25)          not null,
   U_PASSWORD           VARCHAR(32)          not null,
   U_LASTNAME           VARCHAR(30)          null,
   U_NAME               VARCHAR(30)          null,
   U_PATRONYMIC         VARCHAR(30)          null,
   U_ACTIVE             BOOL                 not null default false
);

comment on table USR is 'Пользователь';
comment on column USR.U_ID is 'ID';
comment on column USR.U_LOGIN is 'Логин';
comment on column USR.U_PASSWORD is 'Пароль';
comment on column USR.U_LASTNAME is 'Фамилия';
comment on column USR.U_NAME is 'Имя';
comment on column USR.U_PATRONYMIC is 'Отчество';
comment on column USR.U_ACTIVE is 'Признак активности';

alter table USR
   add constraint PK_USR primary key (U_ID);

create unique index U_AK on USR (
U_LOGIN
);

/*==============================================================*/
/* Table: AVAILABLE_TEST                                        */
/*==============================================================*/
create table AVAILABLE_TEST (
   AT_ID                INT4                 not null,
   AT_NAME              VARCHAR(50)          null,
   AT_DURATION          INT2                 null,
   AT_POSIIBLE_ERRORS   INT2                 null,
   AT_QUESTION_COUNT    INT2                 null
);

comment on table AVAILABLE_TEST is 'Доступный тест';
comment on column AVAILABLE_TEST.AT_ID is 'ID';
comment on column AVAILABLE_TEST.AT_NAME is 'Название';
comment on column AVAILABLE_TEST.AT_DURATION is 'Продолжительность в минутах';
comment on column AVAILABLE_TEST.AT_POSIIBLE_ERRORS is 'Количество допустимых ошибок';
comment on column AVAILABLE_TEST.AT_QUESTION_COUNT is 'Количество вопросов';

alter table AVAILABLE_TEST
   add constraint PK_AVAILABLE_TEST primary key (AT_ID);

/*==============================================================*/
/* Table: CLS_QUESTION_THEME                                    */
/*==============================================================*/
create table CLS_QUESTION_THEME (
   CQTM_ID              INT4                 not null,
   CQTM_NAME            VARCHAR(150)         not null
);

comment on table CLS_QUESTION_THEME is 'Тема вопроса';
comment on column CLS_QUESTION_THEME.CQTM_ID is 'ID темы вопроса';
comment on column CLS_QUESTION_THEME.CQTM_NAME is 'Название';

alter table CLS_QUESTION_THEME
   add constraint PK_CLS_QUESTION_THEME primary key (CQTM_ID);

/*==============================================================*/
/* Table: CLS_QUESTION_TYPE                                     */
/*==============================================================*/
create table CLS_QUESTION_TYPE (
   CQT_ID               INT4                 not null,
   CQT_NAME             VARCHAR(40)          not null
);

comment on table CLS_QUESTION_TYPE is 'Тип вопроса';
comment on column CLS_QUESTION_TYPE.CQT_ID is 'ID';
comment on column CLS_QUESTION_TYPE.CQT_NAME is 'Название';

alter table CLS_QUESTION_TYPE
   add constraint PK_CLS_QUESTION_TYPE primary key (CQT_ID);

/*==============================================================*/
/* Table: CLS_ROLE                                              */
/*==============================================================*/
create table CLS_ROLE (
   CR_ID                INT4                 not null,
   CR_NAME              VARCHAR(30)          not null
);

comment on table CLS_ROLE is 'Роль';
comment on column CLS_ROLE.CR_ID is 'ID роли';
comment on column CLS_ROLE.CR_NAME is 'Имя роли';

alter table CLS_ROLE
   add constraint PK_CLS_ROLE primary key (CR_ID);

/*==============================================================*/
/* Table: QUESTION                                              */
/*==============================================================*/
create table QUESTION (
   Q_ID                 INT8                 not null,
   Q_DESC               VARCHAR(300)         null,
   CQT_ID               INT4                 null,
   CQTM_ID              INT4                 null,
   Q_IMAGE              CHAR(10)             null,
   Q_TEXT               TEXT                 null,
   Q_COMMENT            TEXT                 null
);

comment on table QUESTION is 'Вопрос';
comment on column QUESTION.Q_ID is 'ID';
comment on column QUESTION.Q_DESC is 'Описание';
comment on column QUESTION.CQT_ID is 'ID типа вопроса';
comment on column QUESTION.CQTM_ID is 'ID темы вопроса';
comment on column QUESTION.Q_IMAGE is 'Изображение';
comment on column QUESTION.Q_TEXT is 'Текст вопроса';
comment on column QUESTION.Q_COMMENT is 'Комментарий';

alter table QUESTION
   add constraint PK_QUESTION primary key (Q_ID);

alter table QUESTION
   add constraint FK_Q_CQT foreign key (CQT_ID)
      references CLS_QUESTION_TYPE (CQT_ID)
      on delete restrict on update restrict;

alter table QUESTION
   add constraint FK_Q_CQTM foreign key (CQTM_ID)
      references CLS_QUESTION_THEME (CQTM_ID)
      on delete restrict on update restrict;

/*==============================================================*/
/* Table: ANSWER                                                */
/*==============================================================*/
create table ANSWER (
   A_ID                 INT8                 not null,
   A_DESC               VARCHAR(300)         null,
   A_IS_RIGHT           BOOL                 not null default false,
   Q_ID                 INT8                 null
);

comment on table ANSWER is 'Ответ';
comment on column ANSWER.A_ID is 'ID';
comment on column ANSWER.A_DESC is 'Описание';
comment on column ANSWER.A_IS_RIGHT is 'Признак правильности';
comment on column ANSWER.Q_ID is 'ID вопроса';

alter table ANSWER
   add constraint PK_ANSWER primary key (A_ID);
   
alter table ANSWER
   add constraint FK_A_Q foreign key (Q_ID)
      references QUESTION (Q_ID)
      on delete restrict on update restrict;

/*==============================================================*/
/* Table: USER_TEST                                             */
/*==============================================================*/
create table USER_TEST (
   UT_ID                INT8                 not null,
   AT_ID                INT4                 null,
   U_ID                 INT4                 null,
   UT_START_TIME        TIMESTAMP            not null,
   UT_END_TIME          TIMESTAMP            null,
   UT_SUCCESS_COUNT     INT2                 null,
   UT_ERROR_COUNT       INT2                 null
);

comment on table USER_TEST is 'Тест пользователя';
comment on column USER_TEST.AT_ID is 'ID доступного теста';
comment on column USER_TEST.U_ID is 'ID пользователя';
comment on column USER_TEST.UT_START_TIME is 'Начало теста';
comment on column USER_TEST.UT_END_TIME is 'Окончание теста';
comment on column USER_TEST.UT_SUCCESS_COUNT is 'Количество успешных ответов';
comment on column USER_TEST.UT_ERROR_COUNT is 'Количество ошибок';

alter table USER_TEST
   add constraint PK_USER_TEST primary key (UT_ID);
   
alter table USER_TEST
   add constraint FK_UT_AT foreign key (AT_ID)
      references AVAILABLE_TEST (AT_ID)
      on delete restrict on update restrict;

alter table USER_TEST
   add constraint FK_UT_U foreign key (U_ID)
      references USR (U_ID)
      on delete restrict on update restrict;
	  
/*==============================================================*/
/* Table: USER_ANSWER                                           */
/*==============================================================*/
create table USER_ANSWER (
   UT_ID                INT8                 not null,
   A_ID                 INT8                 not null
);

comment on table USER_ANSWER is 'Выбранный пользователем ответ';
comment on column USER_ANSWER.UT_ID is 'ID теста пользователя';
comment on column USER_ANSWER.A_ID is 'ID ответа';

alter table USER_ANSWER
   add constraint PK_USER_ANSWER primary key (UT_ID, A_ID);
   
alter table USER_ANSWER
   add constraint FK_UA_A foreign key (A_ID)
      references ANSWER (A_ID)
      on delete restrict on update restrict;

alter table USER_ANSWER
   add constraint FK_UA_UT foreign key (UT_ID)
      references USER_TEST (UT_ID)
      on delete restrict on update restrict;

/*==============================================================*/
/* Table: USER_TEST_QUESTION                                    */
/*==============================================================*/
create table USER_TEST_QUESTION (
   UT_ID                INT8                 not null,
   Q_ID                 INT8                 not null
);

comment on table USER_TEST_QUESTION is 'Связь теста пользователя с конкретными вопросами';
comment on column USER_TEST_QUESTION.UT_ID is 'ID теста пользователя';
comment on column USER_TEST_QUESTION.Q_ID is 'ID вопроса';

alter table USER_TEST_QUESTION
   add constraint PK_USER_TEST_QUESTION primary key (UT_ID, Q_ID);
	  
alter table USER_TEST_QUESTION
   add constraint FK_UTQ_Q foreign key (Q_ID)
      references QUESTION (Q_ID)
      on delete restrict on update restrict;

alter table USER_TEST_QUESTION
   add constraint FK_UTQ_UT foreign key (UT_ID)
      references USER_TEST (UT_ID)
      on delete restrict on update restrict;

/*==============================================================*/
/* Table: USER_CLS_ROLE                                         */
/*==============================================================*/
create table USER_CLS_ROLE (
   U_ID                 INT4                 not null,
   CR_ID                INT4                 not null
);

comment on table USER_CLS_ROLE is 'Связь пользователя и ролей';
comment on column USER_CLS_ROLE.U_ID is 'ID пользователя';
comment on column USER_CLS_ROLE.CR_ID is 'ID роли';

alter table USER_CLS_ROLE
   add constraint PK_USER_CLS_ROLE primary key (U_ID, CR_ID);
   
alter table USER_CLS_ROLE
   add constraint FK_UCR_CR foreign key (CR_ID)
      references CLS_ROLE (CR_ID)
      on delete restrict on update restrict;

alter table USER_CLS_ROLE
   add constraint FK_UCR_U foreign key (U_ID)
      references USR (U_ID)
      on delete restrict on update restrict;

