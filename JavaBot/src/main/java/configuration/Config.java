package configuration;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Config {
    private static final String DIR = System.getProperty("user.dir");
    private static final String TOKEN = setToken();
    private static final String PREFIX = setPrefix();
    private static final String OWNERID = setOwnerID();
    private static final String BOTNAME = setBotName();
    private static final String BOTDESCRIPTION = setBotDescription();
    private static final String VERSION = setBotVersion();
    private static final String EMBEDCOLOR = setEmbedColor();

    private static Map<String, String> readConfiguration() {
        String separator = System.getProperty("file.separator");
        File config = new File(DIR+separator+"config"+separator+"config.ini");           
        Map<String, String> configurations = new HashMap<>();       
        String line, key, value;

        try {
            if(!config.isFile()) {
                config.createNewFile();
                Files.copy(new File(DIR+separator+"config"+separator+"example_config.ini"), config);
            }

            Scanner output = new Scanner(config);
            while(output.hasNextLine()) {
                line = output.nextLine();
                if(!line.startsWith(";") && !line.contains(System.getProperty("line.separator"))) {
                    if(line.split("=").length > 1) {
                        key = line.split("=")[0].replaceAll(" ", "");
                        value = line.split("=")[1];
                        while(Character.isWhitespace(value.charAt(0))) {
                            value = value.substring(1, value.length());
                        }
                        configurations.put(key, value);
                    }
                }
            }
            output.close();
        } catch (FileNotFoundException e) {
            System.err.println("Problem opening the File!");
        } catch (IOException e2) {

        }

        return configurations;
    }
	
    private static String setToken(){return readConfiguration().get("token");}
    private static String setPrefix(){return readConfiguration().get("bot_prefix");}
    private static String setOwnerID(){return readConfiguration().get("owner_id");}
    private static String setBotName(){return readConfiguration().get("bot_name");}
    private static String setBotDescription(){return readConfiguration().get("bot_description");}
    private static String setBotVersion(){return readConfiguration().get("version");}
    private static String setEmbedColor(){return readConfiguration().get("color");}

    public static String getToken() {
        return TOKEN;
    }

    public static String getPrefix() {
        return PREFIX;
    }

    public static String getOwnerID() {
        return OWNERID;
    }

    public static String getBotName() {
        return BOTNAME;
    }

    public static String getBotDescription() {
        return BOTDESCRIPTION;
    }

    public static String getVersion() {
        return VERSION;
    }

    public static String getEmbedColor() {
        return EMBEDCOLOR;
    }
        
        
}
