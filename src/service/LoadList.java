package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Folder;
import model.Photo;

public class LoadList {

	public List<Photo> loadFiles(File file) throws IOException {

		List<Photo> photos = new ArrayList<>();

		BufferedReader br = new BufferedReader(new FileReader(file));

		String str;
		while ((str = br.readLine()) != null) {
			if (str.contains("[pathOrigin]") || str.contains("[name]"))
				continue;

			String[] oneLine = str.split("//");

			Photo photo = new Photo();

			photo.setAuthor(oneLine[0].trim());
			photo.setTitle(oneLine[1].trim());
			photo.setImage(oneLine[2].trim());

			photos.add(photo);

		}

		br.close();

		return photos;
	}

	public List<Folder> loadFolders(File root) throws IOException {

		List<Folder> folders = new ArrayList<>();

		File[] files = root.listFiles();

		for (int i = 0; i < files.length; i++) {

			Folder folder = new Folder();

			String pathNow = files[i].toString();
			folder.setPathNow(pathNow);
			String pathOrigin = loadPathOrigin(files[i]);
			folder.setPathOrigin(pathOrigin);
			String name = loadName(files[i]);
			folder.setName(name);

			folders.add(folder);

		}

		return folders;
	}

	public String loadPathOrigin(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));

		String res = br.readLine().replace("[pathOrigin] ", "");
		String str;
		while ((str = br.readLine()) != null) {
			if (str.contains("[pathOrigin]")) {
				res = str.replace("[pathOrigin] ", "");
				br.close();
				return res;
			}
		}

		br.close();

		return res;

	}

	public String loadName(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));

		String res = br.readLine().replace("[name] ", "");
		String str;
		while ((str = br.readLine()) != null) {
			if (str.contains("[name]")) {
				res = str.replace("[name] ", "");
				br.close();
				return res;
			}
		}

		br.close();

		return res;
	}
}
