/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejecucion;

import arduino.Arduino;
import contenedor.Contenedor;

/**
 *
 * @author HenryPatricio
 */
public class Ejecucion {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Arduino arduino = new Arduino();
        arduino.abrir();
        arduino.listener();
        Contenedor contenedorInvernadero = new Contenedor();
        contenedorInvernadero.contenedor();
        contenedorInvernadero.iniciarAgentes();
    }

}
