/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes.mediador;

import actuadores.Envio;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author HenryPatricio
 */
public class AgenteBroker extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new Comportamiento());
    }

    private class Comportamiento extends CyclicBehaviour {

        actuadores.Envio envio = new Envio();
        String[] parametros = new String[4];
        double temperatura, humedad, iluminacion, riego;
        int hora, minutos, segundos;
        Calendar calendario;

        @Override
        public void action() {
            ACLMessage mensaje = getAgent().blockingReceive();
            asignarParametro(mensaje, mensaje.getContent());

            if (((parametros[0] != null) && (parametros[1] != null)
                    && (parametros[2] != null) && (parametros[3] != null))
                    && (!parametros[0].isEmpty()) && (!parametros[1].isEmpty())
                    && (!parametros[2].isEmpty()) && (!parametros[3].isEmpty())) {
                envio.sendArray(parametros, getAgent(), "AgenteDBA");
                imprimir();
                temperatura = Double.parseDouble(parametros[0]);
                humedad = Double.parseDouble(parametros[2]);
                iluminacion = Double.parseDouble(parametros[3]);
                riego = Double.parseDouble(parametros[1]);

                /*
                 System.out.println("Temperatura: " + parametros[0]);
                 System.out.println("Riego: " + parametros[1]);
                 System.out.println("Humedad: " + parametros[2]);
                 System.out.println("Iluminacion: " + parametros[3]);
                 */
                //llamar a la red nueronal
                 /*
                 Activar  
                
                 A1\n - activa la válvula
                 A2\n - activa ventilador
                 A2\nA3\n - activa la calefacción
                 A4\n - activa el foco
                
                 Desactivar
                 D1\n - desactiva la válvula
                 D2\n - desactiva ventilador
                 D2\nD3\n - desactiva la calefacción
                 D4\n - desactiva el foco
                 */
                controlRiego();
                controlCalefaccion();
                controlIluminacion();
                
                for (int i = 0; i < parametros.length; i++) {
                    parametros[i] = null;
                }
            }
        }

        private void controlRiego() {
            if (humedad <= 70) {
                //**************abrir riego, 5 minutos*************
                envio.send(this.getAgent(), "A1 5000", "AgValvula");
            } else {
                if (humedad > 85) {
                    envio.send(this.getAgent(), "D1 0", "AgValvula");
                }
            }
        }

        private void controlIluminacion() {
            getDate();
            if (hora >= 5 && hora <= 17) {
                if (iluminacion < 1500) {
                    //**************prender foco, 5 minutos*************
                    envio.send(this.getAgent(), "A4\n", "AgFoco");
                } else {
                    if (iluminacion > 2500) {
                        //**************apagar foco ***************
                        envio.send(this.getAgent(), "D4\n", "AgFoco");
                    }
                }
            }

        }

        private void controlCalefaccion() {
            if (temperatura > 15) {
                //**************prender ventilador apagar calefaccion, 5 minutos*************
                envio.send(this.getAgent(), "A2 D3 5000", "AgCalefactor");
            } else {
                if (temperatura <= 10) {
                    //**************prender calefaccion, 5 minutos*************
                    envio.send(this.getAgent(), "A2 A3 5000", "AgCalefactor");
                }
            }
        }

        private void imprimir() {

            System.out.println("\n*******************DATOS*******************");
            System.out.println("Temperatura: " + parametros[0]);
            System.out.println("Riego: " + parametros[1]);
            System.out.println("Humedad: " + parametros[2]);
            System.out.println("Iluminacion: " + parametros[3]);
            System.out.println("*******************************************");

        }

        private void getDate() {
            calendario = new GregorianCalendar();
            hora = calendario.get(Calendar.HOUR_OF_DAY);
            minutos = calendario.get(Calendar.MINUTE);
            segundos = calendario.get(Calendar.SECOND);
        }

        private void asignarParametro(ACLMessage mensaje, String content) {
            switch (mensaje.getSender().getLocalName()) {
                case "AgTemperatura":
                    parametros[0] = content;
                    break;
                case "AgDetectorRiego":
                    parametros[1] = content;
                    break;
                case "AgHumedad":
                    parametros[2] = content;
                    break;
                case "AgIluminacion":
                    parametros[3] = content;
                    break;

            }
        }
    }
}
