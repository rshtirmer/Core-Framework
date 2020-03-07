package com.openrsc.server.plugins.itemactions;

import com.openrsc.server.constants.ItemId;
import com.openrsc.server.model.container.Item;
import com.openrsc.server.model.entity.GroundItem;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.plugins.triggers.UseObjTrigger;
import com.openrsc.server.plugins.triggers.TakeObjTrigger;

public class PotFlour implements UseObjTrigger, TakeObjTrigger {

	@Override
	public boolean blockUseObj(Item myItem, GroundItem item, Player player) {
		return item.getID() == ItemId.FLOUR.id() && myItem.getCatalogId() == ItemId.POT.id();
	}

	@Override
	public boolean blockTakeObj(Player p, GroundItem item) {
		return item.getID() == ItemId.FLOUR.id();
	}

	@Override
	public void onUseObj(Item myItem, GroundItem item, Player player) {
		if (myItem.getCatalogId() == ItemId.POT.id()) {
			if (player.getCarriedItems().remove(myItem) < 0)
				return;
			player.message("You put the flour in the pot");
			player.getWorld().unregisterItem(item);
			player.getCarriedItems().getInventory().add(new Item(ItemId.POT_OF_FLOUR.id()));
			return;
		}
	}

	@Override
	public void onTakeObj(Player player, GroundItem item) {
		if (item.getID() == ItemId.FLOUR.id()) {
			if (player.getCarriedItems().hasCatalogID(ItemId.POT.id())) {
				player.message("You put the flour in the pot");
				player.getWorld().unregisterItem(item);
				player.getCarriedItems().getInventory().replace(ItemId.POT.id(), ItemId.POT_OF_FLOUR.id());
			} else {
				player.message("I can't pick it up!");
				player.message("I need a pot to hold it in");
			}
			return;
		}
	}


}
