package com.openrsc.server.plugins.misc;

import com.openrsc.server.constants.ItemId;
import com.openrsc.server.external.SpellDef;
import com.openrsc.server.model.container.Item;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.net.rsc.handlers.SpellHandler;
import com.openrsc.server.plugins.Functions;
import com.openrsc.server.plugins.triggers.SpellInvTrigger;

import static com.openrsc.server.plugins.Functions.delay;

public class EnchantDragonstoneRing implements SpellInvTrigger {
	@Override
	public boolean blockSpellInv(Player p, Integer itemID, Integer spellID) {
		return (p.getWorld().getServer().getConfig().WANT_EQUIPMENT_TAB && itemID.intValue() == ItemId.DRAGONSTONE_RING.id() && spellID.intValue() == 42);
	}

	@Override
	public void onSpellInv(Player p, Integer itemID, Integer spellID) {
		SpellDef spellDef = p.getWorld().getServer().getEntityHandler().getSpellDef(spellID.intValue());
		if (spellDef == null)
			return;
		Item item = p.getCarriedItems().getInventory().get(
			p.getCarriedItems().getInventory().getLastIndexById(ItemId.DRAGONSTONE_RING.id()));
		if (item.getItemStatus().getNoted()) return;

		if (itemID.intValue() == ItemId.DRAGONSTONE_RING.id()) {
			p.message("What type of dragonstone ring would you like to make?");
			delay(600);
			int choice = Functions.multi(p, "Ring of Wealth", "Ring of Avarice");
			int i;
			if (choice == 0) {
				i = ItemId.RING_OF_WEALTH.id();
			} else if (choice == 1) {
				i = ItemId.RING_OF_AVARICE.id();
			} else {
				return;
			}
			SpellHandler.checkAndRemoveRunes(p,spellDef);
			Item toRemove = new Item(item.getCatalogId(), 1, false, item.getItemId());
			p.getCarriedItems().remove(toRemove, false);
			p.getCarriedItems().getInventory().add(new Item(i));
			SpellHandler.finalizeSpell(p, spellDef, "You succesfully enchant the ring");
		}
	}
	/*@Override
	public boolean blockPlayerMageItem(Player player, int itemID, int spell) {
		return (spell == 42 && itemID == ItemId.DRAGONSTONE_RING.id());
	}

	@Override
	public void onPlayerMageItem(Player player, int itemID, int spell) {
		SpellDef spellDef = EntityHandler.getSpellDef(spell);
		if (spellDef == null)
			return;

		if (itemID == ItemId.DRAGONSTONE_RING.id()) {
			player.message("What type of dragonstone ring would you like to make?");
			sleep(600);
			int choice = Functions.showMenu(player, "Ring of Wealth", "Ring of Avarice");
			if (choice == 0) {
				itemID = ItemId.RING_OF_WEALTH.id();
			} else if (choice == 1) {
				itemID = ItemId.RING_OF_AVARICE.id();
			} else {
				player.message("No choice made.");
				return;
			}
			SpellHandler.checkAndRemoveRunes(player,spellDef);
			player.getCarriedItems().getInventory().remove(ItemId.DRAGONSTONE_RING.id());
			player.getCarriedItems().getInventory().add(new Item(itemID));
			SpellHandler.finalizeSpell(player, spellDef);
		}
	}*/
}
