package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.List;

public class BirthdayController {
    @FXML private TextField nameField;
    @FXML private DatePicker datePicker;
    @FXML private TableView<Birthday> birthdayTable;
    @FXML private TableColumn<Birthday, String> nameColumn;
    @FXML private TableColumn<Birthday, LocalDate> dateColumn;

    private BirthdayDAO birthdayDAO = new BirthdayDAO();
    private ObservableList<Birthday> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        loadBirthdays();

        List<Birthday> todayList = birthdayDAO.getTodaysBirthdays();
        if (!todayList.isEmpty()) {
            StringBuilder message = new StringBuilder("আজ জন্মদিন: ");
            for (Birthday b : todayList) {
                message.append(b.getName()).append(", ");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("জন্মদিনের শুভেচ্ছা");
            alert.setContentText(message.toString());
            alert.showAndWait();
        }
    }

    private void loadBirthdays() {
        data.setAll(birthdayDAO.getAllBirthdays());
        birthdayTable.setItems(data);
    }

    @FXML
    private void handleAdd() {
        Birthday b = new Birthday(0, nameField.getText(), datePicker.getValue());
        birthdayDAO.addBirthday(b);
        loadBirthdays();
    }

    @FXML
    private void handleUpdate() {
        Birthday selected = birthdayTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setName(nameField.getText());
            selected.setBirthDate(datePicker.getValue());
            birthdayDAO.updateBirthday(selected);
            loadBirthdays();
        }
    }

    @FXML
    private void handleDelete() {
        Birthday selected = birthdayTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            birthdayDAO.deleteBirthday(selected.getId());
            loadBirthdays();
        }
    }
}