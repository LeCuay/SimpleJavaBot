package plugins;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import utilities.CapitalizeString;

import utilities.OperativeSystem;

public class Info extends AbstractPlugin {

    public Info(JDA bot, Message message, MessageChannel channel) {
        super(bot, message, channel);
    }
    
    /**
     * Creates a Embed with Bot information.
     * @return The rich embed with information.
     */
    private static EmbedBuilder writeInformationEmbed() {
        EmbedBuilder embed = new EmbedBuilder();
        String OS = OperativeSystem.getOS();
        String owner = "Cuayteron (LeCuay, Chechu-san...)";
        String version = "3.6.0_362";
        
        embed.setTitle("Bot Information");
        embed.addField("API Version", version, true);
        embed.addField("Author", owner, true);
        embed.addField("Operative System", CapitalizeString.byFirstLetter(OS), true);
        
        embed.setThumbnail(bot.getSelfUser().getAvatarUrl());
        
        return embed;
    }
    
    @Override
    public void run() {
        channel.sendMessage(writeInformationEmbed().build()).queue();
    }

    @Override
    public String getDescription() {
        return "Gives information about the Operative System the bot's running.";
    }

    @Override
    public String getUsage() {
        return commandPrefix+" - To get general information.";
    }

    @Override
    public String getCategory() {
        return "Meta";
    }

    
}