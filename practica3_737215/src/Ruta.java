/*************************************************
	Tecnología de la programación
	Práctica 3
	Autor: 741294 Víctor Miguel Peñasco Estívalez
		   737215 Rubén Rodríguez Esteban
 *************************************************/
import java.util.LinkedList;

public class Ruta{
	private LinkedList<Directorio> location;
	private Directorio dirActual;

	/*
	 * Construye el elemento Ruta
	 */
	public Ruta(Directorio raiz){
	    location = new LinkedList<Directorio>();
		this.location.add(raiz);
		this.dirActual=raiz;

	}

	/*
	 * Devuelve la ruta completa, con todos los nombres de los directorios
	 * desde la raíz hasta el directorio actual concatenados y separados por
	 * el separador “/”.
	 */
	public String pwd(){
		String ruta = "";
		for(int i=0; i<this.location.size(); i++){
			ruta = ruta + this.location.get(i).getName() + "/";
		}
		return ruta;
	}

	/*
	 * Muestra por pantalla los nombres de todos los ficheros, directorios y
	 * enlaces contenidos en la ruta actual, cada uno de ellos en una línea diferente, sin
	 * ningún dato más.
	 */
	public void ls() throws ExcepcionArbolFicheros{
		for(int i=0; i<this.dirActual.getList().size(); i++){
			System.out.println(this.dirActual.getList().get(i).getName());
		}
	}

	/*
	 * Cambia la ruta a otro directorio (path), pasándole el nombre del
	 * directorio al que quiere cambiar. Hay tres casos especiales: “.” se refiere
	 * al directorio actual, “..” se refiere al directorio anterior en el árbol de
	 * directorios y “/” se refiere a la raíz del árbol de directorios. También se
	 * le puede pasar como parámetro una ruta completa (varios directorios separados por “/”).
	 */
	public void cd(String path) throws ElementoInexistente, NoEsDirectorio{
		switch(path){
			case ".":
				break;
			case "..":
				if(this.location.size()>1) {
					this.location.removeLast();
					this.dirActual=this.location.getLast();
				}
				break;
			case "/":
				while(this.location.size()>1){
					this.location.removeLast();
				}
				this.dirActual=this.location.getLast();
				break;
			default:
			boolean rutaCompleta=false;
				String[] parts = path.split("/");
				if (path.charAt(0) == '/'){
					while(this.location.size()>1){
						this.location.removeLast();
					}
					rutaCompleta=true;
					this.dirActual=this.location.getLast();
				}
				int contador=0;
				for(String el:parts){
					boolean	 encontrado=false;
					for(int i=0; i<this.dirActual.getList().size() && !encontrado; i++){
						if (this.dirActual.getList().get(i).getName().equals(el)){
							encontrado=true;
							//Comprobar si es enlace
							if(this.dirActual.getList().get(i).getType().equals("Enlace")){
								//Pasar a su referenciado
								Directorio sigDir=(Directorio)this.dirActual.getList().get(i).getReference();
								this.dirActual=sigDir;
								this.location.add(sigDir);
							}
							else if(this.dirActual.getList().get(i).getType().equals("Archivo")){
								throw new NoEsDirectorio();
							}
							else{
								Directorio sigDir=(Directorio)this.dirActual.getList().get(i);
								this.dirActual=sigDir;
								this.location.add(sigDir);
							}


						}
					}
					if(!encontrado){
						if(contador>0 || !rutaCompleta){
							//Directorio no encontrado
							throw new ElementoInexistente();
						}
					}
					contador++;
				}
		}
	}

