using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace Flow
{
    public class LevelManager : MonoBehaviour
    {
        [SerializeField]
        int topSideOffset;
        [SerializeField]
        int sideWidth;
        [SerializeField]
        BoardManager _board;
        [SerializeField]
        PlaceBoardInScreen _screenPlacer;
        [SerializeField]
        Camera _camera;
        // Start is called before the first frame update
        void Start()
        {
            Map map = GameManager.getInstance().createMap();
            _board.prepareBoard(map, GameManager.getInstance().getSkin());

            int top = (topSideOffset * Screen.width) / sideWidth;

            _screenPlacer.placeBoard(map.getSizeX(), map.getSizeY(), Screen.height - top * 2, _camera.orthographicSize);
        }

    }

}