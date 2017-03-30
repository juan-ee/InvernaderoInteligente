package arduino;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arduino {

    //private Arduino arduino;
    private String puerto;
    private int tasa;
    static Serial serial;

    public Arduino() {

    }

    public void abrir() {
        try {
            puerto = System.getProperty("serial.port", "/dev/ttyACM0");
            tasa = Integer.parseInt(System.getProperty("baud.rate", "9600"));
            serial = SerialFactory.createInstance();
            serial.open(puerto, tasa);
            System.out.println("Serial listo");

        } catch (IOException ex) {
            System.err.println("Error al iniciar el puerto serial con el arduino");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*public Arduino getArduino(){
     if(arduino==null){
     arduino=new Arduino();
     }
     return arduino;
     }*/

    public static Serial getSerial() {
        return serial;
    }

    public String reley(String opcion) {
//        System.out.println("Cargando dato ...." + opcion);
        if (serial.isOpen()) {
            try {
                serial.write(opcion);
                System.out.println("Se ha enviado" + opcion);
//                System.out.println("durmiendo 100 ..");
//                Thread.sleep(5000);
//                System.out.println("leyendo ...");
//                return new String(serial.read());
            } catch (IllegalStateException ex) {
                Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
            }
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } else {
            System.out.println("Puerto serial cerrado");
        }
        return null;
    }

}
