DELETE FROM pdf_generator.user_sql_file
WHERE username = 'INTEG_TEST';

DELETE FROM pdf_generator.template_text
WHERE template_code = 'PRIVATE_SMALL_LOAN_CONTRACT_EE'
AND language_code = 'et'
AND text_block_name = 'LOAN_CONTRACT_TEST';

DELETE FROM pdf_generator.text_block
WHERE text_block_value = 'Old text block';

DELETE FROM pdf_generator.text_block
WHERE text_block_value = 'New text block';
