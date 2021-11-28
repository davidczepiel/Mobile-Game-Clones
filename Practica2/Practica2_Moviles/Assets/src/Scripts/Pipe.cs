using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace Flow
{

    public class Pipe
    {
        List<Tile> _pipe;
        Tile _firstTile;
        Tile _secondTile;
        bool _finished;

        public Pipe()
        {
            _firstTile = null;
            _secondTile = null;
            _finished = false;
        }
        public Pipe(Tile firstTile, Tile lastTile)
        {
            _firstTile = firstTile;
            _secondTile = lastTile;
            _finished = false;
        }

        public void setFirstAndSecond(Tile first, Tile second)
        {
            this._firstTile = first;
            this._secondTile = second;
        }
        /// <summary>
        /// Aniade un nuevo tile a la tuberia
        /// </summary>
        /// <param name="newTile"></param>
        public bool addTileToPipe(Tile newTile)
        {
            //La tuberia no debe contener ya al tile
            if (!_pipe.Contains(newTile))
            {
                //Si el nuevo tile es uno de los extremos y ya habia tiles en la tuberia, significa que se ha cerrado
                if ((newTile == _secondTile || newTile == _firstTile) && _pipe.Count > 0)
                    _finished = true;

                _pipe.Add(newTile);
                return false;
            }
            else
            {
                //TODO Si ya contiene el tile en la lista recorremos desde el final hasta el newTile restableciendo los tiles
                // dependiendo de su background y establecer el tipo del ultimo como headTile

                removeTilesRange(_pipe.IndexOf(newTile), _pipe.Count - 1);

                return true;
            }
        }

        /// <summary>
        /// Elimina los tiles necesarios cuando la tuberia es cortada por otra
        /// </summary>
        public void clearHiddens()
        {
            int lastHidden = 0, firstHidden = -1;
            for(int i = 0; i < _pipe.Count; ++i)
            {
                if (_pipe[i].isHidden())
                {
                    _pipe[i].setTileType(Tile.TileType.voidTile);
                    _pipe[i].activate();

                    if (firstHidden == -1)
                        firstHidden = i;
                    lastHidden = i;
                }
            }

            if (firstHidden == -1) return;

            _pipe.RemoveRange(firstHidden, lastHidden);

            //La tuberia deja de estar cerrada
            _finished = false;
        }

        /// <summary>
        /// Restablece el corte provisional del pipe
        /// </summary>
        public void restoreHiddens()
        {
            for (int i = 0; i < _pipe.Count; ++i)
            {
                if (_pipe[i].isHidden())
                {
                    _pipe[i].setTileType(Tile.TileType.connectedTile);
                    if(i == 0)
                    {

                    }
                    _pipe[i].activate();
                }
            }

            //La tuberia deja de estar cerrada
            _finished = false;
        }

        /// <summary>
        /// Elimina los tiles necesarios cuando la tuberia es cortada por otra«
        /// </summary>
        /// <param name="cutted"></param>
        public void temporalCut(Tile cutted)
        {
            //Obtener indice del corte
            int where = _pipe.IndexOf(cutted);

            //Si la tuberia no esta cerrada, se eliminan los tiles desde el corte hasta el final
            if (!_finished)
                temporalRemoveTilesRange(where, _pipe.Count);
            //Si esta cerrada, se corta el trazo con mas cantidad de tiles respecto al corte
            else
            {
                if (where < _pipe.Count - 1 - where)
                    temporalRemoveTilesRange(0, where);
                else
                    temporalRemoveTilesRange(where, _pipe.Count);
            }

            //La tuberia deja de estar cerrada
            _finished = false;
        }

        /// <summary>
        /// Todos los tiles de la pipe pasan a ser del color y del tipo de esta pipe
        /// </summary>
        public void confirmSelection()
        {
            //Recorremos cada tile del pipe indicandole su nuevo tipo y color
            for(int i = 0; i < _pipe.Count; ++i)
            {
                if(_pipe[i].getBackgroundColor() != _firstTile.getColor())
                {
                    if (_pipe[i].getTileType() != Tile.TileType.circleTile)
                    {
                        _pipe[i].setTileType(Tile.TileType.connectedTile);
                        _pipe[i].setTileColor(_firstTile.getColor());
                    }
                }
            }

            //Si no hemos finalizado el pipe el ultimo es la cabeza
            if (_pipe[_pipe.Count - 1].getTileType() != Tile.TileType.circleTile)
                _pipe[_pipe.Count - 1].setTileType(Tile.TileType.pipeHead);
        }

        public void clearPipe()
        {
            removeTilesRange(0, _pipe.Count);
        }

        public Color getColor()
        {
            return _firstTile.getColor();
        }


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
                _pipe[i].setTileType(Tile.TileType.voidTile); 
            }

            //Eliminamos de la lista los tiles dentro del rango
            _pipe.RemoveRange(beginning, end - beginning);

            if (_pipe.Count > 0 && _pipe[_pipe.Count - 1].getTileType() != Tile.TileType.circleTile)
                _pipe[_pipe.Count - 1].setTileType(Tile.TileType.pipeHead);
        }


        /// <summary>
        /// Elimina y resetea los tiles de la tuberia desde un indice a otro [inicio, final)
        /// </summary>
        /// <param name="beginning"> Indice inicial </param>
        /// <param name="end"> Indice final </param>
        private void temporalRemoveTilesRange(int beginning, int end)
        {
            //Reseteamos los tiles a vacios
            for (int i = beginning; i < end; ++i)
                _pipe[i].hide();
        }
    }
}