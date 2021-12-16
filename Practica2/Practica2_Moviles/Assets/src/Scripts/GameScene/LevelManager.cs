using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace Flow
{

    /// <summary>
    /// Clase que gestiona la comunicacion entre el board, Gamemanager y UI
    /// </summary>
    public class LevelManager : MonoBehaviour
    {
        [Tooltip("Tablero con el que va a tratar")]
        [SerializeField]
        BoardManager _board;
        [Tooltip("Encargado de situar el tablero segun la pantalla")]
        [SerializeField]
        PlaceBoardInScreen _screenPlacer;
        [Tooltip("InputManager")]
        [SerializeField]
        InputManager _inputManager;
        [Tooltip("Encargado de situar la camara ")]
        [SerializeField]
        CameraPlacer _cameraPlacer;
        [Tooltip("Encargado de lo relacionado con los cambios en la UI")]
        [SerializeField]
        GUIManager _guiManager;

        [Tooltip("Tamaño del panel que ocupa la UI en la parte superior de la pantalla")]
        [SerializeField]
        int topSideOffset;
        [Tooltip("Tamaño en ancho de la pantalla para el que hemos puesto la relacion del panel de la parte superior de la pantalla")]
        [SerializeField]
        int sideWidth;


        // Start is called before the first frame update
        void Start()
        {
            //Crea el mapa e instancia los objetos en la escena
            Map map = GameManager.getInstance().createMap();

            int nLevel = GameManager.getInstance().getCurrentLevel();
            int totalLevels = GameManager.getInstance().getCurrentPackage().numLevels;
            bool endedPackage = (nLevel == totalLevels - 1);
            bool nextLvLAvailable = GameManager.getInstance().isCurrentCategoryLocked() && GameManager.getInstance().getCurrentCompletedLevels() < nLevel + 1;

            _board.prepareBoard(map, GameManager.getInstance().getSkin());

            _guiManager.initGUI(nLevel,
                                GameManager.getInstance().getPackColor(),
                                map.getSizeX(), map.getSizeY(),
                                GameManager.getInstance().getHints(),
                                GameManager.getInstance().getBestCurrentLevelScore(),
                                endedPackage,
                                nLevel == 0,
                                nextLvLAvailable);

            //Coloca los objetos de la escena
            locateObjects(map);
        }



        /// <summary>
        /// Dada una jugada envia info al gamemanager y a la UI para que se actualice
        /// </summary>
        /// <param name="flow">Numero de flows completos actualmente</param>
        /// <param name="maxFlow">Maximo numero de flows en la partida</param>
        /// <param name="perc">Porcentaje del nivel que se ha completado</param>
        /// <param name="moves">Numero de movimientos que lleva en esta partida</param>
        public void processPlay(int flow, int maxFlow, int perc, int moves)
        {
            _guiManager.changeMoves(moves, GameManager.getInstance().getBestCurrentLevelScore());
            _guiManager.changeNFlow(flow, maxFlow);
            _guiManager.changeLvlPercentage(perc);

            if (flow == maxFlow)
            {
                _guiManager.activeFinishPane();
                GameManager.getInstance().levelCompleted(moves);
            }
        }


        /// <summary>
        /// Metodo que aplica una pista al tablero actual
        /// </summary>
        public void getAHint()
        {
            if (GameManager.getInstance().useHint())
            {
                _guiManager.changeHint(GameManager.getInstance().getHints());
                _board.applyHint();
            }
        }


        /// <summary>
        /// Metodo que aniade una pista 
        /// </summary>
        public void AddAHint()
        {
            GameManager.getInstance().addHint();
            _guiManager.changeHint(GameManager.getInstance().getHints());
        }


        /// <summary>
        /// Metodo que le comunica al board que se reinicie el nivel
        /// </summary>
        public void restartLevel()
        {
            _board.restartLevel();
        }


        /// <summary>
        /// Metodo que le indica a cada elemento del juego que se situe en la posicion que le corresponde 
        /// </summary>
        /// <param name="map">Mapa del juego</param>
        private void locateObjects(Map map)
        {
            //Movemos la posicion de la camara a una posicion positiva
            _cameraPlacer.locateCamera();

            //Posiciona el tablero
            int uiOffset = ((topSideOffset * Screen.width) / sideWidth) * 2;
            int canvasHeight = Screen.height - uiOffset;
            _screenPlacer.placeBoard(map.getSizeX(), map.getSizeY(), canvasHeight, _cameraPlacer.getOrthographicSize());

            //Manda informacion necesaria al input
            _inputManager.setData(_screenPlacer.getPos(), _screenPlacer.getScale(), _cameraPlacer.getOrthographicSize());
        }



    }

}