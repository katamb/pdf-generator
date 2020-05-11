INSERT INTO pdf_generator.text_block (text_block_value)
VALUES ('<b>Employment Agreement</b>')
ON CONFLICT DO NOTHING;

UPDATE pdf_generator.template_text tt
SET text_block_id        = (SELECT tb.text_block_id
                            FROM pdf_generator.text_block tb
                            WHERE tb.text_block_value = '<b>Employment Agreement</b>'),
    text_size            = 14.0,
    horizontal_alignment = 1,
    vertical_alignment   = 4,
    padding_top          = 0,
    padding_bottom       = 10,
    padding_left         = 0,
    padding_right        = 0
WHERE tt.template_code = 'EMPLOYMENT_CONTRACT_EN'
  AND tt.language_code = 'en'
  AND tt.text_block_name = 'EMPLOYMENT_AGREEMENT';

INSERT INTO pdf_generator.text_block (text_block_value)
VALUES ('THIS AGREEMENT made as of the ${agreement.day} day of ${agreement.month}, ${agreement.year}, between ${employer.name} a corporation incorporated under the laws of the Province of Ontario, and having its principal place of business at ${employer.name} (the "Employer"); and ${employee.name}, of the City of ${agreement.city} in the Province of Ontario (the "Employee").')
ON CONFLICT DO NOTHING;

UPDATE pdf_generator.template_text tt
SET text_block_id        = (SELECT tb.text_block_id
                            FROM pdf_generator.text_block tb
                            WHERE tb.text_block_value =
                                  'THIS AGREEMENT made as of the ${agreement.day} day of ${agreement.month}, ${agreement.year}, between ${employer.name} a corporation incorporated under the laws of the Province of Ontario, and having its principal place of business at ${employer.name} (the "Employer"); and ${employee.name}, of the City of ${agreement.city} in the Province of Ontario (the "Employee").'),
    text_size            = 12.0,
    horizontal_alignment = 0,
    vertical_alignment   = 4,
    padding_top          = 5,
    padding_bottom       = 10,
    padding_left         = 0,
    padding_right        = 0
WHERE tt.template_code = 'EMPLOYMENT_CONTRACT_EN'
  AND tt.language_code = 'en'
  AND tt.text_block_name = 'AGREEMENT_INFO';

INSERT INTO pdf_generator.text_block (text_block_value)
VALUES ('WHEREAS the Employer desires to obtain the benefit of the services of the Employee, and the Employee desires to render such services on the terms and conditions set forth')
ON CONFLICT DO NOTHING;

UPDATE pdf_generator.template_text tt
SET text_block_id        = (SELECT tb.text_block_id
                            FROM pdf_generator.text_block tb
                            WHERE tb.text_block_value =
                                  'WHEREAS the Employer desires to obtain the benefit of the services of the Employee, and the Employee desires to render such services on the terms and conditions set forth'),
    text_size            = 12.0,
    horizontal_alignment = 0,
    vertical_alignment   = 4,
    padding_top          = 5,
    padding_bottom       = 10,
    padding_left         = 0,
    padding_right        = 0
WHERE tt.template_code = 'EMPLOYMENT_CONTRACT_EN'
  AND tt.language_code = 'en'
  AND tt.text_block_name = 'AGREEMENT_INFO_WHEREAS';

INSERT INTO pdf_generator.text_block (text_block_value)
VALUES ('IN CONSIDERATION of the promises and other good and valuable consideration (the sufficiency and receipt of which are hereby acknowledged) the parties agree as follows:')
ON CONFLICT DO NOTHING;

UPDATE pdf_generator.template_text tt
SET text_block_id        = (SELECT tb.text_block_id
                            FROM pdf_generator.text_block tb
                            WHERE tb.text_block_value =
                                  'IN CONSIDERATION of the promises and other good and valuable consideration (the sufficiency and receipt of which are hereby acknowledged) the parties agree as follows:'),
    text_size            = 12.0,
    horizontal_alignment = 0,
    vertical_alignment   = 4,
    padding_top          = 5,
    padding_bottom       = 10,
    padding_left         = 0,
    padding_right        = 0
WHERE tt.template_code = 'EMPLOYMENT_CONTRACT_EN'
  AND tt.language_code = 'en'
  AND tt.text_block_name = 'AGREEMENT_INFO_CONSIDERATION';

INSERT INTO pdf_generator.text_block (text_block_value)
VALUES ('<b>Employment</b>')
ON CONFLICT DO NOTHING;

