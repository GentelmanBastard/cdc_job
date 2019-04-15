package sample;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private Button calculate_cdc;
    @FXML
    private Button calculate_date;
    @FXML
    private Button clear_input;
    @FXML
    private Button clear_log;
    @FXML
    private TextArea log;

    public void onButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Wrong date format. Should be YYYY-MM-DD");
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Wrong input value and expected result");
        String date_string = date.getText();
        String startDate = "1992-10-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start_date = LocalDate.parse(startDate, formatter);
        if (calculate_cdc.isFocused() && date_radio.isSelected()) {
            try {
                LocalDate past_date = LocalDate.parse(date_string, formatter);
//                LocalDate localDate = LocalDate.now();
                long result = ChronoUnit.DAYS.between(start_date, past_date);
                log.appendText("CDC: " + result + "\n");
            } catch (DateTimeParseException e) {
                alert.showAndWait();
                date.clear();
                date.requestFocus();
            }
        } else if (calculate_date.isFocused() && cdc_radio.isFocused()){
            try{
                int cdc = Integer.parseInt(date_string);
                LocalDate result = start_date.plusDays(cdc);
                System.out.println(result);
                log.appendText("Date: " + result.toString() + "\n");
            }catch (Exception e){}
        } else if((calculate_cdc.isFocused() && cdc_radio.isFocused()) || (calculate_date.isFocused() && date_radio.isFocused())){
            alert2.showAndWait();
        } else if (clear_input.isFocused()) {
            date.clear();
        } else if (clear_log.isFocused()) {
            log.clear();
        }
    }
}
