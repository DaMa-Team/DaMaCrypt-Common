package Client.Protocol.Types;

import java.math.BigInteger;

public class ChatKeyOffer {
	private int chatsessionid;
	private BigInteger key;

	public ChatKeyOffer(int chatsessionid, BigInteger key) {
		super();
		this.chatsessionid = chatsessionid;
		this.key = key;
	}

	public int getChatsessionid() {
		return chatsessionid;
	}	

	public BigInteger getKey() {
		return key;
	}


}
