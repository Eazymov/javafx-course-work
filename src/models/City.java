package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class City {
    private SimpleStringProperty name;
    private SimpleIntegerProperty posX;
    private SimpleIntegerProperty posY;

    public City(String name, int posX, int posY) {
        this.name = new SimpleStringProperty(name);
        this.posX = new SimpleIntegerProperty(posX);
        this.posY = new SimpleIntegerProperty(posY);
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String fName) {
        this.name.set(fName);
    }

    public int getPosX() {
        return this.posX.get();
    }

    public void setPosX(int posX) {
        this.posX.set(posX);
    }

    public int getPosY() {
        return this.posY.get();
    }

    public void setPosY(int posY) {
        this.posY.set(posY);
    }
}
