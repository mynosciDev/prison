package fr.venazia.prison.commands;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockTypes;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.signature.qual.PrimitiveType;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

public class MineResetCommand  {

    @Command("minereset")
    @Description("Reset mine")
    @CommandPermission("prison.admin")
    public void mineReset(BukkitCommandActor a, String m){
        Player p = a.asPlayer();
        if(m.equalsIgnoreCase("A") || m.equalsIgnoreCase("B") || m.equalsIgnoreCase("C") || m.equalsIgnoreCase("D") || m.equalsIgnoreCase("E") || m.equalsIgnoreCase("F") || m.equalsIgnoreCase("G") || m.equalsIgnoreCase("H") || m.equalsIgnoreCase("I") || m.equalsIgnoreCase("J") || m.equalsIgnoreCase("K") || m.equalsIgnoreCase("L") || m.equalsIgnoreCase("M") || m.equalsIgnoreCase("N") || m.equalsIgnoreCase("O") || m.equalsIgnoreCase("P") || m.equalsIgnoreCase("Q") || m.equalsIgnoreCase("R") || m.equalsIgnoreCase("S") || m.equalsIgnoreCase("T") || m.equalsIgnoreCase("U") || m.equalsIgnoreCase("V") || m.equalsIgnoreCase("W") || m.equalsIgnoreCase("X") || m.equalsIgnoreCase("Y") || m.equalsIgnoreCase("Z")) {
            p.sendMessage("§aLa mine " + m + " a été reset avec succès !");
            resetMine(p, m);
        } else {
            p.sendMessage("§cLa mine " + m + " n'existe pas !");
        }
    }

