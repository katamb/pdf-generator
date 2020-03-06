package generate.pdf.openpdf.enums;

public enum TemplateCode {

    CONSUMER_CAR_LOAN_EE,
    CONSUMER_SMALL_LOAN_EE,
    PRIVATE_SMALL_LOAN_CONTRACT_EE,
    PRIVATE_CAR_LOAN_CONTRACT_EE,
    EDITABLE_FORM_EE;

    @Override
    public String toString() {
        return this.name();
    }
}
