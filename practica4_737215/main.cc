#include "practica4.h"
#include <iostream>

int main(int argc, char** argv)
{
	Camion camion(10);

	Contenedor<Generico> contenedor_generico(4);
	Contenedor<Toxico>   contenedor_toxico(3);
	Contenedor<SerVivo>  contenedor_servivo(2);
	Producto             apuntes_tepro(1, "Apuntes de Tecnologia de Programacion");

	if (!camion.guardar(contenedor_generico)) std::cout<<"Camion lleno con contenedor generico"<<std::endl;
	if (!camion.guardar(contenedor_toxico))   std::cout<<"Camion lleno con contenedor toxico"<<std::endl;
	if (!camion.guardar(contenedor_servivo))  std::cout<<"Camion lleno con contenedor de seres vivos"<<std::endl;
	if (!camion.guardar(apuntes_tepro))       std::cout<<"Camion lleno con apuntes de tepro"<<std::endl;

	Producto trenzas_almudevar(0.5, "Trenzas de Almudevar");
	if (!camion.guardar(trenzas_almudevar))   std::cout<<"Camion lleno con trenzas de Almudevar"<<std::endl;
	contenedor_generico.guardar(trenzas_almudevar);

	Toxico discos_melendi(1, "Discos de Melendi");
	// contenedor_generico.guardar(discos_melendi); //Esto no deberia compilar
	contenedor_toxico.guardar(discos_melendi);

	SerVivo elvis_presley(0.1, "Elvis Presley");
	// camion.guardar(elvis_presley); //Esto no deberia compilar
	contenedor_servivo.guardar(elvis_presley);

	Contenedor<Generico> otro_contenedor_generico(1);
	Contenedor<Toxico>   otro_contenedor_toxico(1);
	Contenedor<SerVivo>  otro_contenedor_servivo(1);
	contenedor_generico.guardar(otro_contenedor_generico);
	contenedor_generico.guardar(otro_contenedor_toxico);
	contenedor_generico.guardar(otro_contenedor_servivo);
	//contenedor_generico.guardar(camion); //Esto no deberia compilar
	//contenedor_toxico.guardar(otro_contenedor_toxico); //Esto no deberia compilar
	//contenedor_servivo.guardar(otro_contenedor_servivo); //Esto no deberia compilar
}
