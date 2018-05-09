package plugins;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.EmbedBuilder;

import configuration.Config;

import org.reflections.scanners.SubTypesScanner;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Set;
import java.awt.Color;
import java.util.HashSet;

/**
 *
 * @author LeCuay
 */
public class Help extends AbstractPlugin {
    
    /**
     * The list of every plugin in package <b>"plugins"</b>.
     */
    protected static ArrayList<String> listOfPlugins = getPluginsInFolder();

    public Help(JDA bot, Message message, MessageChannel channel) {
        super(bot, message, channel);
    }
    
    /**
     * This method requires {@link Reflections} in order to get every Class in a package. <br>
     * It ignores {@link AbstractPlugin} and remove <b>"plugins."</b> from the final String <br>
     * before adding it to the {@code ArrayList}.
     * @return An {@code ArrayList<String>} with every plugin.
     */
    private static ArrayList<String> getPluginsInFolder(){
        // We use Reflections to navigate the package "plugins"
        Reflections reflect = new Reflections("plugins", new SubTypesScanner(false));
        // Getting every Class "plugins.Class"
        Set<Class<? extends Object>> allPlugins = reflect.getSubTypesOf(Object.class);
        
        ArrayList<String> plugins = new ArrayList();
        
        // Doing action for-each element in package "plugins"
        allPlugins.forEach(plugin -> {
            // Ignores "AbstractPlugin"
            if (!plugin.getCanonicalName().startsWith("plugins.Abstract")){
                // Removing package reference from the final String
                plugins.add(plugin.getCanonicalName().replaceAll("plugins.", ""));
            }
        });
        
        return plugins;
    }
    
    /**
     * This method creates an Embed with commands separated by categories.
     * @return The embed with the Help message.
     * @throws ClassNotFoundException In case a plugin doesn't exist.
     */
    private EmbedBuilder writeEmbedHelpMsg() throws ClassNotFoundException {
        EmbedBuilder embed = new EmbedBuilder();
        Set<String> categories = getCategories(listOfPlugins);
        StringBuilder helpMsg = new StringBuilder("\n");
        categories.forEach(eachCategory -> {
            helpMsg.append("**").append(eachCategory).append("**\n");
            listOfPlugins.forEach(plugin -> {
                try {
                    if(eachCategory.equals(loadPlugin(plugin).getCategory())) {
                        helpMsg.append("`").append(loadPlugin(plugin).getPluginName()).append("`").append(", ");
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println("Plugin not found.");
                }
            });
            helpMsg.delete(helpMsg.length() - 2, helpMsg.length());
            helpMsg.append("\n\n");
        });
        embed.setTitle("List of Commands:");
        embed.setDescription("Remember you can use: `"+
                Config.getPrefix()+pluginName+" <command>`"+
                helpMsg.toString());
        embed.setColor(Color.pink);
        
        return embed;
    }
    
    private EmbedBuilder writeCommandHelpMsg(String plugin) throws ClassNotFoundException {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Command "+Config.getPrefix()+loadPlugin(plugin).getPluginName());
        StringBuilder helpMsg = new StringBuilder("");
        
        // Getting command
        helpMsg.append("**__Command__**: `").append(loadPlugin(plugin).getPluginName()).append("`");
        // Getting description
        helpMsg.append("\n**__Description__**: *").append(loadPlugin(plugin).getDescription()).append("*");
        // Getting usage
        helpMsg.append("\n**__Usage__**: `").append(loadPlugin(plugin).getUsage()).append("`");
        
        embed.setDescription(helpMsg.toString());
        return embed;
    }
    
    /**
     * Gets every different category in each plugin and remove duplicates.
     * @param listOfPlugins {@code ArrayList<String>} with plugins.
     * @return {@code Set<String>} with all the Categories.
     * @throws ClassNotFoundException In case a plugin in {@code listOfPlugins} doesn't exist.
     */
    private Set<String> getCategories(ArrayList<String> listOfPlugins) throws ClassNotFoundException {
        AbstractPlugin pluginItself;
        Set<String> categories = new HashSet<>();       
        for(String plugin: listOfPlugins) {
            pluginItself = loadPlugin(plugin);
            categories.add(pluginItself.getCategory());
        }    
        return categories;
    }
    
    @Override
    public void run() {
        if(message.getContentDisplay().equals(Config.getPrefix()+getPluginName())) {
            try {
                channel.sendMessage(writeEmbedHelpMsg().build()).queue();
            } catch (ClassNotFoundException e) {
                System.err.println("Plugin not found.");
            }
        } else {
            String plugin = message.getContentDisplay().split(" ")[1];
            try {
                channel.sendMessage(writeCommandHelpMsg(plugin).build()).queue();
            } catch (ClassNotFoundException e2) {
                channel.sendMessage(":x: Error :x:").queue();
            }
        }
    }

    @Override
    public String getDescription() {
        return "Shows all the available plugins and what are they for.";
    }

    @Override
    public String getUsage() {
        return commandPrefix+" - Shows all the available plugins.\n"+
                commandPrefix+" <command> - Shows information about that command.";
    }

    @Override
    public String getCategory() {
        return "Meta";
    }
    
}
