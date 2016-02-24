/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;
//todo: Ajay, implement this interface in your extension

import Game.THEntities.THPlayer;


/**
 * This interface provide a common way to send data from Game Server to Client
 * @author Janhavi
 */
public interface ISFSDispatcher
{

    /**
     * used to sync player data for a newly joined player or sending newly joined player data to existing players
     * @param a_targetPlayer : player who the data will be sent to
     * @param a_players : list of players that needs to be synched, this includes player name
     */
    void SendPlayerSync(THPlayer a_targetPlayer, THPlayer ... a_players);
    
    
    /**
     * same as SendPlayerSync, but its a requester list, only sent to HuntMaster
     * @param a_targetPlayer : player who the data will be sent to
     * @param a_players : list of players that needs to be synched, this includes player name
     */
    void SendRequesterSync(THPlayer a_targetPlayer, THPlayer ... a_players);
}
