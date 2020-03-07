package com.openrsc.server.plugins.npcs.ardougne.east;

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

import static com.openrsc.server.plugins.Functions.npcTalk;
import static com.openrsc.server.plugins.Functions.showMenu;

public class BakerMerchant implements ShopInterface, TalkNpcTrigger {

	private final Shop shop = new Shop(false, 15000, 100, 80, 2, new Item(ItemId.BREAD.id(), 10), new Item(ItemId.CAKE.id(), 3), new Item(ItemId.CHOCOLATE_SLICE.id(), 8));

	@Override
	public void onTalkNpc(Player p, Npc n) {
		npcTalk(p, n, "Good day " + (p.isMale() ? "Monsieur" : "Madame"),
			"Would you like ze nice freshly baked bread",
			"Or perhaps a nice piece of cake");
		int menu = showMenu(p, n, "Lets see what you have", "No thankyou");
		if (menu == 0) {
			p.setAccessingShop(shop);
			ActionSender.showShop(p, shop);
		}
	}

	// WHEN STEALING AND CAUGHT BY A MERCHANT ("Hey thats mine");
	// Delay player busy (3000); after stealing and Npc shout out to you.

	@Override
	public boolean blockTalkNpc(Player p, Npc n) {
		return n.getID() == NpcId.BAKER.id();
	}

	@Override
	public Shop[] getShops(World world) {
		return new Shop[]{shop};
	}

	@Override
	public boolean isMembers() {
		return true;
	}
}
