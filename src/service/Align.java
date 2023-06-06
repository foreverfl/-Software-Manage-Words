package service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.Photo;

public class Align {

	public List<Photo> alignWithTitle(List<Photo> photos) {

		Collections.sort(photos, new Comparator<>() {

			@Override
			public int compare(Photo o1, Photo o2) {

				return o1.getTitle().compareTo(o2.getTitle());
			}

		});

		return photos;
	}

	public List<Photo> alignWithAuthor(List<Photo> photos) {

		Collections.sort(photos, new Comparator<>() {

			@Override
			public int compare(Photo o1, Photo o2) {

				return o1.getAuthor().compareTo(o2.getAuthor());
			}

		});

		return photos;
	}

}
