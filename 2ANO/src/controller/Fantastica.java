/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Clayton Ferraz
 */
public class Fantastica {

    private EntityManager em;
    private EntityManagerFactory emf;
    private EntityTransaction et;
    private String up;

    public Fantastica() {
        this.up = "2AMVCPU";
        emf = Persistence.createEntityManagerFactory(this.up);
    }

    // metodo que inseri um objeto no banco de dados
    public void Inserir(Object obj) {
        // aqui recebe a conexão
        em = emf.createEntityManager();
        //aqui recebe a transação
        et = em.getTransaction();
        //aqui inicia
        et.begin();
        //aqui insiri
        em.persist(obj);
        //grava pra sempre
        et.commit();
        //aqui fechar conexao
        em.close();
    }

    public Object Consultar(Class classe, Integer pk) {
        // aqui eu crio um objeto
        Object objeto = null;
        // aqui recebe a conexão
        em = emf.createEntityManager();
        //aqui recebe a transação
        et = em.getTransaction();
        //aqui inicia
        et.begin();
        //aqui busco o objeto a  ser excluido
        objeto = em.getReference(classe, pk);
        //grava pra sempre
        et.commit();
        //aqui fechar conexao
        em.close();

        return objeto;
    }

    public void Alterar(Object obj) {
        // aqui recebe a conexão
        em = emf.createEntityManager();
        //aqui recebe a transação
        et = em.getTransaction();
        //aqui inicia
        et.begin();
        //aqui insiri
        em.merge(obj);
        //grava pra sempre
        et.commit();
        //aqui fechar conexao
        em.close();
    }

    public void Deletar(Class classe, Integer pk) {
        // aqui eu crio um objeto
        Object objeto = null;
        // aqui recebe a conexão
        em = emf.createEntityManager();
        //aqui recebe a transação
        et = em.getTransaction();
        //aqui inicia
        et.begin();
        //aqui busco o objeto a  ser excluido
        objeto = em.getReference(classe, pk);
        //aqui vou remover
        em.remove(objeto);
        //grava pra sempre
        et.commit();
        //aqui fechar conexao
        em.close();
    }

}
