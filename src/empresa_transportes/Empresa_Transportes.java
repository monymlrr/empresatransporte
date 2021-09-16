/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empresa_transportes;

/**
 *
 * @author monirami
 */
public class Empresa_Transportes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        MySQL db = new MySQL();
        Ventana ventana = new Ventana();
        ventana.setVisible(true);
        
        db.conectar();
    }
    
}
