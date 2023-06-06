package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ForderPathAlertController implements Initializable {

	@FXML
	private Button confirm;

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

	}
}
