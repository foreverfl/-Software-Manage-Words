package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.List;

import model.Folder;
import model.Photo;
import service.Align;
import service.LoadList;
import service.OpenPhoto;
import service.SaveList;
import service.Search;
import service.ShowPhoto;

public class Test {

	static SaveList saveList = new SaveList();
	static LoadList loadList = new LoadList();
	static Search search = new Search();
	static Align align = new Align();
	static OpenPhoto openPhoto = new OpenPhoto();
	static ShowPhoto showPhoto = new ShowPhoto();

	public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
		File comics = new File("C://test/inner");
		File lists = new File("C:\\workspace\\[Software] Photo Manager\\bin\\savefiles\\test.txt");

		List<Photo> allPhotos = loadList.loadFiles(lists);

		for (int i = 0; i < allPhotos.size(); i++) {
			System.out.println(allPhotos.get(i));
		}

		File image = new File(allPhotos.get(0).getImage());
		openPhoto.openProgram(image);

	}

	public static void completed() throws IOException, URISyntaxException {
		File comics = new File("C://test");
		File lists = new File("C:\\workspace\\[Software] Photo Manager\\bin\\savefiles");

		// 세이브 테스트
		String res = saveList.listToString(comics);

		saveList.saveFile(comics, lists, res);

		// 로딩 테스트
		List<Photo> allPhotos = loadList.loadFiles(lists);

		for (int i = 0; i < allPhotos.size(); i++) {
			System.out.println(allPhotos.get(i));
		}

		// 개별 로딩 테스트
		List<Folder> folders = loadList.loadFolders(lists);
		System.out.println(folders.toString());

		// 검색 테스트
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		List<Photo> photosSearched = search.searchPhotos(lists, br.readLine());
		for (int i = 0; i < photosSearched.size(); i++) {
			System.out.println(photosSearched.get(i));
		}

		// 정렬 테스트
		System.out.println("이름 순 정렬");
		List<Photo> photosAligned1 = align.alignWithTitle(allPhotos);
		for (int i = 0; i < photosAligned1.size(); i++) {
			System.out.println(photosAligned1.get(i));
		}

		System.out.println("=========================================");
		System.out.println("작가 순 정렬");
		List<Photo> photosAligned2 = align.alignWithAuthor(allPhotos);
		for (int i = 0; i < photosAligned2.size(); i++) {
			System.out.println(photosAligned2.get(i));
		}

	}
}
