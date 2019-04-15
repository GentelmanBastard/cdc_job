package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Controller {
    @FXML
    private TextField date;
    @FXML
    private RadioButton date_radio;
    @FXML
    private RadioButton cdc_radio;
    @FXML
    private Button calculate;
    @FXML
    private Button clear_input;
    @FXML
    private Button clear_log;
    @FXML
    private TextArea log;

    private void logMessageToFile(String message) {
//        Dopytać Pana Wiewióra o chuj tu chodzi
        try {
            File file = new File("log_"+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".txt");
            if (!file.exists()){
                Files.createFile(file.toPath());
            }
            Files.write(file.toPath(), message.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logMessage(String msg) {
        logMessageToFile(msg);
        log.appendText(msg);
    }

    private void throwErrorAndClearInput(Alert alert){
        alert.showAndWait();
        date.clear();
        date.requestFocus();
    }

    public void onButtonClicked() {
        //        Dopytać Pana Wiewióra o chuj tu chodzi
        Alert alert_format = new Alert(Alert.AlertType.INFORMATION, "Wrong date format. Should be YYYY-MM-DD");
        Alert alert_format_cdc = new Alert(Alert.AlertType.INFORMATION, "Wrong CDC format. Should consist only of digits");
        String date_string = date.getText();
        String startDate = "1992-10-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start_date = LocalDate.parse(startDate, formatter);

        if (calculate.isFocused() && cdc_radio.isSelected()) {
            try {
                LocalDate past_date = LocalDate.parse(date_string, formatter);
                long result = ChronoUnit.DAYS.between(start_date, past_date);
                logMessage("CDC: " + result + "\n");
            } catch (DateTimeParseException e) {
                throwErrorAndClearInput(alert_format);

            }
        } else if (calculate.isFocused() && date_radio.isSelected()) {
            try {
                int cdc = Integer.parseInt(date_string);
                LocalDate result = start_date.plusDays(cdc);
                logMessage("Date: " + result.toString() + "\n");
            } catch (NumberFormatException e) {
                throwErrorAndClearInput(alert_format_cdc);
            }
        } else if (clear_input.isFocused()) {
            date.clear();
        } else if (clear_log.isFocused()) {
            log.clear();
        }
    }
}
