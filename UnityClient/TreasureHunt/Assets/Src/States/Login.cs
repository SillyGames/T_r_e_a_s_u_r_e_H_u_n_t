using UnityEngine;
using System.Collections;

public class Login : FSMState
{

    // Use this for initialization
    public override void Init()
    {
        base.Init();
    }

    public override void OnEnter()
    {
        base.OnEnter();

        FiniteStateMachine.Instance.LoadLevelChangeState(SceneToLoad, State);
        NetworkManager.Instance.addEventListener(NetworkManager.eGameEvents.UserLogin.ToString(), OnUserLogin);
        NetworkManager.Instance.RequestToLogin();
    }

    private void OnUserLogin(object sender, GameEventArgs args)
    {
        //throw new NotImplementedException();
        FiniteStateMachine.Instance.ChangeState(FSMState.EState.MainMenu);
    }

    public override void OnExit()
    {
        base.OnExit();
    }
}
