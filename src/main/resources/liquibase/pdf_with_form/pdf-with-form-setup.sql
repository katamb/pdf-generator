SET search_path=pdf_generator;

INSERT INTO
    template_code (template_code)
VALUES
    ('EDITABLE_FORM_EE')
;

INSERT INTO
    text_block (text_block_value)
VALUES
    ('<b>PDF vorm</b>'),
    ('Täida vorm ja salvesta PDF täidetud kujul. Seejärel digiallkirjasta PDF fail ja saada meile meilile: ${email}.'),
    ('<b>Üldandmed</b>'),
    ('Eesnimi:'),
    ('Perenimi:'),
    ('Sugu:'),
    ('<b>Elukohaandmed</b>'),
    ('Riik:'),
    ('Linn:'),
    ('Tänava nimi, maja nr, korteri nr:'),
    ('Postikood:'),
    ('<b>Muu info</b>'),
    ('Muu oluline info:'),
    ('Mees'),
    ('Naine'),
    ('Muu')
;

INSERT INTO
    template_text (template_code, language_code, text_block_name, text_size, horizontal_alignment, text_group_code, ordering, numbering, numbering_level, text_block_id)
VALUES
    ('EDITABLE_FORM_EE', 'et', 'PDF_FORM', 14.0, 1, 'HEADING', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>PDF vorm</b>')
    ),
    ('EDITABLE_FORM_EE', 'et', 'INTRODUCTION', 12.0, 0, 'INTRODUCTION', 0, false, null,
        (SELECT text_block_id from text_block where text_block_value='Täida vorm ja salvesta PDF täidetud kujul. Seejärel digiallkirjasta PDF fail ja saada meile meilile: ${email}.')
    ),
    ('EDITABLE_FORM_EE', 'et', 'GENERAL_DATA_HEADING', 12.0, 0, 'GENERAL_DATA', 0, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Üldandmed</b>')
    ),
    ('EDITABLE_FORM_EE', 'et', 'GENERAL_DATA_1', 12.0, 0, 'GENERAL_DATA', 1, false, null,
        (SELECT text_block_id from text_block where text_block_value='Eesnimi:')
    ),
    ('EDITABLE_FORM_EE', 'et', 'GENERAL_DATA_2', 12.0, 0, 'GENERAL_DATA', 2, false, null,
        (SELECT text_block_id from text_block where text_block_value='Perenimi:')
    ),
    ('EDITABLE_FORM_EE', 'et', 'GENERAL_DATA_3', 12.0, 0, 'GENERAL_DATA', 3, false, null,
        (SELECT text_block_id from text_block where text_block_value='Sugu:')
    ),
    ('EDITABLE_FORM_EE', 'et', 'LOCATION_DATA_HEADING', 12.0, 0, 'LOCATION_DATA', 0, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Elukohaandmed</b>')
    ),
    ('EDITABLE_FORM_EE', 'et', 'LOCATION_DATA_1', 12.0, 0, 'LOCATION_DATA', 1, false, null,
        (SELECT text_block_id from text_block where text_block_value='Riik:')
    ),
    ('EDITABLE_FORM_EE', 'et', 'LOCATION_DATA_2', 12.0, 0, 'LOCATION_DATA', 2, false, null,
        (SELECT text_block_id from text_block where text_block_value='Linn:')
    ),
    ('EDITABLE_FORM_EE', 'et', 'LOCATION_DATA_3', 12.0, 0, 'LOCATION_DATA', 3, false, null,
        (SELECT text_block_id from text_block where text_block_value='Tänava nimi, maja nr, korteri nr:')
    ),
    ('EDITABLE_FORM_EE', 'et', 'LOCATION_DATA_4', 12.0, 0, 'LOCATION_DATA', 4, false, null,
        (SELECT text_block_id from text_block where text_block_value='Postikood:')
    ),
    ('EDITABLE_FORM_EE', 'et', 'OTHER_DATA_HEADING', 12.0, 0, 'OTHER_DATA', 0, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Muu info</b>')
    ),
    ('EDITABLE_FORM_EE', 'et', 'OTHER_DATA_1', 12.0, 0, 'OTHER_DATA', 1, false, null,
        (SELECT text_block_id from text_block where text_block_value='Muu oluline info:')
    ),
    ('EDITABLE_FORM_EE', 'et', 'GENDER_CHOICE_1', 12.0, 2, 'GENDER_CHOICE', 0, false, null,
        (SELECT text_block_id from text_block where text_block_value='Mees')
    ),
    ('EDITABLE_FORM_EE', 'et', 'GENDER_CHOICE_2', 12.0, 2, 'GENDER_CHOICE', 1, false, null,
        (SELECT text_block_id from text_block where text_block_value='Naine')
    ),
    ('EDITABLE_FORM_EE', 'et', 'GENDER_CHOICE_3', 12.0, 2, 'GENDER_CHOICE', 2, false, null,
        (SELECT text_block_id from text_block where text_block_value='Muu')
    )
;