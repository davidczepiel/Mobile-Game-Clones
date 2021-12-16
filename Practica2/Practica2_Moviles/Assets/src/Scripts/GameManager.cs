using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

namespace Flow
{
    /// <summary>
    /// Gamemanager
    /// </summary>
    public class GameManager : MonoBehaviour
    {
        [SerializeField]
        LevelManager _levelManager;
        [SerializeField]
        CategoryData[] myLevelCategory;
        [SerializeField]
        int startingHints = 3;

        [SerializeField]
        LevelSkin skin;


        PlayerProgress progress;                //Clase encargada de guardar el progreso del jugador

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
        /// <summary>
        /// Carga el progreso del jugador y asigna valores a la estructura de categorias
        /// </summary>
        private void Start()
        {
            loadPlayerProgress();

            for(int i = 0; i < myLevelCategory.Length; ++i)
            {
                for(int j = 0; j < myLevelCategory[i].packagesData.Length; ++j)
                {
                    myLevelCategory[i].packagesData[j].levelsCompleted = progress.categoryProgress[i].packProgress[j].completedLevels;
                }
            }
        }

        /// <summary>
        /// Metodo llamado cuando se completa un nivel
        /// </summary>
        /// <param name="score"> Score obtenido de la partida </param>
        public void levelCompleted(int score)
        {
            //Ultimo mejor score registrado para el nivel que se acaba de jugar
            int lastScore = progress.categoryProgress[category].packProgress[package].levelTopScore[currentLevel];

            //Niveles completados actualmente en el paquete
            int levelsCompleted = progress.categoryProgress[category].packProgress[package].completedLevels;

            bool saveRequired = false;
            //Solo si no se habia registrado antes un score o si es un nuevo record se guarda
            if (lastScore == 0 || lastScore > score)
            {
                progress.categoryProgress[category].packProgress[package].levelTopScore[currentLevel] = score;
                saveRequired = true;
            }
            //De igual forma ocurre con el numero de niveles completados para el paquete
            if(levelsCompleted <= currentLevel)
            {
                myLevelCategory[category].packagesData[package].levelsCompleted = currentLevel + 1;
                progress.categoryProgress[category].packProgress[package].completedLevels = currentLevel + 1;
                saveRequired = true;
            }
            
            if(saveRequired) savePlayerProgress();
        }

        /// <summary>
        /// Cambia a la escena con el nombre dado 
        /// </summary>
        /// <param name="name"></param>
        public void changeScene(string name)
        {
            SceneManager.LoadScene(name);
        }

        /// <summary>
        /// Crea el mapa asociado al nivel actual
        /// </summary>
        /// <returns></returns>
        public Map createMap()
        {
            Map newMap = new Map(myLevelCategory[category].packagesData[package].maps, currentLevel);
            return newMap;
        }

        /// <summary>
        /// Guarda la informacion del nivel y realiza el cambio de escena
        /// </summary>
        /// <param name="a"></param>
        public void prepareLevel(int a)
        {
            currentLevel = a;
        }

        //------------------------------------GETTERS/SETTERS------------------------------------------------
        public int getCurrentCompletedLevels()
        {
            return progress.categoryProgress[category].packProgress[package].completedLevels;
        }

        public int getBestCurrentLevelScore()
        {
            return progress.categoryProgress[category].packProgress[package].levelTopScore[currentLevel];
        }
        public bool isCurrentCategoryLocked()
        {
            return myLevelCategory[category].isLocked;
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

        /// <summary>
        /// Usa una pista en caso de ser posible
        /// </summary>
        /// <returns> Devuelve si ha sido posible usar la pista</returns>
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
        /// <summary>
        /// Aniade una hint y guarda que se ha aniadido
        /// </summary>
        public void addHint()
        {
            hints++;
            progress.availableHints = hints;
            savePlayerProgress();
        }

        public Color[] getSkin()
        {
            return skin.levelColors;
        }

        public Color getPackColor()
        {
            return myLevelCategory[category].categoryColor; ;
        }

        public CategoryData[] getCategoryData()
        {
            return myLevelCategory;
        }
        //------------------------------------GETTERS------------------------------------------------
        #region Metodos de carga de progreso
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
        #endregion
    }

}
