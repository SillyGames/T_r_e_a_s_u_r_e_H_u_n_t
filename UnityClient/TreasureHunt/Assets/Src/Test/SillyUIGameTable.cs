using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;

public class SillyUIGameTable : MonoBehaviour
{
    private static SillyUIGameTable m_instance;

    public static SillyUIGameTable Instance
    {
        get
        {
            return m_instance;
        }
    }

    [SerializeField]
    private GameObject m_contentData;
    [SerializeField]
    private GameObject m_dataContainer;

    [SerializeField]
    private Button m_addContentBtn;

    void Awake()
    {
        m_instance = this;
    }
   	// Use this for initialization
	void Start ()
    {
        m_addContentBtn.onClick.AddListener(OnClickAddedContentBtn);
	}

    private void OnClickAddedContentBtn()
    {
        GameObject l_contentObj = Instantiate(m_contentData) as GameObject;
        l_contentObj.transform.SetParent(m_dataContainer.transform);
        SillyUIContentData l_optionData = l_contentObj.GetComponent<SillyUIContentData>();
        //To DO Ajay Change with Proper Game ID
        l_optionData.GameID = "hunt01";
    }

    // Update is called once per frame
    void Update ()
    {
	
	}
}
