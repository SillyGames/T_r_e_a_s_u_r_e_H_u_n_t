/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

/**
 *
 * @author Janhavi
 */
public class Keys
{
    public static String DEVICE_ID = "deviceid";
    public static String USER_NAME = "username";
    public static String HUNT_NAME = "huntname";
    public static String SUCCESS = "success";
    public static String ERROR = "error";
    
    //<editor-fold defaultstate="collapsed" desc="reuests related to the game">
    public static String GAME_REQUESTS = "game";
    public static String GAME_HUNT_CREATE = "game.createHunt";
    public static String GAME_HUNT_DELETE = "game.deleteHunt";
    public static String GAME_HUNT_OPEN = "game.openHunt";
    //</editor-fold>
    
    public static String HUNT_REQUESTS = "hunt";
    
    public static String CMD_HUNTCREATED = "huntcreated";
    
    public static String USERVAR_DEVICEID = "udid";
    
    //Treasure Hunt ISFSObject conversion
    public static String TH = "th";
    public static String TH_TEAM = "thteam";
    public static String TH_NAME = "thname";
    public static String TH_TEAM_NAME = "thteamname";
    public static String TH_CLUE = "thclue";
    public static String TH_CLUE_NAME = "thcluename"; //used for clueTitle as well, both are same
    public static String TH_CLUE_TITLE = "thcluetitle";
    public static String TH_CLUE_TEXT = "thcluetext";
    public static String TH_CLUE_IMAGE = "thclueimage";
    public static String TH_CLUE_TRACKER = "thcluetracker";
}
