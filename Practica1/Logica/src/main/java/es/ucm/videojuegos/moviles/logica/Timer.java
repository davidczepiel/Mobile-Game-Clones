package es.ucm.videojuegos.moviles.logica;
/*Clase que guarda toda la informacion necesaria para representar un temporizador
* Se utiliza para realizar las animaciones al clicar en los ciculos no modificables
* Ofrece las funciones más básicas necesarias para un temporizador, como
* iniciar el temporizador, preguntar si ha terminado, o establecer el tiempo de duracion
* * Timer*/
public class Timer {

    Timer(double time){
        this._timerTime = time;
        this._time = 0;
        _started = false;
        _finished = false;
    }

    /*Metodo encargado de hacer la cuenta atras del temporizador
    * Solo contara si se ha empezado a contar y aun no ha terminado
    * Una vez termine establecera el valor que le correapondqa a las
    * variables que indican el tiempo restante y si ha terminado o no*/
    public void tick(double elapsedTime){
        if(this._started  && !this._finished) {
            this._time-=elapsedTime;
            if(this._time<=0) {
                this._finished = true;
                this._time = 0;
            }
        }
    }

    /*Metodo que permite establecer el tiempo que transcurre desde
    * que el timer empieza a contar hasta que temina*/
    public void set_timerTime(float _timerTime) {
        this._timerTime = _timerTime;
    }
    /*Metodo que permite indicarle al timer que deseamos empezar la cuenta atras*/
    public void start(){
        this._started= true;
        this._finished = false;
        this._time = this._timerTime;
    }

    /*Metodo que devuelve si el timer ha empezado a contar*/
    public boolean is_Started(){return this._started;}

    /*Metodo que devuelve si el timer ha terminado*/
    public boolean is_finished() {return this._finished;}

    /*Metodo que devuelve el tiempo que le queda al timer para terminar*/
    public double get_time(){return this._time;}

    /*Metodo que devuelve el tiempo que tarda el timer en hacer una cuenta atras*/
    public double get_timerTime(){return this._timerTime;}

    //Numero que representa el tiempo que queda para que el temporizador acabe
    private double _time;

    //Numero que representa el tiempo que durara el temporizador cada vez que empiece a contar
    private double _timerTime;

    //Indica si el timer ha empezado a contar o no
    private boolean _started;

    //Indica si el timer ha terminado o no
    private boolean _finished;
}
