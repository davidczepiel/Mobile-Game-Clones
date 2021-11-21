using System.Collections;
using System.Collections.Generic;

namespace Flow
{

    public class Pipe
    {
        List<Tile> _pipe;
        Tile _firstTile;
        Tile _secondTile;
        bool _finished;

        public Pipe(Tile firstTile, Tile lastTile)
        {
            _firstTile = firstTile;
            _secondTile = lastTile;
            _finished = false;
        }

        public void addTileToPipe(Tile newTile)
        {
            if (!_pipe.Contains(newTile))
            {
                if ((newTile == _secondTile || newTile == _firstTile) && _pipe.Count > 0)
                    _finished = true;
                _pipe.Add(newTile);
            }
        }

        public void cut(Tile cutted)
        {
            int where = _pipe.IndexOf(cutted);
            if (!_finished)
                _pipe.RemoveRange(where, _pipe.Count - where - 1);
            else
            {
                if (where < _pipe.Count - where)
                    _pipe.RemoveRange(0, where);
                else
                    _pipe.RemoveRange(where, _pipe.Count - where - 1);
            }
            _finished = false;
        }

    }

}