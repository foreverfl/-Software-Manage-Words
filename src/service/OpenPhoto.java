package service;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class OpenPhoto {

	public void openProgram(File file) throws IOException, InterruptedException {

		Desktop.getDesktop().open(file);

	}
}
