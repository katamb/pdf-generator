INSERT INTO
    pdf_generator.text_block (text_block_value)
VALUES
    ('qqxx TEST_BLOCK_1 xxqq'),
    ('qqxx TEST_BLOCK_2 xxqq');

INSERT INTO
    pdf_generator.template_text (template_code, language_code, text_block_name, text_group_code, text_block_id)
VALUES
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'TEST_1', 'SCHEDULE', (SELECT text_block_id from pdf_generator.text_block where text_block_value='qqxx TEST_BLOCK_1 xxqq')),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'TEST_1', 'SCHEDULE', (SELECT text_block_id from pdf_generator.text_block where text_block_value='qqxx TEST_BLOCK_1 xxqq'));
