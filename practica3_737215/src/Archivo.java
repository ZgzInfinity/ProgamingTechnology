/*************************************************
	Tecnología de la programación
	Práctica 3
	Autor: 741294 Víctor Miguel Peñasco Estívalez
		   737215 Rubén Rodríguez Esteban
 *************************************************/
public class Archivo implements Generico{
	private String name;
	private int size;

	/*
	 * Construye el elemento Archivo
	 */
	public Archivo(String nombre, int tamanno){
		this.name=nombre;
		this.size=tamanno;
	}

	public String getName(){
		return this.name;
	}
	public int getSize(){
		return this.size;
	}
	public String getType(){
		return "Archivo";
	}
	public void changeSize(int tamanno){
		this.size=tamanno;
	}
	public Generico getReference(){
		Generico vacio= new Directorio("");
		return vacio;
	}
}
