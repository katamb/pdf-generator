SET search_path=pdf_generator;

INSERT INTO
    text_block (text_block_value)
VALUES
    ('LAENULEPING'),
    ('Laenuandja'),
    ('Laenusaaja'),
    ('Nimi'),
    ('Isikukood'),
    ('Aadress'),
    ('Telefon'),
    ('Laenuandjale ja laenusaajale on edaspidi ühiselt viidatud kui Pooled.'),
    ('**Laen ja selle üleandmine**'),
    ('Laenuandja annab Laenusaajale laenu ${loan.principalAmount} ${loan.currency} (edaspidi Laen).'),
    ('Laenuandja kohustub Laenusaajale Laenu üle andma hiljemalt ${loan.transferDate}. Laenu üleandmine toimub Laenu kandmisega Laenusaaja poolt määratud arvelduskontole.'),
    ('**Intress ja laenu tagastamine**'),
    ('Laen on antud tähtajaliselt. Laenusaaja kohustub Laenu tagasi maksma hiljemalt ${loan.lastInstallment}.'),
    ('Laenusaaja tagastab Laenuandjale Laenu Laenuandja arvelduskontole ${lender.iban}.'),
    ('Laenusaaja maksab Laenuandjale Laenu tagastamata osalt intressi ${loan.interestRate}% aastas.'),
    ('Kui Laenusaaja teeb Laenuandjale makse, millest ei piisa kõigi Lepingu alusel võlgnetavate summade tasumiseks, arvestatakse makse:'),
    ('esimeses järjekorras võlgnetava intressi katteks;'),
    ('teises järjekorras võlgnetava viivise katteks'),
    ('kolmandas järjekorras võlgnetava põhisumma katteks;'),
    ('neljandas järjekorras muude Lepingust tulenevate kohustuste katteks.'),
    ('Laenusaajal on õigus tagastada kogu Laen enne Lepingu punktis 2.1 nimetatud maksetähtpäeva, teatades sellest Laenuandjale kirjalikult ${loan.fullRepaymentDaysAdvanceNotification} päeva ette.'),
    ('**Viivis**'),
    ('Laenu tagastamisega viivitamisel on Laenuandjal õigus nõuda Laenusaajalt viivise tasumist ${loan.penaltyRate}% päevas sissenõutavaks muutunud summalt iga tasumisega viivitatud päeva eest. Tasumata intressilt või viiviselt viivist ei arvestata.'),
    ('**Laenuandja õigus leping üles öelda**'),
    ('Laenuandjal on õigus Leping üles öelda ja nõuda Laenu kohest tagastamist, kui:'),
    ('Lepingust tulenevaid Laenusaaja kohustusi tagava vara väärtus väheneb oluliselt ning Laenusaaja ja Laenuandja ei jõua kokkuleppele Laenu täiendava tagamise osas;'),
    ('Laenusaaja ei täida kohaselt Lepingust tulenevaid kohustusi või mõnda neist ning jätkab kohustuse mittetäitmist ka pärast ${loan.maxAllowedDaysAfterNotification} päeva möödumist Laenuandjalt vastavasisulise kirjaliku teatise saamisest.'),
    ('**Tagatised**'),
    ('Laenusaaja vastutab Lepingust tulenevate kohustuste täitmise eest kogu oma varaga.'),
    ('**Vaidluste lahendamise kord**'),
    ('Lepingust tulenevad ja sellega seotud vaidlused püüavad Pooled lahendada läbirääkimiste teel. Kui vaidlust ei õnnestu lahendada Poolte läbirääkimiste teel, on Pooltel õigus pöörduda vaidluse lahendamiseks maakohtusse vastavalt Eesti Vabariigis kehtivatele õigusaktidele.'),
    ('**Lepingu jõustumine**'),
    ('Leping jõustub alates Lepingu allkirjastamise hetkest.'),
    ('**Lõppsätted**'),
    ('Leping on koostatud ja alla kirjutatud eesti keeles kahes (2) võrdset juriidilist jõudu omavas identses eksemplaris, millest üks jääb Laenuandjale ja teine Laenusaajale.'),
    ('${borrower.name}'),
    ('${borrower.address}'),
    ('${borrower.phone}'),
    ('${lender.name}'),
    ('${lender.address}'),
    ('${lender.phone}')
;

INSERT INTO
    template_text (template_code, language_code, text_block_name, text_block_id)
VALUES
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LOAN_CONTRACT', 10001),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER', 10002),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'BORROWER', 10003),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'NAME', 10004),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'ID_CODE', 10005),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'ADDRESS', 10006),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'PHONE', 10007),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'WORDING_INFO', 10008),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LOAN_TRANSFER_PARAGRAPH_HEADING', 10009),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LOAN_TRANSFER_PARAGRAPH_1', 10010),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LOAN_TRANSFER_PARAGRAPH_2', 10011),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_HEADING', 10012),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_1', 10013),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_2', 10014),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_3', 10015),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4', 10016),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4_1', 10017),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4_2', 10018),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4_3', 10019),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_4_4', 10020),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'INTEREST_AND_LOAN_PARAGRAPH_5', 10021),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'PENALTY_PARAGRAPH_HEADING', 10022),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'PENALTY_PARAGRAPH_1', 10023),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER_RIGHTS_HEADING', 10024),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER_RIGHTS_PARAGRAPH_1', 10025),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER_RIGHTS_PARAGRAPH_1_1', 10026),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER_RIGHTS_PARAGRAPH_1_2', 10027),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'COLLATERAL_PARAGRAPH_HEADING', 10028),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'COLLATERAL_PARAGRAPH_1', 10029),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'ARGUMENT_PARAGRAPH_HEADING', 10030),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'ARGUMENT_PARAGRAPH_1', 10031),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'CONTRACT_ENACTMENT_HEADING', 10032),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'CONTRACT_ENACTMENT_1', 10033),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'CONTRACT_INFO_HEADING', 10034),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'CONTRACT_INFO_1', 10035),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'BORROWER_NAME', 10036),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'BORROWER_ADDRESS', 10037),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'BORROWER_PHONE', 10038),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER_NAME', 10039),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER_ADDRESS', 10040),
    ('PRIVATE_SMALL_LOAN_CONTRACT_EE', 'et', 'LENDER_PHONE', 10041)
;