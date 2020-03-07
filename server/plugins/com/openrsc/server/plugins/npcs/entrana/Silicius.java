package com.openrsc.server.plugins.npcs.entrana;

import com.openrsc.server.constants.NpcId;
import com.openrsc.server.model.entity.npc.Npc;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.plugins.Functions;
import com.openrsc.server.plugins.triggers.TalkNpcTrigger;

public class Silicius implements TalkNpcTrigger {
	@Override
	public void onTalkNpc(final Player p, final Npc n) {
		if (n.getID() == NpcId.SILICIUS.id()) {
			Functions.npcTalk(p,n,
				"The monks of Entrana are always in need of vials",
				"You can help us by making vials in this very room",
				"If you do, I will automatically trade you bank notes for them");
		}
	}

	@Override
	public boolean blockTalkNpc(Player p, Npc n) {
		return n.getID() == NpcId.SILICIUS.id();
	}

}

