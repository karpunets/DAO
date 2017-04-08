SELECT * FROM attr_binds;
SELECT * FROM attr_types;
SELECT * FROM attributes;
SELECT * FROM object_types;
SELECT * FROM objects;
SELECT * FROM params;

DELETE FROM attr_binds;
DELETE FROM attr_types;
DELETE FROM attributes;
DELETE FROM object_types;
DELETE FROM objects;
DELETE FROM params;

SELECT max(object_id) as last_id from objects;

COMMIT;