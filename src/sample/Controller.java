package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Controller {
    @FXML
    private TextField date;
    @FXML
    private Button calculate;
    @FXML
    private Button clear_input;
    @FXML
    Button clear_log;
    @FXML
    private TextArea log;

    public void onButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Wrong date format. Should be YYYY-MM-DD");
        String date_string = date.getText();
        if (calculate.isFocused()) {
            try {
//                LocalDate localDate = LocalDate.now();
                String startDate = "1992-10-01";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate past_date = LocalDate.parse(date_string, formatter);
                LocalDate start_date = LocalDate.parse(startDate, formatter);
                long result = ChronoUnit.DAYS.between(start_date, past_date);
                log.appendText("CDC: " + result + "\n");
            } catch (DateTimeParseException e) {
                alert.showAndWait();
                date.clear();
                date.requestFocus();
            }
        } else if (clear_input.isFocused()) {
            date.clear();
        } else if (clear_log.isFocused()) {
            log.clear();
        }
    }
}
