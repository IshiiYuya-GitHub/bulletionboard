package memory;


public class ReJackson {
	private boolean check;
	private int id;
	private String postingName;
	private String postingText;
	private int replyId;
	private String editText;

	public ReJackson(boolean check, int id, String postingName, String postingText) {
		this.check = check;
		this.id = id;
		this.postingName = postingName;
		this.postingText = postingText;
	}

	public ReJackson(boolean check, int id, String postingName, String postingText, int replyId) {
		this.check = check;
		this.id = id;
		this.postingName = postingName;
		this.postingText = postingText;
		this.replyId = replyId;
	}

	public ReJackson(boolean check, String editText) {
		this.check = check;
		this.editText = editText;
	}

	public boolean isCheck() {
		return check;
	}

	public int getId() {
		return id;
	}

	public String getPostingName() {
		return postingName;
	}

	public String getPostingText() {
		return postingText;
	}

	public int getReplyId() {
		return replyId;
	}

	public String getEditText() {
		return editText;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPostingName(String postingName) {
		this.postingName = postingName;
	}

	public void setPostingText(String postingText) {
		this.postingText = postingText;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public void setEditText(String editText) {
		this.editText = editText;
	}
}
