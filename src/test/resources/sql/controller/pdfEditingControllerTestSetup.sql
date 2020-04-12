INSERT INTO pdf_generator.application_roles
    (username, user_role)
VALUES
    ('INTEG_TEST', 'ROLE_USER'),
    ('INTEG_TEST', 'ROLE_EDITOR');

INSERT INTO pdf_generator.user_sql_file
    (username, sql_file_name, selected)
VALUES
    ('INTEG_TEST', 'INTEG_TEST.sql', true);
