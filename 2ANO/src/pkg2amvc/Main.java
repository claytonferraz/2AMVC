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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Pessoa pe = new Pessoa();
        pe.setNomePessoa("Clayton Ferraz Andrade");
        pe.setCpf("12345678911");
        Sistema si = new Sistema();
        si.CadastrarPessoa(pe);

        Endereco end = new Endereco();

        end.setRua("Av Brasil");
        end.setBairro("Jardim Eldorado");
        end.setCidade("Vilhena");
        end.setUf("RO");
        end.setIdPessoafk(pe);
        si.CadastrarEndereco(end);
        Telefone te = new Telefone();
        te.setNumero("6992259283");
        te.setIdPessoafk(pe);
        si.CadastrarTelefone(te);

       // pe= si.BuscarPessoa(1);
//        
//        System.out.println(pe);
    }

}
