package Client.Protocol.Types;

public class Chatopen {
	private int chatpartner;

	public Chatopen(int chatpartner) {
		this.chatpartner = chatpartner;
	}

	public int getChatpartner() {
		return chatpartner;
	}
}
