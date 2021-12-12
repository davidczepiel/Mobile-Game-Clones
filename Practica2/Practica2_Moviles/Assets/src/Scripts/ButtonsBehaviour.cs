using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace Flow{
    public class ButtonsBehaviour : MonoBehaviour
    {
        [SerializeField]
        LevelManager lvlManager;
        public void GoToSelectMenu(string scene)
        {
            GameManager.getInstance().changeScene(scene);
        }

        public void GetHint()
        {
            lvlManager.getAHint();
        }

        public void WatchVideo()
        {
            //
        }

        public void RewindAction()
        {
            //
        }

        public void NextLvl()
        {
            //
        }

        public void PreviousLvl()
        {
            //
        }

        public void RestartLvl()
        {
            //
        }
    }
}

