package Client.Protocol.Types;

import java.math.BigInteger;

public class ChatInvite {
	private BigInteger p;
	private BigInteger q;
	private int chatsessionid;
	private int initiatorid;
	private int partnerid;

	public ChatInvite(BigInteger p, BigInteger q, int chatsessionid,
			int partnerid, int initiatorid) {
		super();
		this.p = p;
		this.q = q;
		this.chatsessionid = chatsessionid;
		this.partnerid = partnerid;
		this.initiatorid = initiatorid;
	}

	public BigInteger getP() {
		return p;
	}

	public BigInteger getQ() {
		return q;
	}

	public int getChatsessionid() {
		return chatsessionid;
	}

	public void setChatsessionid(int chatsessionid) {
		this.chatsessionid = chatsessionid;
	}

	public int getPartnerid() {
		return partnerid;
	}

	public int getInitiatorid() {
		return initiatorid;
	}

}
