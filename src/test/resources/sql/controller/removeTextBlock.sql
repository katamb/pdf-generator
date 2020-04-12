DELETE FROM pdf_generator.template_text
WHERE template_code in ('TEST_CONTRACT_1', 'TEST_CONTRACT_2');

DELETE FROM pdf_generator.template_code
WHERE template_code IN ('TEST_CONTRACT_1', 'TEST_CONTRACT_2');

DELETE FROM pdf_generator.text_block
WHERE text_block_value IN ('qqxx TEST_BLOCK_1 xxqq', 'qqxx TEST_BLOCK_2 xxqq', 'qqxx TEST_BLOCK_3 xxqq');
