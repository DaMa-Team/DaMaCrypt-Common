package Client.Protocol.Types;

import java.util.ArrayList;

import Client.UserHandling.User;

public class OnlineList {
	private User[] online;
	private ArrayList<User> difflist;

	public OnlineList(User[] online, ArrayList<User> difflist) {
		this.online = online;
		this.difflist = difflist;
	}

	public User[] getOnline() {
		return online;
	}

	public ArrayList<User> getDifflist() {
		return difflist;
	}
}
