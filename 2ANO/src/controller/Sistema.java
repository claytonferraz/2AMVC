/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Endereco;
import model.Pessoa;
import model.Telefone;

/**
 *
 * @author Clayton Ferraz
 */
public class Sistema {

   
    public Sistema() {
    }
    
    public void CadastrarPessoa (Pessoa pe){
        PessoaJpaController pejpa = new PessoaJpaController();
        pejpa.create(pe);
    }
    public void CadastrarEndereco (Endereco end){
        EnderecoJpaController endjpa = new EnderecoJpaController();
        endjpa.create(end);
    }
    public void CadastrarTelefone (Telefone te){
        TelefoneJpaController tejpa = new TelefoneJpaController();
        tejpa.create(te);
    }
    public Pessoa BuscarPessoa (Integer id){
        Pessoa pebusca = new Pessoa();
        PessoaJpaController pejpa = new PessoaJpaController();
        pebusca = pejpa.findPessoa(id);
        return pebusca;
    }
    
    
}
