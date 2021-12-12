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
        [SerializeField]
        CategoryData[] myLevelCategory;
        
        PlayerProgress progress;

        int currentLevel, package, category;
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
            Map newMap = new Map(myLevelCategory[category].packagesData[package].maps, currentLevel);
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

        public void setCurrentPackage(int levelPack, int category)
        {
            package = levelPack;
            this.category = category;
        }

        public LevelsInfo getCurrentPackage()
        { 
            return myLevelCategory[category].packagesData[package];
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
            return myLevelCategory[category].packagesData[package].skin.levelColors;
        }

        public CategoryData[] getCategoryData()
        {
            return myLevelCategory;
        }
    }

}
