/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes.deteccion;

import actuadores.Envio;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author HenryPatricio
 */
public class AgenteTemperatura extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new Comportamiento());
    }

    private class Comportamiento extends CyclicBehaviour {

        Envio enviar;

        public Comportamiento() {
            enviar = new actuadores.Envio();
        }

        @Override
        public void action() {
            ACLMessage mensaje = getAgent().blockingReceive();
//            System.out.println("Riego: " + mensaje.getContent());
            enviar.send(getAgent(), mensaje.getContent(), "AgBroker");
        }
    }
}
