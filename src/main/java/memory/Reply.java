package memory;

public class Reply extends Timeline {
	private int replyId;
	private int id;
	private String name;
	private String text;
	private boolean isDeleted;


	public Reply (int replyId, int id, String name, String text, boolean isDeleted) {
		super();
		this.replyId = replyId;
		this.id = id;
		this.name = name;
		this.text = text;
		this.isDeleted = isDeleted;
	}

	public int getId() {
		return id;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setText(String text) {
		this.text = text;
	}

}
