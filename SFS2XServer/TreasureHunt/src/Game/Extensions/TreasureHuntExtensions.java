/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game.Extensions;

import com.sillygames.eventhandler.LoginHandler;
import com.sillygames.eventhandler.UserRegistrationHandler;
import com.sillygames.eventhandler.ZoneJoinHandler;
import com.sillygames.utils.TreasureHuntEvent;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.SFSExtension;
/**
 *
 * @author Janhavi
 */

public class TreasureHuntExtensions extends  SFSExtension
{
    private static TreasureHuntExtensions instance = null;
    
    public static TreasureHuntExtensions getInstance() {
        return instance;
    }
    @Override
    public void init() 
    {
        instance = this;
        addToTrance("wallah... in tza login...!!!!----------------------------------------------");
        instance.addEventHandler(SFSEventType.USER_JOIN_ZONE, ZoneJoinHandler.class); 
        instance.addEventHandler(SFSEventType.USER_LOGIN, LoginHandler.class);
        instance.addRequestHandler(TreasureHuntEvent.REGISTER_USER, UserRegistrationHandler.class);
    }
    public void addToTrance(String a_traceStr)
    {
        trace(a_traceStr);
    }

   
}
