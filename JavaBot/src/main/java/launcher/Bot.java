package launcher;

// Java Dependencies
import java.lang.reflect.Constructor;
// Own dependencies
import configuration.Config;
// JDA Dependencies
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
// Excepciones
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import javax.security.auth.login.LoginException;

// Plugins
import plugins.AbstractPlugin;

// Utilities
import utilities.CapitalizeString;

/**
 * Bot
 */
public class Bot extends ListenerAdapter implements EventListener {
    protected static JDA bot;

    public Bot()
    throws LoginException, RateLimitedException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(Config.getToken());
        builder.addEventListener(new Bot());
        bot = builder.buildAsync();
    }

    /**
     * This method will show information about our bot once is activated.
     */
    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("\nActivating bot...");
        System.out.println("Name: " + bot.getSelfUser().getName());
        System.out.println("ID: " + bot.getSelfUser().getId());
        System.out.println("Bot's ready!");
        System.out.println("--------------");
    }

    /**
     * This method will get the message if it starts with our prefix and extracts
     * the command.
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith(Config.getPrefix()) && !event.getAuthor().isBot()) {
            String plugin = event.getMessage().getContentRaw().replace(Config.getPrefix(), "").split(" ")[0];
            try {
                Class<?> pluginClass = Class.forName("plugins." + CapitalizeString.byFirstLetter(plugin));
                Constructor<?> pluginCons = pluginClass.getConstructors()[0];
                Object pluginObj = pluginCons.newInstance(bot, event.getMessage(), event.getChannel());
                AbstractPlugin runner = (AbstractPlugin) pluginObj;
                runner.run();
            } catch (ClassNotFoundException e) {
                System.err.println(plugin + " plugin not found!");
                event.getChannel().sendMessage("`" + plugin + "` is not a feature, try " + Config.getPrefix() + "help")
                        .queue();
            } catch (Exception e2) {
                System.err.println(e2.getMessage());
            }
        }
    }
    
}