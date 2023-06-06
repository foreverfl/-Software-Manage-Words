package model;

public class Photo {

	private String title;
	private String author;
	private String image;

	public Photo() {

	}

	public Photo(String title, String author, String image) {
		super();
		this.title = title;
		this.author = author;
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Photo [title=" + title + ", author=" + author + ", image=" + image + "]";
	}

}
