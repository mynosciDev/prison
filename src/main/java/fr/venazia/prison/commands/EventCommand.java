package fr.venazia.prison.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Suggest;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

public class EventCommand implements Listener {
    private int currentQuestionIndex = 0;
    private boolean questionAnswered = false;
    private final String[] questions = {"§eQuelle est la couleur du cheval blanc d'Henry IV ?", "§eQuelle est la capitale de la France ?", "§eCombien de lettres comporte le mot 'bonjour' ?", "Comment prestige ?", "A quel niveau peut on voyager dans le nether ?"};
    private final String[] answers = {"blanc", "paris", "7", "prestige", "65"};

    @Command("confevent")
    public void confEvent(BukkitCommandActor a, @Suggest({"QUESTIONS_CHAT", "END_PVP", "OTHER"}) String event, @Optional String name) {
        Player p = a.asPlayer();
        if(event.equalsIgnoreCase("QUESTIONS_CHAT")) {
            p.sendTitle("§a", "§e§l§oRegardez votre chat", 10, 90, 20);
            Bukkit.broadcastMessage("§b§l[Q] §eUn évènement Questions Chat a été lancé §e ! §eDémarrage dans 10 secondes.");
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("Prison"), () -> {
                Bukkit.broadcastMessage("§b§l[Q] §eL'évènement Questions Chat a démarré §e ! §aBonne chance à tous.");
                askNextQuestion();
            }, 200L);
        }
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
            Bukkit.broadcastMessage("§b§l[Q] §e" + p.getName() + " a donné la bonne réponse : " + answers[currentQuestionIndex]);
            currentQuestionIndex++;
            askNextQuestion();
        }
    }
}