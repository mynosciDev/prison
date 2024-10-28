package fr.venazia.prison.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.exception.BukkitExceptionHandler;
import revxrsal.commands.exception.CommandInvocationException;
import revxrsal.commands.exception.DefaultExceptionHandler;
import revxrsal.commands.exception.MissingArgumentException;
import revxrsal.commands.exception.NoPermissionException;
import revxrsal.commands.node.ParameterNode;

public class BukkitExceptionHandleCommand extends BukkitExceptionHandler {

    @Override
    public void onMissingArgument(MissingArgumentException e, BukkitCommandActor actor, ParameterNode<BukkitCommandActor, ?> parameter) {
        Player p = actor.asPlayer();
        String msg = "&6Usage : &a/" + e.command().usage() + " &b- " + e.command().description() + "&c\n &9&o(Arguments manquant)";
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    @Override
    public void onNoPermission(NoPermissionException e, BukkitCommandActor actor) {
        Player p = actor.asPlayer();
        String msg = "&cVous n'avez pas la permission d'exécuter cette commande.";
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }




}