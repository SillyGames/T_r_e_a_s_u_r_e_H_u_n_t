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
public class DBQueries
{
    public static final String SELECT_USER_BY_USER_ID = "SELECT displayname FROM treasurehunt.userlogin WHERE userid = ?";
    
    public static final String CREATE_HUNT = "INSERT INTO `treasurehunt`.`Hunts` (`name`, `owner`) VALUES (?, ?);";
    
    public static final String GET_ASSETS_INFO ="SELECT * from treasurehunt.AssetsInfo";
}
