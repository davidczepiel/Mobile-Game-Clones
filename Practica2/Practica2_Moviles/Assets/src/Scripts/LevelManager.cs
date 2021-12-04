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
        int topSideOffset;
        [SerializeField]
        int sideWidth;

        // Start is called before the first frame update
        void Start()
        {
            //Crea el mapa e instancia los objetos en la escena
            Map map = GameManager.getInstance().createMap();
            _board.prepareBoard(map, GameManager.getInstance().getSkin());
            //Coloca los objetos de la escena
            locateObjects(map);
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