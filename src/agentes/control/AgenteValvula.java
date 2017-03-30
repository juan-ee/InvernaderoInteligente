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
public class AgenteValvula extends Agent {

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
            System.out.println("Valvula: " + mensaje.getContent());
            StringTokenizer tokens = new StringTokenizer(mensaje.getContent());
            String control = tokens.nextToken();
            if (control.equals("A1")) {
                String tiempo = tokens.nextToken();
                arduino.reley("A1\n");
                doWait(Long.parseLong(tiempo));
                arduino.reley("D1\n");
            } else {
                if (control.equals("D1")) {
                    arduino.reley("D1\n");

                }
            }

        }

    }
}
