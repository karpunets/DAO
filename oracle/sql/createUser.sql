--������� ������ ������������ karpunets � ������� 248163264
create user karpunets IDENTIFIED BY 248163264
default tablespace users
temporary tablespace temp;

--��������� ����� �� �������� ������������.
alter user karpunets quota 100M on users;

--��������� ��������� ������ ������������ (������������)
grant create session to karpunets;

--��������� ��������� ������� ������������
grant create table to karpunets;

--��������� ��������� ���������
grant create procedure to karpunets;

--��������� ��������� ��������
grant create trigger to karpunets;

--��������� ��������� �������������
grant create view to karpunets;

--��������� ��������� ��������
grant create sequence to karpunets;

--��������� �������� �������, ���������, �������� � �������
grant alter any table to karpunets;
grant alter any procedure to karpunets;
grant alter any trigger to karpunets;
grant alter profile to karpunets;

--��������� ��������
grant delete any table to karpunets;
grant drop any table to karpunets;
grant drop any procedure to karpunets;
grant drop any trigger to karpunets;
grant drop any view to karpunets;
grant drop profile to karpunets;