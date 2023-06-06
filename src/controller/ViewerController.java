package controller;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Folder;
import model.Photo;
import service.Align;
import service.LoadList;
import service.SaveList;
import service.Search;

public class ViewerController implements Initializable {

	// injects dependency.
	private SaveList saveList = new SaveList();

	private LoadList loadList = new LoadList();

	private Search search = new Search();

	private Align align = new Align();

	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@FXML
	private BorderPane window;
	@FXML
	private MenuItem folderPathAddition;
	@FXML
	private MenuItem folderPathCheck;
	@FXML
	private MenuItem option;
	@FXML
	private MenuItem information;
	@FXML
	private MenuItem help;
	@FXML
	private Button btnSearch;
	@FXML
	private HBox folderListBox;
	@FXML
	private ListView<String> folderList;
	@FXML
	private ScrollPane photoAreaBox;
	@FXML
	private TilePane photoArea;

	private boolean isDuplicate;

	private File savefiles = new File("C:\\workspace\\[Software] Photo Manager\\bin\\savefiles");
	private List<Photo> photos = null;
	private double photoBoxWidth = 230;
	private double photoBoxHeight = 350;
	private double photoWidth = photoBoxWidth * 0.9;
	private double photoHeight = photoBoxHeight * 0.7;

	public static String folderName;

	// plans to implement with multi-thread
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		photoAreaBox.setOnScroll(new EventHandler<ScrollEvent>() {

			@Override
			public void handle(ScrollEvent scrollEvent) {
				if (scrollEvent.isControlDown()) {

					if (scrollEvent.getDeltaY() > 0) {
						System.out.println("확대되었습니다.");

						photoBoxWidth = photoBoxWidth * 1.1;
						photoBoxHeight = photoBoxHeight * 1.1;
						photoWidth = photoWidth * 1.1;
						photoHeight = photoHeight * 1.1;

						System.out.println(photoBoxWidth);
						System.out.println(photoBoxHeight);
						System.out.println(photoWidth);
						System.out.println(photoWidth);

					} else {
						System.out.println("축소되었습니다.");

						photoBoxWidth = photoBoxWidth * 0.9;
						photoBoxHeight = photoBoxHeight * 0.9;
						photoWidth = photoWidth * 0.9;
						photoHeight = photoHeight * 0.9;

						System.out.println(photoBoxWidth);
						System.out.println(photoBoxHeight);
						System.out.println(photoWidth);
						System.out.println(photoWidth);
					}

				}

			}

		});

		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		double screenHeight = screenBounds.getHeight();
		double screenWidth = screenBounds.getWidth();

