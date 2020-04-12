INSERT INTO
    pdf_generator.template_code (template_code)
VALUES
    ('TEST_CONTRACT_1'),
    ('TEST_CONTRACT_2');

INSERT INTO
    pdf_generator.text_block (text_block_value)
VALUES
    ('qqxx TEST_BLOCK_1 xxqq'),
    ('qqxx TEST_BLOCK_2 xxqq');

INSERT INTO
    pdf_generator.template_text (template_code, language_code, text_block_name, text_group_code, text_block_id)
VALUES
    ('TEST_CONTRACT_1', 'et', 'TEST_1', 'SCHEDULE', (SELECT text_block_id from pdf_generator.text_block where text_block_value='qqxx TEST_BLOCK_1 xxqq')),
    ('TEST_CONTRACT_2', 'et', 'TEST_1', 'SCHEDULE', (SELECT text_block_id from pdf_generator.text_block where text_block_value='qqxx TEST_BLOCK_1 xxqq'));
