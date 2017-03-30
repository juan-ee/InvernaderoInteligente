/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actuadores;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author personal
 */
public class Envio {

    jade.core.Agent agent;

    public Envio() {
    }

    public void send(Agent agent, String content, String receptor) {
        //ID del agente, se necesita el nickname
        AID id = new AID();//Genera un ID de la conversacion Agent ID    
        id.setLocalName(receptor);

        //ACL //Quient envia
        ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
        mensaje.setContent(content);
        mensaje.addReceiver(id);
        mensaje.setSender(agent.getAID());
        agent.send(mensaje);
    }
    public void sendArray(String[] content, Agent emisor, String receptor) {
        try {
            AID id = new AID();
            id.setLocalName(receptor);
            ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
            mensaje.setContentObject(content);
            mensaje.addReceiver(id);
            mensaje.setSender(emisor.getAID());
            emisor.send(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(Envio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
