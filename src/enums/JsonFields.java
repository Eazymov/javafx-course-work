package enums;

public enum JsonFields {
    FIRST_CITY_NAME("firstCityName"),
    CITIES("cities"),
    WAYS("ways");

    private final String text;

    JsonFields(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
