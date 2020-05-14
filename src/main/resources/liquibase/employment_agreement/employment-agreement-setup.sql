SET search_path=pdf_generator;

INSERT INTO
    template_code (template_code)
VALUES
    ('EMPLOYMENT_CONTRACT_EN')
;

--https://sehub.stanford.edu/sites/default/files/SampleEmploymentContract.pdf
INSERT INTO
    text_block (text_block_value)
VALUES
    ('Employment Agreement'),
    ('THIS AGREEMENT made as of the ${agreement.day} day of ${agreement.month}, ${agreement.year}, between ${employer.name} a corporation incorporated under the laws of the Province of Ontario, and having its principal place of business at ${employer.name} (the "Employer"); and ${employee.name}, of the City of ${agreement.city} in the Province of Ontario (the "Employee").'),
    ('WHEREAS the Employer desires to obtain the benefit of the services of the Employee, and the Employee desires to render such services on the terms and conditions set forth'),
    ('IN CONSIDERATION of the promises and other good and valuable consideration (the sufficiency and receipt of which are hereby acknowledged) the parties agree as follows:'),
    ('Employment'),
    ('The Employee agrees that he will at all times faithfully, industriously, and to the best of his skill, ability, experience and talents, perform all of the duties required of his position. In carrying out these duties and responsibilities, the Employee shall comply with all Employer policies, procedures, rules and regulations, both written and oral, as are announced by the Employer from time to time. It is also understood and agreed to by the Employee that his assignment, duties and responsibilities and reporting arrangements may be changed by the Employer in its sole discretion without causing termination of this agreement.'),
    ('Position Title'),
    ('As a ${employee.position}, the Employee is required to perform the following duties and undertake the following responsibilities in a professional manner.'),
    ('Compensation'),
    ('As full compensation for all services provided the employee shall be paid at the rate of ${employee.salary}. Such payments shall be subject to such normal statutory deductions by the Employer.'),
    ('Yearly bonuses.'),
    ('The salary mentioned in paragraph (l)(a) shall be review on an annual basis.'),
    ('All reasonable expenses arising out of employment shall be reimbursed assuming same have been authorized prior to being incurred and with the provision of appropriate receipts.'),
    ('Vacation'),
    ('The Employee shall be entitled to vacations in the amount of ${employee.vacationDays} weeks per annum.'),
    ('Benefits'),
    ('The Employer shall at its expense provide the Employee with the Health Plan that is currently in place or as may be in place from time to time.'),
    ('Probation Period'),
    ('It is understood and agreed that the first ninety days of employment shall constitute a probationary period during which period the Employer may, in its absolute discretion, terminate the Employee''s employment, for any reason without notice or cause.'),
    ('Performance Reviews'),
    ('The Employee will be provided with a written performance appraisal at least once per year and said appraisal will be reviewed at which time all aspects of the assessment can be fully discussed.'),
    ('Termination'),
    ('The Employee may at any time terminate this agreement and his employment by giving not less than two weeks written notice to the Employer.'),
    ('The Employer may terminate this Agreement and the Employee’s employment at any time, without notice or payment in lieu of notice, for sufficient cause.'),
    ('The Employer may terminate the employment of the Employee at any time without the requirement to show sufficient cause pursuant to (b) above, provided the Employer pays to the Employee an amount as required by the Employment Standards Act 2000 or other such legislation as may be in effect at the time of termination. This payment shall constitute the employees entire entitlement arising from said termination.'),
    ('The Employee agrees to return any property of ${employer.name} at the time of termination.'),
    ('Non- Competition'),
    ('It is further acknowledged and agreed that following termination of the employee’s employment with ${employer.name} for any reason the employee shall not hire or attempt to hire any current employees of ${employer.name}.'),
    ('It is further acknowledged and agreed that following termination of the employee’s employment with ${employer.name} for any reason the employee shall not solicit business from current clients or clients who have retained ${employer.name} in the 6 month period immediately preceding the employee’s termination.'),
    ('Laws'),
    ('This agreement shall be governed by the laws of the ${employee.province}.'),
    ('Independent Legal Advice'),
    ('The Employee acknowledges that the Employer has provided the Employee with a reasonable opportunity to obtain independent legal advice with respect to this agreement, and that either:'),
    ('The Employee has had such independent legal advice prior to executing this agreement, or;'),
    ('The Employee has willingly chosen not to obtain such advice and to execute this agreement without having obtained such advice.'),
    ('Entire Agreement'),
    ('This agreement contains the entire agreement between the parties, superseding in all respects any and all prior oral or written agreements or understandings pertaining to the employment of the Employee by the Employer and shall be amended or modified only by written instrument signed by both of the parties hereto.'),
    ('Severability'),
    ('The parties hereto agree that in the event any article or part thereof of this agreement is held to be unenforceable or invalid then said article or part shall be struck and all remaining provision shall remain in full force and effect.'),
    ('IN WITNESS WHEREOF the Employer has caused this agreement to be executed by its duly authorized officers and the Employee has set his hand as of the date first above written.'),
    ('SIGNED, SEALED AND DELIVERED in the presence of: '),
    ('[Name of employee]'),
    ('[Signature of employee]'),
    ('[Name of Employer Rep]'),
    ('[Signature of Employer Rep] \n [Title]')
