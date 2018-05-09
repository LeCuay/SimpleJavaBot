package plugins;

import configuration.Config;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class Shutdown extends AbstractPlugin {

	public Shutdown(JDA bot, Message message, MessageChannel channel) {
		super(bot, message, channel);
	}

	/**
	 * The bot will turn off.
	 */
	@Override
	public void run() {
		if(message.getAuthor().getId().equals(Config.getOwnerID())) {
			channel.sendMessage("Bye, bye~").queue();
			bot.shutdown();
		} else {
			channel.sendMessage("You are not the Owner of the Bot!").queue();
		}
	}

	@Override
	public String getDescription() {
		return "This will shut down the Bot";
	}

	@Override
	public String getUsage() {
		return commandPrefix+" - To turn off the bot.";
	}

	@Override
	public String getCategory() {
		return "Owner Commands";
	}
	
	
}
