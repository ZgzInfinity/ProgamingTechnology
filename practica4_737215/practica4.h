/*
 *---------------------------------
 * Autor: Rubén Rodríguez Esteban
 * NIP: 737215
 * --------------------------------
 */


#include <iostream>
#include <cstring>
#include <list>
#include <iterator>

using namespace std;

/*
 * Clase Transportables
 * Proporciona el volumen de todos los elementos
 * que se pueden transportar
 */
class Transportables{
	private:
	double capacidad;
	public:
		Transportables(double volumen){
			capacidad = volumen;
		}
		virtual double devolverVolumen(){
			return capacidad;
		}
};


/*
 * Clase SerVivo
 * Clase derivada de la clase Transportable
 * Representa los elementos transportables de tipo serVvivo
 */
class SerVivo : public Transportables{
	private:
		double capacidad;
		string alias;
	public:
		SerVivo(double volumen, const string& nombre) : Transportables(volumen){
			alias = nombre;
		}
};

/*
 * Clase Toxico
 * Clase derivada de la clase Transportable
 * Representa los elementos transportables de tipo Toxico
 */
class Toxico : public Transportables{
	private:
		double capacidad;
		string alias;
	public:
		Toxico(double volumen, const string& nombre) : Transportables(volumen)
		{
			alias = nombre;
		}
};


/*
 * Clase Generico
 * Clase derivada de la clase Transportable
 * Representa los elementos transportables de tipo Generico
 */
class Generico : public Transportables{
	private:
		double capacidad;
	public:
		Generico(double volumen) : Transportables(volumen){};
};


/*
 * Clase Producto
 * Clase derivada de la clase Generico
 * Representa los elementos que son de de tipo Producto generico
 * siendo generico cualquier cosa a transportar
 */
class Producto : public Generico{
	private:
		double capacidad;
		string alias;
	public:
	Producto(double volumen, const string& nombre) : Generico(volumen)
	{ alias = nombre; }
	
	string devolNombre(){
		return alias;
	}
};

/*
 * Clase Transporte generico
 * Implementada para derivar clases camión y contenedor
 */
template <typename T>
class Transporte{
	private:
		list<T>contenido;
		typename list<T>::iterator it;
		double capacidad;
	public:
		Transporte<T>(double volumen){
			capacidad = volumen;
		}
		
		double devolverVolumen(){
			double tamanyo = 0.0;
			for (it = contenido.begin(); it != contenido.end(); it++){
				tamanyo += (*it).devolverVolumen();
			}
			return tamanyo;
		}
		
		bool guardar(T Elemento){
			bool insercionCorrecta;
			double volumenAnyadido = Elemento.devolverVolumen();
			double volumenOcupado = devolverVolumen();
			if (volumenOcupado < capacidad){
				if (volumenOcupado + volumenAnyadido <= capacidad){
					contenido.push_back(Elemento);
					insercionCorrecta = true;
				}
				else{
					insercionCorrecta = false;
				}
			}
			else{
				insercionCorrecta =  false;
			}
		return insercionCorrecta;
		}
};


/*
 * Clase contenedor
 * Se deriva de la clase generica Tranasporte y de la clase Generico
 */
template <typename T> 
class Contenedor : public Transporte<T> , public Generico{
	public:
		Contenedor<T>(double volumen) : Transporte<T>(volumen), Generico(volumen){};
};


/*
 * Clase Camion
 * Se deriva de la clase generica Transporte que guarda elementos de la clase Generico
 */
class Camion : public Transporte<Generico>{
	public:
		Camion(double volumen) : Transporte<Generico>(volumen){};
};
