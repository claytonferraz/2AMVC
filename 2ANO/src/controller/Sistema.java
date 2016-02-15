/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
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
    
    public Endereco BuscarEndereco (Integer id){
       Endereco endbusca = new Endereco();
        EnderecoJpaController endjpa = new EnderecoJpaController();
        endbusca = endjpa.findEndereco(id);
        return endbusca;
    }
    
    public Telefone BuscarTelefone (Integer id){
       Telefone telbusca = new Telefone();
        TelefoneJpaController teljpa = new TelefoneJpaController();
       telbusca = teljpa.findTelefone(id);
        return telbusca;
    }
    public void AlterarPessoa (Pessoa pe) throws NonexistentEntityException, Exception{
        PessoaJpaController pejpa = new PessoaJpaController();
        pejpa.edit(pe);
    }
    public void AlterarEndereco (Endereco end) throws Exception{
        EnderecoJpaController endjpa = new EnderecoJpaController();
        endjpa.edit(end);
    }
    public void AlterarTelefone (Telefone te) throws Exception{
        TelefoneJpaController tejpa = new TelefoneJpaController();
        tejpa.edit(te);
    }
    
    public void DeletarTelefone (Integer id) throws IllegalOrphanException, NonexistentEntityException{
        TelefoneJpaController teljpa = new TelefoneJpaController();
        teljpa.destroy(id);
    }
     public void DeletarPessoa (Integer id) throws IllegalOrphanException, NonexistentEntityException{
        PessoaJpaController pejpa = new PessoaJpaController();
        pejpa.destroy(id);
    }
     public void DeletarEndereco (Integer id) throws IllegalOrphanException, NonexistentEntityException{
        EnderecoJpaController endjpa = new EnderecoJpaController();
        endjpa.destroy(id);
    }
    
}
