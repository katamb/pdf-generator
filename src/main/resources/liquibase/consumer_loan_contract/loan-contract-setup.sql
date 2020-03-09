SET search_path=pdf_generator;

INSERT INTO
    text_block (text_block_value)
VALUES
    ('LAENULEPING'),
    ('<b>Laenuandja</b>'),
    ('<b>Laenusaaja</b>'),
    ('Nimi'),
    ('Isikukood'),
    ('Aadress'),
    ('Telefon'),
    ('Laenuandjale ja laenusaajale on edaspidi ühiselt viidatud kui Pooled.'),
    ('<b>Laen ja selle üleandmine</b>'),
    ('Laenuandja annab Laenusaajale laenu ${loan.principalAmount} ${loan.currency} (edaspidi Laen).'),
    ('Laenuandja kohustub Laenusaajale Laenu üle andma hiljemalt ${loan.transferDate}. Laenu üleandmine toimub Laenu kandmisega Laenusaaja poolt määratud arvelduskontole.'),
    ('<b>Intress ja laenu tagastamine</b>'),
    ('Laen on antud tähtajaliselt. Laenusaaja kohustub Laenu tagasi maksma hiljemalt ${loan.lastInstallment}.'),
    ('Laenusaaja tagastab Laenuandjale Laenu Laenuandja arvelduskontole ${lender.iban}.'),
    ('Laenusaaja maksab Laenuandjale Laenu tagastamata osalt intressi ${loan.interestRate}% aastas.'),
    ('Kui Laenusaaja teeb Laenuandjale makse, millest ei piisa kõigi Lepingu alusel võlgnetavate summade tasumiseks, arvestatakse makse:'),
    ('esimeses järjekorras võlgnetava intressi katteks;'),
    ('teises järjekorras võlgnetava viivise katteks'),
    ('kolmandas järjekorras võlgnetava põhisumma katteks;'),
    ('neljandas järjekorras muude Lepingust tulenevate kohustuste katteks.'),
    ('Laenusaajal on õigus tagastada kogu Laen enne Lepingu punktis 2.1 nimetatud maksetähtpäeva, teatades sellest Laenuandjale kirjalikult ${loan.fullRepaymentDaysAdvanceNotification} päeva ette.'),
    ('<b>Viivis</b>'),
    ('Laenu tagastamisega viivitamisel on Laenuandjal õigus nõuda Laenusaajalt viivise tasumist ${loan.penaltyRate}% päevas sissenõutavaks muutunud summalt iga tasumisega viivitatud päeva eest. Tasumata intressilt või viiviselt viivist ei arvestata.'),
    ('<b>Laenuandja õigus leping üles öelda</b>'),
    ('Laenuandjal on õigus Leping üles öelda ja nõuda Laenu kohest tagastamist, kui:'),
    ('Lepingust tulenevaid Laenusaaja kohustusi tagava vara väärtus väheneb oluliselt ning Laenusaaja ja Laenuandja ei jõua kokkuleppele Laenu täiendava tagamise osas;'),
    ('Laenusaaja ei täida kohaselt Lepingust tulenevaid kohustusi või mõnda neist ning jätkab kohustuse mittetäitmist ka pärast ${loan.maxAllowedDaysAfterNotification} päeva möödumist Laenuandjalt vastavasisulise kirjaliku teatise saamisest.'),
    ('<b>Tagatised</b>'),
    ('Laenusaaja vastutab Lepingust tulenevate kohustuste täitmise eest kogu oma varaga.'),
    ('<b>Vaidluste lahendamise kord</b>'),
    ('Lepingust tulenevad ja sellega seotud vaidlused püüavad Pooled lahendada läbirääkimiste teel. Kui vaidlust ei õnnestu lahendada Poolte läbirääkimiste teel, on Pooltel õigus pöörduda vaidluse lahendamiseks maakohtusse vastavalt Eesti Vabariigis kehtivatele õigusaktidele.'),
    ('<b>Lepingu jõustumine</b>'),
    ('Leping jõustub alates Lepingu allkirjastamise hetkest.'),
    ('<b>Lõppsätted</b>'),
    ('Leping on koostatud ja alla kirjutatud eesti keeles kahes (2) võrdset juriidilist jõudu omavas identses eksemplaris, millest üks jääb Laenuandjale ja teine Laenusaajale.'),
    ('${borrower.name}'),
    ('${borrower.address}'),
    ('${borrower.phone}'),
    ('${lender.name}'),
    ('${lender.address}'),
    ('${lender.phone}'),
    ('Laenusaaja on kohustatud maksma laenuandjale ${loan.conclusionFee} ${loan.currency}, mis arvestatakse maha laenu summast.')