UPDATE pdf_generator.template_text tt
SET text_block_id        = (SELECT tb.text_block_id
                            FROM pdf_generator.text_block tb
                            WHERE tb.text_block_value = '<b>Employment</b>'),
    text_size            = 12.0,
    horizontal_alignment = 0,
    vertical_alignment   = 4,
    padding_top          = 0,
    padding_bottom       = 0,
    padding_left         = 0,
    padding_right        = 0
WHERE tt.template_code = 'EMPLOYMENT_CONTRACT_EN'
  AND tt.language_code = 'en'
  AND tt.text_block_name = 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_1';

INSERT INTO pdf_generator.text_block (text_block_value)
VALUES ('<b>Employment</b>')
ON CONFLICT DO NOTHING;

UPDATE pdf_generator.template_text tt
SET text_block_id        = (SELECT tb.text_block_id
                            FROM pdf_generator.text_block tb
                            WHERE tb.text_block_value = '<b>Employment</b>'),
    text_size            = 12.0,
    horizontal_alignment = 0,
    vertical_alignment   = 4,
    padding_top          = 0,
    padding_bottom       = 5,
    padding_left         = 0,
    padding_right        = 0
WHERE tt.template_code = 'EMPLOYMENT_CONTRACT_EN'
  AND tt.language_code = 'en'
  AND tt.text_block_name = 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_1';

INSERT INTO pdf_generator.text_block (text_block_value)
VALUES ('<b>Position Title</b>')
ON CONFLICT DO NOTHING;

UPDATE pdf_generator.template_text tt
SET text_block_id        = (SELECT tb.text_block_id
                            FROM pdf_generator.text_block tb
                            WHERE tb.text_block_value = '<b>Position Title</b>'),
    text_size            = 12.0,
    horizontal_alignment = 0,
    vertical_alignment   = 4,
    padding_top          = 5,
    padding_bottom       = 5,
    padding_left         = 0,
    padding_right        = 0
WHERE tt.template_code = 'EMPLOYMENT_CONTRACT_EN'
  AND tt.language_code = 'en'
  AND tt.text_block_name = 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_2';

INSERT INTO pdf_generator.text_block (text_block_value)
VALUES ('<b>Position Title</b>')
ON CONFLICT DO NOTHING;

UPDATE pdf_generator.template_text tt
SET text_block_id        = (SELECT tb.text_block_id
                            FROM pdf_generator.text_block tb
                            WHERE tb.text_block_value = '<b>Position Title</b>'),
    text_size            = 12.0,
    horizontal_alignment = 0,
    vertical_alignment   = 4,
    padding_top          = 10,
    padding_bottom       = 5,
    padding_left         = 0,
    padding_right        = 0
WHERE tt.template_code = 'EMPLOYMENT_CONTRACT_EN'
  AND tt.language_code = 'en'
  AND tt.text_block_name = 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_2';

INSERT INTO pdf_generator.text_block (text_block_value)
VALUES ('[Signature of Employer Rep] [Title]')
ON CONFLICT DO NOTHING;

UPDATE pdf_generator.template_text tt
SET text_block_id        = (SELECT tb.text_block_id
                            FROM pdf_generator.text_block tb
                            WHERE tb.text_block_value = '[Signature of Employer Rep] [Title]'),
    text_size            = 12.0,
    horizontal_alignment = 0,
    vertical_alignment   = 4,
    padding_top          = 0,
    padding_bottom       = 0,
    padding_left         = 0,
    padding_right        = 0
WHERE tt.template_code = 'EMPLOYMENT_CONTRACT_EN'
  AND tt.language_code = 'en'
  AND tt.text_block_name = 'EMPLOYMENT_END_CONDITIONS_6';

INSERT INTO pdf_generator.text_block (text_block_value)
VALUES ('[Name of Employer Rep]')
ON CONFLICT DO NOTHING;

UPDATE pdf_generator.template_text tt
SET text_block_id        = (SELECT tb.text_block_id
                            FROM pdf_generator.text_block tb
                            WHERE tb.text_block_value = '[Name of Employer Rep]'),
    text_size            = 12.0,
    horizontal_alignment = 0,
    vertical_alignment   = 4,
    padding_top          = 0,
    padding_bottom       = 8,
    padding_left         = 0,
    padding_right        = 0
WHERE tt.template_code = 'EMPLOYMENT_CONTRACT_EN'
  AND tt.language_code = 'en'
  AND tt.text_block_name = 'EMPLOYMENT_END_CONDITIONS_5';

INSERT INTO pdf_generator.template_text (template_code, language_code, text_group_code, ordering, numbering,
                                         numbering_level, text_block_name, text_block_id, text_size,
                                         horizontal_alignment, vertical_alignment, padding_top, padding_bottom,
                                         padding_left, padding_right)
