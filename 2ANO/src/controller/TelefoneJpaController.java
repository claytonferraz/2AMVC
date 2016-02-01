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
import model.Pessoa;
import model.Telefone;

/**
 *
 * @author Clayton Ferraz
 */
public class TelefoneJpaController implements Serializable {

    public TelefoneJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public TelefoneJpaController() {
        String up = "2AMVCPU";
        emf = Persistence.createEntityManagerFactory(up);
    }

    public void create(Telefone telefone) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa idPessoafk = telefone.getIdPessoafk();
            if (idPessoafk != null) {
                idPessoafk = em.getReference(idPessoafk.getClass(), idPessoafk.getIdPessoa());
                telefone.setIdPessoafk(idPessoafk);
            }
            em.persist(telefone);
            if (idPessoafk != null) {
                idPessoafk.getTelefoneCollection().add(telefone);
                idPessoafk = em.merge(idPessoafk);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telefone telefone) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telefone persistentTelefone = em.find(Telefone.class, telefone.getIdTelefone());
            Pessoa idPessoafkOld = persistentTelefone.getIdPessoafk();
            Pessoa idPessoafkNew = telefone.getIdPessoafk();
            if (idPessoafkNew != null) {
                idPessoafkNew = em.getReference(idPessoafkNew.getClass(), idPessoafkNew.getIdPessoa());
                telefone.setIdPessoafk(idPessoafkNew);
            }
            telefone = em.merge(telefone);
            if (idPessoafkOld != null && !idPessoafkOld.equals(idPessoafkNew)) {
                idPessoafkOld.getTelefoneCollection().remove(telefone);
                idPessoafkOld = em.merge(idPessoafkOld);
            }
            if (idPessoafkNew != null && !idPessoafkNew.equals(idPessoafkOld)) {
                idPessoafkNew.getTelefoneCollection().add(telefone);
                idPessoafkNew = em.merge(idPessoafkNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telefone.getIdTelefone();
                if (findTelefone(id) == null) {
                    throw new NonexistentEntityException("The telefone with id " + id + " no longer exists.");
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
            Telefone telefone;
            try {
                telefone = em.getReference(Telefone.class, id);
                telefone.getIdTelefone();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telefone with id " + id + " no longer exists.", enfe);
            }
            Pessoa idPessoafk = telefone.getIdPessoafk();
            if (idPessoafk != null) {
                idPessoafk.getTelefoneCollection().remove(telefone);
                idPessoafk = em.merge(idPessoafk);
            }
            em.remove(telefone);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Telefone> findTelefoneEntities() {
        return findTelefoneEntities(true, -1, -1);
    }

    public List<Telefone> findTelefoneEntities(int maxResults, int firstResult) {
        return findTelefoneEntities(false, maxResults, firstResult);
    }

    private List<Telefone> findTelefoneEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Telefone.class));
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

    public Telefone findTelefone(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telefone.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelefoneCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Telefone> rt = cq.from(Telefone.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
