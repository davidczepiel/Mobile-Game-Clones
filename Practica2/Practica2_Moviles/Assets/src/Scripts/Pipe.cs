using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace Flow
{

    public class Pipe
    {
        List<Tile> _currentPipe;
        List<Tile> _lastPipe;

        Tile _firstTile;
        Tile _secondTile;
        bool _finished;
        bool haveBeenReversed;

        public Pipe()
        {
            _firstTile = null;
            _secondTile = null;
            _finished = false;
            _lastPipe = new List<Tile>();
            _currentPipe = new List<Tile>();
        }
        public Pipe(Tile firstTile, Tile lastTile)
        {
            _firstTile = firstTile;
            _secondTile = lastTile;
            _finished = false;
            _lastPipe = new List<Tile>();
            _currentPipe = new List<Tile>();
        }

        public Color getColor()
        {
            return _firstTile.getColor();
        }
        public void clearPipe()
        {
            removeTilesRange(0, _currentPipe.Count);
        }
        /// <summary>
        /// Aniade un nuevo tile a la tuberia
        /// </summary>
        /// <param name="newTile"></param>
        public bool addTileToPipe(Tile newTile)
        {
            //La tuberia no debe contener ya al tile
            if (!_currentPipe.Contains(newTile))
            {
                //Si el nuevo tile es uno de los extremos y ya habia tiles en la tuberia, significa que se ha cerrado
                if ((newTile == _secondTile || newTile == _firstTile) && _currentPipe.Count > 0)
                    _finished = true;

                _currentPipe.Add(newTile);
                return false;
            }
            else
            {
                removeTilesRange(_currentPipe.IndexOf(newTile), _currentPipe.Count);
                return true;
            }
        }

        /// <summary>
        /// Elimina los tiles necesarios cuando la tuberia es cortada por otra
        /// </summary>
        /// <param name="cutted"></param>
        public void cut(Tile cutted)
        {
            //Obtener indice del corte
            int where = _currentPipe.IndexOf(cutted);

            //Si la tuberia no esta cerrada, se eliminan los tiles desde el corte hasta el final
            if (!_finished)
                removeTilesRange(where, _currentPipe.Count);
            //Si esta cerrada, se corta el trazo con mas cantidad de tiles respecto al corte
            else
            {
                if (where < _currentPipe.Count - 1 - where)
                {
                    removeTilesRange(0, where);
                    _currentPipe.Reverse();
                    haveBeenReversed = true;
                }
                else
                    removeTilesRange(where, _currentPipe.Count);
            }

            //La tuberia deja de estar cerrada
            _finished = false;
        }


        public void saveFlow()
        {
            haveBeenReversed = false;
            _lastPipe.Clear();
            foreach (Tile t in _currentPipe)
            {
                _lastPipe.Add(new Tile(t));
            }
        }

        public void restoreFlow()
        {
            if (_currentPipe.Count == _lastPipe.Count) return;

            if (!_finished)
            {
                for (int i = _lastPipe.Count; i != 0; --i)
                {
                    if (_currentPipe[i].getTileType() == Tile.TileType.voidTile)
                    {
                        _currentPipe[i].setPipeColor(_lastPipe[i].getColor());
                        _currentPipe[i].setDirection(_lastPipe[i].getDirection());
                        _currentPipe[i].setPipeColor(_lastPipe[i].getColor());
                        _currentPipe[i].setTileType(_lastPipe[i].getTileType());

                    }
                    else
                        break;
                }
            }
            else
            {
                if (haveBeenReversed)
                {
                    _lastPipe.Reverse();
                    haveBeenReversed = false;
                }

                for (int i = _lastPipe.Count - 1; i >= 0; --i)
                {
                    if (_currentPipe[i].getTileType() == Tile.TileType.voidTile)
                    {
                        _currentPipe[i].setPipeColor(_lastPipe[i].getColor());
                        _currentPipe[i].setDirection(_lastPipe[i].getDirection());
                        _currentPipe[i].setPipeColor(_lastPipe[i].getColor());
                        _currentPipe[i].setTileType(_lastPipe[i].getTileType());

                    }
                    else
                        break;
                }
            }
        }


        /// +--------------------------------------------------------------------------------------+
        /// |                                 METODOS AUXILIARES                                   |
        /// +--------------------------------------------------------------------------------------+

        /// <summary>
        /// Elimina y resetea los tiles de la tuberia desde un indice a otro [inicio, final)
        /// </summary>
        /// <param name="beginning"> Indice inicial </param>
        /// <param name="end"> Indice final </param>
        private void removeTilesRange(int beginning, int end)
        {
            //Reseteamos los tiles a vacios
            for (int i = beginning; i < end; ++i)
            {
                _currentPipe[i].setTileType(Tile.TileType.voidTile);
            }

            //Eliminamos de la lista los tiles dentro del rango
            _currentPipe.RemoveRange(beginning, end - beginning);

            if (_currentPipe.Count > 0 && _currentPipe[_currentPipe.Count - 1].getTileType() != Tile.TileType.circleTile)
                _currentPipe[_currentPipe.Count - 1].setTileType(Tile.TileType.pipeHead);
        }
    }
}