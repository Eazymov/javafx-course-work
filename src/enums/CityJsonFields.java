package enums;

public enum CityJsonFields {
    NAME("name"),
    POS_X("posX"),
    POS_Y("posY");

    private final String text;

    CityJsonFields(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
