CREATE SCHEMA IF NOT EXISTS pdf_generator;

SET search_path=pdf_generator;

CREATE SEQUENCE IF NOT EXISTS text_block_id_seq INCREMENT 1 START 10001;

CREATE TABLE IF NOT EXISTS text_block (
    text_block_id                       BIGINT DEFAULT nextval('text_block_id_seq') NOT NULL,
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

CREATE TABLE IF NOT EXISTS template_to_text_translation (
    template_to_text_translation_id     BIGSERIAL NOT NULL,
    printout_type                       VARCHAR(150) NOT NULL,
    language_code                       CHAR(2) NOT NULL,
    text_block_name                     VARCHAR(255) NOT NULL,
    text_block_id                       BIGINT NOT NULL,
    text_size                           NUMERIC(3, 1) NOT NULL DEFAULT 12.0,
    horizontal_alignment                SMALLINT NOT NULL DEFAULT 0,
    vertical_alignment                  SMALLINT NOT NULL DEFAULT 4,
    CONSTRAINT pk_template_to_text_translation_id PRIMARY KEY (template_to_text_translation_id),
    CONSTRAINT ak_text_block_unique_in_language_and_template UNIQUE (printout_type,language_code,text_block_name),
    CONSTRAINT chk_language_code_two_lowercase_letters CHECK (language_code~'^[a-z]{2}$'),
    CONSTRAINT chk_size_positive_value CHECK (text_size>0),
    CONSTRAINT chk_horizontal_alignment_allowed_value CHECK (horizontal_alignment IN (-1, 0, 1, 2, 3)),
    CONSTRAINT chk_vertical_alignment_allowed_value CHECK (vertical_alignment IN (4, 5, 6, 7, 8)),
    CONSTRAINT fk_text_block FOREIGN KEY (text_block_id)
        REFERENCES text_block (text_block_id) ON DELETE No Action ON UPDATE No Action
) WITH (fillfactor=95);

COMMENT ON TABLE template_to_text_translation
IS 'Links templates to text blocks and holds their styling info';
COMMENT ON COLUMN template_to_text_translation.template_to_text_translation_id
IS 'Unique identifier.';
COMMENT ON COLUMN template_to_text_translation.printout_type
IS 'The name of the template this text block is used in.';
COMMENT ON COLUMN template_to_text_translation.language_code
IS 'Lowercase language code according to ISO 639-1 standard.';
COMMENT ON COLUMN template_to_text_translation.text_block_name
IS 'Unique identifier for the text block in this template and language domain.';
COMMENT ON COLUMN template_to_text_translation.text_block_id
IS 'References text on text_block table.';
COMMENT ON COLUMN template_to_text_translation.text_size
IS 'Text size for the text block (DEFAULT=12.0).';
COMMENT ON COLUMN template_to_text_translation.horizontal_alignment
IS 'According to values defined in OpenPdf library Element interface.
-1 and 0 (DEFAULT) are for left alignment. 1 is for center alignment. 2 is for right alignment.
3 is for justified alignment.';
COMMENT ON COLUMN template_to_text_translation.vertical_alignment
IS 'According to values defined in OpenPdf library Element interface.
4 (DEFAULT) is for top alignment. 5 is for center alignment. 6 is for bottom alignment.
7 is for baseline alignment. 8 is for justified alignment.';