	/*
	 * Muestra por pantalla un número que es el tamaño del
	 * archivo, directorio o enlace dentro de la ruta actual identificado por la cadena
	 * de texto que se le pasa como parámetro. También se le puede pasar una ruta completa.
	 */
	public void stat(String element) throws ElementoInexistente{
		// Guarda la ruta actual en rutaInicial
		String rutaInicial=pwd();
		String[] parts = element.split("/");
		String ultimo=parts[parts.length-1];

		// Si se pasa como parámetro una ruta completa se posiciona en la raíz
		boolean rutaCompleta=false;
		if (element.charAt(0) == '/'){
			while(this.location.size()>1){
				this.location.removeLast();
			}
			rutaCompleta=true;
			this.dirActual=this.location.getLast();
		}
		// Se avanza hasta el directorio destino
		int j=0;
		for(String el:parts){
			if(j==parts.length-1){
				break;
			}
			boolean	 encontrado=false;
			for(int i=0; i<this.dirActual.getList().size() && !encontrado; i++){
				if (this.dirActual.getList().get(i).getName().equals(el)){
					encontrado=true;
					Directorio sigDir=(Directorio)this.dirActual.getList().get(i);
					this.dirActual=sigDir;
					this.location.add(sigDir);
				}
			}
			if(!encontrado){
				if(j>0 || !rutaCompleta){
					//Directorio no encontrado
					throw new ElementoInexistente();
				}
			}
			j++;
		}
		// Se muestra el tamaño del objeto en cuestion
		boolean encontrado=false;
		for(int i=0; i<this.dirActual.getList().size() && !encontrado; i++){
			if(this.dirActual.getList().get(i).getName().equals(ultimo)){
				encontrado=true;
				System.out.println(this.dirActual.getList().get(i).getSize());
			}
		}
		System.out.println(pwd());
		// Se regresa a la ruta original
		String[] cachos = rutaInicial.split("/");
		while(this.location.size()>1){
			this.location.removeLast();
		}
		this.dirActual=this.location.getLast();
		for(String dirs:cachos){
			boolean found=false;
			for(int i=0; i<this.dirActual.getList().size() && !found; i++){
				if (this.dirActual.getList().get(i).getName().equals(dirs)){
					found=true;
					Directorio sigDir=(Directorio)this.dirActual.getList().get(i);
					this.dirActual=sigDir;
					this.location.add(sigDir);
				}
			}
			if(!found){
				//Directorio no encontrado
				throw new ElementoInexistente();
			}
		}
		if(!encontrado) {
			// Elemento no encontrado
			throw new ElementoInexistente();
		}
	}

	/*
	 * Cambia el tamaño de un archivo dentro de la
	 * ruta actual (no se le puede pasar como parámetro una ruta completa). Si el
	 * archivo no existe dentro de la ruta actual, se crea automáticamente con el
	 * nombre y tamaño espeficados. Si el archivo referenciado por “file” es en
	 * realidad un enlace a un archivo, también cambia su tamaño.
	 */
	public void vim(String file, int size){
		boolean encontrado=false;
		for(int i=0; i<this.dirActual.getList().size() && !encontrado; i++){
			if(this.dirActual.getList().get(i).getName().equals(file)){
				encontrado=true;
				this.dirActual.getList().get(i).changeSize(size);
			}
		}
		if(!encontrado) {
			Archivo arch = new Archivo(file,size);
			this.dirActual.anyadir(arch);
		}
	}

	/*
	 * Crea un directorio dentro de la ruta actual. No se le puede pasar como
	 * parámetro una ruta completa.
	 */
	public void mkdir(String dir) throws ElementoExistente{
		boolean encontrado=false;
		for(int i=0; i<this.dirActual.getList().size() && !encontrado; i++){
			if(this.dirActual.getList().get(i).getName().equals(dir)){
				encontrado=true;
				throw new ElementoExistente();
			}
		}
		if(!encontrado){
			Directorio nuevoDir = new Directorio(dir);
			this.dirActual.anyadir(nuevoDir);
		}
	}

