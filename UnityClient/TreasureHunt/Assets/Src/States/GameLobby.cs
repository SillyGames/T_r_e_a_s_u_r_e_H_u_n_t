using UnityEngine;
using System.Collections;

public class GameLobby : FSMState
{

    // Use this for initialization
    public override void Init()
    {
        base.Init();
    }

    public override void OnEnter()
    {
        base.OnEnter();
        Debug.Log(SceneToLoad +"  Ready to Load the Scene");
        FiniteStateMachine.Instance.LoadLevelChangeState(SceneToLoad, State);
    }

    public override void OnExit()
    {
        base.OnExit();
    }
}
