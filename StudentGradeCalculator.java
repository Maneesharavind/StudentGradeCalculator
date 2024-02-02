package application;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StudentGradeCalculator extends Application {
    private TextField numSubjectsField;
    private VBox subjectsVBox;
    private Label resultLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Grade Calculator");

        // Initialize components
        numSubjectsField = new TextField();
        subjectsVBox = new VBox(10);
        resultLabel = new Label();

        // Set up layout
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Styling
        layout.setStyle("-fx-background-color: #3498DB; -fx-font-size: 20; -fx-text-fill: white;");

        numSubjectsField.setPromptText("Enter the number of subjects");
        numSubjectsField.setStyle("-fx-background-color: #ECF0F1;");
        numSubjectsField.setMaxWidth(90);
        numSubjectsField.setAlignment(Pos.CENTER);

        Button calculateButton = new Button("Calculate Grades");
        calculateButton.setStyle("-fx-background-color: #2ECC71; -fx-text-fill: white;");

        calculateButton.setOnAction(event -> calculateGrades());

        layout.getChildren().addAll(
                new Label("Welcome to the Student Grade Calculator!"),
                new Label("Enter the Number of Subjects"),
                numSubjectsField,
                subjectsVBox,
                calculateButton,
                resultLabel
        );

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void calculateGrades() {
        try {
            int numSubjects = Integer.parseInt(numSubjectsField.getText());
            subjectsVBox.getChildren().clear();

            for (int i = 1; i <= numSubjects; i++) {
                HBox subjectBox = new HBox(10);
                subjectBox.setAlignment(Pos.CENTER);

                Label subjectLabel = new Label("Subject " + i);
                TextField subjectField = new TextField();
                subjectField.setPromptText("Enter marks");
                subjectField.setStyle("-fx-background-color: #ECF0F1;");
                subjectField.setMaxWidth(150);
                subjectField.setAlignment(Pos.CENTER);

                subjectBox.getChildren().addAll(subjectLabel, subjectField);
                subjectsVBox.getChildren().add(subjectBox);
            }

            Button submitButton = new Button("Submit");
            submitButton.setStyle("-fx-background-color: #2ECC71; -fx-text-fill: white;");

            submitButton.setOnAction(event -> displayResults());

            VBox submitBox = new VBox(submitButton);
            submitBox.setAlignment(Pos.CENTER);
            subjectsVBox.getChildren().add(submitBox);
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid input. Please enter a valid number of subjects.");
        }
    }

    private void displayResults() {
        double totalMarks = 0;
        int numSubjects = subjectsVBox.getChildren().size() - 1;

        for (int i = 0; i < numSubjects; i++) {
            HBox subjectBox = (HBox) subjectsVBox.getChildren().get(i);
            TextField subjectField = (TextField) subjectBox.getChildren().get(1);

            try {
                double subjectMarks = Double.parseDouble(subjectField.getText());

                if (subjectMarks < 0 || subjectMarks > 100) {
                    resultLabel.setText("Invalid marks! Marks should be between 0 and 100. Exiting.");
                    return;
                }

                totalMarks += subjectMarks;
            } catch (NumberFormatException e) {
                resultLabel.setText("Invalid input. Please enter valid marks for all subjects.");
                return;
            }
        }

        double averagePercentage = totalMarks / numSubjects;
        char grade = calculateGrade(averagePercentage);

        String resultText = String.format(
                "Results:\nTotal Marks: %.2f\nAverage Percentage: %.2f%%\nGrade: %c",
                totalMarks, averagePercentage, grade);

        resultLabel.setText(resultText);

        // Styling for the result label
        resultLabel.setStyle("-fx-background-color: #ECF0F1; -fx-padding: 10px; -fx-alignment: center;");
    }

    private char calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return 'A';
        } else if (averagePercentage >= 80) {
            return 'B';
        } else if (averagePercentage >= 70) {
            return 'C';
        } else if (averagePercentage >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }
}