	/*
	 * Crea un enlace simbólico de nombre “dest” a que enlaza el elemento
	 * identificado mediante el nombre “orig”. “dest” no puede contener una ruta
	 * completa, pero “orig” sí, de tal modo que pueden crearse enlaces simbólicos
	 * entre elementos dentro de diferentes posiciones del árbol de directorios.
	 */
	public void ln(String orig, String dest) throws ElementoInexistente{
		// Guarda la ruta actual en rutaInicial
		String rutaInicial=pwd();
		String[] parts = orig.split("/");
		String ultimo=parts[parts.length-1];

		// Si se pasa como parámetro una ruta completa se posiciona en la raíz
		boolean rutaCompleta=false;
		if (orig.charAt(0) == '/'){
			while(this.location.size()>1){
				this.location.removeLast();
			}
			rutaCompleta=true;
			this.dirActual=this.location.getLast();
		}
		// Se avanza hasta el directorio destino
		int j=0;
		for(String el:parts){
			if(j==parts.length-1){
				break;
			}
			boolean	 encontrado=false;
			for(int i=0; i<this.dirActual.getList().size() && !encontrado; i++){
				if (this.dirActual.getList().get(i).getName().equals(el)){
					encontrado=true;
					Directorio sigDir=(Directorio)this.dirActual.getList().get(i);
					this.dirActual=sigDir;
					this.location.add(sigDir);
				}
			}
			if(!encontrado){
				if(j>0 || !rutaCompleta){
					//Directorio no encontrado
					throw new ElementoInexistente();
				}
			}
			j++;
		}
		// Se crea el enlace
		Enlace nuevoEnlace=new Enlace(dest, this.dirActual.getList().get(0));
		boolean encontrado=false;
		for(int i=0; i<this.dirActual.getList().size() && !encontrado; i++){
			if(this.dirActual.getList().get(i).getName().equals(ultimo)){
				encontrado=true;
				nuevoEnlace = new Enlace(dest, this.dirActual.getList().get(i));
			}
		}
		// Se regresa a la ruta original
		String[] cachos = rutaInicial.split("/");
		while(this.location.size()>1){
			this.location.removeLast();
		}
		this.dirActual=this.location.getLast();

		for(String dirs:cachos){
			boolean found=false;
			for(int i=0; i<this.dirActual.getList().size() && !found; i++){
				if (this.dirActual.getList().get(i).getName().equals(dirs)){
					found=true;
					Directorio sigDir=(Directorio)this.dirActual.getList().get(i);
					this.dirActual=sigDir;
					this.location.add(sigDir);
				}
			}
			if(!found){
				//Directorio no encontrado
				throw new ElementoInexistente();
			}
		}
		if(encontrado) {
			this.dirActual.anyadir(nuevoEnlace);
		}
		else{
			//Elemento no encontrado
			throw new ElementoInexistente();
		}

	}

	/*
	 * Elimina un elemento dentro de la ruta actual (puede pasársele como
	 * parámetro una ruta completa). Si es un archivo es simplemente eliminado. Si es
	 * un enlace elimina el enlace pero no lo enlazado. Si es un directorio, elimina
	 * el directorio y todo su contenido. Los enlaces a elementos borrados, sin embargo,
	 * siguen enlazando al elemento borrado. Así pues, para eliminar completamente un
	 * elemento hay que borrar el elemento y todos los enlaces que apuntan a dicho
	 * elemento de forma manual.
	 */
	public void rm(String e) throws ElementoInexistente{
		// Guarda la ruta actual en rutaInicial
		String rutaInicial=pwd();
		String[] parts = e.split("/");
		String ultimo=parts[parts.length-1];

		// Si se pasa como parámetro una ruta completa se posiciona en la raíz
		boolean rutaCompleta=false;
		if (e.charAt(0) == '/'){
			while(this.location.size()>1){
				this.location.removeLast();
			}
			rutaCompleta=true;
			this.dirActual=this.location.getLast();
		}
		// Se avanza hasta el directorio destino
		int j=0;
		for(String el:parts){
			if(j==parts.length-1){
				break;
			}
			boolean	 encontrado=false;
			for(int i=0; i<this.dirActual.getList().size() && !encontrado; i++){
				if (this.dirActual.getList().get(i).getName().equals(el)){
					encontrado=true;
					Directorio sigDir=(Directorio)this.dirActual.getList().get(i);
					this.dirActual=sigDir;
					this.location.add(sigDir);
				}
			}
			if(!encontrado){
				if(j>0 || !rutaCompleta){
					//Directorio no encontrado
					throw new ElementoInexistente();
				}
			}
			j++;
		}
		// Se elimina el objeto en cuestion
		boolean encontrado=false;
		for(int i=0; i<this.dirActual.getList().size() && !encontrado; i++){
			if(this.dirActual.getList().get(i).getName().equals(ultimo)){
				encontrado=true;
				this.dirActual.getList().remove(i);
			}
		}
		// Se regresa a la ruta original
		String[] cachos = rutaInicial.split("/");
		while(this.location.size()>1){
			this.location.removeLast();
		}
		this.dirActual=this.location.getLast();
		for(String dirs:cachos){
			boolean found=false;
			for(int i=0; i<this.dirActual.getList().size() && !found; i++){
				if (this.dirActual.getList().get(i).getName().equals(dirs)){
					found=true;
					Directorio sigDir=(Directorio)this.dirActual.getList().get(i);
					this.dirActual=sigDir;
					this.location.add(sigDir);
				}
			}
			if(!found){
				//Directorio no encontrado
				throw new ElementoInexistente();
			}
		}
		if(!encontrado) {
			// Elemento no encontrado
			throw new ElementoInexistente();
		}
	}
}
