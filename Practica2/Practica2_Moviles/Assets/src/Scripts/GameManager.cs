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
        [SerializeField]
        int startingHints = 3;
        
        PlayerProgress progress;

        int currentLevel, package, category;
        int hints;

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

        private void Start()
        {
            loadPlayerProgress();
        }

        /// <summary>
        /// Metodo llamado cuando se completa un nivel
        /// </summary>
        /// <param name="score"> Score obtenido de la partida </param>
        public void levelCompleted(int score)
        {
            //Ultimo mejor score registrado para el nivel que se acaba de jugar
            int lastScore = progress.categoryProgress[category].packProgress[package].levelTopScore[currentLevel];
            
            //Solo si no se habia registrado antes un score o si es un nuevo record se guarda
            if (lastScore == 0 || lastScore > score)
            {
                progress.categoryProgress[category].packProgress[package].levelTopScore[currentLevel] = score;
                savePlayerProgress();
            }
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
                progress.availableHints = hints;
                savePlayerProgress();
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

        /// <summary>
        /// Devuelve un objeto player progress que representa una nueva partida
        /// </summary>
        /// <returns></returns>
        private PlayerProgress createNewPlayerProgress()
        {
            PlayerProgress newProgress = new PlayerProgress();

            //Rellenar con los valores para una nueva partida
            newProgress.availableHints = startingHints;
            foreach(CategoryData category in myLevelCategory)
            {
                //Crear la categoria
                newProgress.categoryProgress.Add(new CategoryProgress());
                CategoryProgress categoryProgress = newProgress.categoryProgress[newProgress.categoryProgress.Count - 1];
                //Recorrer los paquetes de la categoria
                for(int i = 0; i < category.packagesData.Length; ++i)
                {
                    //Numero de niveles del paquete
                    int numLevels = category.packagesData[i].numLevels;
                    //paquete i de la categoria
                    categoryProgress.packProgress.Add(new PackProgress(numLevels));
                }
            }

            return newProgress;
        }

        /// <summary>
        /// Lee el progreso del jugador del disco
        /// </summary>
        private void loadPlayerProgress()
        {
            progress = ProgressSerialization.loadProgress();

            //Si no ha sido capaz de leer el progreso, se crea uno desde 0
            if (progress == null)
                progress = createNewPlayerProgress();

            hints = progress.availableHints;
        }

        /// <summary>
        /// Guarda el progreso actual del juego
        /// </summary>
        private void savePlayerProgress()
        {
            ProgressSerialization.saveProgress(progress);
        }
    }

}
