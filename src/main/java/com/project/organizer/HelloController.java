package com.project.organizer;

import com.project.organizer.dao.LiteratureDao;
import com.project.organizer.dao.LiteratureDaoImpl;
import com.project.organizer.mediator.*;
import com.project.organizer.model.Literature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.List;

public class HelloController implements Initializable, Mediator {
    @FXML
    private ChoiceBox<String> choiceBox1;
    @FXML
    private ChoiceBox<String> choiceBox2;
    @FXML
    public TableView<Literature> literatureTable;
    public ScrollPane viewpane;
    private ObservableList list1 = FXCollections.observableArrayList();
    private ObservableList list2 = FXCollections.observableArrayList();
    private Map<String, Colleague> mapMembers = new HashMap<>();
    private Colleague currentcolleague;
    private LiteratureDao literatureDao;
    @FXML
    private TableColumn<Literature, String> titleColumn;
    @FXML
    private TableColumn<Literature, String> authorColumn;
    @FXML
    private TableColumn<Literature, Integer> yearColumn;
    @FXML
    private TableColumn<Literature, String> descriptionColumn;

    // записать map в качестве ключа и их значения (для сравнения)
    public HelloController() {
        mapMembers.put("Школьник", new Schoolboy(this, "Школьник"));
        mapMembers.put("Студент", new Student(this, "Студент", "student"));
        mapMembers.put("Научный работник", new Scientist(this));
        this.literatureDao = new LiteratureDaoImpl();
    }

    // 1 изначально вызывается добавление: школьника, студента, научного сотрудника
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addChoiceBox();
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadLiterature();
    }

    // 4 вызываем по ключу  реализацию если они одинаковые
    public void onStart(String genre) {
        currentcolleague = mapMembers.get(choiceBox1.getValue());
        if (currentcolleague == null)
            currentcolleague = mapMembers.get("Школьник");
        currentcolleague.notifyMediator("load");
    }

    // 2 после нажатии на кнопку "выбрать жанр" в switch перебираем значение полученного пользователя
    // и в зависимости от пользователя выбираем соответсвующий жанр
    @FXML
    private void addChoiceBox2() {
        choiceBox2.getItems().clear();
        list2.clear();

        if (!choiceBox1.getValue().isEmpty()) {
            switch (choiceBox1.getValue()) {
                case "Школьник":
                    list2.add("Школьная программа");
                    list2.add("Художественная литература");
                    list2.add("Дополнительная учебная литература");
                    break;
                case "Студент":
                    list2.add("Документальная проза");
                    list2.add("Мемуарная литература");
                    list2.add("Техническая литература");
                    break;
                case "Научный работник":
                    list2.add("Справочная литература");
                    list2.add("Научная и научно-популярная литература");
                    break;
                default:
                    list2.add("");
                    break;
            }
        }
        choiceBox2.getItems().addAll(list2);
    }

    // 3 нажатие кнопку найти вызов метода onStart
    @FXML
    public void find() {
        // получили жанр
        if (choiceBox2.getValue().isEmpty()) {
            System.out.println("пусто");
        } else {
            System.out.println(choiceBox2.getValue());
        }

        onStart(choiceBox2.getValue());
    }

    private void addChoiceBox() {
        list1.add("Школьник");
        list1.add("Студент");
        list1.add("Научный работник");

        choiceBox1.getItems().addAll(list1);
    }

    private void loadLiterature() {
        List<Literature> literatureList = literatureDao.findAll();
        ObservableList<Literature> observableList = FXCollections.observableArrayList(literatureList);
        literatureTable.setItems(observableList);
    }

    // 6 создаем таблицу и записаваем в нее данные
    @Override
    public void setView(ArrayList<Literature> message) {
        TableColumn Col0 = new TableColumn("№");
        Col0.setMinWidth(5);
        Col0.setCellValueFactory(new PropertyValueFactory<Literature, Integer>("id"));

        TableColumn Col1 = new TableColumn("Жанр");
        Col1.setMinWidth(15);
        Col1.setCellValueFactory(new PropertyValueFactory<Literature, String>("genre"));

        TableColumn Col2 = new TableColumn("Автор");
        Col2.setMinWidth(30);
        Col2.setCellValueFactory(new PropertyValueFactory<Literature, String>("author"));

        TableColumn Col3 = new TableColumn("Название");
        Col3.setMinWidth(100);
        Col3.setCellValueFactory(new PropertyValueFactory<Literature, String>("name"));

        TableColumn Col4 = new TableColumn("Дата публикации");
        Col4.setMinWidth(150);
        Col4.setCellValueFactory(new PropertyValueFactory<Literature, String>("datePublication"));

        TableColumn Col5 = new TableColumn("Доп. Информация");
        Col5.setMinWidth(150);
        Col5.setCellValueFactory(new PropertyValueFactory<Literature, String>("otherInfo"));

        literatureTable.getColumns().clear();
        literatureTable.getColumns().addAll(Col0, Col1, Col2, Col3, Col4, Col5);
    }

    // 6 если пользователь научный сотрудник устанавлием данное view
    @Override
    public void setView(Node control) {
        Group root = new Group();
        ScrollBar sc = new ScrollBar();

        sc.setLayoutX(control.getLayoutX());
        control.setLayoutX(control.getLayoutX() + sc.getWidth());

        sc.setMin(0);
        sc.setValue(50);
        sc.setMax(100);
        sc.setOrientation(Orientation.VERTICAL);

        root.getChildren().addAll(control, sc);

        viewpane.setContent(control);
    }

    @Override
    public void notify(Colleague sender, String event) {
        // Реализация логики уведомления, например, обновление интерфейса или обработка событий
        System.out.println("Mediator received event: " + event + " from: " + sender.getClass().getSimpleName());
    }
}