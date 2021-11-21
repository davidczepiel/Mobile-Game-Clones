using System.Collections.Generic;
using System.IO;


namespace Flow
{
    public class Map
    {
        List<List<int>> _pipesSolution;
        List<KeyValuePair<int, int>> _wallInfo;

        int _sizeX , _sizeY;
        int numNivel, _numPipes;


        // Start is called before the first frame update
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

        public List<KeyValuePair<int, int>> getWallsInfo()
        {
            return _wallInfo;
        }

    }
}
