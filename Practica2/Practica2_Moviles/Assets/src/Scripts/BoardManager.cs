using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace Flow
{
    public class BoardManager : MonoBehaviour
    {
        [SerializeField]
        GameObject tilePrefab;
        [SerializeField]
        Transform boardGO;

        Tile[,] _myTileMap;
        Map _myMap;
        List<Pipe> _currentPipes;

        public void prepareBoard(Map map, Color[] skin)
        {
            _myMap = map;
            _currentPipes = new List<Pipe>();
            createBoard(skin);
        }

        void createBoard(Color[] skin)
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
                    _myTileMap[i, j] = Instantiate(tilePrefab, new Vector3(i, j, 0), Quaternion.identity, boardGO).GetComponent<Tile>();
                    _myTileMap[i, j].setTileType(Tile.TileType.voidTile);
                }
            }

            //Numero de tuberias del mapa
            int numPipes = _myMap.getNumPipes();

            //Colocar los extremos de las tuberias en los tiles del mapa
            for (int i = 0; i < numPipes; i++)
            {
                //Obtenemos los valores de las tuberias
                List<Vector2> pipeSol = _myMap.getPipeSolution(i);

                //Primer elemento de la tuberia
                _myTileMap[(int)pipeSol[0].x, (int)pipeSol[0].y].initTile(Tile.TileType.circleTile, skin[i]);

                //Ultimo elemento de la tuberia
                _myTileMap[(int)pipeSol[pipeSol.Count - 1].x, (int)pipeSol[pipeSol.Count - 1].y].initTile(Tile.TileType.circleTile, skin[i]);

                //Nuevo registro de tuberia
                _currentPipes.Add(new Pipe(_myTileMap[(int)pipeSol[0].x, (int)pipeSol[0].y], _myTileMap[(int)pipeSol[pipeSol.Count - 1].x, (int)pipeSol[pipeSol.Count - 1].y]));
                Debug.Log(i);
            }

            //Obtenemos la informacion de las paredes del mapa
            List<Tuple<Vector2, Vector2>> wallsInfo = _myMap.getWallsInfo();

            if (wallsInfo != null)
                for (int i = 0; i < wallsInfo.Count; i++)
                {
                    //Primer y segundo tile entre las que se encuentra la pared
                    Vector2 firstTile = wallsInfo[i].Item1;
                    Vector2 secondTile = wallsInfo[i].Item2;

                    //Colocar pared entre dos tiles
                    colocaPared(firstTile, secondTile);
                }

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

        private void onTouch(int x, int y)
        {
            Tile touchedTile = _myTileMap[x, y];
            Color col = touchedTile.getColor();
            Pipe touchedPipe = pipeWithColor(col);
            switch (touchedTile.getTileType())
            {
                case Tile.TileType.circleTile:
                    //Animacion
                    touchedPipe.clearPipe();
                    touchedPipe.addTileToPipe(touchedTile);
                    break;

                case Tile.TileType.connectedTile:
                    //Animacion Cabeza
                    touchedPipe.cut(touchedTile);
                    break;

                case Tile.TileType.pipeHead:
                    //Animacion
                    break;
            }
        }

        private Pipe pipeWithColor(Color col)
        {
            foreach(Pipe p in _currentPipes)
                if (p.getColor() == col) return p;

            return null;
        }
    }

}
