
CREATE TABLE object_types (
       object_type_id       NUMBER(20,0) NOT NULL,
       parent_type_id	    NUMBER(20,0) NULL,
       name                 VARCHAR2(100) NOT NULL
);

ALTER TABLE object_types
       ADD CONSTRAINT pk_ot_otID PRIMARY KEY (object_type_id);

CREATE TABLE objects (
       object_id            NUMBER(20,0) NOT NULL,
       object_type_id       NUMBER(20,0) NOT NULL
);

ALTER TABLE objects
       ADD CONSTRAINT pk_o_oID PRIMARY KEY (object_id);

CREATE TABLE attr_types (
       attr_type_id         NUMBER(20,0) NOT NULL,
       name                 VARCHAR2(50) NOT NULL
);

ALTER TABLE attr_types
       ADD CONSTRAINT pk_at_atID PRIMARY KEY (attr_type_id);

CREATE TABLE attributes (
       attr_id              NUMBER(20,0) NOT NULL,
       attr_type_id         NUMBER(20,0) NOT NULL,
       name                 VARCHAR2(100) NOT NULL
);

ALTER TABLE attributes
       ADD CONSTRAINT pk_a_aID PRIMARY KEY (attr_id);

CREATE TABLE attr_binds (
       attr_id              NUMBER(20,0) NOT NULL,
       object_type_id       NUMBER(20,0) NOT NULL
);

CREATE INDEX i_ab_otID ON attr_binds
(
       object_type_id                 ASC
);

CREATE TABLE params (
       object_id            NUMBER(20,0) NOT NULL,
       attr_id              NUMBER(20,0) NOT NULL,
       value                VARCHAR2(1000) NULL
);

CREATE INDEX i_p_oID ON params
(
       object_id                      ASC
);

ALTER TABLE object_types
       ADD CONSTRAINT fk_ot_ptID_ot FOREIGN KEY (parent_type_id)
                             REFERENCES object_types
                             ON DELETE CASCADE;

ALTER TABLE objects
       ADD CONSTRAINT fk_o_otID_ot FOREIGN KEY (object_type_id)
                             REFERENCES object_types
                             ON DELETE CASCADE;

ALTER TABLE attributes
       ADD CONSTRAINT fk_a_atID_at FOREIGN KEY (attr_type_id)
                             REFERENCES attr_types
                             ON DELETE CASCADE;

ALTER TABLE attr_binds
       ADD CONSTRAINT fk_ab_otID_ot FOREIGN KEY (object_type_id)
                             REFERENCES object_types
                             ON DELETE CASCADE;

ALTER TABLE attr_binds
       ADD CONSTRAINT fk_ab_atID_a FOREIGN KEY (attr_id)
                             REFERENCES attributes
                             ON DELETE CASCADE;

ALTER TABLE params
       ADD CONSTRAINT fk_p_oID_o FOREIGN KEY (object_id)
                             REFERENCES objects
                             ON DELETE CASCADE;

ALTER TABLE params
       ADD CONSTRAINT fk_p_aID_a FOREIGN KEY (attr_id)
                             REFERENCES attributes
                             ON DELETE CASCADE;