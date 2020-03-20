package generate.pdf.openpdf.enums;

public enum TemplateCode {

    PRIVATE_SMALL_LOAN_CONTRACT_EE,
    PRIVATE_CAR_LOAN_CONTRACT_EE,
    EDITABLE_FORM_EE,

    // This value is for testing purposes
    TEMPLATE_CODE_FOR_TESTING;

    @Override
    public String toString() {
        return this.name();
    }
}
