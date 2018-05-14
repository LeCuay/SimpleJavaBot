package plugins;

import configuration.Config;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import utilities.CapitalizeString;

/**
 * Abstract Class to standardize the Plugins.
 * @author LeCuay
 * @version 0.1
 */
public abstract class AbstractPlugin {
	
	/** The bot will be asked by every Constructor since it's necessary for the plugin to work. */
	protected static JDA bot;
	
	/** This represents the message sended by the User. */
	protected static Message message;
	
	/** This variables does reference to the channel the {@code message} comes from. */
	protected static MessageChannel channel;
	
	/** The name of the plugin. */
	protected static String pluginName;
	
	/** The description of the plugin. */
	protected static String description;
	
	/** The different ways to use the plugin. */
	protected static String usage;
	
	/** The category this plugin belongs to. */
	protected static String category;
        
	/** The prefix this command use to initialize. */
	protected static String commandPrefix;

	/** Bot's prefix */
	protected static String botPrefix = Config.getPrefix();
	
	/**
	 * Obligatory constructor for each Plugin.
	 * @param bot The Bot we are working with.
	 * @param message The message sended by the User.
	 * @param channel The channel were that {@code message} was caught.
	 */
	public AbstractPlugin(JDA bot, Message message, MessageChannel channel) {
		AbstractPlugin.bot = bot;
		AbstractPlugin.message = message;
		AbstractPlugin.channel = channel;
		AbstractPlugin.pluginName = getPluginName();
		AbstractPlugin.description = getDescription();
		AbstractPlugin.commandPrefix = Config.getPrefix() + pluginName;
		AbstractPlugin.usage = commandPrefix + getUsage();
		AbstractPlugin.category = getCategory();
	}
	
	/**
	 * This method will run the plugin itself.
	 */
	public abstract void run();
	
	/**
	 * Obligatory method to writes a description.
	 * @return The description of the plugin.
	 */
	public abstract String getDescription();
	
	/**
	 * Obligatory method to sets an usage. <br>
	 * @return The usage of the plugin.
	 */
	public abstract String getUsage();
	
	/**
	 * Obligatory metho to sets a category.
	 * @return The category this plugins belongs to.
	 */
	public abstract String getCategory();
	
	/**
	 * Gets Class' name.
	 * @return Class' name as Plugin's name.
	 */
	public String getPluginName() {
		String nameOfPlugin = this.getClass().getSimpleName();
		return CapitalizeString.decapitalizeByFirstLetter(nameOfPlugin);
	}
	
	public String getCommandPrefix(){return commandPrefix;}
	
	/**
	 * Method created to load plugins as {@code modules}. <br>
	 * It works the same way <b><i>__import__</i></b> from Python.
	 * @param pluginName The plugin to load.
	 * @return The Plugin Object initializated.
	 * @throws ClassNotFoundException In case that plugin doesn't exist.
	 */
	protected static AbstractPlugin loadPlugin(String pluginName) throws ClassNotFoundException {
		Class<?> pluginClass = Class.forName("plugins." + CapitalizeString.byFirstLetter(pluginName));
		Constructor<?> pluginConst = pluginClass.getConstructors()[0];
		Object pluginObj = new Object();
		try {
			pluginObj = pluginConst.newInstance(bot, message, channel);
		} catch (IllegalArgumentException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
			System.err.println("Variables might not be initializated.");
		}
		return (AbstractPlugin) pluginObj;
	}

}
