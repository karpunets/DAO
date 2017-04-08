--Создаем нового пользователя karpunets с паролем 248163264
create user karpunets IDENTIFIED BY 248163264
default tablespace users
temporary tablespace temp;

--Добавляем квоту на дисковое пространство.
alter user karpunets quota 100M on users;

--Разрещаем создавать сессии пользователю (подключаться)
grant create session to karpunets;

--Разрешаем создавать таблицы пользователю
grant create table to karpunets;

--Разрешаем создавать процедуры
grant create procedure to karpunets;

--Разрешаем создавать триггеры
grant create trigger to karpunets;

--Разрешаем создавать представления
grant create view to karpunets;

--Разрешаем создвавть счетчики
grant create sequence to karpunets;

--Разрешаем изменять таблицы, процедуры, триггеры и профиль
grant alter any table to karpunets;
grant alter any procedure to karpunets;
grant alter any trigger to karpunets;
grant alter profile to karpunets;

--Разрешаем удаления
grant delete any table to karpunets;
grant drop any table to karpunets;
grant drop any procedure to karpunets;
grant drop any trigger to karpunets;
grant drop any view to karpunets;
grant drop profile to karpunets;