using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

namespace Flow
{
    public class GameManager : MonoBehaviour
    {
        [SerializeField]
        LevelManager _levelManager;

        LevelsInfo myLevelPack;
        PlayerProgress progress;

        int currentLevel;
        int hints = 3;

        static GameManager _instance;

        public static GameManager getInstance()
        {
            return _instance;
        }

        void Awake()
        {
            if (_instance == null)
            {
                _instance = this;
                DontDestroyOnLoad(this);
            }
            else
            {
                _instance._levelManager = _levelManager;
                Destroy(this);
            }
        }

        /// <summary>
        /// Lee el progreso del jugador del disco
        /// </summary>
        private void loadPlayerProgress()
        {
            progress = ProgressSerialization.loadProgress();
        }

        /// <summary>
        /// Guarda el progreso actual del juego
        /// </summary>
        private void savePlayerProgress()
        {
            ProgressSerialization.saveProgress(progress);
        }

        public void changeScene(string name)
        {
            SceneManager.LoadScene(name);
        }

        public Map createMap()
        {
            Map newMap = new Map(myLevelPack.maps, currentLevel);

            return newMap;
        }

        public void prepareLevel(int a)
        {
            currentLevel = a;
            changeScene("Game");
        }


        public int getCurrentLevel()
        {
            return currentLevel;
        }

        public void setCurrentPackage(LevelsInfo levelPack)
        {
            myLevelPack = levelPack;

        }

        public LevelsInfo getCurrentPackage()
        { 
            return myLevelPack;
        }

        public bool useHint()
        {
            if(hints > 0)
            {
                hints--;
                return true;
            }
            return false;
        }


        public int getHints()
        {
            return hints;
        }

        public Color[] getSkin()
        {
            return myLevelPack.skin.levelColors;
        }

    }

}
