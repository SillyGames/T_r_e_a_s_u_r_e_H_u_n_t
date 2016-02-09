using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;

public class SillyUILogin : MonoBehaviour {

    // Use this for initialization

    [SerializeField]
    private GameObject m_loginUI;
    [SerializeField]
    private GameObject m_registrationUI;
    [SerializeField]
    private Text m_userName;

    [SerializeField]
    private Button m_loginBtn;
    [SerializeField]
    private Button m_registrationBtn;
	void Start ()
    {
        m_loginBtn.onClick.AddListener(OnClickLoginBtn);
        m_registrationBtn.onClick.AddListener(OnClickRegistrationBtn);
	}

    private void OnClickRegistrationBtn()
    {
        m_registrationUI.SetActive(true);
        m_loginUI.SetActive(false);
    }

    private void OnClickLoginBtn()
    {
        //
        NetworkManager.Instance.addEventListener(NetworkManager.eGameEvents.UserLogin.ToString(),OnUserLogin);
        NetworkManager.Instance.RequestToLogin(m_userName.text.ToLower(),SystemInfo.deviceUniqueIdentifier);
    }

    private void OnUserLogin(object sender, GameEventArgs args)
    {
        //throw new NotImplementedException();
        FiniteStateMachine.Instance.ChangeState(FSMState.EState.MainMenu);
    }

    // Update is called once per frame
    void Update ()
    {
	
	}
}
