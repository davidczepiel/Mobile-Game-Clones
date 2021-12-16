- Queremos comentarte que intentamos realizar un manager de las Ads evitando así que hubira diferentes archivos y que el lmanager manejara el load y show de
los anuncios con callbacks que llamaran a funcionalidades. Sin embargo no pudimos debido a que los Listeners no están correctamente implementados en esta versión.
Ya no es solo que llame(o no) cuando mees un this en el parámetro de Load o Show, es que si realizas un Show(por ejemplo, con el de Reward) y anteriormente se ha realizado
un Show de un Interstitial, se llaman a los dos!!!
Lo más curioso de todo esto es que el siguiente método

 public void OnUnityAdsShowComplete(string adUnitId, UnityAdsShowCompletionState showCompletionState) { }

debería de pasarte correctamente (al menos) el tipo del que se ha terminado el Show, sin embargo te pasa el tuyo! Por ello es imposible comparar que tipo es
como lo hacen en los ejemplos(adUnitId.Equals(_adUnitId)) guardandote el tipo que tu tenías porque siempre va a ser el mismo.

