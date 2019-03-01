/*************************************************
	Tecnología de la programación
	Práctica 3
	Autor: 741294 Víctor Miguel Peñasco Estívalez
		   737215 Rubén Rodríguez Esteban
 *************************************************/
public class Enlace implements Generico{
	private String name;
	private Generico ref;

	/*
	 * Construye el elemento Enlace
	 */
	public Enlace(String nombre, Generico referencia){
		this.name=nombre;
		this.ref=referencia;
	}

	public String getName(){
		return this.name;
	}
	public int getSize(){
		return this.ref.getSize();
	}
	public String getType(){
		return "Enlace";
	}
	public void changeSize(int size){

	}
	public Generico getReference(){
		return this.ref;
	}
}
