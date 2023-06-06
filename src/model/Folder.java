package model;

public class Folder {
	private String name;
	private String pathOrigin;
	private String pathNow;

	public Folder() {
	}

	public Folder(String name, String pathOrigin, String pathNow) {
		super();
		this.name = name;
		this.pathOrigin = pathOrigin;
		this.pathNow = pathNow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPathOrigin() {
		return pathOrigin;
	}

	public void setPathOrigin(String pathOrigin) {
		this.pathOrigin = pathOrigin;
	}

	public String getPathNow() {
		return pathNow;
	}

	public void setPathNow(String pathNow) {
		this.pathNow = pathNow;
	}

	@Override
	public String toString() {
		return "Folder [name=" + name + ", pathOrigin=" + pathOrigin + ", pathNow=" + pathNow + "]";
	}

}
