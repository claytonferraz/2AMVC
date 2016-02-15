/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2amvc;

import controller.Sistema;
import model.Endereco;
import model.Pessoa;
import model.Telefone;

/**
 *
 * @author Clayton Ferraz
 */
public class AlterarDadosEndereco {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        Sistema si = new Sistema();
        Endereco end = new Endereco();
       end = si.BuscarEndereco(1);

        System.out.println(end);
        
        end.setCidade("PVH");
        
        si.AlterarEndereco(end);
    }

}
