CREATE SCHEMA IF NOT EXISTS pdf_generator;

SET search_path=pdf_generator;

CREATE TABLE IF NOT EXISTS language_code (
    language_code                       CHAR(2) NOT NULL,
    language_name                       VARCHAR(50) NOT NULL,
    CONSTRAINT pk_language_code PRIMARY KEY (language_code),
    CONSTRAINT chk_language_code_two_lowercase_letters CHECK (language_code~'^[a-z]{2}$')
);

COMMENT ON COLUMN language_code.language_code
IS 'Lowercase language code according to ISO 639-1 standard.';
COMMENT ON COLUMN language_code.language_name
IS 'Language name in english.';

CREATE TABLE IF NOT EXISTS template_code (
    template_code                       VARCHAR(150) NOT NULL,
    template_description                VARCHAR(255),
    CONSTRAINT pk_template_code PRIMARY KEY (template_code)
);

COMMENT ON COLUMN template_code.template_code
IS 'The name of the template this text block is used in.';
COMMENT ON COLUMN template_code.template_description
IS 'Can be used to describe what this template is for.';

CREATE TABLE IF NOT EXISTS text_block (
    text_block_id                       BIGSERIAL NOT NULL,
    text_block_value                    text NOT NULL,
    CONSTRAINT ak_text_block_value_unique UNIQUE (text_block_value),
    CONSTRAINT pk_text_block_id PRIMARY KEY (text_block_id)
) WITH (fillfactor=90);

COMMENT ON TABLE text_block
    IS 'Text block values with placeholders for dynamic data and some inline styling information.';
COMMENT ON COLUMN text_block.text_block_id
    IS 'Unique text block identifier.';
COMMENT ON COLUMN text_block.text_block_value
    IS 'Text block values with placeholders for dynamic data and some inline styling information.';

CREATE TABLE IF NOT EXISTS template_text (
    template_text_id                    BIGSERIAL NOT NULL,
    template_code                       VARCHAR(150) NOT NULL,
    language_code                       CHAR(2) NOT NULL,
    text_group_code                     VARCHAR(255) NOT NULL,
    ordering                            INT,
    numbering                           BOOLEAN,
    numbering_level                     SMALLINT,
    text_block_name                     VARCHAR(255) NOT NULL,
    text_block_id                       BIGINT NOT NULL,
    text_size                           NUMERIC(3, 1) NOT NULL DEFAULT 12.0,
    horizontal_alignment                SMALLINT NOT NULL DEFAULT 0,
    vertical_alignment                  SMALLINT NOT NULL DEFAULT 4,
    CONSTRAINT pk_template_text_id PRIMARY KEY (template_text_id),
    CONSTRAINT ak_text_block_unique_in_language_and_template UNIQUE (template_code,language_code,text_block_name),
    CONSTRAINT chk_ordering_positive_value CHECK (ordering>=0),
    CONSTRAINT chk_numbering_level_positive_value CHECK (numbering_level>0),
    CONSTRAINT chk_size_positive_value CHECK (text_size>0),
    CONSTRAINT chk_horizontal_alignment_allowed_value CHECK (horizontal_alignment IN (-1, 0, 1, 2, 3)),
    CONSTRAINT chk_vertical_alignment_allowed_value CHECK (vertical_alignment IN (4, 5, 6, 7, 8)),
    CONSTRAINT fk_template_code FOREIGN KEY (template_code)
        REFERENCES template_code (template_code) ON DELETE No Action ON UPDATE No Action,
    CONSTRAINT fk_language_code FOREIGN KEY (language_code)
        REFERENCES language_code (language_code) ON DELETE No Action ON UPDATE No Action,
    CONSTRAINT fk_text_block FOREIGN KEY (text_block_id)
        REFERENCES text_block (text_block_id) ON DELETE No Action ON UPDATE No Action
) WITH (fillfactor=95);

COMMENT ON TABLE template_text
IS 'Links templates to text blocks and holds their styling info';
COMMENT ON COLUMN template_text.template_text_id
IS 'Unique identifier.';
COMMENT ON COLUMN template_text.template_code
IS 'The name of the template this text block is used in.';
COMMENT ON COLUMN template_text.language_code
IS 'Lowercase language code according to ISO 639-1 standard.';
COMMENT ON COLUMN template_text.text_group_code
IS 'Identifies the group for the text.';
COMMENT ON COLUMN template_text.ordering
IS 'Assigns the order of the elements in the group.';
COMMENT ON COLUMN template_text.numbering
IS 'Shows if this text block is numbered.';
COMMENT ON COLUMN template_text.numbering_level
IS 'Context advice for automatic numbering.';
COMMENT ON COLUMN template_text.text_block_name
IS 'Unique identifier for the text block in this template and language domain.';
COMMENT ON COLUMN template_text.text_block_id
IS 'References text on text_block table.';
COMMENT ON COLUMN template_text.text_size
IS 'Text size for the text block (DEFAULT=12.0).';
COMMENT ON COLUMN template_text.horizontal_alignment
IS 'According to values defined in OpenPdf library Element interface.
-1 and 0 (DEFAULT) are for left alignment. 1 is for center alignment. 2 is for right alignment.
3 is for justified alignment.';
COMMENT ON COLUMN template_text.vertical_alignment
IS 'According to values defined in OpenPdf library Element interface.
4 (DEFAULT) is for top alignment. 5 is for center alignment. 6 is for bottom alignment.
7 is for baseline alignment. 8 is for justified alignment.';
