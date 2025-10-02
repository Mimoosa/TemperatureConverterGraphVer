import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private TextField celsiusField = new TextField();
    private TextField fahrenheitField = new TextField();
    private TextField kelvinField = new TextField();
    private Label resultLabel = new Label();

    private Double celsiusResult = null;
    private Double fahrenheitResult = null;
    private Double kelvinResult = null;
    private String source = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        celsiusField.setPromptText("Enter Celsius");
        fahrenheitField.setPromptText("Enter Fahrenheit");
        kelvinField.setPromptText("Enter Kelvin");

        Button cToFButton = new Button("Celsius to Fahrenheit");
        cToFButton.setOnAction(e -> {
            try {
                double celsius = Double.parseDouble(celsiusField.getText());
                fahrenheitResult = (celsius * 9 / 5) + 32;
                celsiusResult = celsius;
                kelvinResult = celsius + 273.15;
                source = "Celsius";
                resultLabel.setText(String.format("Fahrenheit: %.2f", fahrenheitResult));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid Celsius input!");
            }
        });

        Button fToCButton = new Button("Fahrenheit to Celsius");
        fToCButton.setOnAction(e -> {
            try {
                double fahrenheit = Double.parseDouble(fahrenheitField.getText());
                celsiusResult = (fahrenheit - 32) * 5 / 9;
                fahrenheitResult = fahrenheit;
                kelvinResult = celsiusResult + 273.15;
                source = "Fahrenheit";
                resultLabel.setText(String.format("Celsius: %.2f", celsiusResult));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid Fahrenheit input!");
            }
        });

        Button kToCButton = new Button("Kelvin to Celsius");
        kToCButton.setOnAction(e -> {
            try {
                double kelvin = Double.parseDouble(kelvinField.getText());
                celsiusResult = kelvin - 273.15;
                kelvinResult = kelvin;
                fahrenheitResult = (celsiusResult * 9 / 5) + 32;
                source = "Kelvin";
                resultLabel.setText(String.format("Celsius: %.2f", celsiusResult));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid Kelvin input!");
            }
        });

        Button cToKButton = new Button("Celsius to Kelvin");
        cToKButton.setOnAction(e -> {
            try {
                double celsius = Double.parseDouble(celsiusField.getText());
                kelvinResult = celsius + 273.15;
                celsiusResult = celsius;
                fahrenheitResult = (celsius * 9 / 5) + 32;
                source = "Celsius";
                resultLabel.setText(String.format("Kelvin: %.2f", kelvinResult));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid Celsius input!");
            }
        });

        Button saveButton = new Button("Save to DB");
        saveButton.setOnAction(e -> {
            if (celsiusResult == null && fahrenheitResult == null && kelvinResult == null) {
                resultLabel.setText("No conversion result to save!");
                return;
            }
            Database.saveTemperature(celsiusResult, fahrenheitResult, kelvinResult, source, resultLabel);
        });

        VBox root = new VBox(10,
                celsiusField, cToFButton, cToKButton,
                fahrenheitField, fToCButton,
                kelvinField, kToCButton,
                resultLabel, saveButton);

        Scene scene = new Scene(root, 350, 400);
        stage.setTitle("Temperature Converter");
        stage.setScene(scene);
        stage.show();
    }
}
