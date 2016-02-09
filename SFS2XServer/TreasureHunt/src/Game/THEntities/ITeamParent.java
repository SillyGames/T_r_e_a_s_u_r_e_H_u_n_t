/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game.THEntities;

import java.util.List;

/**
 *
 * @author Janhavi
 */
public interface ITeamParent
{

    /**
     *
     * @param a_strTeamID
     * @param a_bIncludeGlobal
     * @return
     */
    List<THPlayer> GetTeamPlayers(String a_strTeamID,boolean a_bIncludeGlobal);
}
