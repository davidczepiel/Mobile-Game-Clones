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

        /// <summary>
        /// Aniade un nuevo tile a la tuberia
        /// </summary>
        /// <param name="newTile"></param>
        public void addTileToPipe(Tile newTile)
        {
            //La tuberia no debe contener ya al tile
            if (!_pipe.Contains(newTile))
            {
                //Si el nuevo tile es uno de los extremos y ya habia tiles en la tuberia, significa que se ha cerrado
                if ((newTile == _secondTile || newTile == _firstTile) && _pipe.Count > 0)
                    _finished = true;

                _pipe.Add(newTile);
            }
        }

        /// <summary>
        /// Elimina los tiles necesarios cuando la tuberia es cortada por otra
        /// </summary>
        /// <param name="cutted"></param>
        public void cut(Tile cutted)
        {
            //Obtener indice del corte
            int where = _pipe.IndexOf(cutted);

            //Si la tuberia no esta cerrada, se eliminan los tiles desde el corte hasta el final
            if (!_finished)
                removeTilesRange(where, _pipe.Count - 1);
            //Si esta cerrada, se corta el trazo con mas cantidad de tiles respecto al corte
            else
            {
                if (where < _pipe.Count - 1 - where)
                    removeTilesRange(0, where);
                else
                    removeTilesRange(where, _pipe.Count - 1);
            }

            //La tuberia deja de estar cerrada
            _finished = false;
        }

        /// <summary>
        /// Elimina y resetea los tiles de la tuberia desde un indice a otro [inicio, final)
        /// </summary>
        /// <param name="beginning"> Indice inicial </param>
        /// <param name="end"> Indice final </param>
        private void removeTilesRange(int beginning, int end)
        {
            //Reseteamos los tiles a vacios
            for(int i = beginning; i < end; ++i)
            {
                _pipe[i].setTileType(Tile.TileType.voidTile); 
            }

            //Eliminamos de la lista los tiles dentro del rango
            _pipe.RemoveRange(beginning, end - beginning);
        }

    }

}