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

        int _bestMoves = 0, _moves = 0;



        // Start is called before the first frame update
        void Start()
        {
            //Crea el mapa e instancia los objetos en la escena
            Map map = GameManager.getInstance().createMap();
            _board.prepareBoard(map, GameManager.getInstance().getSkin());

            _guiManager.initGUI(GameManager.getInstance().getCurrentLevel(),
                                map.getSizeX(), map.getSizeY(),
                                GameManager.getInstance().getHints(),
                                _bestMoves);

            //Coloca los objetos de la escena
            locateObjects(map);
        }

        public void processPlay(int flow, int perc, int moves, bool hasEnded)
        {
            _moves = moves;
            _guiManager.changeMoves(moves, _bestMoves);
            _guiManager.changeNFlow(flow);
            _guiManager.changeLvlPercentage(perc);
        }

        public void getAHint()
        {
            if (GameManager.getInstance().useHint())
            {
                _guiManager.changeHint(GameManager.getInstance().getHints());
                _board.applyHint();
            }
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