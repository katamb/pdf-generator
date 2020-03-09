SET search_path=pdf_generator;

INSERT INTO
    text_block (text_block_value)
VALUES
    ('Created @ TalTech 2020'),
    ('Page ${pagenumber} of '),
    ('${all.pages}'),
    ('Laenuleping nr. ${loan.contractNumber}')
;

INSERT INTO
    template_text (template_code, language_code, text_block_name, text_size, horizontal_alignment, text_group_code, text_block_id)
VALUES
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'FOOTER_TEXT_1', 12.0, 0, 'FOOTER',
        (SELECT text_block_id from text_block where text_block_value='Created @ TalTech 2020')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'PAGE_NR', 10.0, 2, 'FOOTER',
        (SELECT text_block_id from text_block where text_block_value='Page ${pagenumber} of ')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'ALL_PAGES', 10.0, 0, 'FOOTER',
        (SELECT text_block_id from text_block where text_block_value='${all.pages}')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'HEADER_TEXT_1', 14.0, 2, 'HEADER',
        (SELECT text_block_id from text_block where text_block_value='Laenuleping nr. ${loan.contractNumber}')
    )
;