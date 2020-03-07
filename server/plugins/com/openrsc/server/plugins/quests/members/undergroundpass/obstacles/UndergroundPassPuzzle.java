package com.openrsc.server.plugins.quests.members.undergroundpass.obstacles;

import com.openrsc.server.model.Point;
import com.openrsc.server.constants.Skills;
import com.openrsc.server.model.entity.GameObject;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.plugins.triggers.OpLocTrigger;

import static com.openrsc.server.plugins.Functions.*;

public class UndergroundPassPuzzle implements OpLocTrigger {

	public static int WALK_HERE_ROCK_EAST = 792;
	public static int WALK_HERE_ROCK_WEST = 793;
	public static int FAIL_GRILL = 782;
	public static int LEVER = 801;
	public static int CAGE = 802;

	/**
	 * Tile puzzle grills
	 **/
	public static int[] WORKING_GRILLS = {777, 785, 786, 787, 788, 789, 790, 791};

	@Override
	public boolean blockOpLoc(GameObject obj, String command, Player p) {
		return inArray(obj.getID(), WORKING_GRILLS) || obj.getID() == FAIL_GRILL || obj.getID() == WALK_HERE_ROCK_EAST
				|| obj.getID() == WALK_HERE_ROCK_WEST || obj.getID() == LEVER;
	}

	@Override
	public void onOpLoc(GameObject obj, String command, Player p) {
		if (inArray(obj.getID(), WORKING_GRILLS)) {
			moveForward(p, obj);
		}
		else if (obj.getID() == FAIL_GRILL) {
			trap(p, obj);
		}
		else if (obj.getID() == WALK_HERE_ROCK_EAST) {
			p.teleport(679, 3447);
		}
		else if (obj.getID() == WALK_HERE_ROCK_WEST) {
			p.walkToEntity(689, 3452);
		}
		else if (obj.getID() == LEVER) {
			message(p, "you pull on the lever",
				"you hear a loud mechanical churning");
			GameObject cage_closed = new GameObject(p.getWorld(), Point.location(690, 3449), CAGE, 6, 0);
			GameObject cage_open = new GameObject(p.getWorld(), Point.location(690, 3449), CAGE + 1, 6, 0);
			p.getWorld().registerGameObject(cage_open);
			p.getWorld().delayedSpawnObject(cage_closed.getLoc(), 5000);
			p.message("as the huge railing raises to the cave roof");
			p.message("the cage lowers behind you");
			p.teleport(690, 3451);
		}
	}

	private void trap(Player p, GameObject obj) {
		message(p, "you step onto the metal grill");
		p.message("it's a trap");
		p.teleport(711, 3464);
		sleep(1600);
		message(p, "you fall onto a pit of spikes");
		p.teleport(679, 3448);
		p.damage((int) (getCurrentLevel(p, Skills.HITS) * 0.2D));
		p.message("you crawl out of the pit");
		p.getWorld().replaceGameObject(obj,
			new GameObject(obj.getWorld(), obj.getLocation(), 778, obj.getDirection(), obj
				.getType()));
		p.getWorld().delayedSpawnObject(obj.getLoc(), 1000);
		sleep(1600);
		p.message("and off the metal grill");
	}

	private void moveForward(Player p, GameObject obj) {
		p.message("you step onto the metal grill");
		p.message("you tread carefully as you move forward");
		if (obj.getID() == 777) {
			p.teleport(681, 3446);
		} else if (obj.getID() == 785) {
			p.teleport(683, 3446);
		} else if (obj.getID() == 786) {
			p.teleport(683, 3448);
		} else if (obj.getID() == 787) {
			p.teleport(685, 3448);
		} else if (obj.getID() == 788) {
			p.teleport(687, 3448);
		} else if (obj.getID() == 789) {
			p.teleport(687, 3450);
		} else if (obj.getID() == 790) {
			p.teleport(687, 3452);
		} else if (obj.getID() == 791) {
			p.teleport(689, 3452);
		}
	}
}
