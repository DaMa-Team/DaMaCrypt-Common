package Client.Protocol.Types;

public class Chatopen {
	private int chatpartner;

	public Chatopen(int chatpartner) {
		this.chatpartner = chatpartner;
		System.out.println("Your Partner is" + chatpartner);
	}

	public int getChatpartner() {
		return chatpartner;
	}
}
