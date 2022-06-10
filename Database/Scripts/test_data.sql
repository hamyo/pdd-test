insert into [dbo].[PERSON]([P_ID], [P_NAME], [P_LASTNAME], [P_PER_PATRONYMIC], [P_EMAIL])
select 1, 'Иван', 'Иванов', 'Иванович', 'test@mail.ru'
go

insert into [USER]([U_ID], [U_LOGIN], [U_PASSWORD])
select 1, 'root', '$2a$10$WQy6U0TjvXKzqeXse8447OWsDJOosBp9GLarbLt7lKCawW8NOPgpe'
go
 