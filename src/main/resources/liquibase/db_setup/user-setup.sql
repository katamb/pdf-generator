SET search_path=pdf_generator;

CREATE TABLE IF NOT EXISTS application_roles (
    username                VARCHAR(255) NOT NULL,
    user_role               VARCHAR(50) NOT NULL,
    CONSTRAINT pk_username_user_role PRIMARY KEY (username, user_role)
);
