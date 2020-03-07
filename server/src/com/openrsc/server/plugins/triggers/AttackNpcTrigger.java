package com.openrsc.server.plugins.triggers;

import com.openrsc.server.model.entity.npc.Npc;
import com.openrsc.server.model.entity.player.Player;

public interface AttackNpcTrigger {
	void onAttackNpc(Player p, Npc affectedmob);
	boolean blockAttackNpc(Player p, Npc affectedmob);
}
