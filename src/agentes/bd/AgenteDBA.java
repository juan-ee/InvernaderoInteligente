package agentes.bd;

import java.sql.*;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AgenteDBA extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new CompAgenteDBA());
    }

    @Override
    protected void takeDown() {
        super.takeDown();
    }

    public class CompAgenteDBA extends CyclicBehaviour {

        private Connection conexion = null;
        
        public CompAgenteDBA() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexion = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "rSTC2djE");
            } catch (ClassNotFoundException ex) {
                System.err.println("error en la clase");
                Logger.getLogger(AgenteDBA.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                System.err.println("error al conectar a la DB");
                Logger.getLogger(AgenteDBA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        private void escribir(String[] parametros){            
            try {
                PreparedStatement consulta = conexion.prepareStatement(String.format("CALL insertar_valores(%s, '%s', '%s', '%s', '%s');" ,"1",parametros[0],parametros[1],parametros[2],parametros[3]));                
                consulta.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(AgenteDBA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void action() {
            try {
                ACLMessage mensaje = getAgent().blockingReceive();
                escribir((String[]) mensaje.getContentObject());
            } catch (UnreadableException ex) {
                Logger.getLogger(AgenteDBA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
