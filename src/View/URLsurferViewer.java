package View;

import Database.VisitedLink;
import Parsing.CountImg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Stack;

public class URLsurferViewer {
    private Stack<String> stringStack;

    public URLsurferViewer(){
        stringStack = new Stack<>();
    }

    public void initialize(){
        List<VisitedLink> visitedLinks = Database.DBconnector.VisitedLinks();
        if (visitedLinks != null)
            for(VisitedLink visitedLink : visitedLinks)
                historyList.getItems().add(visitedLink.toString());
    }

    @FXML
    private MenuItem resetHistoryMenuItem;

    @FXML
    private MenuItem showAbout;

    @FXML
    private TextField inputField;

    @FXML
    private ChoiceBox<String> historyList;

    @FXML
    private Button parseInput;

    @FXML
    private Button loadHistory;

    @FXML
    private Label numOfImages;

    @FXML
    private Label sizeOfImages;

    @FXML
    private ListView<String> availableLinks;

    @FXML
    private Button goBack;

    @FXML
    void chooseAvailable(MouseEvent event) {
        inputField.setText(availableLinks.getSelectionModel().getSelectedItem());
    }

    @FXML
    void parseURL(ActionEvent event) {
        String link = inputField.getText();

        historyList.getItems().add(link);
        Database.DBconnector.insertLink(link);
        stringStack.add(link);

        try {
            Pair<Integer, Integer> Images = CountImg.count(link);
            numOfImages.setText(Images.getKey().toString());
            sizeOfImages.setText(Images.getValue().toString());

            List<String> linkList = Parsing.ListLinks.list(link);
            availableLinks.getItems().clear();
            for(String available : linkList)
                availableLinks.getItems().add(available);

        } catch (Exception e) {
            inputField.setText("Wrong URL");
            System.out.println("Parsing: Error");

            //clear last results
            numOfImages.setText("");
            sizeOfImages.setText("");
            availableLinks.getItems().clear();
            //e.printStackTrace();
        }
    }

    @FXML
    void resetHistory(ActionEvent event) {
        Database.DBconnector.clear();
        historyList.getItems().clear();
        availableLinks.getItems().clear();
        stringStack.clear();
    }

    @FXML
    void showAbout(ActionEvent event) {
        Parent aboutRoot = null;
        try {
            aboutRoot = FXMLLoader.load(getClass().getResource("about.fxml"));
        } catch (IOException e) {
            System.out.println("View: About loading error");
            //e.printStackTrace();
            return;
        }
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About URLsurfer");
        aboutStage.setScene(new Scene(aboutRoot));
        aboutStage.show();
    }

    @FXML
    void loadFromHistory(ActionEvent actionEvent) {
        inputField.setText(historyList.getValue());
    }

    @FXML
    void getPreviousLink(ActionEvent event) {
        if(stringStack.empty()) return;
        inputField.setText(stringStack.pop());
    }
}
