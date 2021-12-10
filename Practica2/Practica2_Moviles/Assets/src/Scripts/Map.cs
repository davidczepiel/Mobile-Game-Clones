using System;
using System.IO;
using System.Collections.Generic;
using UnityEngine;


namespace Flow
{
    public class Map
    {
        List<List<Vector2Int>> _pipesSolution;
        List<Tuple<Vector2, Vector2>> _wallInfo;
        Color[] skin;

        int _sizeX, _sizeY;
        int numNivel, _numPipes;

        public Map(TextAsset map, int level)
        {
            _pipesSolution = new List<List<Vector2Int>>();
            //Leer mapa
            string info = map.ToString();
            string[] levels = info.Split('\n');
            levels = levels[level].Split(';');
            string[] header = levels[0].Split(',');

            //En caso de que sea un cuadrado
            _sizeX = int.Parse(header[0]);
            _sizeY = int.Parse(header[0]);

            numNivel = int.Parse(header[2]);
            _numPipes = int.Parse(header[3]);

            for (int i = 0; i < _numPipes; i++)
            {
                string[] pipe = levels[i + 1].Split(',');
                _pipesSolution.Add(new List<Vector2Int>());
                for (int j = 0; j < pipe.Length; j++)
                {
                    _pipesSolution[i].Add(transformCoord(int.Parse(pipe[j]), _sizeX));
                }
            }

            //Muros y csas opcionales


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
            return numNivel;
        }

        public List<Vector2Int> getPipeSolution(int index)
        {
            return _pipesSolution[index];
        }

        public int getNumPipes()
        {
            return _numPipes;
        }

        public List<Tuple<Vector2, Vector2>> getWallsInfo()
        {
            return _wallInfo;
        }

    }
}
