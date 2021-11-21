using System.Collections.Generic;
using System.IO;
using UnityEngine;

namespace Flow
{
    public class Map
    {
        List<List<int>> _pipesSolution;
        List<Vector2> _wallInfo;

        int _sizeX , _sizeY;
        int numNivel, _numPipes;

        Map(string filePath)
        {
            StreamReader reader; //Open
            //mapInfo = new int[sizeX][sizeY];
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

        public List<int> getPipeSolution(int index)
        {
            return _pipesSolution[index];
        }

        public int getNumPipes()
        {
            return _numPipes;
        }

        public List<Vector2> getWallsInfo()
        {
            return _wallInfo;
        }

    }
}
