package com.openrsc.server.plugins.quests.members.undergroundpass.npcs;

import com.openrsc.server.constants.*;
import com.openrsc.server.model.container.Item;
import com.openrsc.server.model.entity.GameObject;
import com.openrsc.server.model.entity.GroundItem;
import com.openrsc.server.model.entity.npc.Npc;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.plugins.triggers.UseBoundTrigger;
import com.openrsc.server.plugins.triggers.OpLocTrigger;
import com.openrsc.server.plugins.triggers.TakeObjTrigger;
import com.openrsc.server.plugins.triggers.OpBoundTrigger;

import java.util.Optional;

import static com.openrsc.server.plugins.Functions.*;

public class UndergroundPassKardiaTheWitch implements OpLocTrigger, OpBoundTrigger, TakeObjTrigger, UseBoundTrigger {

	/**
	 * OBJECT IDs
	 **/
	private static int WITCH_RAILING = 172;
	private static int WITCH_DOOR = 173;
	private static int WITCH_CHEST = 885;

	@Override
	public boolean blockOpBound(GameObject obj, Integer click, Player player) {
		return obj.getID() == WITCH_RAILING || obj.getID() == WITCH_DOOR;
	}

	@Override
	public void onOpBound(GameObject obj, Integer click, Player p) {
		if (obj.getID() == WITCH_RAILING) {
			message(p, "inside you see Kardia the witch");
			p.message("her appearence make's you feel quite ill");
		}
		else if (obj.getID() == WITCH_DOOR) {
			if (click == 0) {
				if (p.getCache().hasKey("kardia_cat")) {
					p.message("you open the door");
					doDoor(obj, p);
					message(p, "and walk through");
					p.message("the witch is busy talking to the cat");
				} else {
					Npc witch = getNearestNpc(p, NpcId.KARDIA_THE_WITCH.id(), 5);
					p.message("you reach to open the door");
					npcTalk(p, witch, "get away...far away from here");
					sleep(1000);
					p.message("the witch raises her hands above her");
					displayTeleportBubble(p, p.getX(), p.getY(), true);
					p.damage(((int) getCurrentLevel(p, Skills.HITS) / 5) + 5); // 6 lowest, 25 max.
					npcTalk(p, witch, "haa haa.. die mortal");
				}
			} else if (click == 1) {
				if (p.getCarriedItems().hasCatalogID(ItemId.KARDIA_CAT.id(), Optional.of(false)) && !p.getCache().hasKey("kardia_cat")) {
					message(p, "you place the cat by the door");
					removeItem(p, ItemId.KARDIA_CAT.id(), 1);
					p.teleport(776, 3535);
					message(p, "you knock on the door and hide around the corner");
					p.message("the witch takes the cat inside");
					if (!p.getCache().hasKey("kardia_cat")) {
						p.getCache().store("kardia_cat", true);
					}
				} else if (p.getCache().hasKey("kardia_cat")) {
					message(p, "there is no reply");
					p.message("inside you can hear the witch talking to her cat");
				} else {
					message(p, "you knock on the door");
					p.message("there is no reply");
				}
			}
		}
	}

	@Override
	public boolean blockTakeObj(Player p, GroundItem i) {
		return i.getID() == ItemId.KARDIA_CAT.id() && p.getCarriedItems().hasCatalogID(ItemId.KARDIA_CAT.id(), Optional.of(false));
	}

	@Override
	public void onTakeObj(Player p, GroundItem i) {
		if (i.getID() == ItemId.KARDIA_CAT.id() && p.getCarriedItems().hasCatalogID(ItemId.KARDIA_CAT.id(), Optional.of(false))) {
			message(p, "it's not very nice to squeeze one cat into a satchel");
			p.message("...two's just plain cruel!");
		}
	}

	@Override
	public boolean blockUseBound(GameObject obj, Item item, Player p) {
		return obj.getID() == WITCH_DOOR && item.getCatalogId() == ItemId.KARDIA_CAT.id();
	}

	@Override
	public void onUseBound(GameObject obj, Item item, Player p) {
		if (obj.getID() == WITCH_DOOR && item.getCatalogId() == ItemId.KARDIA_CAT.id()) {
			if (!p.getCache().hasKey("kardia_cat")) {
				message(p, "you place the cat by the door");
				removeItem(p, ItemId.KARDIA_CAT.id(), 1);
				p.teleport(776, 3535);
				message(p, "you knock on the door and hide around the corner");
				p.message("the witch takes the cat inside");
				if (!p.getCache().hasKey("kardia_cat")) {
					p.getCache().store("kardia_cat", true);
				}
			} else {
				message(p, "the witch is busy playing...");
				p.message("with her other cat");
			}
		}
	}

	@Override
	public boolean blockOpLoc(GameObject obj, String command, Player p) {
		return obj.getID() == WITCH_CHEST;
	}

	@Override
	public void onOpLoc(GameObject obj, String command, Player p) {
		if (obj.getID() == WITCH_CHEST) {
			message(p, "you search the chest");
			if (p.getQuestStage(Quests.UNDERGROUND_PASS) == 6 && !p.getCache().hasKey("doll_of_iban")) {
				p.message("..inside you find a book a wooden doll..");
				p.message("...and two potions");
				addItem(p, ItemId.A_DOLL_OF_IBAN.id(), 1);
				addItem(p, ItemId.OLD_JOURNAL.id(), 1);
				addItem(p, ItemId.FULL_SUPER_ATTACK_POTION.id(), 1);
				addItem(p, ItemId.FULL_STAT_RESTORATION_POTION.id(), 1);
				if (!p.getCache().hasKey("doll_of_iban")) {
					p.getCache().store("doll_of_iban", true);
				}
			} else {
				p.message("but you find nothing of interest");
			}
		}
	}
}