;

INSERT INTO
    template_text (template_code, language_code, text_block_name, text_size, horizontal_alignment, text_group_code, ordering, numbering, numbering_level, text_block_id)
VALUES
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LOAN_CONTRACT', 12.0, 1, 'HEADING', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='LAENULEPING')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER', 12.0, 1, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Laenuandja</b>')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'BORROWER', 12.0, 1, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Laenusaaja</b>')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'NAME', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Nimi')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'ID_CODE', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Isikukood')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'ADDRESS', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Aadress')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'PHONE', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Telefon')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'WORDING_INFO', 12.0, 0, 'GENERAL_INFO', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Laenuandjale ja laenusaajale on edaspidi ühiselt viidatud kui Pooled.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LOAN_TRANSFER_PARAGRAPH_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 0, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Laen ja selle üleandmine</b>')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LOAN_TRANSFER_PARAGRAPH_1', 12.0, 0, 'MAIN_CONDITIONS', 1, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenuandja annab Laenusaajale laenu ${loan.principalAmount} ${loan.currency} (edaspidi Laen).')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LOAN_TRANSFER_PARAGRAPH_2', 12.0, 0, 'MAIN_CONDITIONS', 2, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenusaaja on kohustatud maksma laenuandjale ${loan.conclusionFee} ${loan.currency}, mis arvestatakse maha laenu summast.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LOAN_TRANSFER_PARAGRAPH_3', 12.0, 0, 'MAIN_CONDITIONS', 3, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenuandja kohustub Laenusaajale Laenu üle andma hiljemalt ${loan.transferDate}. Laenu üleandmine toimub Laenu kandmisega Laenusaaja poolt määratud arvelduskontole.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 4, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Intress ja laenu tagastamine</b>')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_1', 12.0, 0, 'MAIN_CONDITIONS', 5, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laen on antud tähtajaliselt. Laenusaaja kohustub Laenu tagasi maksma hiljemalt ${loan.lastInstallment}.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_2', 12.0, 0, 'MAIN_CONDITIONS', 6, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenusaaja tagastab Laenuandjale Laenu Laenuandja arvelduskontole ${lender.iban}.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_3', 12.0, 0, 'MAIN_CONDITIONS', 7, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenusaaja maksab Laenuandjale Laenu tagastamata osalt intressi ${loan.interestRate}% aastas.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4', 12.0, 0, 'MAIN_CONDITIONS', 8, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Kui Laenusaaja teeb Laenuandjale makse, millest ei piisa kõigi Lepingu alusel võlgnetavate summade tasumiseks, arvestatakse makse:')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4_1', 12.0, 0, 'MAIN_CONDITIONS', 9, true, 2,
        (SELECT text_block_id from text_block where text_block_value='esimeses järjekorras võlgnetava intressi katteks;')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4_2', 12.0, 0, 'MAIN_CONDITIONS', 10, true, 2,
        (SELECT text_block_id from text_block where text_block_value='teises järjekorras võlgnetava viivise katteks')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4_3', 12.0, 0, 'MAIN_CONDITIONS', 11, true, 2,
        (SELECT text_block_id from text_block where text_block_value='kolmandas järjekorras võlgnetava põhisumma katteks;')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4_4', 12.0, 0, 'MAIN_CONDITIONS', 12, true, 2,
        (SELECT text_block_id from text_block where text_block_value='neljandas järjekorras muude Lepingust tulenevate kohustuste katteks.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_5', 12.0, 0, 'MAIN_CONDITIONS', 13, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenusaajal on õigus tagastada kogu Laen enne Lepingu punktis 2.1 nimetatud maksetähtpäeva, teatades sellest Laenuandjale kirjalikult ${loan.fullRepaymentDaysAdvanceNotification} päeva ette.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'PENALTY_PARAGRAPH_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 14, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Viivis</b>')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'PENALTY_PARAGRAPH_1', 12.0, 0, 'MAIN_CONDITIONS', 15, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenu tagastamisega viivitamisel on Laenuandjal õigus nõuda Laenusaajalt viivise tasumist ${loan.penaltyRate}% päevas sissenõutavaks muutunud summalt iga tasumisega viivitatud päeva eest. Tasumata intressilt või viiviselt viivist ei arvestata.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER_RIGHTS_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 16, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Laenuandja õigus leping üles öelda</b>')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER_RIGHTS_PARAGRAPH_1', 12.0, 0, 'MAIN_CONDITIONS', 17, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenuandjal on õigus Leping üles öelda ja nõuda Laenu kohest tagastamist, kui:')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER_RIGHTS_PARAGRAPH_1_1', 12.0, 0, 'MAIN_CONDITIONS', 18, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Lepingust tulenevaid Laenusaaja kohustusi tagava vara väärtus väheneb oluliselt ning Laenusaaja ja Laenuandja ei jõua kokkuleppele Laenu täiendava tagamise osas;')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER_RIGHTS_PARAGRAPH_1_2', 12.0, 0, 'MAIN_CONDITIONS', 19, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenusaaja ei täida kohaselt Lepingust tulenevaid kohustusi või mõnda neist ning jätkab kohustuse mittetäitmist ka pärast ${loan.maxAllowedDaysAfterNotification} päeva möödumist Laenuandjalt vastavasisulise kirjaliku teatise saamisest.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'COLLATERAL_PARAGRAPH_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 20, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Tagatised</b>')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'COLLATERAL_PARAGRAPH_1', 12.0, 0, 'MAIN_CONDITIONS', 21, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenusaaja vastutab Lepingust tulenevate kohustuste täitmise eest kogu oma varaga.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'ARGUMENT_PARAGRAPH_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 22, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Vaidluste lahendamise kord</b>')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'ARGUMENT_PARAGRAPH_1', 12.0, 0, 'MAIN_CONDITIONS', 23, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Lepingust tulenevad ja sellega seotud vaidlused püüavad Pooled lahendada läbirääkimiste teel. Kui vaidlust ei õnnestu lahendada Poolte läbirääkimiste teel, on Pooltel õigus pöörduda vaidluse lahendamiseks maakohtusse vastavalt Eesti Vabariigis kehtivatele õigusaktidele.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'CONTRACT_ENACTMENT_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 24, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Lepingu jõustumine</b>')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'CONTRACT_ENACTMENT_1', 12.0, 0, 'MAIN_CONDITIONS', 25, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Leping jõustub alates Lepingu allkirjastamise hetkest.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'CONTRACT_INFO_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 26, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Lõppsätted</b>')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'CONTRACT_INFO_1', 12.0, 0, 'MAIN_CONDITIONS', 27, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Leping on koostatud ja alla kirjutatud eesti keeles kahes (2) võrdset juriidilist jõudu omavas identses eksemplaris, millest üks jääb Laenuandjale ja teine Laenusaajale.')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'BORROWER_NAME', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${borrower.name}')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'BORROWER_ADDRESS', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${borrower.address}')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'BORROWER_PHONE', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${borrower.phone}')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER_NAME', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${lender.name}')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER_ADDRESS', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${lender.address}')
    ),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER_PHONE', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${lender.phone}')
    )
;