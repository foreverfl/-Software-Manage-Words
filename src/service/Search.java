package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Photo;

public class Search {

	private LoadList loadList = new LoadList();

	public List<Photo> searchPhotos(File file, String input) throws IOException {
		List<Photo> allPhotos = loadList.loadFiles(file);

		List<Photo> photosSearched = new ArrayList<>();

		for (int i = 0; i < allPhotos.size(); i++) {
			if (allPhotos.get(i).getAuthor().contains(input)) {
				photosSearched.add(allPhotos.get(i));
			} else if (allPhotos.get(i).getTitle().contains(input)) {
				photosSearched.add(allPhotos.get(i));
			}
		}

		return photosSearched;

	}

}
