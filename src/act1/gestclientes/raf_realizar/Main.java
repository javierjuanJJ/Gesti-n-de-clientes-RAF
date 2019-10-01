/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package act1.gestclientes.raf_realizar;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author vesprada
 */
public class Main {

	/**
	 * @param args the command line arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String Resultado="";
		File f = new File("clientes.dat");
		File f1 = new File("clientes2.dat");
		File f2 = new File("Clientes_compactados.dat");
		RandomAccessFile raf = new RandomAccessFile(f, "rw");
		
		Resultado= Resultado + "compactar_con_fichero_auxiliar" + "\n" + Leer(f) + "\n";		
		compactar_con_fichero_auxiliar(f);
		Resultado= Resultado + Leer(f2) + "\n" + Leer(f) + "\n";		
		f2.delete();		
		Resultado= Resultado + "compactar_sobre_mismo_fichero" + "\n" + Leer(f1) + "\n";			
		compactar_sobre_mismo_fichero(f1);
		Resultado= Resultado + Leer(f1);
		
		System.out.println(Resultado);

	}

	private static void compactar_con_fichero_auxiliar(File f) throws FileNotFoundException, IOException {
		Cliente c = new Cliente();
		int contador = 0;
		int contador2 = 0;
		File archivo_compactado = new File("Clientes_compactados.dat");
		try (RandomAccessFile fichero_aleatorio_lectura = new RandomAccessFile(f, "r");
				RandomAccessFile fichero_aleatorio_escritura = new RandomAccessFile(archivo_compactado, "rw");) {
			contador = 0;
			while ((contador < (fichero_aleatorio_lectura.length() / c.KTAM))) {

				Cliente Objeto_cliente = new Cliente();

				Objeto_cliente.leerDeFichero(fichero_aleatorio_lectura);

				if (Objeto_cliente.getId() >= 0) {

					Objeto_cliente.escribirEnFichero(fichero_aleatorio_escritura);
					contador2++;
				}

				contador++;
			}

			fichero_aleatorio_escritura.setLength(c.KTAM * contador2);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (RandomAccessFile fichero_aleatorio_escritura = new RandomAccessFile(f, "rw");
				RandomAccessFile fichero_aleatorio_lectura = new RandomAccessFile((archivo_compactado), "r");) {
			contador = 0;
			while ((contador < (fichero_aleatorio_lectura.length() / c.KTAM))) {

				Cliente Objeto_cliente = new Cliente();
				Objeto_cliente.leerDeFichero(fichero_aleatorio_lectura);
				Objeto_cliente.escribirEnFichero(fichero_aleatorio_escritura);
				contador++;
				
			}

			fichero_aleatorio_escritura.setLength(c.KTAM * contador);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void compactar_sobre_mismo_fichero(File f) throws FileNotFoundException, IOException {
		Cliente c = new Cliente();
		int contador = 0;
		int contador2 = 0;
		Cliente Objeto_cliente = null;
		try (RandomAccessFile fichero_aleatorio_lectura = new RandomAccessFile(f, "r");
				RandomAccessFile fichero_aleatorio_escritura = new RandomAccessFile(f, "rw");) {

			while ((contador <= (fichero_aleatorio_lectura.length() / c.KTAM))) {

				Objeto_cliente = new Cliente();

				Objeto_cliente.leerDeFichero(fichero_aleatorio_lectura);

				if (Objeto_cliente.getId() >= 0) {
					Objeto_cliente.escribirEnFichero(fichero_aleatorio_escritura);
					contador2++;

				}

				contador++;
			}
		} 
		catch (EOFException e) {
			System.err.println();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		truncar(f,contador2);

	}

	private static String Leer(File f) throws FileNotFoundException {
		Cliente c = new Cliente();
		int contador = 0;
		String lectura = "";
		
		lectura = lectura + "----------------------------------" + "\n";
		lectura = lectura + "Archivo: " + f.getName() + " con tamanyo " + f.length() + " bytes." + "\n";
		lectura = lectura + "----------------------------------" + "\n";

		try (RandomAccessFile fichero_aleatorio_lectura = new RandomAccessFile(f, "r");) {
			contador = 1;

			while ((contador <= (fichero_aleatorio_lectura.length() / c.KTAM))) {

				Cliente Objeto_cliente = new Cliente();

				Objeto_cliente.leerDeFichero(fichero_aleatorio_lectura);
				
				lectura = lectura + Objeto_cliente.getId() + " " + Objeto_cliente.getNombre() + " "
						+ Objeto_cliente.getApellidos() + " " + Objeto_cliente.getSaldo() + "\n";

				contador++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lectura;
	}
	
	private static void truncar(File f, int hasta) throws FileNotFoundException {
		
		try (RandomAccessFile fichero_aleatorio_escritura = new RandomAccessFile(f, "rw");) {
			Cliente c = new Cliente();
			fichero_aleatorio_escritura.setLength(c.KTAM * hasta);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
