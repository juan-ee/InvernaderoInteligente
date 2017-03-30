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

/**
 *
 * @author HenryPatricio
 */
public class AgenteFoco extends Agent {

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
            System.out.println("Foco: " + mensaje.getContent());
            arduino.reley(mensaje.getContent());
        }

    }
}
