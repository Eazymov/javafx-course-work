package tabs;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HelpTab extends Tab {
    private final Font labelFont = new Font("Arial", 20);
    private final Font descriptionFont = new Font("Arial", 14);

    public HelpTab() {
        this.setText("Помощь");
        this.setClosable(false);
        this.setContent(this.createLayout());
    }

    private VBox createLayout() {
        VBox vBox = new VBox();

        vBox.setSpacing(20);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(
            this.createCitiesListHelp(),
            this.createMapHelp(),
            this.createRoutesHelp()
        );

        return vBox;
    }

    private VBox createHelp(String labelText, String descriptionText) {
        VBox vBox = new VBox();
        Label label = new Label(labelText);
        Label description = new Label(descriptionText);

        label.setFont(labelFont);
        label.setPadding(new Insets(0, 0, 0, 27));
        description.setWrapText(true);
        description.setLineSpacing(3.5);
        description.setFont(descriptionFont);

        vBox.setSpacing(10);
        vBox.getChildren().addAll(label, description);

        return vBox;
    }

    private VBox createCitiesListHelp() {
        return this.createHelp(
            "Список городов",
            "       На этой вкладке можно задать список городов. " +
                "В нижней части окна находится форма добавления города. " +
                "Если необходимо удалить город то нужно выбрать его в таблице и нажать кнопку «Удалить выбранный». " +
                "\n       Список городов можно сохранить в файл, для этого нужно нажать на кнопку «Загрузить список». " +
                "В дальнейшем можно загрузить данные из этого файла с помощью кнопки «Сохранить список»."
        );
    }

    private VBox createMapHelp() {
        return this.createHelp(
            "Карта",
            "       На этой вкладке показана карта на которой расположены все " +
                "указанные города в соответствии с их координатами."
        );
    }

    private VBox createRoutesHelp() {
        return this.createHelp(
            "Маршрут",
            "       На этой вкладке строится маршрут между городами. Для " +
                "этого в верхнем выпадающем списке нужно выбрать город с которого " +
                "начинается путь, и нажать кнопку «Построить маршрут». Сохранить " +
                "полученный маршрут в файл можно с помощью кнопки «Сохранить»"
        );
    }
}
