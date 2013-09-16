package Client.Protocol.Types;

import Client.UserHandling.ChatSession;

public class Common {
	public static ChatInvite chatSessionToChatInvite(ChatSession s) {
		return new ChatInvite(s.getP(), s.getQ(), (int) s.getId(), (int) s
				.getPartner().getUser().getClientId(), (int) s.getInitiator()
				.getUser().getClientId());
	}
}
