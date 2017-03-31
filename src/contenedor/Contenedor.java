/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contenedor;

import agentes.bd.AgenteDBA;
import agentes.control.AgenteCalefactor;
import agentes.control.AgenteFoco;
import agentes.control.AgenteValvula;
import agentes.deteccion.AgenteDetectorRiego;
import agentes.deteccion.AgenteHumedad;
import agentes.deteccion.AgenteIluminacion;
import agentes.deteccion.AgenteTemperatura;
import agentes.mediador.AgenteBroker;
import agentes.receptor.AgenteReceptor;
import jade.wrapper.AgentContainer;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author personal
 */
public class Contenedor {

    //Agente controlador del contenedor
    AgentController agenteController;
    AgentContainer main_agenteContainer;
    //método para contenedor

    public void contenedor() {
        jade.core.Runtime runtime = jade.core.Runtime.instance();//Runtime de jade
        runtime.setCloseVM(true);//Cerrar la maquina virtual de jade
        //el contenedor corre en un puerto y con una ip conocid como PROFILE
        Profile perfil = new ProfileImpl(null, 1099, null);
        //creacion del contenedor principal
        main_agenteContainer = runtime.createMainContainer(perfil);
    }

    public void iniciarAgentes() {
        try {
            //Inicializar los agentes
            //mediador
            agenteController = main_agenteContainer.createNewAgent("AgBroker", AgenteBroker.class.getName(), null);
            agenteController.start();

//            //control
            agenteController = main_agenteContainer.createNewAgent("AgCalefactor", AgenteCalefactor.class.getName(), null);
            agenteController.start();
            agenteController = main_agenteContainer.createNewAgent("AgFoco", AgenteFoco.class.getName(), null);
            agenteController.start();
            agenteController = main_agenteContainer.createNewAgent("AgValvula", AgenteValvula.class.getName(), null);
            agenteController.start();
//
//            //deteccion
            agenteController = main_agenteContainer.createNewAgent("AgDetectorRiego", AgenteDetectorRiego.class.getName(), null);
            agenteController.start();
            agenteController = main_agenteContainer.createNewAgent("AgHumedad", AgenteHumedad.class.getName(), null);
            agenteController.start();
            agenteController = main_agenteContainer.createNewAgent("AgIluminacion", AgenteIluminacion.class.getName(), null);
            agenteController.start();
            agenteController = main_agenteContainer.createNewAgent("AgTemperatura", AgenteTemperatura.class.getName(), null);
            agenteController.start();
            //bd
            agenteController = main_agenteContainer.createNewAgent("AgenteDBA", AgenteDBA.class.getName(), null);
            agenteController.start();
            //receptor
            agenteController = main_agenteContainer.createNewAgent("AgReceptor", AgenteReceptor.class.getName(), null);
            agenteController.start();
        } catch (StaleProxyException ex) {
            Logger.getLogger(Contenedor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
