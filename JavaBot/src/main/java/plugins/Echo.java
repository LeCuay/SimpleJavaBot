package plugins;

import configuration.Config;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class Echo extends AbstractPlugin {

	public Echo(JDA bot, Message message, MessageChannel channel) {
		super(bot, message, channel);
	}

	/**
	 * The bot will repeat everything you say.
	 */
	@Override
	public void run() {
		String msg = message.getContentDisplay().replaceAll(Config.getPrefix()+pluginName, "");
		if(!msg.equals(""))
			channel.sendMessage("You said: **"+msg+"**!").queue();
		else
			channel.sendMessage("You said nothing!").queue();
	}

	@Override
	public String getDescription() {
		return "The bot will repeat what you said.";
	}

	@Override
	public String getUsage() {
		return commandPrefix+"<Text> - Will repeat that text.";
	}

	@Override
	public String getCategory() {
		return "Fun";
	}

}
