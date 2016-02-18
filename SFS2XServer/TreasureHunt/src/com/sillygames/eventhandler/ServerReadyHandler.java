/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sillygames.eventhandler;

import Game.Extensions.TreasureHunZonetExtension;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

/**
 *
 * @author Janhavi
 */
public class ServerReadyHandler extends BaseServerEventHandler
{

    @Override
    public void handleServerEvent(ISFSEvent isfse) throws SFSException
    {
        trace("^^^^^^^^^^^^^^^^^^^^server Ready^^^^^^^^^^^^^^^^^^^^^^^^^^");
        TreasureHunZonetExtension.getInstance().CreateTreasureHuntRoom();
    }
    
}
