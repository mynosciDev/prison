package fr.venazia.prison.utils;

import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.exception.BukkitExceptionHandler;
import revxrsal.commands.bukkit.exception.InvalidPlayerException;

import static revxrsal.commands.bukkit.util.BukkitUtils.legacyColorize;

public class ExceptionHandler extends BukkitExceptionHandler {

    @Override
    public void onInvalidPlayer(InvalidPlayerException e, BukkitCommandActor actor) {
        actor.error(legacyColorize("&cJoueur invalide: &e" + e.input() + "&c."));
    }
}
