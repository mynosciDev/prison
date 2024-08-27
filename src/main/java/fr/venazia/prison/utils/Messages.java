package fr.venazia.prison.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {
    public static String PREFIX = "§8[§6Prison§8] ";
    public static String NO_PERMISSION = PREFIX + "§cVous n'avez pas la permission d'executer cette commande";
    public static String PLAYER_ONLY = PREFIX + "§cVous devez être un joueur pour executer cette commande";

    public static void sendMessage(Player p, String m){
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
    }

    public static void sendCentered(Player p, String m){
        int messagePxSize = 0;
        m = ChatColor.translateAlternateColorCodes('&', m);
        boolean previousCode = false;
        boolean isBold = false;
        for(char c : m.toCharArray()){
            if(c == '§'){
                previousCode = true;
                continue;
            } else if(previousCode == true){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                    continue;
                } else isBold = false;
            } else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }
        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = 154 - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        p.sendMessage(sb.toString() + m);
    }

}