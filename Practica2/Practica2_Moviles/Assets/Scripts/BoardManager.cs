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

            //Se crea el array de Tiles
            _myTileMap = new Tile[sizeX, sizeY];

            //Instanciamos todas las casillas y las inicializamos como vacias
            for (int i = 0; i < sizeX; i++)
            {
                for (int j = 0; j < sizeX; j++)
                {
                    _myTileMap[i, j] = Instantiate(tilePrefab, new Vector3(i,0,j), Quaternion.identity).GetComponent<Tile>();
                    _myTileMap[i, j].setTileType(Tile.TileType.voidTile);
                }
            }

            //Numero de tuberias del mapa
            int numPipes = _myMap.getNumPipes();

            //Colocar los extremos de las tuberias en los tiles del mapa
            for (int i = 0; i < numPipes; i++)
            {
                //Obtenemos los valores de las tuberias
                List<int> pipeSol = _myMap.getPipeSolution(i);

                //Primer elemento de la tuberia
                Vector2 firstTilePos = transformCoord(pipeSol[0], sizeX);
                _myTileMap[(int)firstTilePos.x, (int)firstTilePos.y].setTileType(Tile.TileType.circleTile);
                _myTileMap[(int)firstTilePos.x, (int)firstTilePos.y].setColor(_myColors[i]);

                //Ultimo elemento de la tuberia
                Vector2 secondTilePos = transformCoord(pipeSol[pipeSol.Count - 1], sizeX);
                _myTileMap[(int)secondTilePos.x, (int)secondTilePos.y].setTileType(Tile.TileType.circleTile);
                _myTileMap[(int)secondTilePos.x, (int)secondTilePos.y].setColor(_myColors[i]);

                //Nuevo registro de tuberia
                _currentPipes[i] = new Pipe(_myTileMap[(int)firstTilePos.x, (int)firstTilePos.y], _myTileMap[(int)secondTilePos.x, (int)secondTilePos.y]);
            }

            //Obtenemos la informacion de las paredes del mapa
            List<Vector2> wallsInfo = _myMap.getWallsInfo();
            for (int i = 0; i < wallsInfo.Count; i++)
            {
                //Primer y segundo tile entre las que se encuentra la pared
                Vector2 firstTile = transformCoord((int)wallsInfo[i].x, sizeX);
                Vector2 secondTile = transformCoord((int)wallsInfo[i].y, sizeX);

                //Colocar pared entre dos tiles
                colocaPared(firstTile, secondTile);
            }

        }

        /// <summary>
        /// transformar desde coordenadas unidimensionales a bidimensionales
        /// </summary>
        /// <param name="x"> Posicion unidimensional</param>
        /// <param name="size"> Ancho de la matriz</param>
        /// <returns> Vector2 de la (x) e (y) para un array bidimensional</returns>
        private Vector2 transformCoord(int x, int size)
        {
            return new Vector2(x / size, x % size);
        }

        /// <summary>
        /// Coloca una pared entre la primera casilla y la segunda
        /// </summary>
        /// <param name="firstTile"> Primera casilla </param>
        /// <param name="secondTile"> Segunda casilla </param>
        private void colocaPared(Vector2 firstTile, Vector2 secondTile)
        {
            //Si los tiles se encuentran en la misma fila
            if (firstTile.x == secondTile.x)
            {
                //Si el primer tile esta en la izquierda
                if (firstTile.x < secondTile.x)
                {
                    _myTileMap[(int)firstTile.x, (int)firstTile.y].setWall(3, true);
                    _myTileMap[(int)secondTile.x, (int)secondTile.y].setWall(2, true);
                }
                //Si el primer tile esta en la derecha
                else
                {
                    _myTileMap[(int)firstTile.x, (int)firstTile.y].setWall(2, true);
                    _myTileMap[(int)secondTile.x, (int)secondTile.y].setWall(3, true);
                }
            }
            //Si los tiles se encuentran en la misma columna
            else
            {
                //Si el primer tile esta arriba
                if (firstTile.x < secondTile.y)
                {
                    _myTileMap[(int)firstTile.x, (int)firstTile.y].setWall(1, true);
                    _myTileMap[(int)secondTile.x, (int)secondTile.y].setWall(0, true);
                }
                //Si el primer tile esta abajo
                else
                {
                    _myTileMap[(int)firstTile.x, (int)firstTile.y].setWall(0, true);
                    _myTileMap[(int)secondTile.x, (int)secondTile.y].setWall(1, true);
                }
            }
        }
    }

}
