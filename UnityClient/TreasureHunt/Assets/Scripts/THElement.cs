using UnityEngine;
using System.Collections;
using Sfs2X.Entities.Data;
using System;

namespace SillyGames.TreasureHunt.Entities
{
    [Serializable]
    public class THElement
    {
        private string m_strID = string.Empty;
        public string ID
        {
            get
            {
                return m_strID;
            }
            private set
            {
                m_strID = value;
            }
        }

        private string m_strName = string.Empty;
        public string Name
        {
            get
            {
                return m_strName;
            }
            set
            {
                m_strName = value;
            }
        }

        public virtual ISFSObject ConvertToSFSObject()
        {
            ISFSObject obj = new SFSObject();
            obj.PutUtfString(THConstants.TH_ELEMENT_ID, m_strID);
            return obj;
        }
    }
}