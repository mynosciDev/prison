package fr.venazia.prison.commands;

import fr.venazia.prison.utils.DataUtils;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@Command("money")
public class MoneyCommand {

    @Subcommand("add")
    @CommandPermission("money.add")
    public void addMoney(BukkitCommandActor actor, Player target, double amount) {
        double money = Double.parseDouble(String.valueOf(DataUtils.readValue("money", target.getUniqueId().toString())));
        DataUtils.writeValue("money", target.getUniqueId().toString(), String.valueOf(money + amount));
        actor.reply("§eVous avez ajouté §b" + amount + "§e€ à §b" + target.getName() + "§e !");
        target.sendMessage("§eVous avez reçu §b" + amount + "§e€");
    }

    @Subcommand("remove")
    @CommandPermission("money.remove")
    public void removeMoney(BukkitCommandActor actor, Player target, double amount) {
        double money = Double.parseDouble(String.valueOf(DataUtils.readValue("money", target.getUniqueId().toString())));
        DataUtils.writeValue("money", target.getUniqueId().toString(), String.valueOf(money - amount));
        actor.reply("§eVous avez retiré §b" + amount + "§e€ à §b" + target.getName() + "§e !");
        target.sendMessage("§eVous avez perdu §b" + amount + "§e€");
    }

    @Subcommand("set")
    @CommandPermission("money.set")
    public void setMoney(BukkitCommandActor actor, Player target, double amount) {
        DataUtils.writeValue("money", target.getUniqueId().toString(), String.valueOf(amount));
        actor.reply("§eVous avez défini §b" + target.getName() + "§e à §b" + amount + "§e€ !");
        target.sendMessage("§eVous avez été défini à §b" + amount + "§e€");
    }

    @Command("bal")
    @CommandPermission("money.bal")
    public void checkMoney(BukkitCommandActor actor, @Optional Player target) {
        Player player = target != null ? target : actor.requirePlayer();
        double money = Double.parseDouble(String.valueOf(DataUtils.readValue("money", player.getUniqueId().toString())));
        actor.reply("§eLe joueur §b" + player.getName() + " §epossède actuellement §b" + money + "§e€ !");
    }
}