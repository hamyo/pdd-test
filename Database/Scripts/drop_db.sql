alter table ANSWER
   drop constraint PK_ANSWER;

drop table ANSWER;

alter table AVAILABLE_TEST
   drop constraint PK_AVAILABLE_TEST;

drop table AVAILABLE_TEST;

alter table CLS_QUESTION_THEME
   drop constraint PK_CLS_QUESTION_THEME;

drop table CLS_QUESTION_THEME;

alter table CLS_QUESTION_TYPE
   drop constraint PK_CLS_QUESTION_TYPE;

drop table CLS_QUESTION_TYPE;

alter table CLS_ROLE
   drop constraint PK_CLS_ROLE;

drop table CLS_ROLE;

alter table QUESTION
   drop constraint PK_QUESTION;

drop table QUESTION;

alter table USER_ANSWER
   drop constraint PK_USER_ANSWER;

drop table USER_ANSWER;

alter table USER_CLS_ROLE
   drop constraint PK_USER_CLS_ROLE;

drop table USER_CLS_ROLE;

alter table USER_TEST
   drop constraint PK_USER_TEST;

drop table USER_TEST;

alter table USER_TEST_QUESTION
   drop constraint PK_USER_TEST_QUESTION;

drop table USER_TEST_QUESTION;

drop index U_AK;

alter table USR
   drop constraint PK_USR;

drop table USR;