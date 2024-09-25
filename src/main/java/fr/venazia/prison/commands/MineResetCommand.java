package fr.venazia.prison.commands;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockTypes;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MineResetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (strings.length == 0) {
                p.sendMessage("§cUsage: /minereset <mine>");
            } else {
                if (strings[0].equalsIgnoreCase("A") || strings[0].equalsIgnoreCase("B") || strings[0].equalsIgnoreCase("C") || strings[0].equalsIgnoreCase("D") || strings[0].equalsIgnoreCase("E") || strings[0].equalsIgnoreCase("F") || strings[0].equalsIgnoreCase("G") || strings[0].equalsIgnoreCase("H") || strings[0].equalsIgnoreCase("I") || strings[0].equalsIgnoreCase("J") || strings[0].equalsIgnoreCase("K") || strings[0].equalsIgnoreCase("L") || strings[0].equalsIgnoreCase("M") || strings[0].equalsIgnoreCase("N") || strings[0].equalsIgnoreCase("O") || strings[0].equalsIgnoreCase("P") || strings[0].equalsIgnoreCase("Q") || strings[0].equalsIgnoreCase("R") || strings[0].equalsIgnoreCase("S") || strings[0].equalsIgnoreCase("T") || strings[0].equalsIgnoreCase("U") || strings[0].equalsIgnoreCase("V") || strings[0].equalsIgnoreCase("W") || strings[0].equalsIgnoreCase("X") || strings[0].equalsIgnoreCase("Y") || strings[0].equalsIgnoreCase("Z")) {
                    p.sendMessage("§aLa mine " + strings[0] + " a été reset avec succès !");
                    resetMine(p, strings[0]);
                } else {
                    p.sendMessage("§cLa mine " + strings[0] + " n'existe pas !");
                }
            }
        } else {
            if (strings.length == 0) {
                commandSender.sendMessage("§cUsage: /minereset <mine>");
            }
        }
        return true;
    }

    private void resetMine(Player player, String mineName) {
        if(!player.getWorld().getName().equalsIgnoreCase("mines")){
            player.sendMessage("§cVous devez être dans le monde des mines pour utiliser cette commande !");
            return;
        }
        String m = mineName;
        Player p = player;
        if(m.equalsIgnoreCase("A")) {
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
        } else if(m.equalsIgnoreCase("B")){
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
        }
    }
}
