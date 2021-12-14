using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace Flow
{
    public class LevelManager : MonoBehaviour
    {
        [SerializeField]
        BoardManager _board;
        [SerializeField]
        PlaceBoardInScreen _screenPlacer;
        [SerializeField]
        InputManager _inputManager;
        [SerializeField]
        CameraPlacer _cameraPlacer;
        [SerializeField]
        GUIManager _guiManager;

        [SerializeField]
        int topSideOffset;
        [SerializeField]
        int sideWidth;

        int _moves = 0;



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

        public void processPlay(int flow, int maxFlow, int perc, int moves)
        {
            _moves = moves;
            _guiManager.changeMoves(moves, GameManager.getInstance().getBestCurrentLevelScore());
            _guiManager.changeNFlow(flow, maxFlow);
            _guiManager.changeLvlPercentage(perc);

            if(flow == maxFlow)
            {
                 _guiManager.activeFinishPane();
                 GameManager.getInstance().levelCompleted(moves);
            }
        }

        public void getAHint()
        {
            if (GameManager.getInstance().useHint())
            {
                _guiManager.changeHint(GameManager.getInstance().getHints());
                _board.applyHint();
            }
        }

        public void restartLevel()
        {
            _board.restartLevel();
        }

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