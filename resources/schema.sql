DROP TABLE IF EXISTS activities;
DROP TABLE IF EXISTS routines;
DROP TABLE IF EXISTS routine_types;


CREATE TABLE routine_types (
                               id SERIAL PRIMARY KEY,
                               name VARCHAR(60) NOT NULL UNIQUE
);

CREATE TABLE routines (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(80) NOT NULL,
                          type_id INT NOT NULL,

                          CONSTRAINT fk_routines_type
                              FOREIGN KEY (type_id) REFERENCES routine_types(id),

                          CONSTRAINT uq_routine_name_per_type
                              UNIQUE (name, type_id)
);

CREATE TABLE activities (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            kind VARCHAR(20) NOT NULL,
                            routine_id INT NOT NULL,


                            minutes INT,
                            intensity VARCHAR(30),


                            product VARCHAR(100),
                            step_count INT,

                            CONSTRAINT fk_activities_routine
                                FOREIGN KEY (routine_id) REFERENCES routines(id)
);

INSERT INTO routine_types (name) VALUES
                                     ('Morning'),
                                     ('Night');

INSERT INTO routines (name, type_id) VALUES
                                         ('Glow Up Morning', 1),
                                         ('Soft Night Reset', 2);

INSERT INTO activities (name, kind, routine_id, minutes, intensity, product, step_count) VALUES
                                                                                             ('Skincare basics', 'BEAUTY', 1, NULL, NULL, 'Cleanser + Moisturizer', 3),
                                                                                             ('Hair care', 'BEAUTY', 2, NULL, NULL, 'Hair mask', 2),
                                                                                             ('Stretching', 'WELLNESS', 1, 10, 'low', NULL, NULL),
                                                                                             ('Walk outside', 'WELLNESS', 2, 20, 'medium', NULL, NULL);