VALUES ('EMPLOYMENT_CONTRACT_EN', 'de', 'HEADING', NULL, 'FALSE', NULL, 'EMPLOYMENT_AGREEMENT', (SELECT tb.text_block_id
                                                                                                 FROM pdf_generator.text_block tb
                                                                                                 WHERE tb.text_block_value = '<b>Employment Agreement</b>'),
        14.0, 1, 4, 0, 10, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 2, 'TRUE', 1, 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_2',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = '<b>Position Title</b>'),
        12.0, 0, 4, 10, 5, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 0, 'TRUE', 1, 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_1',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = '<b>Employment</b>'),
        12.0, 0, 4, 0, 5, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'CONDITION_WITH_INPUT', 3, 'FALSE', NULL, 'EMPLOYMENT_END_CONDITIONS_6',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value = '[Signature of Employer Rep] [Title]'), 12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'GENERAL', 0, 'FALSE', NULL, 'AGREEMENT_INFO', (SELECT tb.text_block_id
                                                                                        FROM pdf_generator.text_block tb
                                                                                        WHERE tb.text_block_value =
                                                                                              'THIS AGREEMENT made as of the ${agreement.day} day of ${agreement.month}, ${agreement.year}, between ${employer.name} a corporation incorporated under the laws of the Province of Ontario, and having its principal place of business at ${employer.name} (the "Employer"); and ${employee.name}, of the City of ${agreement.city} in the Province of Ontario (the "Employee").'),
        12.0, 0, 4, 5, 10, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'GENERAL', 1, 'FALSE', NULL, 'AGREEMENT_INFO_WHEREAS', (SELECT tb.text_block_id
                                                                                                FROM pdf_generator.text_block tb
                                                                                                WHERE tb.text_block_value =
                                                                                                      'WHEREAS the Employer desires to obtain the benefit of the services of the Employee, and the Employee desires to render such services on the terms and conditions set forth'),
        12.0, 0, 4, 5, 10, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'GENERAL', 2, 'FALSE', NULL, 'AGREEMENT_INFO_CONSIDERATION',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'IN CONSIDERATION of the promises and other good and valuable consideration (the sufficiency and receipt of which are hereby acknowledged) the parties agree as follows:'),
        12.0, 0, 4, 5, 10, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 1, 'FALSE', NULL, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_1',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'The Employee agrees that he will at all times faithfully, industriously, and to the best of his skill, ability, experience and talents, perform all of the duties required of his position. In carrying out these duties and responsibilities, the Employee shall comply with all Employer policies, procedures, rules and regulations, both written and oral, as are announced by the Employer from time to time. It is also understood and agreed to by the Employee that his assignment, duties and responsibilities and reporting arrangements may be changed by the Employer in its sole discretion without causing termination of this agreement.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 3, 'FALSE', NULL, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_2',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'As a ${employee.position}, the Employee is required to perform the following duties and undertake the following responsibilities in a professional manner.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 4, 'TRUE', 1, 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_3',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = 'Compensation'), 12.0, 0,
        4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 5, 'TRUE', 2, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_3_1',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'As full compensation for all services provided the employee shall be paid at the rate of ${employee.salary}. Such payments shall be subject to such normal statutory deductions by the Employer.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 6, 'TRUE', 2, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_3_2',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = 'Yearly bonuses.'), 12.0,
        0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 7, 'TRUE', 2, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_3_3',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value = 'The salary mentioned in paragraph (l)(a) shall be review on an annual basis.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 8, 'TRUE', 2, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_3_4',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'All reasonable expenses arising out of employment shall be reimbursed assuming same have been authorized prior to being incurred and with the provision of appropriate receipts.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 9, 'TRUE', 1, 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_4',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = 'Vacation'), 12.0, 0, 4,
        0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 10, 'FALSE', NULL, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_4',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'The Employee shall be entitled to vacations in the amount of ${employee.vacationDays} weeks per annum.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 11, 'TRUE', 1, 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_5',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = 'Benefits'), 12.0, 0, 4,
        0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 12, 'FALSE', NULL, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_5',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'The Employer shall at its expense provide the Employee with the Health Plan that is currently in place or as may be in place from time to time.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 13, 'TRUE', 1, 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_6',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = 'Probation Period'), 12.0,
        0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 14, 'FALSE', NULL, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_6',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'It is understood and agreed that the first ninety days of employment shall constitute a probationary period during which period the Employer may, in its absolute discretion, terminate the Employee''s employment, for any reason without notice or cause.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 15, 'TRUE', 1, 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_7',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = 'Performance Reviews'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 16, 'FALSE', NULL, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_7',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'The Employee will be provided with a written performance appraisal at least once per year and said appraisal will be reviewed at which time all aspects of the assessment can be fully discussed.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 17, 'TRUE', 1, 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_8',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = 'Termination'), 12.0, 0,
        4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 18, 'TRUE', 2, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_8_1',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'The Employee may at any time terminate this agreement and his employment by giving not less than two weeks written notice to the Employer.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 19, 'TRUE', 2, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_8_2',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'The Employer may terminate this Agreement and the Employee’s employment at any time, without notice or payment in lieu of notice, for sufficient cause.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 20, 'TRUE', 2, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_8_3',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'The Employer may terminate the employment of the Employee at any time without the requirement to show sufficient cause pursuant to (b) above, provided the Employer pays to the Employee an amount as required by the Employment Standards Act 2000 or other such legislation as may be in effect at the time of termination. This payment shall constitute the employees entire entitlement arising from said termination.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 21, 'TRUE', 2, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_8_4',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'The Employee agrees to return any property of ${employer.name} at the time of termination.'), 12.0, 0,
        4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 22, 'TRUE', 1, 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_9',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = 'Non- Competition'), 12.0,
        0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 23, 'TRUE', 2, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_9_1',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'It is further acknowledged and agreed that following termination of the employee’s employment with ${employer.name} for any reason the employee shall not hire or attempt to hire any current employees of ${employer.name}.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 24, 'TRUE', 2, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_9_2',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'It is further acknowledged and agreed that following termination of the employee’s employment with ${employer.name} for any reason the employee shall not solicit business from current clients or clients who have retained ${employer.name} in the 6 month period immediately preceding the employee’s termination.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 25, 'TRUE', 1, 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_10',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = 'Laws'), 12.0, 0, 4, 0, 0,
        0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 26, 'FALSE', NULL, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_10',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value = 'This agreement shall be governed by the laws of the ${employee.province}.'), 12.0,
        0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 27, 'TRUE', 1, 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_11',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value = 'Independent Legal Advice'), 12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 28, 'FALSE', NULL, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_11',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'The Employee acknowledges that the Employer has provided the Employee with a reasonable opportunity to obtain independent legal advice with respect to this agreement, and that either:'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 29, 'TRUE', 2, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_11_1',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'The Employee has had such independent legal advice prior to executing this agreement, or;'), 12.0, 0, 4,
        0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 30, 'TRUE', 2, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_11_2',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'The Employee has willingly chosen not to obtain such advice and to execute this agreement without having obtained such advice.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 31, 'TRUE', 1, 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_12',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = 'Entire Agreement'), 12.0,
        0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 32, 'FALSE', NULL, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_12',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'This agreement contains the entire agreement between the parties, superseding in all respects any and all prior oral or written agreements or understandings pertaining to the employment of the Employee by the Employer and shall be amended or modified only by written instrument signed by both of the parties hereto.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 33, 'TRUE', 1, 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_13',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = 'Severability'), 12.0, 0,
        4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'MAIN_CONDITIONS', 34, 'FALSE', NULL, 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_13',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'The parties hereto agree that in the event any article or part thereof of this agreement is held to be unenforceable or invalid then said article or part shall be struck and all remaining provision shall remain in full force and effect.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'END_CONDITIONS', 0, 'FALSE', NULL, 'EMPLOYMENT_END_CONDITIONS_1',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value =
               'IN WITNESS WHEREOF the Employer has caused this agreement to be executed by its duly authorized officers and the Employee has set his hand as of the date first above written.'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'END_CONDITIONS', 1, 'FALSE', NULL, 'EMPLOYMENT_END_CONDITIONS_2',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value = 'SIGNED, SEALED AND DELIVERED in the presence of: '), 12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'CONDITION_WITH_INPUT', 0, 'FALSE', NULL, 'EMPLOYMENT_END_CONDITIONS_3',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = '[Name of employee]'),
        12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'CONDITION_WITH_INPUT', 1, 'FALSE', NULL, 'EMPLOYMENT_END_CONDITIONS_4',
        (SELECT tb.text_block_id
         FROM pdf_generator.text_block tb
         WHERE tb.text_block_value = '[Signature of employee]'), 12.0, 0, 4, 0, 0, 0, 0),
       ('EMPLOYMENT_CONTRACT_EN', 'de', 'CONDITION_WITH_INPUT', 2, 'FALSE', NULL, 'EMPLOYMENT_END_CONDITIONS_5',
        (SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = '[Name of Employer Rep]'),
        12.0, 0, 4, 0, 8, 0, 0)
ON CONFLICT DO NOTHING;