ON CONFLICT DO NOTHING;

INSERT INTO
    template_text (template_code, language_code, text_block_name, text_group_code, ordering, numbering, numbering_level, text_block_id)
VALUES
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_AGREEMENT', 'HEADING', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Employment Agreement')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'AGREEMENT_INFO', 'GENERAL', 0, false, null,
        (SELECT text_block_id from text_block where text_block_value='THIS AGREEMENT made as of the ${agreement.day} day of ${agreement.month}, ${agreement.year}, between ${employer.name} a corporation incorporated under the laws of the Province of Ontario, and having its principal place of business at ${employer.name} (the "Employer"); and ${employee.name}, of the City of ${agreement.city} in the Province of Ontario (the "Employee").')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'AGREEMENT_INFO_WHEREAS', 'GENERAL', 1, false, null,
        (SELECT text_block_id from text_block where text_block_value='WHEREAS the Employer desires to obtain the benefit of the services of the Employee, and the Employee desires to render such services on the terms and conditions set forth')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'AGREEMENT_INFO_CONSIDERATION', 'GENERAL', 2, false, null,
        (SELECT text_block_id from text_block where text_block_value='IN CONSIDERATION of the promises and other good and valuable consideration (the sufficiency and receipt of which are hereby acknowledged) the parties agree as follows:')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_1', 'MAIN_CONDITIONS', 0, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Employment')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_1', 'MAIN_CONDITIONS', 1, false, null,
        (SELECT text_block_id from text_block where text_block_value='The Employee agrees that he will at all times faithfully, industriously, and to the best of his skill, ability, experience and talents, perform all of the duties required of his position. In carrying out these duties and responsibilities, the Employee shall comply with all Employer policies, procedures, rules and regulations, both written and oral, as are announced by the Employer from time to time. It is also understood and agreed to by the Employee that his assignment, duties and responsibilities and reporting arrangements may be changed by the Employer in its sole discretion without causing termination of this agreement.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_2', 'MAIN_CONDITIONS', 2, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Position Title')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_2', 'MAIN_CONDITIONS', 3, false, null,
        (SELECT text_block_id from text_block where text_block_value='As a ${employee.position}, the Employee is required to perform the following duties and undertake the following responsibilities in a professional manner.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_3', 'MAIN_CONDITIONS', 4, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Compensation')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_3_1', 'MAIN_CONDITIONS', 5, true, 2,
        (SELECT text_block_id from text_block where text_block_value='As full compensation for all services provided the employee shall be paid at the rate of ${employee.salary}. Such payments shall be subject to such normal statutory deductions by the Employer.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_3_2', 'MAIN_CONDITIONS', 6, true, 2,
        (SELECT text_block_id from text_block where text_block_value='Yearly bonuses.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_3_3', 'MAIN_CONDITIONS', 7, true, 2,
        (SELECT text_block_id from text_block where text_block_value='The salary mentioned in paragraph (l)(a) shall be review on an annual basis.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_3_4', 'MAIN_CONDITIONS', 8, true, 2,
        (SELECT text_block_id from text_block where text_block_value='All reasonable expenses arising out of employment shall be reimbursed assuming same have been authorized prior to being incurred and with the provision of appropriate receipts.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_4', 'MAIN_CONDITIONS', 9, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Vacation')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_4', 'MAIN_CONDITIONS', 10, false, null,
        (SELECT text_block_id from text_block where text_block_value='The Employee shall be entitled to vacations in the amount of ${employee.vacationDays} weeks per annum.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_5', 'MAIN_CONDITIONS', 11, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Benefits')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_5', 'MAIN_CONDITIONS', 12, false, null,
        (SELECT text_block_id from text_block where text_block_value='The Employer shall at its expense provide the Employee with the Health Plan that is currently in place or as may be in place from time to time.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_6', 'MAIN_CONDITIONS', 13, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Probation Period')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_6', 'MAIN_CONDITIONS', 14, false, null,
        (SELECT text_block_id from text_block where text_block_value='It is understood and agreed that the first ninety days of employment shall constitute a probationary period during which period the Employer may, in its absolute discretion, terminate the Employee''s employment, for any reason without notice or cause.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_7', 'MAIN_CONDITIONS', 15, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Performance Reviews')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_7', 'MAIN_CONDITIONS', 16, false, null,
        (SELECT text_block_id from text_block where text_block_value='The Employee will be provided with a written performance appraisal at least once per year and said appraisal will be reviewed at which time all aspects of the assessment can be fully discussed.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_8', 'MAIN_CONDITIONS', 17, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Termination')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_8_1', 'MAIN_CONDITIONS', 18, true, 2,
        (SELECT text_block_id from text_block where text_block_value='The Employee may at any time terminate this agreement and his employment by giving not less than two weeks written notice to the Employer.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_8_2', 'MAIN_CONDITIONS', 19, true, 2,
        (SELECT text_block_id from text_block where text_block_value='The Employer may terminate this Agreement and the Employee’s employment at any time, without notice or payment in lieu of notice, for sufficient cause.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_8_3', 'MAIN_CONDITIONS', 20, true, 2,
        (SELECT text_block_id from text_block where text_block_value='The Employer may terminate the employment of the Employee at any time without the requirement to show sufficient cause pursuant to (b) above, provided the Employer pays to the Employee an amount as required by the Employment Standards Act 2000 or other such legislation as may be in effect at the time of termination. This payment shall constitute the employees entire entitlement arising from said termination.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_8_4', 'MAIN_CONDITIONS', 21, true, 2,
        (SELECT text_block_id from text_block where text_block_value='The Employee agrees to return any property of ${employer.name} at the time of termination.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_9', 'MAIN_CONDITIONS', 22, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Non- Competition')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_9_1', 'MAIN_CONDITIONS', 23, true, 2,
        (SELECT text_block_id from text_block where text_block_value='It is further acknowledged and agreed that following termination of the employee’s employment with ${employer.name} for any reason the employee shall not hire or attempt to hire any current employees of ${employer.name}.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_9_2', 'MAIN_CONDITIONS', 24, true, 2,
        (SELECT text_block_id from text_block where text_block_value='It is further acknowledged and agreed that following termination of the employee’s employment with ${employer.name} for any reason the employee shall not solicit business from current clients or clients who have retained ${employer.name} in the 6 month period immediately preceding the employee’s termination.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_10', 'MAIN_CONDITIONS', 25, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laws')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_10', 'MAIN_CONDITIONS', 26, false, null,
        (SELECT text_block_id from text_block where text_block_value='This agreement shall be governed by the laws of the ${employee.province}.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_11', 'MAIN_CONDITIONS', 27, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Independent Legal Advice')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_11', 'MAIN_CONDITIONS', 28, false, null,
        (SELECT text_block_id from text_block where text_block_value='The Employee acknowledges that the Employer has provided the Employee with a reasonable opportunity to obtain independent legal advice with respect to this agreement, and that either:')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_11_1', 'MAIN_CONDITIONS', 29, true, 2,
        (SELECT text_block_id from text_block where text_block_value='The Employee has had such independent legal advice prior to executing this agreement, or;')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_11_2', 'MAIN_CONDITIONS', 30, true, 2,
     (SELECT text_block_id from text_block where text_block_value='The Employee has willingly chosen not to obtain such advice and to execute this agreement without having obtained such advice.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_12', 'MAIN_CONDITIONS', 31, true, 1,
     (SELECT text_block_id from text_block where text_block_value='Entire Agreement')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_12', 'MAIN_CONDITIONS', 32, false, null,
     (SELECT text_block_id from text_block where text_block_value='This agreement contains the entire agreement between the parties, superseding in all respects any and all prior oral or written agreements or understandings pertaining to the employment of the Employee by the Employer and shall be amended or modified only by written instrument signed by both of the parties hereto.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_HEADING_13', 'MAIN_CONDITIONS', 33, true, 1,
     (SELECT text_block_id from text_block where text_block_value='Severability')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_MAIN_CONDITIONS_PARAGRAPH_13', 'MAIN_CONDITIONS', 34, false, null,
     (SELECT text_block_id from text_block where text_block_value='The parties hereto agree that in the event any article or part thereof of this agreement is held to be unenforceable or invalid then said article or part shall be struck and all remaining provision shall remain in full force and effect.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_END_CONDITIONS_1', 'END_CONDITIONS', 0, false, null,
     (SELECT text_block_id from text_block where text_block_value='IN WITNESS WHEREOF the Employer has caused this agreement to be executed by its duly authorized officers and the Employee has set his hand as of the date first above written.')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_END_CONDITIONS_2', 'END_CONDITIONS', 1, false, null,
     (SELECT text_block_id from text_block where text_block_value='SIGNED, SEALED AND DELIVERED in the presence of: ')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_END_CONDITIONS_3', 'CONDITION_WITH_INPUT', 0, false, null,
     (SELECT text_block_id from text_block where text_block_value='[Name of employee]')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_END_CONDITIONS_4', 'CONDITION_WITH_INPUT', 1, false, null,
     (SELECT text_block_id from text_block where text_block_value='[Signature of employee]')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_END_CONDITIONS_5', 'CONDITION_WITH_INPUT', 2, false, null,
     (SELECT text_block_id from text_block where text_block_value='[Name of Employer Rep]')
    ),
    ('EMPLOYMENT_CONTRACT_EN', 'en', 'EMPLOYMENT_END_CONDITIONS_6', 'CONDITION_WITH_INPUT', 3, false, null,
     (SELECT text_block_id from text_block where text_block_value='[Signature of Employer Rep] \n [Title]')
    )
;