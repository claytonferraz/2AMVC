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
public class ExcluirDadosTelefone1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        Sistema si = new Sistema();
        Telefone tel = new Telefone();
        tel = si.BuscarTelefone(1);

        System.out.println(tel);

        si.DeletarTelefone(tel.getIdTelefone());

    }

}
