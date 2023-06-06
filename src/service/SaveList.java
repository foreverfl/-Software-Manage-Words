package service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import model.Folder;

public class SaveList {

	private LoadList loadList = new LoadList();

	public boolean saveFile(File origin, File target, String list) throws IOException, URISyntaxException {

		URL path = getClass().getResource("../savefiles");
		File file = Paths.get(path.toURI()).toFile();

		if (isDuplicate(file)) {
			System.out.println("이미 추가된 폴더입니다.");
			return true;
		}

		String filePath = file.toString() + "\\" + parsePath(origin.toString()) + ".txt";
		FileWriter fileWriter = new FileWriter(filePath);
		fileWriter.write(list);
		fileWriter.close();

		return false;

	}

	public String parsePath(String path) {
		String res = "";
		res = path.replaceAll("\\\\", "-");
		res = res.replaceFirst("^[a-zA-Z]{1}\\:\\-", "");

		return res;
	}

	public boolean isDuplicate(File root) throws IOException {

		List<Folder> folders = loadList.loadFolders(root);

		for (int i = 0; i < folders.size(); i++) {
			for (int j = 0; j < folders.size(); j++) {
				if (folders.get(i).getPathOrigin().equals(folders.get(j).getPathOrigin())) {
					return true;
				}
			}
		}

		return false;

	}

	public String listToString(File root) {

		String res = "";

		res += "[pathOrigin] " + root.toString() + "\n";
		for (File files : root.listFiles()) {
			if (files.isDirectory()) {
				File folderPath = files;
				boolean flag = false;
				for (File pages : folderPath.listFiles()) {
					if (!flag) {
						res += getAuthor(pages.toString(), root);
						res += " // ";
						res += getTitle(pages.toString(), root);
						res += " // ";
						res += pages.toString();
						res += "\n";

						flag = true;
					}
				}
			}
		}

		return res.trim();

	}

	public String getAuthor(String path, File root) {
		String[] tmp = path.split("\\]");
		String rootAsString = root.toString() + "\\[";
		String res = tmp[0].replace(rootAsString, "");

		return res;

	}

	public String getTitle(String path, File root) {
		String[] tmp_1 = path.split("\\]");
		String[] tmp_2 = tmp_1[1].split("\\\\");
		String res = tmp_2[0];

		return res.trim();

	}
}
