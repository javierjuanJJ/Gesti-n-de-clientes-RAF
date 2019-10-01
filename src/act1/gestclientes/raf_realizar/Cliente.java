/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package act1.gestclientes.raf_realizar;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Clase utilizada para encapsular los datos de la entidad cliente.
 *
 * @author sergio
 */
public class Cliente {

    static final byte KTAM = 126;           // total bytes que ocupa el registro en memoria
    static final byte KTNOMBRE = 20;        // tamaño máximo campo nombre
    static final byte KTAPELLIDOS = 40;     // tamaño máximo campo apellidos
    private short id;                       // 2 bytes. Si es negativo indica registro borrado
    private StringBuilder nombre;           // max. 20 caracteres -> 40 bytes (2 bytes por caracter)
    private StringBuilder apellidos;        // max. 40 caracteres -> 80 bytes 
    private float saldo;                    // 4 bytes.

    /**
     * Constructor de clase vacío.
     */
    public Cliente() {
    }

    /**
     * Constructor de clase con parámetros.
     *
     * @param id identificador del cliente
     * @param n nombre del cliente
     * @param a apellido del cliente
     * @param saldo saldo del cliente
     */
    public Cliente(short id, String n, String a, float saldo) {
        this.id = id;
        this.nombre = new StringBuilder(n);
        this.nombre.setLength(KTNOMBRE);
        this.apellidos = new StringBuilder(a);
        this.apellidos.setLength(KTAPELLIDOS);
        this.saldo = saldo;
    }
    
    public short getId() {
        return this.id;
    }
    
    public void setId(short i) {
        this.id = i;
    }

    public String getNombre() {
        return this.nombre.toString().trim();
    }

    public String getApellidos() {
        return this.apellidos.toString().trim();
    }

    public float getSaldo() {
        return this.saldo;
    }

    public void setNombre(String nombre) {
        this.nombre = new StringBuilder(nombre);
    }

    /**
     * Escribe físicamente en el fichero los datos del cliente.
     *
     * @param raf El descriptor de fichero de acceso aleatorio
     * @throws IOException
     */
    public void escribirEnFichero(RandomAccessFile raf) throws IOException {
        raf.writeShort(this.id);
        raf.writeChars(this.nombre.toString());
        raf.writeChars(this.apellidos.toString());
        raf.writeFloat(saldo);
    }

    /**
     * Lee los datos del fichero y los almacena en el objeto.
     *
     * @param raf El descriptor de fichero de acceso aleatorio
     * @throws IOException
     */
    public void leerDeFichero(RandomAccessFile raf) throws IOException {
        this.id = raf.readShort();        
        String aux = "";        
        for (int i = 0; i < KTNOMBRE; i++) {
            aux = aux + raf.readChar();
        }
        this.nombre = new StringBuilder(aux);
        this.nombre.setLength(KTNOMBRE);
        aux = "";
        for (int i = 0; i < KTAPELLIDOS; i++) {
            aux = aux + raf.readChar();
        }        
        this.apellidos = new StringBuilder(aux);
        this.apellidos.setLength(KTAPELLIDOS);
        this.saldo = raf.readFloat();
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", saldo=" + saldo + '}';
    }

    boolean estaBorrado() {
        return this.getId()<0;
    }

}
