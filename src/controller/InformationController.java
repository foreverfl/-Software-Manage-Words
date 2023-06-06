package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InformationController implements Initializable {

	@FXML
	private Button confirm;
	@FXML
	private Label detail;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		confirm.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// get a handle to the stage
				Stage stage = (Stage) confirm.getScene().getWindow();

				stage.close();

			}

		});

		String detailContent = "";
		detailContent += "Developed by foreverfl\n\n";
		detailContent += "Github: https://github.com/foreverfl";

		detail.setText(detailContent);
	}

}
