package generate.pdf.openpdf.enums;

public enum TemplateCode {

    PRIVATE_SMALL_LOAN_CONTRACT_EE,
    PRIVATE_CAR_LOAN_CONTRACT_EE,
    EDITABLE_FORM_EE,
    EMPLOYMENT_CONTRACT_EN;

    @Override
    public String toString() {
        return this.name();
    }

}
