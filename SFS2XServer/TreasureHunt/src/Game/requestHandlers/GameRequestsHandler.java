package Game.requestHandlers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.smartfoxserver.v2.annotations.MultiHandler;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;

/**
 *
 * @author Janhavi
 */
@MultiHandler
public class GameRequestsHandler extends baseRequestHandler
{   
    public void createHunt(ISFSObject a_data, User a_user)
    {
        trace("YYYYYYYYYYYYYYYYYYYYYYYeeeey.....................");
    }
    
    public void deleteHunt(ISFSObject a_data, User a_user)
    {
        
    }
    
    public void openHunt(ISFSObject a_data, User a_user)
    {
        
    }
    
    
}
