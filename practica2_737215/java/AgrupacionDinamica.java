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
public class AgrupacionDinamica<T> implements Iterable<T>, Agrupacion<T> 
{
	
	//Habr�s notado que en Java no hay bloques "private" y "public", sino que es una palabra clave
	//que se pone en cada elemento (atributo o m�todo).
	class Nodo {
		T dato;
		Nodo sig;
	}
	
	private Nodo cima;

	//El constructor de Java llama a los constructores de los par�metros dentro del bloque de c�digo.
	@SuppressWarnings("unchecked")
	public AgrupacionDinamica()
	{
		this.cima = null;
	}

	public boolean anyadir(T t) 
	{
		//TODO: Rellena el c�digo de este m�todo para que anyada un elemento a la agrupacion
		//y devuelva si ha sido posible (o no) meterlo.
		Nodo aux;
		aux = new Nodo();
		aux.dato = t;
		aux.sig = cima;
		cima = aux;
		return true;
	}

	public boolean borrarUltimo()
	{
		//TODO: Rellena el c�digo de este m�todo para que borre el �ltimo elemento de la agrupaci�n
		//y devuelva si ha sido posible (o no) borrarlo.
		if (cima != null)
        {
			Nodo aux;
			aux = new Nodo();
			aux = cima;
			cima = cima.sig;
			aux = null;
            return true;   
        }
		else
		{
			return false;
		}
       
	}

	//Esta clase representa a un iterador sobre la agrupaci�n. De nuevo, por el comportamiento est�ndar de los
	//iteradores en Java, deberemos utilizar la herencia.
	private class IteradorAgrupacion implements Iterator<T>
	{
		//Aqu� declaramos los atributos
		Nodo iter;
		
		//Este es el constructor del iterador.
		private IteradorAgrupacion(Nodo iterador)
		{		
		   this.iter = iterador;
		}

		//Todos los iteradores deben de implementar un m�todo que devuelva
		//si hay siguiente elemento (o no).
		public boolean hasNext()	
		{
			return this.iter != null;
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
				T valor = this.iter.dato;
				this.iter = this.iter.sig;
				return valor;
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
		return new IteradorAgrupacion(this.cima);
	}
}