CREATE SEQUENCE object_types_seq START WITH 1;
CREATE OR REPLACE TRIGGER object_types_bir 
BEFORE INSERT ON object_types
FOR EACH ROW
BEGIN
  SELECT object_types_seq.NEXTVAL
  INTO   :new.object_type_id
  FROM   dual;
END;
/

CREATE SEQUENCE attributes_seq START WITH 1;
CREATE OR REPLACE TRIGGER attributes_bir 
BEFORE INSERT ON attributes
FOR EACH ROW
BEGIN
  SELECT attributes_seq.NEXTVAL
  INTO   :new.attr_id
  FROM   dual;
END;
/

CREATE SEQUENCE attr_types_seq START WITH 1;
CREATE OR REPLACE TRIGGER attr_types_bir 
BEFORE INSERT ON attr_types
FOR EACH ROW
BEGIN
  SELECT attr_types_seq.NEXTVAL
  INTO   :new.attr_type_id
  FROM   dual;
END;
/

CREATE SEQUENCE objects_seq START WITH 1;
CREATE OR REPLACE TRIGGER objects_bir 
BEFORE INSERT ON objects
FOR EACH ROW
BEGIN
  SELECT objects_seq.NEXTVAL
  INTO   :new.object_id
  FROM   dual;
END;
/