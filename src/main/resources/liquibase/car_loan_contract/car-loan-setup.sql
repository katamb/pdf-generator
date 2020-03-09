SET search_path=pdf_generator;

INSERT INTO
    text_block (text_block_value)
VALUES
    ('<b>Eritingimused</b>'),
    ('Laenusaaja on kohustatud sõlmima autole liiklus- ja kaskokindlustuslepingu.')
;

INSERT INTO
    template_text (template_code, language_code, text_block_name, text_size, horizontal_alignment, text_group_code, ordering, numbering, numbering_level, text_block_id)
VALUES
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LOAN_CONTRACT', 12.0, 1, 'HEADING', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='LAENULEPING')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LENDER', 12.0, 1, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Laenuandja</b>')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'BORROWER', 12.0, 1, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Laenusaaja</b>')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'NAME', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Nimi')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'ID_CODE', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Isikukood')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'ADDRESS', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Aadress')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'PHONE', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Telefon')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'WORDING_INFO', 12.0, 0, 'GENERAL_INFO', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Laenuandjale ja laenusaajale on edaspidi ühiselt viidatud kui Pooled.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LOAN_SPECIAL_CONDITIONS_PARAGRAPH_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 0, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Eritingimused</b>')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LOAN_SPECIAL_CONDITIONS_PARAGRAPH_1', 12.0, 0, 'MAIN_CONDITIONS', 1, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenusaaja on kohustatud sõlmima autole liiklus- ja kaskokindlustuslepingu.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LOAN_TRANSFER_PARAGRAPH_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 2, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Laen ja selle üleandmine</b>')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LOAN_TRANSFER_PARAGRAPH_1', 12.0, 0, 'MAIN_CONDITIONS', 3, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenuandja annab Laenusaajale laenu ${loan.principalAmount} ${loan.currency} (edaspidi Laen).')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LOAN_TRANSFER_PARAGRAPH_2', 12.0, 0, 'MAIN_CONDITIONS', 4, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenusaaja on kohustatud maksma laenuandjale ${loan.conclusionFee} ${loan.currency}, mis arvestatakse maha laenu summast.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LOAN_TRANSFER_PARAGRAPH_3', 12.0, 0, 'MAIN_CONDITIONS', 5, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenuandja kohustub Laenusaajale Laenu üle andma hiljemalt ${loan.transferDate}. Laenu üleandmine toimub Laenu kandmisega Laenusaaja poolt määratud arvelduskontole.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 6, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Intress ja laenu tagastamine</b>')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_1', 12.0, 0, 'MAIN_CONDITIONS', 7, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laen on antud tähtajaliselt. Laenusaaja kohustub Laenu tagasi maksma hiljemalt ${loan.lastInstallment}.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_2', 12.0, 0, 'MAIN_CONDITIONS', 8, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenusaaja tagastab Laenuandjale Laenu Laenuandja arvelduskontole ${lender.iban}.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_3', 12.0, 0, 'MAIN_CONDITIONS', 9, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenusaaja maksab Laenuandjale Laenu tagastamata osalt intressi ${loan.interestRate}% aastas.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4', 12.0, 0, 'MAIN_CONDITIONS', 10, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Kui Laenusaaja teeb Laenuandjale makse, millest ei piisa kõigi Lepingu alusel võlgnetavate summade tasumiseks, arvestatakse makse:')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4_1', 12.0, 0, 'MAIN_CONDITIONS', 11, true, 2,
        (SELECT text_block_id from text_block where text_block_value='esimeses järjekorras võlgnetava intressi katteks;')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4_2', 12.0, 0, 'MAIN_CONDITIONS', 12, true, 2,
        (SELECT text_block_id from text_block where text_block_value='teises järjekorras võlgnetava viivise katteks')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4_3', 12.0, 0, 'MAIN_CONDITIONS', 13, true, 2,
        (SELECT text_block_id from text_block where text_block_value='kolmandas järjekorras võlgnetava põhisumma katteks;')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4_4', 12.0, 0, 'MAIN_CONDITIONS', 14, true, 2,
        (SELECT text_block_id from text_block where text_block_value='neljandas järjekorras muude Lepingust tulenevate kohustuste katteks.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_5', 12.0, 0, 'MAIN_CONDITIONS', 15, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenusaajal on õigus tagastada kogu Laen enne Lepingu punktis 2.1 nimetatud maksetähtpäeva, teatades sellest Laenuandjale kirjalikult ${loan.fullRepaymentDaysAdvanceNotification} päeva ette.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'PENALTY_PARAGRAPH_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 16, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Viivis</b>')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'PENALTY_PARAGRAPH_1', 12.0, 0, 'MAIN_CONDITIONS', 17, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenu tagastamisega viivitamisel on Laenuandjal õigus nõuda Laenusaajalt viivise tasumist ${loan.penaltyRate}% päevas sissenõutavaks muutunud summalt iga tasumisega viivitatud päeva eest. Tasumata intressilt või viiviselt viivist ei arvestata.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LENDER_RIGHTS_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 18, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Laenuandja õigus leping üles öelda</b>')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LENDER_RIGHTS_PARAGRAPH_1', 12.0, 0, 'MAIN_CONDITIONS', 19, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenuandjal on õigus Leping üles öelda ja nõuda Laenu kohest tagastamist, kui:')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LENDER_RIGHTS_PARAGRAPH_1_1', 12.0, 0, 'MAIN_CONDITIONS', 20, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Lepingust tulenevaid Laenusaaja kohustusi tagava vara väärtus väheneb oluliselt ning Laenusaaja ja Laenuandja ei jõua kokkuleppele Laenu täiendava tagamise osas;')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LENDER_RIGHTS_PARAGRAPH_1_2', 12.0, 0, 'MAIN_CONDITIONS', 21, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenusaaja ei täida kohaselt Lepingust tulenevaid kohustusi või mõnda neist ning jätkab kohustuse mittetäitmist ka pärast ${loan.maxAllowedDaysAfterNotification} päeva möödumist Laenuandjalt vastavasisulise kirjaliku teatise saamisest.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'COLLATERAL_PARAGRAPH_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 22, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Tagatised</b>')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'COLLATERAL_PARAGRAPH_1', 12.0, 0, 'MAIN_CONDITIONS', 23, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Laenusaaja vastutab Lepingust tulenevate kohustuste täitmise eest kogu oma varaga.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'ARGUMENT_PARAGRAPH_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 24, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Vaidluste lahendamise kord</b>')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'ARGUMENT_PARAGRAPH_1', 12.0, 0, 'MAIN_CONDITIONS', 25, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Lepingust tulenevad ja sellega seotud vaidlused püüavad Pooled lahendada läbirääkimiste teel. Kui vaidlust ei õnnestu lahendada Poolte läbirääkimiste teel, on Pooltel õigus pöörduda vaidluse lahendamiseks maakohtusse vastavalt Eesti Vabariigis kehtivatele õigusaktidele.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'CONTRACT_ENACTMENT_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 26, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Lepingu jõustumine</b>')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'CONTRACT_ENACTMENT_1', 12.0, 0, 'MAIN_CONDITIONS', 27, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Leping jõustub alates Lepingu allkirjastamise hetkest.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'CONTRACT_INFO_HEADING', 12.0, 0, 'MAIN_CONDITIONS', 28, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>Lõppsätted</b>')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'CONTRACT_INFO_1', 12.0, 0, 'MAIN_CONDITIONS', 29, true, 1,
        (SELECT text_block_id from text_block where text_block_value='Leping on koostatud ja alla kirjutatud eesti keeles kahes (2) võrdset juriidilist jõudu omavas identses eksemplaris, millest üks jääb Laenuandjale ja teine Laenusaajale.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'BORROWER_NAME', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${borrower.name}')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'BORROWER_ADDRESS', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${borrower.address}')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'BORROWER_PHONE', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${borrower.phone}')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LENDER_NAME', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${lender.name}')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LENDER_ADDRESS', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${lender.address}')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'LENDER_PHONE', 12.0, 0, 'PARTIES', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${lender.phone}')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'FOOTER_TEXT_1', 12.0, 0, 'FOOTER', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Created @ TalTech 2020')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'PAGE_NR', 10.0, 2, 'FOOTER', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Page ${pagenumber} of ')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'ALL_PAGES', 10.0, 0, 'FOOTER', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${all.pages}')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'HEADER_TEXT_1', 14.0, 2, 'HEADER', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Laenuleping nr. ${loan.contractNumber}')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'PAYMENT_NR', 8.0, 0, 'SCHEDULE', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Makse nr.')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'DATE', 8.0, 0, 'SCHEDULE', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Kuupäev')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'CREDIT_SUM', 8.0, 0, 'SCHEDULE', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Krediidisumma')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'INTEREST', 8.0, 0, 'SCHEDULE', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Intress')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'ADM_FEE', 8.0, 0, 'SCHEDULE', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Lepingu haldustasu')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'PAYMENT_SUM', 8.0, 0, 'SCHEDULE', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='Makse kokku')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'SCHEDULE_YEARS', 14.0, 1, 'SCHEDULE', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='<b>${scheduleYears.year}</b>')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'SCHEDULE_PAYMENT_DATE', 8.0, 0, 'SCHEDULE', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${scheduleYears.scheduleLines.paymentDate}')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'SCHEDULE_PRINCIPAL_AMOUNT', 8.0, 0, 'SCHEDULE', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${scheduleYears.scheduleLines.principal}')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'SCHEDULE_INTEREST_AMOUNT', 8.0, 0, 'SCHEDULE', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${scheduleYears.scheduleLines.interest}')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'SCHEDULE_ADMINISTRATION_FEE', 8.0, 0, 'SCHEDULE', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${scheduleYears.scheduleLines.administrationFee}')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'SCHEDULE_PAYMENT_NR', 8.0, 0, 'SCHEDULE', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${scheduleYears.scheduleLines.paymentNr}')
    ),
    ('PRIVATE_CAR_LOAN_CONTRACT_EE', 'et', 'SCHEDULE_PAYMENT_SUM', 8.0, 0, 'SCHEDULE', null, false, null,
        (SELECT text_block_id from text_block where text_block_value='${scheduleYears.scheduleLines.payment}')
    )
;