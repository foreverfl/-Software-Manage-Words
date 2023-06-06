package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Folder;
import service.LoadList;

public class FolderPathSetNameController implements Initializable {

	private LoadList loadList = new LoadList();

	@FXML
	private TextField name;
	@FXML
	private Button cancel;
	@FXML
	private Button confirm;

	private File savefiles = new File("C:\\workspace\\[Software] Photo Manager\\bin\\savefiles");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {

					String pathOrigin = ViewerController.folderName;

					List<Folder> folders = loadList.loadFolders(savefiles);

					for (int i = 0; i < folders.size(); i++) {
						if (folders.get(i).getPathOrigin().equals(pathOrigin)) {
							File fileToDelete = new File(folders.get(i).getPathNow());

							if (fileToDelete.exists()) {
								fileToDelete.delete();
								System.out.println("deleted successfully");
							}

						}
					}

				} catch (IOException e) {

					e.printStackTrace();
				}

				// get a handle to the stage
				Stage stage = (Stage) confirm.getScene().getWindow();

				stage.close();

			}

		});

		confirm.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String nameToSave = name.getText().trim();
				String pathOrigin = ViewerController.folderName;

				try {

					List<Folder> folders = loadList.loadFolders(savefiles);
					for (int i = 0; i < folders.size(); i++) {
						if (folders.get(i).getPathOrigin().equals(pathOrigin)) {
							File fileToUpdate = new File(folders.get(i).getPathNow());

							if (fileToUpdate.exists()) {

								BufferedReader br = new BufferedReader(new FileReader(fileToUpdate));
								String newStr = "";
								String str;

								while ((str = br.readLine()) != null) {
									if (str.contains("[pathOrigin]")) {
										newStr += "[name] " + nameToSave + "\n";
										newStr += str + "\n";
									} else {
										newStr += str + "\n";
									}
								}
								

								FileWriter fileWriter = new FileWriter(folders.get(i).getPathNow(), false);
								fileWriter.write(newStr.trim());
								fileWriter.flush();
								fileWriter.close();
								br.close();
								break;
							}

						}
					}

				} catch (IOException e) {

					e.printStackTrace();

				}

				// get a handle to the stage
				Stage stage = (Stage) confirm.getScene().getWindow();

				stage.close();

			}

		});

	}

}
