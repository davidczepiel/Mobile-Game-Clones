using System.Collections;
using System.Collections.Generic;
using UnityEngine;



namespace Flow
{
    public class GameManager : MonoBehaviour
    {
        [SerializeField]
        LevelsInfo[] myLevels;
        [SerializeField]
        LevelManager _levelManager;

        int currentLevel;
        int hints;
        int package;

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


        public Map createMap()
        {
            Map newMap = new Map(myLevels[package].maps, 0);

            return newMap;
        }

        public void prepareLevel(int a)
        {
            currentLevel = a;
            //TODO cambio de escena
        }

        public int getCurrentLevel()
        {
            return currentLevel;
        }

        public void setCurrentPackage(int a)
        {
            package = a;
        }

        public int getCurrentPackage()
        {
            return package;
        }

        public void setHint(int a)
        {
            hints = a;
        }

        public int getHints()
        {
            return hints;
        }

        public Color[] getSkin()
        {
            return myLevels[currentLevel].skin.levelColors;
        }

    }

}
