package Client.UserHandling;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

import Client.Protocol.ChatSessionUser;

public class ChatSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2042963137019063739L;

	public static final int KEY_LENGHT = 32;

	private long id;

	private BigInteger q;
	private BigInteger p;

	private ChatSessionUser initiator;
	private ChatSessionUser guest;

	public static ChatSession generateChatSession(long id,
			ChatSessionUser initiator, ChatSessionUser guest) {

		Random rnd = new Random();
		BigInteger q = new BigInteger(randomByteArray(KEY_LENGHT, rnd));
		q = q.abs();
		BigInteger p = new BigInteger(randomByteArray(KEY_LENGHT, rnd));
		p = p.abs();
		BigInteger cry_guest = BigInteger.ZERO;
		BigInteger cry_invader = BigInteger.ZERO;
		return new ChatSession(id, cry_guest, cry_invader, q, p, initiator,
				guest);
	}

	private static byte[] randomByteArray(int bytelenght, Random rnd) {
		byte[] result = new byte[bytelenght];
		rnd.nextBytes(result);
		return result;
	}

	public ChatSession(long id, BigInteger cry_guest, BigInteger cry_invader,
			BigInteger q, BigInteger p, ChatSessionUser initiator,
			ChatSessionUser guest) {
		super();
		this.id = id;
		this.q = q;
		this.p = p;
		this.initiator = initiator;
		this.guest = guest;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigInteger getP() {
		return p;
	}

	public BigInteger getQ() {
		return q;
	}

	public long getId() {
		return id;
	}

	public ChatSessionUser getInitiator() {
		return initiator;
	}

	public ChatSessionUser getPartner() {
		return guest;
	}

	/**
	 * 
	 * @return The Name of the Initiator
	 * @deprecated do not use this
	 */
	public String getNameInitiator() {
		return initiator.getUser().getName();
	}

	/**
	 * 
	 * @return The Name of the Guest
	 * @deprecated do not use this
	 */
	public String getNamePartner() {
		return guest.getUser().getName();
	}

	public ChatSessionUser getMe(long idyou) {
		if (idyou == guest.getUser().getClientId())
			return guest;
		else
			return initiator;
	}
	public ChatSessionUser getPartner(long idyou) {
		if (idyou == initiator.getUser().getClientId())
			return guest;
		else
			return initiator;
	}
	/*
	 * public static User getPartner(ChatSession s, User you) { if
	 * (s.getPartner().getClientId() == you.getClientId()) return
	 * s.getInitiator(); else return s.getPartner(); }
	 * 
	 * public static User getYou(ChatSession s, User you) { if
	 * (s.getPartner().getClientId() == you.getClientId()) return
	 * s.getPartner(); else return s.getInitiator(); }
	 */
}
