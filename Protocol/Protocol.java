package Client.Protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import Client.Protocol.Types.ChatInvite;
import Client.Protocol.Types.ChatKeyOffer;
import Client.Protocol.Types.Chatopen;
import Client.Protocol.Types.Message;
import Client.Protocol.Types.Namerequest;
import Client.Protocol.Types.OnlineList;
import Client.UserHandling.ChatSession;
import Client.UserHandling.User;

public class Protocol {
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

	private DataInputStream in;
	private DataOutputStream out;

	private boolean writelock;

	public Protocol(DataInputStream in, DataOutputStream out) {
		this.in = in;
		this.out = out;
	}

	public void writeID(int id) throws IOException {
		appendWritelock();
		out.writeByte(Protocol.DACRY_SERVER_COMM_OPEN);
		out.writeLong(id);
		out.writeByte(Protocol.DACRY_SERVER_COMM_CLOSE);
		out.flush();
		releaseWritelock();
	}

	public int applyForID() throws IOException {
		// Get the ID
		out.writeByte(Protocol.DACRY_SERVER_COMM_OPEN);
		out.writeByte(Protocol.CMD_REGISTER);
		out.writeByte(Protocol.DACRY_SERVER_COMM_CLOSE);
		out.flush();
		// openingbyte
		in.readByte();
		int id = (int) in.readLong();
		in.readByte();
		return id;
	}

	public void writeOnlineListApply() throws IOException {
		appendWritelock();
		out.writeByte(Protocol.DACRY_SERVER_COMM_OPEN);
		out.writeByte(Protocol.CMD_GET_ONLINE_USERS);
		out.writeByte(Protocol.DACRY_SERVER_COMM_CLOSE);
		out.flush();
		releaseWritelock();
	}

	public void writeCloseByte() throws IOException {
		appendWritelock();
		out.writeByte(Protocol.DACRY_SERVER_COMM_OPEN);
		out.writeByte(Protocol.CMD_UNREGISTER);
		out.writeByte(Protocol.DACRY_SERVER_COMM_CLOSE);
		out.flush();
		releaseWritelock();
	}

	/**
	 * TODO Change ClientHandling to User
	 * 
	 * @param hand
	 * @throws IOException
	 */
	public void writeOnlineList(ArrayList<User> handler) throws IOException {
		appendWritelock();
		out.writeByte(Protocol.DACRY_SERVER_COMM_OPEN);
		out.writeByte(Protocol.NOTIFY_NEW_ONLINE_LIST);
		out.writeInt(handler.size());
		for (User user : handler) {
			out.writeLong(user.getClientId());
			out.writeInt(user.getName().length());
			out.writeChars(user.getName());
		}
		out.writeByte(Protocol.DACRY_SERVER_COMM_CLOSE);
		out.flush();
		releaseWritelock();
	}

	public OnlineList readOnlineList() throws IOException {
		int lenght = in.readInt();
		User[] onlineusers = new User[lenght];
		for (int i = 0; i < lenght; i++) {
			long id = in.readLong();
			int lenghtName = in.readInt();
			StringBuilder builder = new StringBuilder();
			for (int j = 0; j < lenghtName; j++) {
				builder.append(in.readChar());
			}
			onlineusers[i] = new User(builder.toString(), id);
		}
		return new OnlineList(onlineusers, null);
	}

	public void writeMessageToServer(Message message) throws IOException {
		writeMessage(message, Protocol.CMD_SEND_MESSAGE);
	}

	public void writeMessageToClient(Message message) throws IOException {
		writeMessage(message, Protocol.NOTIFY_CHAT_MESSAGE);

	}

	public void writeChatInvite(Chatopen open) throws IOException {
		appendWritelock();
		out.writeByte(Protocol.DACRY_SERVER_COMM_OPEN);
		out.writeByte(Protocol.CMD_SETUP_CHAT);
		out.writeLong(open.getChatpartner());
		out.writeByte(Protocol.DACRY_SERVER_COMM_CLOSE);
		out.flush();
		releaseWritelock();
	}

