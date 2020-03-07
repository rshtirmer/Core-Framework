package com.openrsc.server.plugins.npcs.varrock;

import com.openrsc.server.constants.ItemId;
import com.openrsc.server.constants.NpcId;
import com.openrsc.server.model.Shop;
import com.openrsc.server.model.container.Item;
import com.openrsc.server.model.entity.npc.Npc;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.model.world.World;
import com.openrsc.server.net.rsc.ActionSender;
import com.openrsc.server.plugins.ShopInterface;
import com.openrsc.server.plugins.triggers.TalkNpcTrigger;

import static com.openrsc.server.plugins.Functions.*;

public final class LowesArchery implements ShopInterface,
	TalkNpcTrigger {

	private final Shop shop = new Shop(false, 3000, 100, 55, 1, new Item(ItemId.BRONZE_ARROWS.id(),
		200), new Item(ItemId.CROSSBOW_BOLTS.id(), 150), new Item(ItemId.SHORTBOW.id(), 4), new Item(
		ItemId.LONGBOW.id(), 2), new Item(ItemId.CROSSBOW.id(), 2));

	@Override
	public boolean blockTalkNpc(final Player p, final Npc n) {
		return n.getID() == NpcId.LOWE.id();
	}

	@Override
	public Shop[] getShops(World world) {
		return new Shop[]{shop};
	}

	@Override
	public boolean isMembers() {
		return false;
	}

	@Override
	public void onTalkNpc(final Player p, final Npc n) {
		npcTalk(p, n, "Welcome to Lowe's Archery Store",
			"Do you want to see my wares?");

		int option = showMenu(p, n, false, //do not send over
			"Yes please", "No, I prefer to bash things close up");

		if (option == 0) {
			playerTalk(p, n, "Yes Please");
			p.setAccessingShop(shop);
			ActionSender.showShop(p, shop);
		} else if (option == 1) {
			playerTalk(p, n, "No, I prefer to bash things close up");
		}
	}

}
