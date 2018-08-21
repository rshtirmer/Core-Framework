/**
* Generated By NPCScript :: A scripting engine created for openrsc by Zilent
*/
package org.openrsc.server.npchandler.Prince_Ali_Rescue;

import org.openrsc.server.Config;
import org.openrsc.server.event.SingleEvent;
import org.openrsc.server.model.InvItem;
import org.openrsc.server.model.Npc;
import org.openrsc.server.model.ChatMessage;
import org.openrsc.server.model.MenuHandler;
import org.openrsc.server.model.World;
import org.openrsc.server.event.DelayedQuestChat;
import org.openrsc.server.model.Player;
import org.openrsc.server.model.Quest;
import org.openrsc.server.model.Quests;
import org.openrsc.server.npchandler.NpcHandler;
public class Joe implements NpcHandler {
	public void handleNpc(final Npc npc, final Player owner) throws Exception {
		npc.blockedBy(owner);
		owner.setBusy(true);
		Quest q = owner.getQuest(Quests.PRINCE_ALI_RESCUE);
		if(q != null) {
			if(q.finished()) {
				questFinished(npc, owner);
			} else {
				switch(q.getStage()) {
					case 0:
					case 1:
						questNotStarted(npc, owner);
						break;
					case 2:
						if(owner.getInventory().countId(193) > 0) {
							getJoeDrunk(npc, owner);
						} else {
							questNotStarted(npc, owner);
						}
						break;
					case 3:
						joeSmashed(npc, owner);
						break;
					case 4:
						joeSmashed(npc, owner);
						break;
					case 5:
						questFinished(npc, owner);
						break;
				}
			}
		} else {
			questNotStarted(npc, owner);
		}
	}
	
