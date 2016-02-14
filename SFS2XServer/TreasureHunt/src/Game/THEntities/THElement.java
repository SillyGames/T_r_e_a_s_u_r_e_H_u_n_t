/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game.THEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

/**
 *
 * @author Janhavi
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class THElement
{
    @JsonProperty
    private String m_strID = "";    
    public String GetID()
    {
        return m_strID;
    }    
    public void SetID(String a_strID)
    {
        m_strID = a_strID;
    }
    
    @JsonProperty
    private String m_strName = "defaultName";
    public String getName()
    {
        return m_strName;
    }
    public void setName(String m_strName)
    {
        this.m_strName = m_strName;
    }
    
    public ISFSObject ConvertToSFSObject()
    {
        ISFSObject obj = new SFSObject();
        obj.putUtfString(THConstants.TH_ELEMENT_ID, m_strID );
        return obj;        
    }
    
    public static void THTrace(Object a_data)
    {
        System.out.println("THTrace: " + a_data);
    }
}
