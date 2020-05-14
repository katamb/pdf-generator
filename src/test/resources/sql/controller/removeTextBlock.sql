DELETE FROM pdf_generator.template_text
WHERE template_code IN ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'PRIVATE_CAR_LOAN_CONTRACT_EE')
AND text_block_id IN (
    SELECT text_block_id
    FROM pdf_generator.text_block
    WHERE text_block_value IN ('qqxx TEST_BLOCK_1 xxqq', 'qqxx TEST_BLOCK_2 xxqq', 'qqxx TEST_BLOCK_3 xxqq')
    );

DELETE FROM pdf_generator.text_block
WHERE text_block_value IN ('qqxx TEST_BLOCK_1 xxqq', 'qqxx TEST_BLOCK_2 xxqq', 'qqxx TEST_BLOCK_3 xxqq');
