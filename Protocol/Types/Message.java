package Client.Protocol.Types;

public class Message {
	private int chatsessionid;
	private byte[] msg;

	public Message(int chatsessionid, byte[] msg) {
		this.chatsessionid = chatsessionid;
		this.msg = msg;
	}

	public int getChatsessionid() {
		return chatsessionid;
	}

	public byte[] getMsg() {
		return msg;
	}
}
