INSERT INTO pdf_generator.user_sql_file
    (username, sql_file_name, selected)
VALUES
    ('INTEG_TEST', 'INTEG_TEST.sql', true);

INSERT INTO pdf_generator.text_block
    (text_block_value)
VALUES
    ('New text block');

INSERT INTO pdf_generator.text_block
    (text_block_value)
VALUES
    ('Old text block');

INSERT INTO
    pdf_generator.template_text (template_code, language_code, text_block_name, text_size, horizontal_alignment, text_group_code, ordering, numbering, numbering_level, text_block_id)
VALUES
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LOAN_CONTRACT_TEST', 12.0, 1, 'HEADING', null, false, null,
     (SELECT text_block_id from pdf_generator.text_block where text_block_value='Old text block')
    );