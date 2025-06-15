package com.project.organizer.controller;

import com.project.organizer.model.StudyMaterial;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {
    @FXML private TableView<StudyMaterial> materialsTable;
    @FXML private TableColumn<StudyMaterial, String> titleColumn;
    @FXML private TableColumn<StudyMaterial, String> disciplineColumn;
    @FXML private TableColumn<StudyMaterial, LocalDateTime> dateColumn;
    
    @FXML private TextField searchField;
    @FXML private ComboBox<String> disciplineFilter;
    @FXML private TextField tagFilter;
    
    @FXML private TextField titleField;
    @FXML private TextField disciplineField;
    @FXML private TextArea descriptionArea;
    @FXML private TextField tagField;
    
    private ObservableList<StudyMaterial> materials = FXCollections.observableArrayList();
    private ObservableList<String> disciplines = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Инициализация таблицы
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        disciplineColumn.setCellValueFactory(new PropertyValueFactory<>("discipline"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        
        materialsTable.setItems(materials);
        
        // Инициализация фильтров
        disciplineFilter.setItems(disciplines);
        
        // Добавление тестовых данных
        StudyMaterial m1 = new StudyMaterial();
        m1.setTitle("Математика. Лекции");
        m1.setDiscipline("Математика");
        m1.setDescription("Конспект лекций по математике");
        m1.addTag("лекции");
        m1.addTag("математика");
        materials.add(m1);

        StudyMaterial m2 = new StudyMaterial();
        m2.setTitle("История России. Учебник");
        m2.setDiscipline("История");
        m2.setDescription("Учебник по истории России");
        m2.addTag("учебник");
        m2.addTag("история");
        materials.add(m2);

        StudyMaterial m3 = new StudyMaterial();
        m3.setTitle("Физика. Практика");
        m3.setDiscipline("Физика");
        m3.setDescription("Практические задания по физике");
        m3.addTag("практика");
        m3.addTag("физика");
        materials.add(m3);

        updateDisciplines();
        
        // Обработчики событий
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterMaterials());
        disciplineFilter.valueProperty().addListener((obs, oldVal, newVal) -> filterMaterials());
        tagFilter.textProperty().addListener((obs, oldVal, newVal) -> filterMaterials());
    }

    @FXML
    private void handleAddMaterial() {
        StudyMaterial material = new StudyMaterial();
        material.setTitle(titleField.getText());
        material.setDiscipline(disciplineField.getText());
        material.setDescription(descriptionArea.getText());
        
        // Добавление тегов
        String[] tags = tagField.getText().split(",");
        for (String tag : tags) {
            material.addTag(tag.trim());
        }
        
        materials.add(material);
        updateDisciplines();
        clearInputFields();
    }

    @FXML
    private void handleAddFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        
        File file = fileChooser.showOpenDialog(materialsTable.getScene().getWindow());
        if (file != null) {
            StudyMaterial selectedMaterial = materialsTable.getSelectionModel().getSelectedItem();
            if (selectedMaterial != null) {
                selectedMaterial.addFile(file.getAbsolutePath());
            }
        }
    }

    @FXML
    private void handleAddLink() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Добавить ссылку");
        dialog.setHeaderText("Введите URL");
        
        dialog.showAndWait().ifPresent(link -> {
            StudyMaterial selectedMaterial = materialsTable.getSelectionModel().getSelectedItem();
            if (selectedMaterial != null) {
                selectedMaterial.addLink(link);
            }
        });
    }

    @FXML
    private void handleDeleteMaterial() {
        StudyMaterial selected = materialsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            materials.remove(selected);
            materialsTable.getSelectionModel().clearSelection();
        }
    }

    private void filterMaterials() {
        String searchText = searchField.getText().toLowerCase();
        String selectedDiscipline = disciplineFilter.getValue();
        String tagText = tagFilter.getText().toLowerCase();
        
        List<StudyMaterial> filtered = materials.stream()
            .filter(material -> 
                material.getTitle().toLowerCase().contains(searchText) &&
                (selectedDiscipline == null || material.getDiscipline().equals(selectedDiscipline)) &&
                (tagText.isEmpty() || material.getTags().stream()
                    .anyMatch(tag -> tag.toLowerCase().contains(tagText)))
            )
            .collect(Collectors.toList());
        
        materialsTable.setItems(FXCollections.observableArrayList(filtered));
    }

    private void updateDisciplines() {
        List<String> uniqueDisciplines = materials.stream()
            .map(StudyMaterial::getDiscipline)
            .distinct()
            .collect(Collectors.toList());
        
        disciplines.setAll(uniqueDisciplines);
    }

    private void clearInputFields() {
        titleField.clear();
        disciplineField.clear();
        descriptionArea.clear();
        tagField.clear();
    }
} 