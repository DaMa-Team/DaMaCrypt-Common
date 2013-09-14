package Client;

import java.io.Serializable;

public class ChatMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 123455678890L;
	private byte[] message;
	private ChatSession session;

	public ChatMessage(ChatSession session, byte[] msg) {
		this.session = session;
		this.message = msg;
	}

	public byte[] getMessage() {
		return message;
	}

	public ChatSession getSession() {
		return session;
	}

}
