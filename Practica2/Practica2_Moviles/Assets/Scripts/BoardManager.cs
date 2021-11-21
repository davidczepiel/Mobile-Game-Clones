using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace Flow
{
    public class BoardManager : MonoBehaviour
    {
        [SerializeField]
        GameObject tilePrefab;

        Color[] _myColors = { Color.red, Color.blue, Color.green, Color.yellow, Color.white, Color.cyan, Color.magenta, Color.grey };
        Tile[,] _myTileMap;
        Map _myMap;
        List<Pipe> _currentPipes;

        public void prepareBoard(Map map)
        {
            _myMap = map;
            _currentPipes = new List<Pipe>(_myMap.getNumPipes());
            createBoard();

        }

        void createBoard()
        {
            int sizeX = _myMap.getSizeX();
            int sizeY = _myMap.getSizeY();
            _myTileMap = new Tile[sizeX, sizeY];
            for (int i = 0; i < sizeX; i++)
            {
                for (int j = 0; j < sizeX; j++)
                {
                    _myTileMap[i, j] = Instantiate(tilePrefab, new Vector3(i,0,j), Quaternion.identity).GetComponent<Tile>();
                    _myTileMap[i, j].setTileType(Tile.TileType.voidTile);
                }
            }

            //Num tuberias del mapa
            int numPipes = _myMap.getNumPipes();
            for (int i = 0; i < numPipes; i++)
            {
                //Primer elemento de la tuberia
                List<int> pipeSol = _myMap.getPipeSolution(i);
                KeyValuePair<int, int> firstTilePos = transformCoord(pipeSol[0], sizeX);
                _myTileMap[firstTilePos.Key, firstTilePos.Value].setTileType(Tile.TileType.circleTile);
                _myTileMap[firstTilePos.Key, firstTilePos.Value].setColor(_myColors[i]);

                //Ultimo elemento de la tuberia
                KeyValuePair<int, int> secondTilePos = transformCoord(pipeSol[pipeSol.Count - 1], sizeX);
                _myTileMap[secondTilePos.Key, secondTilePos.Value].setTileType(Tile.TileType.circleTile);
                _myTileMap[secondTilePos.Key, secondTilePos.Value].setColor(_myColors[i]);

                //Nueva tuberia
                _currentPipes[i] = new Pipe(_myTileMap[firstTilePos.Key, firstTilePos.Value], _myTileMap[secondTilePos.Key, secondTilePos.Value]);
            }

            List<KeyValuePair<int, int>> wallsInfo = _myMap.getWallsInfo();
            for (int i = 0; i < wallsInfo.Count; i++)
            {
                KeyValuePair<int, int> firstTile = transformCoord(wallsInfo[i].Key, sizeX);
                KeyValuePair<int, int> secondTile = transformCoord(wallsInfo[i].Value, sizeX);

                //Si los tiles se encuentran en la misma fila
                if (firstTile.Key == secondTile.Key)
                {
                    //Si el primer tile esta en la izquierda
                    if (firstTile.Key < secondTile.Key)
                    {
                        _myTileMap[firstTile.Key, firstTile.Value].setWall(3, true);
                        _myTileMap[secondTile.Key, secondTile.Value].setWall(2, true);
                    }
                    //Si el primer tile esta en la derecha
                    else
                    {
                        _myTileMap[firstTile.Key, firstTile.Value].setWall(2, true);
                        _myTileMap[secondTile.Key, secondTile.Value].setWall(3, true);
                    }
                }
                //Si los tiles se encuentran en la misma columna
                else
                {
                    //Si el primer tile esta arriba
                    if (firstTile.Value < secondTile.Value)
                    {
                        _myTileMap[firstTile.Key, firstTile.Value].setWall(1, true);
                        _myTileMap[secondTile.Key, secondTile.Value].setWall(0, true);
                    }
                    //Si el primer tile esta abajo
                    else
                    {
                        _myTileMap[firstTile.Key, firstTile.Value].setWall(0, true);
                        _myTileMap[secondTile.Key, secondTile.Value].setWall(1, true);
                    }
                }
            }

        }

        private KeyValuePair<int, int> transformCoord(int x, int size)
        {
            return new KeyValuePair<int, int>(x / size, x % size);
        }
    }

}
