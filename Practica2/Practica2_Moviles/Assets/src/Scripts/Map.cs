using System;
using System.IO;
using System.Collections.Generic;
using UnityEngine;


namespace Flow
{
    public class Map
    {
        List<List<Vector2Int>> _pipesSolution;
        List<Vector2Int> _emptySquares;
        List<Tuple<Vector2Int, Vector2Int>> _wallInfo;

        int _sizeX, _sizeY;
        int _numLevel, _numPipes;

        public Map(TextAsset map, int level)
        {
            _pipesSolution = new List<List<Vector2Int>>();
            //Leer mapa
            string info = map.ToString();
            string[] levels = info.Split('\n');
            levels = levels[level].Split(';');
            string[] header = levels[0].Split(',');

            //En caso de que sea un cuadrado
            if (!header[0].Contains(":"))
            {
                _sizeX = int.Parse(header[0]);
                _sizeY = int.Parse(header[0]);
            }
            //Caso rectangulo
            else
            {
                string[] size = header[0].Split(':');
                _sizeX = int.Parse(size[0]);
                _sizeY = int.Parse(size[1]);
            }

            _numLevel = int.Parse(header[2]);
            _numPipes = int.Parse(header[3]);

            //Si el header contiene mas de 3 campos significa que hay campos opcionales por procesar
            if(header.Length > 4)
            {
                //Puentes (indice 4)

                //Celdas huecas
                if(header[5] != "")
                {
                    _emptySquares = new List<Vector2Int>();
                    string[] emptySquares = header[5].Split(':');
                    for (int i = 0; i < emptySquares.Length; ++i)
                    {
                        _emptySquares.Add(transformCoord(int.Parse(emptySquares[i]), _sizeX));
                    }
                }

                //Muros y opcionales
                if(header[6] != "")
                {
                    _wallInfo = new List<Tuple<Vector2Int, Vector2Int>>();
                    string[] walls = header[6].Split(':');
                    for (int i = 0; i < walls.Length; ++i)
                    {
                        string[] wall = walls[i].Split('|');
                        int firstWall = int.Parse(wall[0]);
                        int secondWall = int.Parse(wall[1]);

                        _wallInfo.Add(new Tuple<Vector2Int, Vector2Int>(transformCoord(firstWall, _sizeX), transformCoord(secondWall, _sizeX)));
                    }
                }
            }
            

            //Pipes
            for (int i = 0; i < _numPipes; i++)
            {
                string[] pipe = levels[i + 1].Split(',');
                _pipesSolution.Add(new List<Vector2Int>());
                for (int j = 0; j < pipe.Length; j++)
                {
                    _pipesSolution[i].Add(transformCoord(int.Parse(pipe[j]), _sizeX));
                }
            }

            //mapInfo = new int[sizeX][sizeY];
        }

        /// <summary>
        /// transformar desde coordenadas unidimensionales a bidimensionales
        /// </summary>
        /// <param name="x"> Posicion unidimensional</param>
        /// <param name="size"> Ancho de la matriz</param>
        /// <returns> Vector2 de la (x) e (y) para un array bidimensional</returns>
        private Vector2Int transformCoord(int x, int size)
        {
            return new Vector2Int(x / size, x % size);
        }

        public int getSizeX()
        {
            return _sizeX;
        }

        public int getSizeY()
        {
            return _sizeY;
        }

        public int getNumLevel()
        {
            return _numLevel;
        }

        public List<Vector2Int> getPipeSolution(int index)
        {
            return _pipesSolution[index];
        }

        public int getNumPipes()
        {
            return _numPipes;
        }

        public List<Tuple<Vector2Int, Vector2Int>> getWallsInfo()
        {
            return _wallInfo;
        }

        public List<Vector2Int> getEmptySquares()
        {
            return _emptySquares;
        }
    }
}
