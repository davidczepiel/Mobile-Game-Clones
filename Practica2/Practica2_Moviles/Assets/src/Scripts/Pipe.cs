using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace Flow
{

    public class Pipe
    {
        List<Vector2Int> _currentPipe;
        List<Vector2Int> _lastPipe;

        Vector2Int _firstTile;
        Vector2Int _secondTile;

        bool _finished;
        Color _color;

        /// <summary>
        /// Constructora de clase
        /// </summary>
        /// <param name="firstTile"> Posicion del primer circulo de la tuberia </param>
        /// <param name="lastTile"> Posicion del ultimo circulo de la tuberia </param>
        /// <param name="color"> Color de la tuberia </param>
        public Pipe(Vector2Int firstTile, Vector2Int lastTile, Color color)
        {
            _firstTile = firstTile;
            _secondTile = lastTile;
            _finished = false;
            _lastPipe = new List<Vector2Int>();
            _currentPipe = new List<Vector2Int>();
            _color = color;
        }

        #region Metodos de control

        public List<Vector2Int> getCurrentPipe()
        {
            return this._currentPipe;
        }

        public List<Vector2Int> getLastPipe()
        {
            return this._lastPipe;
        }

        public Color getColor()
        {
            return _color;
        }

        /// <summary>
        /// Metodo que limpia la pipe por completo
        /// </summary>
        public void clearPipe()
        {
            _currentPipe.Clear();

            //La pipe pasa a estar incompleta
            _finished = false;
        }

        /// <summary>
        /// Aniade un nuevo tile a la tuberia, devuelve true si se ha cortado a si misma
        /// </summary>
        /// <param name="newTile"></param>
        public List<Vector2Int> addTileToPipe(Vector2Int newTile)
        {
            //La tuberia no debe contener ya al tile ni estar terminada aun para meter nuevos tiles
            if (!_currentPipe.Contains(newTile))
            {
                //Si el nuevo tile es uno de los extremos y ya habia tiles en la tuberia, significa que se ha cerrado
                if ((newTile == _secondTile || newTile == _firstTile) && _currentPipe.Count > 1)
                    _finished = true;

                _currentPipe.Add(newTile);
            }
            else if (_currentPipe.Count > 1)     //Volver atras solo si hay mas tiles en la lista que el primer circulo
            {
                //La pipe pasa a estar incompleta
                _finished = false;
                return removeTilesRange(_currentPipe.IndexOf(newTile) + 1, _currentPipe.Count);
            }
            return null;
        }

        /// <summary>
        /// Elimina los tiles necesarios cuando la tuberia es cortada por otra
        /// </summary>
        /// <param name="cutted"></param>
        public List<Vector2Int> cut(Vector2Int cutted, int offset = 0)
        {
            //Obtener indice del corte
            int where = _currentPipe.IndexOf(cutted);

            //La tuberia deja de estar cerrada
            _finished = false;
            return removeTilesRange(where + offset, _currentPipe.Count);
        }

        public void saveFlow()
        {
            _lastPipe.Clear();

            foreach (Vector2Int t in _currentPipe)
            {
                _lastPipe.Add(new Vector2Int(t.x, t.y));
            }
        }

        #endregion

        #region Metodos auxiliares
        /// +--------------------------------------------------------------------------------------+
        /// |                                 METODOS AUXILIARES                                   |
        /// +--------------------------------------------------------------------------------------+

        /// <summary>
        /// Devuelve si la tuberia esta completa
        /// </summary>
        public bool isCompleted()
        {
            return _finished;
        }

        public bool isEmpty()
        {
            return _currentPipe.Count == 0;
        }

        /// <summary>
        /// Elimina y resetea los tiles de la tuberia desde un indice a otro [inicio, final)
        /// </summary>
        /// <param name="beginning"> Indice inicial </param>
        /// <param name="end"> Indice final </param>
        private List<Vector2Int> removeTilesRange(int beginning, int end)
        {
            int a = 0;
            if (beginning < 0)
                a = 0;
            //Eliminamos de la lista los tiles dentro del rango
            List<Vector2Int> aux = new List<Vector2Int>(_currentPipe);
            aux.RemoveRange(0, beginning);
            _currentPipe.RemoveRange(beginning, end - beginning);
            return aux;
        }
        #endregion
    }
}