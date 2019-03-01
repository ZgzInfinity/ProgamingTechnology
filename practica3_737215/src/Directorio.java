/*************************************************
	Tecnología de la programación
	Práctica 3
	Autor: 741294 Víctor Miguel Peñasco Estívalez
		   737215 Rubén Rodríguez Esteban
 *************************************************/
import java.util.LinkedList;

public class Directorio implements Generico{
	private String name;
	private LinkedList<Generico> list;

	/*
	 * Construye el elemento Directorio
	 */
	public Directorio(String nombre){
		this.name=nombre;
		list = new LinkedList<Generico>();
	}

	public String getName(){
		return this.name;
	}

	public int getSize(){
		int tamanno=0;
		for(int i=0; i< this.list.size(); i++){
			tamanno += this.list.get(i).getSize();
		}
		return tamanno;
	}
	public String getType(){
		return "Directorio";
	}
	public void anyadir(Generico nombre){
		this.list.add(nombre);
	}
	public LinkedList<Generico> getList(){
		return this.list;
	}
	public void changeSize(int size) {
	}
	public Generico getReference(){
		Generico vacio= new Directorio("");
		return vacio;
	}
}
