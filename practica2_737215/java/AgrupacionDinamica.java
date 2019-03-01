//Similar a los includes de C++, traemos aquellas clases que nos seran utiles
import java.util.Iterator;
import java.util.NoSuchElementException;

//En Java, no existe el concepto de pre-declaracion ni de funcion. Java es un 
//lenguaje puramente orientado a objetos, por lo que todo son clases.
//
//El uso de programación paramétrica no requiere ninguna palabra clave. Directamente ponemos
//el tipo parámetro entre corchetes.
//Para hacer uso de iteradores, implementamos el interfaz Iterable<T>. Esto es un mecanismo
//básico de la herencia en Java que es inevitable en este caso y sobre el que hablaremos
//más adelante en clase de teoría.
public class AgrupacionDinamica<T> implements Iterable<T>, Agrupacion<T> 
{
	
	//Habrás notado que en Java no hay bloques "private" y "public", sino que es una palabra clave
	//que se pone en cada elemento (atributo o método).
	class Nodo {
		T dato;
		Nodo sig;
	}
	
	private Nodo cima;

	//El constructor de Java llama a los constructores de los parámetros dentro del bloque de código.
	@SuppressWarnings("unchecked")
	public AgrupacionDinamica()
	{
		this.cima = null;
	}

	public boolean anyadir(T t) 
	{
		//TODO: Rellena el código de este método para que anyada un elemento a la agrupacion
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
		//TODO: Rellena el código de este método para que borre el último elemento de la agrupación
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

	//Esta clase representa a un iterador sobre la agrupación. De nuevo, por el comportamiento estándar de los
	//iteradores en Java, deberemos utilizar la herencia.
	private class IteradorAgrupacion implements Iterator<T>
	{
		//Aquí declaramos los atributos
		Nodo iter;
		
		//Este es el constructor del iterador.
		private IteradorAgrupacion(Nodo iterador)
		{		
		   this.iter = iterador;
		}

		//Todos los iteradores deben de implementar un método que devuelva
		//si hay siguiente elemento (o no).
		public boolean hasNext()	
		{
			return this.iter != null;
		}

		//Todos los iteradores deben de implementar un método que devuelva el elemento
		//en la posción actual y avance el iterador. Por construcción, este método debe
		//lanzar una excepción si no existe el siguiente elemento.
		//Recuerda que se recorren desde el último al primero.
		public T next() throws NoSuchElementException
		{
			//Aquí lanzamos la excepción
			if (!hasNext()) throw new NoSuchElementException();
			else {
				//TODO: Devuelve el elemento apuntado por el iterador, y avanza el iterador.
				T valor = this.iter.dato;
				this.iter = this.iter.sig;
				return valor;
			}
		}

		//Los iteradores en Java tienen que tener este método, también. Para no implementarlo (no lo vamos
		//a usar y nuestra definición de agrupación no lo incluye) simplemente lanzamos una excepción.
		public void remove() throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();
		}
	}

	//Este método de la estructura de datos simplemente devuelve un nuevo iterador con el que recorrerse la estructura de datos.
	public Iterator<T> iterator()
	{
		return new IteradorAgrupacion(this.cima);
	}
}