package generate.pdf.openpdf.enums;

public enum LanguageCode {

    ET,
    FI,
    SV,
    RU,
    LV,
    LT,
    DE,
    ES,
    PL,

    // Next values are for testing purposes
    XX,
    YY;

    @Override
    public String toString() {
        // According to ISO 639-1 standard, the language codes need to be in lower case.
        return this.name().toLowerCase();
    }
}
