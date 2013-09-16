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
import Client.UserHandling.User;
import Clienthandling.ClientHandling;

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

	public void writeMessageToServer(Message message) throws IOException {
		writeMessage(message, Protocol.CMD_SEND_MESSAGE);
	}

	public void writeMessageToClient(Message message) throws IOException {
		writeMessage(message, Protocol.NOTIFY_CHAT_MESSAGE);

	}

	private void writeMessage(Message message, byte key) throws IOException {
		appendWritelock();
		out.writeByte(Protocol.DACRY_SERVER_COMM_OPEN);
		out.writeByte(Protocol.NOTIFY_CHAT_MESSAGE);
		out.writeLong(message.getChatsessionid());
		out.writeInt(message.getMsg().length);
		for (int i = 0; i < message.getMsg().length; i++) {
			out.write(message.getMsg()[i]);
		}
		out.writeByte(Protocol.DACRY_SERVER_COMM_CLOSE);
		out.flush();
		releaseWritelock();
	}

	/**
	 * TODO remove the ChatSession
	 * 
	 * @param offer
	 * @param server
	 * @throws IOException
	 */
	public void writeChatKeyOffer(ChatKeyOffer offer) throws IOException {
		appendWritelock();
		out.writeByte(Protocol.DACRY_SERVER_COMM_OPEN);
		out.writeByte(Protocol.NOTIFY_CHAT_SETTET_UP);
		out.writeLong(offer.getChatsessionid());
		out.writeInt(offer.getKey().toByteArray().length);
		for (int i = 0; i < offer.getKey().toByteArray().length; i++) {
			out.writeByte(offer.getKey().toByteArray()[i]);
		}
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

	private void appendWritelock() {
		while (writelock) {

		}
		writelock = true;
	}

	private void releaseWritelock() {
		writelock = false;
	}
}
