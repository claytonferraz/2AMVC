/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Endereco;
import model.Pessoa;

/**
 *
 * @author Clayton Ferraz
 */
public class EnderecoJpaController implements Serializable {

    public EnderecoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public EnderecoJpaController() {
        String up = "2AMVCPU";
        emf = Persistence.createEntityManagerFactory(up);
    }

    public void create(Endereco endereco) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa idPessoafk = endereco.getIdPessoafk();
            if (idPessoafk != null) {
                idPessoafk = em.getReference(idPessoafk.getClass(), idPessoafk.getIdPessoa());
                endereco.setIdPessoafk(idPessoafk);
            }
            em.persist(endereco);
            if (idPessoafk != null) {
                idPessoafk.getEnderecoCollection().add(endereco);
                idPessoafk = em.merge(idPessoafk);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Endereco endereco) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco persistentEndereco = em.find(Endereco.class, endereco.getIdEndereco());
            Pessoa idPessoafkOld = persistentEndereco.getIdPessoafk();
            Pessoa idPessoafkNew = endereco.getIdPessoafk();
            if (idPessoafkNew != null) {
                idPessoafkNew = em.getReference(idPessoafkNew.getClass(), idPessoafkNew.getIdPessoa());
                endereco.setIdPessoafk(idPessoafkNew);
            }
            endereco = em.merge(endereco);
            if (idPessoafkOld != null && !idPessoafkOld.equals(idPessoafkNew)) {
                idPessoafkOld.getEnderecoCollection().remove(endereco);
                idPessoafkOld = em.merge(idPessoafkOld);
            }
            if (idPessoafkNew != null && !idPessoafkNew.equals(idPessoafkOld)) {
                idPessoafkNew.getEnderecoCollection().add(endereco);
                idPessoafkNew = em.merge(idPessoafkNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = endereco.getIdEndereco();
                if (findEndereco(id) == null) {
                    throw new NonexistentEntityException("The endereco with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco endereco;
            try {
                endereco = em.getReference(Endereco.class, id);
                endereco.getIdEndereco();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The endereco with id " + id + " no longer exists.", enfe);
            }
            Pessoa idPessoafk = endereco.getIdPessoafk();
            if (idPessoafk != null) {
                idPessoafk.getEnderecoCollection().remove(endereco);
                idPessoafk = em.merge(idPessoafk);
            }
            em.remove(endereco);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Endereco> findEnderecoEntities() {
        return findEnderecoEntities(true, -1, -1);
    }

    public List<Endereco> findEnderecoEntities(int maxResults, int firstResult) {
        return findEnderecoEntities(false, maxResults, firstResult);
    }

    private List<Endereco> findEnderecoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Endereco.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Endereco findEndereco(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Endereco.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnderecoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Endereco> rt = cq.from(Endereco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
