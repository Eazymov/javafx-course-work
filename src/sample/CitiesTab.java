package sample;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class CitiesTab {
    public void CitiesTab(TabPane tabPane) {
        Tab tab = new Tab("Cities list");

        tab.setClosable(false);

        tabPane.getTabs().add(tab);
    }
}
