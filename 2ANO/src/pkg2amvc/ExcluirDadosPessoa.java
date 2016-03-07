/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2amvc;

import controller.Sistema;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import model.Pessoa;

/**
 *
 * @author Clayton Ferraz
 */
public class ExcluirDadosPessoa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IllegalOrphanException, NonexistentEntityException {
       
        // classe que busca os dados da pessoa
        Sistema si = new Sistema();
        // cria a classe Pessoa temporária
        Pessoa pe = new Pessoa();
        // buscar uma entidade do tipo pessoa no banco de dados
        pe = si.BuscarPessoa(1);
        // print
        
        System.out.println(pe);
        // deletar forever
        si.DeletarPessoa(pe.getIdPessoa());
        
        
        
        
    }
    
}
