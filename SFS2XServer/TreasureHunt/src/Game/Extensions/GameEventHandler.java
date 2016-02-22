/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game.Extensions;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

/**
 *
 * @author Janhavi
 */
public class GameEventHandler extends BaseClientRequestHandler
{

    @Override
    public void handleClientRequest(User user, ISFSObject isfso)
    {
        trace("handled Game Event, parent type: " + getParentExtension().getType() + ", isfso: " + isfso.getDump());
        
    }
    
}
