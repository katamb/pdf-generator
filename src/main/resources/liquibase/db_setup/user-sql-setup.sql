SET search_path=pdf_generator;

CREATE TABLE IF NOT EXISTS user_sql_file (
    id                      BIGSERIAL NOT NULL,
    username                VARCHAR(255) NOT NULL,
    sql_file_name           VARCHAR(255) NOT NULL,
    selected                BOOLEAN NOT NULL DEFAULT false,
    created_at              TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    CONSTRAINT pk_id PRIMARY KEY (id)
);

COMMENT ON COLUMN user_sql_file.username
IS 'String that uniquely identifies users, e.g. email.';
COMMENT ON COLUMN user_sql_file.sql_file_name
IS 'The file name given to sql file.';
COMMENT ON COLUMN user_sql_file.selected
IS 'The file currently written to.';
COMMENT ON COLUMN user_sql_file.created_at
IS 'The timestamp when the sql file was created.';
