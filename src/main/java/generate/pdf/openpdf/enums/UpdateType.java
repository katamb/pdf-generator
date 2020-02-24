package generate.pdf.openpdf.enums;

public enum UpdateType {

    UPDATE_ALL,
    UPDATE_ONLY_CURRENT,
    CONFIRM_UPDATE;

    @Override
    public String toString() {
        return this.name();
    }
}
