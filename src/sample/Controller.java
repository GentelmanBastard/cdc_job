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
    private TextField change_date_area;
    @FXML
    private Button change_date;
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

    String startDate = "1992-10-01";
    Alert alert_format = new Alert(Alert.AlertType.INFORMATION, "Wrong date format. Should be YYYY-MM-DD");
    Alert alert_format_cdc = new Alert(Alert.AlertType.INFORMATION, "Wrong CDC format. Should consist only of digits");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private boolean changeStartDate(String date){
        try {
            startDate = date;
            LocalDate start_date = LocalDate.parse(startDate, formatter).minusDays(1);
            return true;
        }catch(DateTimeParseException e){
            throwErrorAndClearInput(alert_format);
            change_date_area.clear();
            return false;
        }
    }

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
        if(change_date.isFocused() && (change_date_area.getText() != null)){
            if(!changeStartDate(change_date_area.getText())){
                return;
            }
        }
        //        Dopytać Pana Wiewióra o chuj tu chodzi

        String date_string = date.getText();
        LocalDate start_date = LocalDate.parse(startDate, formatter).minusDays(1);

        if (calculate.isFocused() && cdc_radio.isSelected()) {
            try {
                LocalDate past_date = LocalDate.parse(date_string, formatter);
                long result = ChronoUnit.DAYS.between(start_date, past_date);
                logMessage("Input of date: " + date_string + " corresponds to CDC: " + result + "\n");
            } catch (DateTimeParseException e) {
                throwErrorAndClearInput(alert_format);

            }
        } else if (calculate.isFocused() && date_radio.isSelected()) {
            try {
                int cdc = Integer.parseInt(date_string);
                LocalDate result = start_date.plusDays(cdc);
                logMessage("Input of CDC: " + date_string + " corresponds to date: " + result.toString() + "\n");
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
