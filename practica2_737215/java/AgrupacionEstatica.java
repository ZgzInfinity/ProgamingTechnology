//Similar a los includes de C++, traemos aquellas clases que nos seran utiles
import java.util.Iterator;
import java.util.NoSuchElementException;

//En Java, no existe el concepto de pre-declaracion ni de funcion. Java es un 
//lenguaje puramente orientado a objetos, por lo que todo son clases.
//
//El uso de programaci�n param�trica no requiere ninguna palabra clave. Directamente ponemos
//el tipo par�metro entre corchetes.
//Para hacer uso de iteradores, implementamos el interfaz Iterable<T>. Esto es un mecanismo
//b�sico de la herencia en Java que es inevitable en este caso y sobre el que hablaremos
//m�s adelante en clase de teor�a.
public class AgrupacionEstatica<T> implements Iterable<T>, Agrupacion<T>
{
	//Esta es la �nica forma que tiene Java de declarar valores inmutables.
	private static final int MAX = 40; 

	//Habr�s notado que en Java no hay bloques "private" y "public", sino que es una palabra clave
	//que se pone en cada elemento (atributo o m�todo).
	private T[] datos;
	private int total;

	//El constructor de Java llama a los constructores de los par�metros dentro del bloque de c�digo.
	@SuppressWarnings("unchecked")
	public AgrupacionEstatica()
	{
		datos = (T[])(new Object[MAX]);
		total = 0;
	}

	public boolean anyadir(T t) 
	{
		//TODO: Rellena el c�digo de este m�todo para que anyada un elemento a la agrupacion
		//y devuelva si ha sido posible (o no) meterlo.
		boolean sePuede = this.total < MAX;
		if (sePuede) {
			this.datos[this.total] = t;
			this.total++;
		}
		return sePuede;
	}

	public boolean borrarUltimo()
	{
		//TODO: Rellena el c�digo de este m�todo para que borre el �ltimo elemento de la agrupaci�n
		//y devuelva si ha sido posible (o no) borrarlo.
		boolean sePuede = this.total > 0;
		if (sePuede) this.total--;
		return sePuede;
	}

	//Esta clase representa a un iterador sobre la agrupaci�n. De nuevo, por el comportamiento est�ndar de los
	//iteradores en Java, deberemos utilizar la herencia.
	private class IteradorAgrupacion<T> implements Iterator<T> 
	{
		//Aqu� declaramos los atributos
		AgrupacionEstatica<T> ag;
		int i;

		//Este es el constructor del iterador.
		private IteradorAgrupacion(AgrupacionEstatica<T> ag) 
		{
			this.ag = ag;
			i = ag.total - 1;
		}

		//Todos los iteradores deben de implementar un m�todo que devuelva
		//si hay siguiente elemento (o no).
		public boolean hasNext()	
		{
			//TODO: Devuelve si hay siguiente elemento o no.
			 return this.i >= 0;
		}

		//Todos los iteradores deben de implementar un m�todo que devuelva el elemento
		//en la posci�n actual y avance el iterador. Por construcci�n, este m�todo debe
		//lanzar una excepci�n si no existe el siguiente elemento.
		//Recuerda que se recorren desde el �ltimo al primero.
		public T next() throws NoSuchElementException
		{
			//Aqu� lanzamos la excepci�n
			if (!hasNext()) throw new NoSuchElementException();
			else {
				//TODO: Devuelve el elemento apuntado por el iterador, y avanza el iterador.
				this.i--;
				return this.ag.datos[this.i + 1];
			}
		}

		//Los iteradores en Java tienen que tener este m�todo, tambi�n. Para no implementarlo (no lo vamos
		//a usar y nuestra definici�n de agrupaci�n no lo incluye) simplemente lanzamos una excepci�n.
		public void remove() throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();
		}
	}

	//Este m�todo de la estructura de datos simplemente devuelve un nuevo iterador con el que recorrerse la estructura de datos.
	public Iterator<T> iterator()
	{
		return new IteradorAgrupacion<T>(this);
	}
}

