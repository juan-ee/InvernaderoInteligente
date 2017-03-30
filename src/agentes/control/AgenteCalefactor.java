/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes.control;

import actuadores.Envio;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.StringTokenizer;

/**
 *
 * @author HenryPatricio
 */
public class AgenteCalefactor extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new Comportamiento());
    }

    private class Comportamiento extends CyclicBehaviour {

        arduino.Arduino arduino = new arduino.Arduino();
        Envio enviar;

        public Comportamiento() {
            enviar = new actuadores.Envio();
        }

        @Override
        public void action() {
            ACLMessage mensaje = getAgent().blockingReceive();
//            arduino.abrir();
            System.out.println("Calefactor: " + mensaje.getContent());
            StringTokenizer tokens = new StringTokenizer(mensaje.getContent());
            String control1 = tokens.nextToken();
            String control2 = tokens.nextToken();
            String tiempo = tokens.nextToken();

            if (control1.equals("A2") && control2.equals("D3")) {
                //prender ventilador
                arduino.reley("A2\nD3\n");
                doWait(Long.parseLong(tiempo));
                arduino.reley("D2\nD3\n");
            } else {
                if (control1.equals("A2") && control2.equals("A3")) {
                    //prender ventilador
                    arduino.reley("A2\nA3\n");
                    doWait(Long.parseLong(tiempo));
                    arduino.reley("D2\nD3\n");
                }
            }

        }

    }
}
