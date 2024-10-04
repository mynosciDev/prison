package fr.venazia.prison.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Suggest;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

public class EventCommand implements Listener {
    private int currentQuestionIndex = 0;
    private boolean questionAnswered = false;
    private boolean laby = false;
    private final String[] questions = {"§eQuelle est la couleur du cheval blanc d'Henry IV ?", "§eQuelle est la capitale de la France ?", "§eCombien de lettres comporte le mot 'bonjour' ?", "Comment prestige ?", "A quel niveau peut on voyager dans le nether ?"};
    private final String[] answers = {"blanc", "paris", "7", "prestige", "65"};


    @Command("confevent")
    @CommandPermission("prison.event")
    public void confEvent(BukkitCommandActor a, @Suggest({"QUESTIONS_CHAT", "END_PVP", "OTHER"}) String event, @Optional String name) {
        Player p = a.asPlayer();
        if(event.equalsIgnoreCase("QUESTIONS_CHAT")) {
            p.sendTitle("§a", "§e§l§oRegardez votre chat", 10, 90, 20);
            p.sendMessage("§b§lQCHAT_EVENT §e§l=> §a§ltrue");
            Bukkit.broadcastMessage("§b§l[Q] §eUn évènement Questions Chat a été lancé §e ! §eDémarrage dans 10 secondes.");
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("Prison"), () -> {
                Bukkit.broadcastMessage("§b§l[Q] §eL'évènement Questions Chat a démarré §e ! §aBonne chance à tous.");
                askNextQuestion();
            }, 200L);
        } else if(event.equalsIgnoreCase("OTHER")){
            if(name == null) {
                p.sendMessage("§cUsage: /confevent OTHER <nom_de_l'évènement>");
                return;
            }
            if(name.equalsIgnoreCase("LABY")) {
                p.sendMessage("§b§lLABY_EVENT §e§l=> §a§ltrue");
                Bukkit.broadcastMessage("§e§l[EVENT] §eUn évènement Labyrinthe a été lancé §e ! §aPour y accéder faites /laby\n§4Attention: Votre inventaire est vidé à l'entrée du labyrinthe.");
                laby = true;
            }
        }
    }

    @Command("laby")
    public void laby(BukkitCommandActor a) {
        Player p = a.asPlayer();
        if(!laby) {
            p.sendMessage("§c§oAucun évènement §5§l§oLabyrinthe §c§on'est en cours.");
            return;
        }
        p.sendMessage("§aVous avez rejoint l'évènement §5§l§oLabyrinthe§c§o. Bonne chance !");
        p.teleport(new Location(Bukkit.getWorld("events"), 140, -60, -204));
    }

    private void askNextQuestion() {
        if (currentQuestionIndex < questions.length) {
            questionAnswered = false;
            Bukkit.broadcastMessage("§b§l[Q] §e" + questions[currentQuestionIndex]);
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("Prison"), () -> {
                if (!questionAnswered) {
                    Bukkit.broadcastMessage("§b§l[Q] §cFin du temps imparti pour répondre à la question. La réponse était : " + answers[currentQuestionIndex]);
                    currentQuestionIndex++;
                    askNextQuestion();
                }
            }, 500L);
        } else {
            Bukkit.broadcastMessage("§b§l[Q] §eL'évènement Questions Chat est terminé. Merci d'avoir participé !");
        }
    }

    @EventHandler
    public void onChatEvent(PlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage().toLowerCase();
        if (!questionAnswered && currentQuestionIndex < questions.length && message.equals(answers[currentQuestionIndex])) {
            questionAnswered = true;
            e.setCancelled(true);
            Bukkit.broadcastMessage("§b§l[Q] §e" + p.getName() + " a donné la bonne réponse : " + answers[currentQuestionIndex]);
            currentQuestionIndex++;
            askNextQuestion();
        }
    }
}