    private void resetMine(Player player, String mineName) {
        if (!player.getWorld().getName().equalsIgnoreCase("mines")) {
            player.sendMessage("§cVous devez être dans le monde des mines pour utiliser cette commande !");
            return;
        }
        String m = mineName;
        Player p = player;
        if (m.equalsIgnoreCase("A")) {
            World world = BukkitAdapter.adapt(player.getWorld());
            BlockVector3 pos1 = BlockVector3.at(-1079, 22, -1287);
            BlockVector3 pos2 = BlockVector3.at(-1103, 8, -1263);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 99.5);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 0.5);
                editSession.setBlocks(region, pattern);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (m.equalsIgnoreCase("B")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1103, 22, -1206);
            BlockVector3 pos2 = BlockVector3.at(-1079, 8, -1182);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 92.5);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 7.5);
                editSession.setBlocks(region, pattern);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (m.equalsIgnoreCase("C")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1103, 22, -1344);
            BlockVector3 pos2 = BlockVector3.at(-1079, 8, -1368);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 90);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 9);
                pattern.add(BlockTypes.COAL_ORE.getDefaultState(), 1);
                editSession.setBlocks(region, pattern);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (m.equalsIgnoreCase("D")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1168, 22, -1363);
            BlockVector3 pos2 = BlockVector3.at(-1144, 8, -1387);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 75);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 15);
                pattern.add(BlockTypes.COAL_ORE.getDefaultState(), 10);
                editSession.setBlocks(region, pattern);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (m.equalsIgnoreCase("E")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1139, 22, -1263);
            BlockVector3 pos2 = BlockVector3.at(-1163, 8, -1287);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 70);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 20);
                pattern.add(BlockTypes.COAL_ORE.getDefaultState(), 5);
                editSession.setBlocks(region, pattern);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (m.equalsIgnoreCase("F")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1223, 22, -1287);
            BlockVector3 pos2 = BlockVector3.at(-1199, 8, -1263);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 60);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 25);
                pattern.add(BlockTypes.COAL_ORE.getDefaultState(), 15);
                editSession.setBlocks(region, pattern);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (m.equalsIgnoreCase("G")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1199, 8, -1344);
            BlockVector3 pos2 = BlockVector3.at(-1223, 22, -1368);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 50);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 30);
                pattern.add(BlockTypes.COAL_ORE.getDefaultState(), 20);
                editSession.setBlocks(region, pattern);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (m.equalsIgnoreCase("H")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1259, 22, -1182);
            BlockVector3 pos2 = BlockVector3.at(-1283, 8, -1204);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 40);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 35);
                pattern.add(BlockTypes.COAL_ORE.getDefaultState(), 25);
                editSession.setBlocks(region, pattern);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (m.equalsIgnoreCase("I")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1352, 22, -935);
            BlockVector3 pos2 = BlockVector3.at(-1376, 8, -959);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 20);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 50);
                pattern.add(BlockTypes.COAL_ORE.getDefaultState(), 30);
                try {
                    editSession.setBlocks(region, pattern);
                } catch (MaxChangedBlocksException e) {
                    throw new RuntimeException(e);
                }

            }
        } else if (m.equalsIgnoreCase("J")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1093, 8, -840);
            BlockVector3 pos2 = BlockVector3.at(-1069, 22, -864);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 10);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 60);
                pattern.add(BlockTypes.COAL_ORE.getDefaultState(), 30);
                try {
                    editSession.setBlocks(region, pattern);
                } catch (MaxChangedBlocksException e) {
                    throw new RuntimeException(e);
                }

            }
        } else if (m.equalsIgnoreCase("K")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1183, 22, -842);
            BlockVector3 pos2 = BlockVector3.at(-1207, 8, -866);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 5);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 70);
                pattern.add(BlockTypes.COAL_ORE.getDefaultState(), 25);
                try {
                    editSession.setBlocks(region, pattern);
                } catch (MaxChangedBlocksException e) {
                    throw new RuntimeException(e);
                }

            }
        } else if (m.equalsIgnoreCase("L")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1199, 22, -1020);
            BlockVector3 pos2 = BlockVector3.at(-1223, 8, -1044);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 5);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 65);
                pattern.add(BlockTypes.COAL_ORE.getDefaultState(), 25);
                pattern.add(BlockTypes.COAL_BLOCK.getDefaultState(), 5);
                try {
                    editSession.setBlocks(region, pattern);
                } catch (MaxChangedBlocksException e) {
                    throw new RuntimeException(e);
                }

            }
        } else if (m.equalsIgnoreCase("M")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1163, 22, -1344);
            BlockVector3 pos2 = BlockVector3.at(-1139, 8, -1368);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 5);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 50);
                pattern.add(BlockTypes.COAL_BLOCK.getDefaultState(), 10);
                pattern.add(BlockTypes.COAL_ORE.getDefaultState(), 35);
                try {
                    editSession.setBlocks(region, pattern);
                } catch (MaxChangedBlocksException e) {
                    throw new RuntimeException(e);
                }

            }
        } else if(m.equalsIgnoreCase("N")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1163, 22, -1344);
            BlockVector3 pos2 = BlockVector3.at(-1139, 8, -1368);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 5);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 60);
                pattern.add(BlockTypes.COAL_ORE.getDefaultState(), 35);
                try {
                    editSession.setBlocks(region, pattern);
                } catch (MaxChangedBlocksException e) {
                    throw new RuntimeException(e);
                }

            }
        } else if(m.equalsIgnoreCase("O")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1163, 22, -1344);
            BlockVector3 pos2 = BlockVector3.at(-1139, 8, -1368);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 5);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 60);
                pattern.add(BlockTypes.COAL_ORE.getDefaultState(), 35);
                try {
                    editSession.setBlocks(region, pattern);
                } catch (MaxChangedBlocksException e) {
                    throw new RuntimeException(e);
                }

            }
        } else if(m.equalsIgnoreCase("P")) {
            World world = BukkitAdapter.adapt(Bukkit.getWorld("mines"));
            BlockVector3 pos1 = BlockVector3.at(-1163, 22, -1344);
            BlockVector3 pos2 = BlockVector3.at(-1139, 8, -1368);
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                RandomPattern pattern = new RandomPattern();
                pattern.add(BlockTypes.STONE.getDefaultState(), 5);
                pattern.add(BlockTypes.CRACKED_STONE_BRICKS.getDefaultState(), 60);
                pattern.add(BlockTypes.COAL_ORE.getDefaultState(), 35);
                try {
                    editSession.setBlocks(region, pattern);
                } catch (MaxChangedBlocksException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
}

