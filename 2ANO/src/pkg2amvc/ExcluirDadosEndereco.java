/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2amvc;

import controller.Sistema;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import model.Endereco;

/**
 *
 * @author Clayton Ferraz
 */
public class ExcluirDadosEndereco {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IllegalOrphanException, NonexistentEntityException {
     
        // Aqui crio a classe que manipula os dados
        Sistema si = new Sistema();
        //a classe que recebe os dados
        Endereco end = new Endereco();
        // aqui vou buscar os dados
        end = si.BuscarEndereco(1);
        
        System.out.println(end);
        si.DeletarEndereco(end.getIdEndereco());
        
        
        
        
    }
    
}
