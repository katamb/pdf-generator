SET search_path=pdf_generator;

INSERT INTO
    text_block (text_block_value)
VALUES
    ('Makse nr.'),
    ('Kuupäev'),
    ('Krediidisumma'),
    ('Intress'),
    ('Lepingu haldustasu'),
    ('Makse kokku'),
    ('<b>${scheduleYears.year}</b>'),
    ('${scheduleYears.scheduleLines.paymentDate}'),
    ('${scheduleYears.scheduleLines.principal}'),
    ('${scheduleYears.scheduleLines.interest}'),
    ('${scheduleYears.scheduleLines.administrationFee}'),
    ('${scheduleYears.scheduleLines.paymentNr}'),
    ('${scheduleYears.scheduleLines.payment}')
;

INSERT INTO
    template_text (template_code, language_code, text_block_name, text_size, horizontal_alignment, text_group_code, text_block_id)
VALUES
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'PAYMENT_NR', 8.0, 0, 'SCHEDULE',
        (SELECT text_block_id from text_block where text_block_value='Makse nr.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'DATE', 8.0, 0, 'SCHEDULE',
        (SELECT text_block_id from text_block where text_block_value='Kuupäev')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'CREDIT_SUM', 8.0, 0, 'SCHEDULE',
        (SELECT text_block_id from text_block where text_block_value='Krediidisumma')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST', 8.0, 0, 'SCHEDULE',
        (SELECT text_block_id from text_block where text_block_value='Intress')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'ADM_FEE', 8.0, 0, 'SCHEDULE',
        (SELECT text_block_id from text_block where text_block_value='Lepingu haldustasu')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'PAYMENT_SUM', 8.0, 0, 'SCHEDULE',
        (SELECT text_block_id from text_block where text_block_value='Makse kokku')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'SCHEDULE_YEARS', 14.0, 1, 'SCHEDULE',
        (SELECT text_block_id from text_block where text_block_value='<b>${scheduleYears.year}</b>')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'SCHEDULE_PAYMENT_DATE', 8.0, 0, 'SCHEDULE',
        (SELECT text_block_id from text_block where text_block_value='${scheduleYears.scheduleLines.paymentDate}')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'SCHEDULE_PRINCIPAL_AMOUNT', 8.0, 0, 'SCHEDULE',
        (SELECT text_block_id from text_block where text_block_value='${scheduleYears.scheduleLines.principal}')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'SCHEDULE_INTEREST_AMOUNT', 8.0, 0, 'SCHEDULE',
        (SELECT text_block_id from text_block where text_block_value='${scheduleYears.scheduleLines.interest}')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'SCHEDULE_ADMINISTRATION_FEE', 8.0, 0, 'SCHEDULE',
        (SELECT text_block_id from text_block where text_block_value='${scheduleYears.scheduleLines.administrationFee}')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'SCHEDULE_PAYMENT_NR', 8.0, 0, 'SCHEDULE',
        (SELECT text_block_id from text_block where text_block_value='${scheduleYears.scheduleLines.paymentNr}')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'SCHEDULE_PAYMENT_SUM', 8.0, 0, 'SCHEDULE',
        (SELECT text_block_id from text_block where text_block_value='${scheduleYears.scheduleLines.payment}')
    )
;