	private final void joeSmashed(final Npc npc, final Player owner) {
		final String[] messages0 = {"Halt. Who goes there?"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages0, true) {
			public void finished() {
				final String[] messages1 = {"Hello friend, I am just rescuing the prince, is that ok?"};
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages1) {
					public void finished() {
						final String[] messages2 = {"Thatsh a funny joke. you are lucky I am shober", "Go in peace, friend"};
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages2) {
							public void finished() {
								owner.setBusy(false);
								npc.unblock();
							}
						});
					}
				});
			}
		});
	}

	private final void getJoeDrunk(final Npc npc, final Player owner) {
		final String[] options1 = {"I have some beer here, fancy one?", "Tell me about the life of a guard", "What did you want to be when you were a boy", "I had better leave, I don't want trouble"};
		owner.setBusy(false);
		owner.sendMenu(options1);
		owner.setMenuHandler(new MenuHandler(options1) {
			public void handleReply(final int option, final String reply) {
				owner.setBusy(true);
				for(Player informee : owner.getViewArea().getPlayersInView()) {
					informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
				}
				switch(option) {
					case 0:
						if(owner.getInventory().countId(193) > 2) {
							threeBeer(npc, owner);
						} else {
							oneBeer(npc, owner);
						}
						break;
					case 1:
						guardLife(npc, owner);
						break;
					case 2:
						aBoy(npc, owner);
						break;
					case 3:
						betterLeave(npc, owner);
						break;
				}
			}
		});
	}

	private final void questFinished(final Npc npc, final Player owner) {
		final String[] messages0 = {"Hi friend, I am just checking out things here"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages0, true) {
			public void finished() {
				final String[] messages1 = {"The Prince got away, I am in trouble", "I better not talk to you, they are not sure I was drunk"};
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages1) {
					public void finished() {
						final String[] messages2 = {"I won't say anything, your secret is safe with me"};
						World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages2) {
							public void finished() {
								owner.setBusy(false);
								npc.unblock();
							}
						});
					}
				});
			}
		});
	}

	private final void questNotStarted(final Npc npc, final Player owner) {
		final String[] messages0 = {"Hi, I'm Joe, door guard for Lady Keli"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages0, true) {
			public void finished() {
				final String[] messages1 = {"Hi, who are you guarding here?"};
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages1) {
					public void finished() {
						final String[] messages2 = {"Can't say, all very secret. you should get out of here", "I am not supposed to talk while I guard"};
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages2) {
							public void finished() {
								World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
									public void action() {
										final String[] options0 = {"Hey, chill out, I won't cause you trouble", "Tell me about the life of a guard", "What did you want to be when you were a boy", "I had better leave, I don't want trouble"};
										owner.setBusy(false);
										owner.sendMenu(options0);
										owner.setMenuHandler(new MenuHandler(options0) {
											public void handleReply(final int option, final String reply) {
												owner.setBusy(true);
												for(Player informee : owner.getViewArea().getPlayersInView()) {
													informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
												}
												switch(option) {
													case 0:
														chillOut(npc, owner);
														break;
													case 1:
														guardLife(npc, owner);
														break;
													case 2:
														aBoy(npc, owner);
														break;
													case 3:
														betterLeave(npc, owner);
														break;
												}
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});
	}

	private void chillOut(final Npc npc, final Player owner) {
		final String[] messages3 = {"I was just wondering what you do to relax"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages3) {
			public void finished() {
				final String[] messages4 = {"You never relax with these people, but its a good career for a young man", "And some of the shouting I rather like", "RESISTANCE IS USELESS!"};
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages4) {
					public void finished() {
						World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
							public void action() {
								final String[] options1 = {"So what do you buy with these great wages?", "Tell me about the life of a guard", "Would you be interested in making a little more money?", "I had better leave, I don't want trouble"};
								owner.setBusy(false);
								owner.sendMenu(options1);
								owner.setMenuHandler(new MenuHandler(options1) {
									public void handleReply(final int option, final String reply) {
										owner.setBusy(true);
										for(Player informee : owner.getViewArea().getPlayersInView()) {
											informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
										}
										switch(option) {
											case 0:
												buyWhat(npc, owner);
												break;
											case 1:
												guardLife(npc, owner);
												break;
											case 2:
												moreMoney(npc, owner);
												break;
											case 3:
												betterLeave(npc, owner);
												break;
										}
									}
								});
							}
						});
					}
				});
			}
		});
	}

	private void betterLeave(final Npc npc, final Player owner) {
		final String[] messages5 = {"Thanks I appreciate that", "Talking on duty can be punishable by having your mouth stitched up", "These are tough people, no mistake"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages5) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void guardLife(final Npc npc, final Player owner) {
		final String[] messages6 = {"Well the hours are good.....", ".....But most of those hours are a drag", "If only I had spent more time in Knight school when I was a young boy", "Maybe I wouldn't be here now, scared of Keli"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages6) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options2 = {"Hey, chill out, I won't cause you trouble", "What did you want to be when you were a boy", "I had better leave, I don't want trouble"};
						owner.setBusy(false);
						owner.sendMenu(options2);
						owner.setMenuHandler(new MenuHandler(options2) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										chillOut(npc, owner);
										break;
									case 1:
										aBoy(npc, owner);
										break;
									case 2:
										betterLeave(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private void aBoy(final Npc npc, final Player owner) {
		final String[] messages7 = {"Well, I loved to sit by the lake, with my toes in the water", "And shoot the fish with my bow and arrow"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages7) {
			public void finished() {
				final String[] messages8 = {"That was a strange hobby for a little boy"};
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages8) {
					public void finished() {
						final String[] messages9 = {"It kept us from goblin hunting, which was what most boys did", "What are you here for?"};
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages9) {
							public void finished() {
								World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
									public void action() {
										final String[] options3 = {"Chill out, I won't cause you trouble", "Tell me about the life of a guard", "I had better leave, I don't want trouble"};
										owner.setBusy(false);
										owner.sendMenu(options3);
										owner.setMenuHandler(new MenuHandler(options3) {
											public void handleReply(final int option, final String reply) {
												owner.setBusy(true);
												for(Player informee : owner.getViewArea().getPlayersInView()) {
													informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
												}
												switch(option) {
													case 0:
														chillOut(npc, owner);
														break;
													case 1:
														guardLife(npc, owner);
														break;
													case 2:
														betterLeave(npc, owner);
														break;
												}
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});
	}

	private void buyWhat(final Npc npc, final Player owner) {
		final String[] messages10 = {"Really, after working here, theres only time for a drink or three", "All us guards go to the same bar, And drink ourselves stupid", "Its what I enjoy these days, that fade into unconciousness", "I can't resist the sight of a really cold beer"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages10) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options4 = {"Tell me about the life of a guard", "What did you want to be when you were a boy", "I had better leave, I don't want trouble"};
						owner.setBusy(false);
						owner.sendMenu(options4);
						owner.setMenuHandler(new MenuHandler(options4) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										guardLife(npc, owner);
										break;
									case 1:
										aBoy(npc, owner);
										break;
									case 2:
										betterLeave(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private void moreMoney(final Npc npc, final Player owner) {
		final String[] messages11 = {"WHAT! are you trying to bribe me?", "I may not be a great guard, but I am loyal", "How DARE you try to bribe me!"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages11) {
			public void finished() {
				final String[] messages12 = {"No, no, you got the wrong idea, totally", "I just wondered if you wanted some part-time bodyguard work"};
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages12) {
					public void finished() {
						final String[] messages13 = {"Oh. Sorry. No, I don't need money", "As long as you were not offering me a bribe"};
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages13) {
							public void finished() {
								World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
									public void action() {
										final String[] options5 = {"Tell me about the life of a guard", "What did you want to be when you were a boy", "I had better leave, I don't want trouble"};
										owner.setBusy(false);
										owner.sendMenu(options5);
										owner.setMenuHandler(new MenuHandler(options5) {
											public void handleReply(final int option, final String reply) {
												owner.setBusy(true);
												for(Player informee : owner.getViewArea().getPlayersInView()) {
													informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
												}
												switch(option) {
													case 0:
														guardLife(npc, owner);
														break;
													case 1:
														aBoy(npc, owner);
														break;
													case 2:
														betterLeave(npc, owner);
														break;
												}
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});
	}

	private void oneBeer(final Npc npc, final Player owner) {
		final String[] messages9 = {"Ah that would be lovely, just one now, just to wet my throat"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages9) {
			public void finished() {
				final String[] messages10 = {"Of course, it must be tough being here without a drink"};
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages10) {
					public void finished() {
						owner.sendMessage("You hand a beer to the guard, he drinks it in seconds");
						owner.getInventory().remove(new InvItem(193, 1));
						owner.sendInventory();
						final String[] messages11 = {"Thas was perfect, i cant thank you enough"};
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages11) {
							public void finished() {
								final String[] messages12 = {"How are you? Still ok. Not too drunk?"};
								World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages12) {
									public void finished() {
										final String[] messages13 = {"No, I don't get drunk with only one drink", "You would need a few to do that, but thanks for the beer"};
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages13) {
											public void finished() {
												owner.setBusy(false);
												npc.unblock();
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});
	}

	private void threeBeer(final Npc npc, final Player owner) {
		final String[] messages9 = {"Ah that would be lovely, just one now, just to wet my throat"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages9) {
			public void finished() {
				final String[] messages10 = {"Of course, it must be tough being here without a drink"};
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages10) {
					public void finished() {
						owner.sendMessage("You hand a beer to the guard, he drinks it in seconds");
						owner.getInventory().remove(new InvItem(193, 1));
						owner.sendInventory();
						final String[] messages11 = {"Thas was perfect, i cant thank you enough"};
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages11) {
							public void finished() {
								final String[] messages14 = {"Would you care for another, my friend?"};
								World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages14) {
									public void finished() {
										final String[] messages15 = {"I better not, I don't want to be drunk on duty"};
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages15) {
											public void finished() {
												final String[] messages16 = {"Here, just take these for later, I hate to see a thirsty guard"};
												World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages16) {
													public void finished() {
														owner.sendMessage("You hand two more beers to the guard");
														owner.getInventory().remove(new InvItem(193, 1));
														owner.getInventory().remove(new InvItem(193, 1));
														owner.sendInventory();
														World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
															public void action() {
																owner.sendMessage("he takes a sip of one, and then drinks them both");
																final String[] messages17 = {"Franksh, that wash just want I need to shtay on guard", "No more beersh, I don't want to get drunk"};
																World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages17) {
																	public void finished() {
																		owner.sendMessage("The guard is drunk, and no longer a problem");
																		owner.incQuestCompletionStage(Quests.PRINCE_ALI_RESCUE);
																		owner.setBusy(false);
																		npc.unblock();
																	}
																});
															}
														});
													}
												});
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});
	}
}