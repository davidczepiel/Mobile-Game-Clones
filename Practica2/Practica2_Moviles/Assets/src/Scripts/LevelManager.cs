using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace Flow
{
    public class LevelManager : MonoBehaviour
    {

        [SerializeField]
        BoardManager _board;
        // Start is called before the first frame update
        void Start()
        {
            _board.prepareBoard(GameManager.getInstance().createMap(), GameManager.getInstance().getSkin());
        }

    }

}