		window.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// sidebar size
				folderListBox.setPrefHeight(newValue.doubleValue());
				folderList.setPrefHeight(newValue.doubleValue());
				photoAreaBox.setPrefHeight(newValue.doubleValue());
			}

		});

		window.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// sidebar size
				if (newValue.doubleValue() >= screenWidth * 0.9) {
					folderListBox.setPrefWidth(newValue.doubleValue() * 0.12);
					folderList.setPrefWidth(newValue.doubleValue() * 0.12);
					photoAreaBox.setPrefWidth(newValue.doubleValue() * 0.85);
					photoArea.setPrefWidth(newValue.doubleValue() * 0.85);

					// adds save files to side-bar.

					try {
						List<Folder> folders = loadList.loadFolders(savefiles);
						List<String> folderNames = new ArrayList<>();

						for (int i = 0; i < folders.size(); i++) {
							folderNames.add(folders.get(i).getName());
						}

						folderList.setItems(FXCollections.observableArrayList(folderNames));
					} catch (IOException e) {
						e.printStackTrace();
					}

				} else {
					folderListBox.setPrefWidth(0);
					folderList.setPrefWidth(0);
					folderList.setItems(null);

				}
			}

		});

		folderList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {

					List<Folder> folders = loadList.loadFolders(savefiles);

					for (int i = 0; i < folders.size(); i++) {
						if (folders.get(i).getName().equals(newValue)) {

							File pathNow = new File(folders.get(i).getPathNow());
							photos = loadList.loadFiles(pathNow);

						}

					}

					showPhotoArea(photos);

				} catch (IOException e) {

					e.printStackTrace();
				}

			}
		});

		window.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() != window && event.getDragboard().hasFiles()) {
					/* allow for both copying and moving, whatever user chooses */
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			}
		});

		window.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard dragboard = event.getDragboard();
				boolean success = false;
				if (dragboard.hasFiles()) {

					File origin = dragboard.getFiles().get(0);
					folderName = origin.toString();
					File target = new File("C:\\workspace\\[Software] Photo Manager\\bin\\savefiles");

					if (origin != null) {
						try {
							String Photolist = saveList.listToString(origin);
							isDuplicate = saveList.saveFile(origin, target, Photolist);
							if (isDuplicate) {
								showFolderPathAlert();
							} else {
								showFolderPathSetName();
							}
						} catch (IOException | URISyntaxException e) {
							e.printStackTrace();
						}
					}

					success = true;
				}
				/* let the source know whether the string was successfully 
				 * transferred and used */
				event.setDropCompleted(success);

				event.consume();
			}
		});

		folderPathAddition.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DirectoryChooser directoryChooser = new DirectoryChooser();

				File origin = directoryChooser.showDialog(primaryStage);
				folderName = origin.toString();
				File target = new File("C:\\workspace\\[Software] Photo Manager\\bin\\savefiles");

				if (origin != null) {
					try {
						String Photolist = saveList.listToString(origin);
						isDuplicate = saveList.saveFile(origin, target, Photolist);
						if (isDuplicate) {
							showFolderPathAlert();
						} else {
							showFolderPathSetName();
						}
					} catch (IOException | URISyntaxException e) {
						e.printStackTrace();
					}
				}
			}
		});

		folderPathCheck.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					URL fxmlPath = new File("src/view/FolderPath.fxml").toURI().toURL();
					Parent parent = FXMLLoader.load(fxmlPath);
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.initOwner(primaryStage);
					stage.setTitle("폴더 경로 확인");
					stage.setScene(new Scene(parent));
					stage.setResizable(false);
					stage.getIcons().add(new Image("/resources/icon/icon.png"));
					stage.show();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

		});

		option.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					URL fxmlPath = new File("src/view/Option.fxml").toURI().toURL();
					Parent parent = FXMLLoader.load(fxmlPath);
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.initOwner(primaryStage);
					stage.setTitle("옵션");
					stage.setScene(new Scene(parent));
					stage.setResizable(false);
					stage.getIcons().add(new Image("/resources/icon/icon.png"));
					stage.show();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

		});

		information.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					URL fxmlPath = new File("src/view/Information.fxml").toURI().toURL();
					Parent parent = FXMLLoader.load(fxmlPath);
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.initOwner(primaryStage);
					stage.setTitle("정보");
					stage.setScene(new Scene(parent));
					stage.setResizable(false);
					stage.getIcons().add(new Image("/resources/icon/icon.png"));
					stage.show();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}
		});

		help.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					URL fxmlPath = new File("src/view/Help.fxml").toURI().toURL();
					Parent parent = FXMLLoader.load(fxmlPath);
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.initOwner(primaryStage);
					stage.setTitle("도움말");
					stage.setScene(new Scene(parent));
					stage.setResizable(false);
					stage.getIcons().add(new Image("/resources/icon/icon.png"));
					stage.show();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

		});

		btnSearch.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("검색 버튼 클릭!!");

			}

		});

	}

	public void showFolderPathSetName() {
		try {
			URL fxmlPath = new File("src/view/FolderPathSetName.fxml").toURI().toURL();
			Parent parent = FXMLLoader.load(fxmlPath);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(primaryStage);
			stage.setTitle("도움말");
			stage.setScene(new Scene(parent));
			stage.setResizable(false);
			stage.getIcons().add(new Image("/resources/icon/icon.png"));
			stage.show();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public void showFolderPathAlert() {
		try {

			URL fxmlPath = new File("src/view/ForderPathAlert.fxml").toURI().toURL();
			Parent parent = FXMLLoader.load(fxmlPath);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(primaryStage);
			stage.setTitle("경고");
			stage.setScene(new Scene(parent));
			stage.setResizable(false);
			stage.getIcons().add(new Image("/resources/icon/icon.png"));
			stage.show();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public void showPhotoArea(List<Photo> photos) throws IOException {

		photoArea.setPrefTileWidth(photoBoxWidth);
		photoArea.setPrefTileHeight(photoBoxHeight);

		for (int i = 0; i < photos.size(); i++) {
			VBox comic = new VBox();
			comic.setStyle("-fx-border-color: black;");
			comic.setAlignment(Pos.CENTER);

			/*
			 * processing WEBP
			 */
			File imagePath = new File(photos.get(i).getImage());
			BufferedImage bufferedImage = ImageIO.read(imagePath);
//			System.out.println(bufferedImage.getWidth() + " / " + bufferedImage.getHeight());

			Image image = SwingFXUtils.toFXImage(bufferedImage, null);

			ImageView imageView = new ImageView();
			imageView.setImage(image);
			imageView.setFitWidth(photoWidth);
			imageView.setFitHeight(photoHeight);
			imageView.setPreserveRatio(true);
			imageView.setCache(true);
			imageView.setStyle("-fx-border-color: black; -fx-cursor: HAND");
			imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					try {
						Desktop.getDesktop().open(imagePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
					event.consume();
				}
			});

			imageView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {

					System.out.println("이미지에 호버 이벤트 발생!!");
					
					event.consume();
				}
			});

			Label author = new Label();
			author.setStyle("-fx-border-color: black");
			author.setText(photos.get(i).getAuthor());

			Label title = new Label();
			title.setStyle("-fx-border-color: black");
			title.setText(photos.get(i).getTitle());

			comic.getChildren().addAll(imageView, author, title);

			photoArea.getChildren().add(comic);
			TilePane.setMargin(comic, new Insets(10));
		}

	}

	public void exitProgram() {
		Platform.exit();
	}

}
