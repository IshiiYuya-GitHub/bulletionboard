package memory;

public class Timeline {
	private int id;
	private String name;
	private String text;
	private boolean isDeleted;

	public Timeline (int id, String name, String text, boolean isDeleted) {
		this.id = id;
		this.name = name;
		this.text = text;
		this.isDeleted = isDeleted;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setText(String text) {
		this.text = text;
	}
}
