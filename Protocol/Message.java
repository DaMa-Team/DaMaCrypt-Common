package Client.Protocol;

import java.io.Serializable;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 666L;

	public static final byte CMD_REGISTER = 0;
	public static final byte CMD_UNREGISTER = 1;
	public static final byte CMD_GET_ONLINE_USERS = 2;
	public static final byte CMD_SETUP_CHAT = 3;
	public static final byte CMD_SETUP_CHAT_2 = 4;
	public static final byte CMD_SEND_MESSAGE = 5;
	public static final byte CMD_SET_NAME = 6;

	public static final byte NOTIFY_CHAT_INV = 10;
	public static final byte NOTIFY_CHAT_SETTET_UP = 11;
	public static final byte NOTIFY_CHAT_MESSAGE = 12;
	public static final byte NOTIFY_NEW_ONLINE_LIST = 13;
	public static final byte NOTIFY_NAME_ALLREADY_IN_USE = 14;

	public static final byte DACRY_SERVER_COMM_OPEN = 20;
	public static final byte DACRY_SERVER_COMM_CLOSE = 21;

	private byte header;
	private Object content;

	public Message(byte header, Object content) {
		this.header = header;
		this.content = content;
	}

	public byte getHeader() {
		return header;
	}

	public Object getContent() {
		return content;
	}
}