	private void writeMessage(Message message, byte key) throws IOException {
		appendWritelock();
		out.writeByte(Protocol.DACRY_SERVER_COMM_OPEN);
		out.writeByte(key);
		out.writeLong(message.getChatsessionid());
		out.writeInt(message.getMsg().length);
		for (int i = 0; i < message.getMsg().length; i++) {
			out.write(message.getMsg()[i]);
		}
		out.writeByte(Protocol.DACRY_SERVER_COMM_CLOSE);
		out.flush();
		releaseWritelock();
	}

	public void writeChatKeyOfferToServer(ChatKeyOffer offer)
			throws IOException {
		writeChatKeyOffer(offer, Protocol.CMD_SETUP_CHAT_2);
	}

	public void writeChatKeyOfferToClient(ChatKeyOffer offer)
			throws IOException {
		writeChatKeyOffer(offer, Protocol.NOTIFY_CHAT_SETTET_UP);
	}

	private void writeChatKeyOffer(ChatKeyOffer offer, byte keybyte)
			throws IOException {
		appendWritelock();
		out.writeByte(Protocol.DACRY_SERVER_COMM_OPEN);
		out.writeByte(keybyte);
		out.writeLong(offer.getChatsessionid());
		out.writeInt(offer.getKey().toByteArray().length);
		for (int i = 0; i < offer.getKey().toByteArray().length; i++) {
			out.writeByte(offer.getKey().toByteArray()[i]);
		}
		out.writeByte(Protocol.DACRY_SERVER_COMM_CLOSE);
		out.flush();
		releaseWritelock();
	}

	public void writeNamerequest(Namerequest n) throws IOException {
		appendWritelock();
		out.writeByte(Protocol.DACRY_SERVER_COMM_OPEN);
		out.writeByte(Protocol.CMD_SET_NAME);
		out.writeByte(n.getName().length());
		out.writeChars(n.getName());
		out.writeByte(Protocol.DACRY_SERVER_COMM_CLOSE);
		out.flush();
		releaseWritelock();
	}

	public Namerequest readNamerequest() throws IOException {
		int lenght = in.readByte();
		StringBuilder builder = new StringBuilder(lenght);
		for (int i = 0; i < lenght; i++) {
			builder.append(in.readChar());

		}
		return new Namerequest(builder.toString());
	}

	public ChatKeyOffer readKeyOffer() throws IOException {
		long id = in.readLong();
		byte[] cry = new byte[in.readInt()];
		for (int i = 0; i < cry.length; i++) {
			cry[i] = in.readByte();
		}
		return new ChatKeyOffer((int) id, new BigInteger(cry));
	}

	public Message readMessage() throws IOException {
		long sessionID = in.readLong();
		byte[] content = new byte[in.readInt()];
		for (int i = 0; i < content.length; i++) {
			content[i] = in.readByte();
		}
		return new Message((int) sessionID, content);
	}

	public Chatopen readChatopen() throws IOException {
		return new Chatopen((int) in.readLong());
	}

	public void writeChatInvite(ChatInvite inv) throws IOException {
		appendWritelock();
		out.writeByte(Protocol.DACRY_SERVER_COMM_OPEN);
		out.writeByte(Protocol.NOTIFY_CHAT_INV);
		out.writeLong(inv.getChatsessionid());
		out.writeLong(inv.getInitiatorid());
		out.writeLong(inv.getPartnerid());
		for (byte b : inv.getQ().toByteArray()) {
			out.writeByte(b);
		}
		for (byte b : inv.getP().toByteArray()) {
			out.writeByte(b);
		}
		out.writeByte(Protocol.DACRY_SERVER_COMM_CLOSE);
		out.flush();
		releaseWritelock();
	}

	public ChatInvite readChatInvite() throws IOException {
		long sessionid = in.readLong();
		int id_initiator = in.readInt();
		int id_guest = in.readInt();
		byte[] q = new byte[ChatSession.KEY_LENGHT];
		for (int i = 0; i < q.length; i++) {
			q[i] = in.readByte();
		}
		byte[] p = new byte[ChatSession.KEY_LENGHT];
		for (int i = 0; i < p.length; i++) {
			p[i] = in.readByte();
		}
		return new ChatInvite(new BigInteger(p), new BigInteger(q),
				(int) sessionid, id_guest, id_initiator);
	}

	private void appendWritelock() {
		while (writelock) {

		}
		writelock = true;
	}

	private void releaseWritelock() {
		writelock = false;
	}
}
