/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes.receptor;

import actuadores.Envio;
import agentes.deteccion.AgenteIluminacion;
import arduino.Arduino;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HenryPatricio
 */
public class AgenteReceptor extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new Comportamiento());
    }

    private class Comportamiento extends CyclicBehaviour {

        Arduino arduino;
        Envio enviar;
        String temperatura, humedad, iluminacion, riego;

        public Comportamiento() {
            arduino = new Arduino();
            enviar = new actuadores.Envio();
//            arduino.abrir();
        }

        @Override
        public void action() {

//            arduino.getSerial().addListener((SerialDataEventListener) (SerialDataEvent event) -> {
//                try {
//                    String valorArduino = event.getAsciiString();
//                    System.out.println(valorArduino);
//                    descomponer(valorArduino);
//                    enviar.send(getAgent(), iluminacion + "", "AgIluminacion");//enviar iluminacion
//                    enviar.send(getAgent(), humedad + "", "AgHumedad");//enviar humedad
//                    enviar.send(getAgent(), riego + "", "AgDetectorRiego");//enviar riego
//                    enviar.send(getAgent(), temperatura + "", "AgTemperatura");//enviar temperatura
//
//                } catch (IOException ex) {
//                    Logger.getLogger(AgenteIluminacion.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });
//            arduino.getSerial().addListener((SerialDataEventListener) (SerialDataEvent event) -> {
//                try {
            String valorArduino = arduino.getValoresArduino();
            System.out.println(valorArduino);
            descomponer(valorArduino);
            enviar.send(getAgent(), iluminacion + "", "AgIluminacion");//enviar iluminacion
            enviar.send(getAgent(), humedad + "", "AgHumedad");//enviar humedad
            enviar.send(getAgent(), riego + "", "AgDetectorRiego");//enviar riego
            enviar.send(getAgent(), temperatura + "", "AgTemperatura");//enviar temperatura
            try {
                //                } catch (IOException ex) {
//                    Logger.getLogger(AgenteIluminacion.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });
                Thread.sleep(30000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AgenteReceptor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void descomponer(String valores) {//obtiene valores del arduino
            try {

                StringTokenizer tokens = new StringTokenizer(valores);
                double temperaturaD = 0,
                        riegoD = 0,
                        humedadD = 0,
                        iluminacionD = 0;
                for (int i = 0; i < 3; i++) {
                    temperaturaD += Double.parseDouble(tokens.nextToken(";"));
                }
                for (int i = 0; i < 3; i++) {
                    humedadD += Double.parseDouble(tokens.nextToken());
                }
                //token de iluminacion
                iluminacionD = Double.parseDouble(tokens.nextToken());
                //token de riego
                riegoD = Double.parseDouble(tokens.nextToken());
                temperatura = String.format("%.2f", temperaturaD / 3);
                riego = String.format("%.2f", riegoD);
                humedad = String.format("%.2f", humedadD / 3);
                iluminacion = String.format("%.2f", iluminacionD);
            } catch (Exception e) {
                temperatura = "0";
                riego = "0";
                humedad = "0";
                iluminacion = "0";
            }
        }

    }
}
