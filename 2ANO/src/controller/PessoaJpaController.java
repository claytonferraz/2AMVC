/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Telefone;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Endereco;
import model.Pessoa;

/**
 *
 * @author Clayton Ferraz
 */
public class PessoaJpaController implements Serializable {

    public PessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public PessoaJpaController() {
        String up = "2AMVCPU";
        emf = Persistence.createEntityManagerFactory(up);
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pessoa pessoa) {
        if (pessoa.getTelefoneCollection() == null) {
            pessoa.setTelefoneCollection(new ArrayList<Telefone>());
        }
        if (pessoa.getEnderecoCollection() == null) {
            pessoa.setEnderecoCollection(new ArrayList<Endereco>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Telefone> attachedTelefoneCollection = new ArrayList<Telefone>();
            for (Telefone telefoneCollectionTelefoneToAttach : pessoa.getTelefoneCollection()) {
                telefoneCollectionTelefoneToAttach = em.getReference(telefoneCollectionTelefoneToAttach.getClass(), telefoneCollectionTelefoneToAttach.getIdTelefone());
                attachedTelefoneCollection.add(telefoneCollectionTelefoneToAttach);
            }
            pessoa.setTelefoneCollection(attachedTelefoneCollection);
            Collection<Endereco> attachedEnderecoCollection = new ArrayList<Endereco>();
            for (Endereco enderecoCollectionEnderecoToAttach : pessoa.getEnderecoCollection()) {
                enderecoCollectionEnderecoToAttach = em.getReference(enderecoCollectionEnderecoToAttach.getClass(), enderecoCollectionEnderecoToAttach.getIdEndereco());
                attachedEnderecoCollection.add(enderecoCollectionEnderecoToAttach);
            }
            pessoa.setEnderecoCollection(attachedEnderecoCollection);
            em.persist(pessoa);
            for (Telefone telefoneCollectionTelefone : pessoa.getTelefoneCollection()) {
                Pessoa oldIdPessoafkOfTelefoneCollectionTelefone = telefoneCollectionTelefone.getIdPessoafk();
                telefoneCollectionTelefone.setIdPessoafk(pessoa);
                telefoneCollectionTelefone = em.merge(telefoneCollectionTelefone);
                if (oldIdPessoafkOfTelefoneCollectionTelefone != null) {
                    oldIdPessoafkOfTelefoneCollectionTelefone.getTelefoneCollection().remove(telefoneCollectionTelefone);
                    oldIdPessoafkOfTelefoneCollectionTelefone = em.merge(oldIdPessoafkOfTelefoneCollectionTelefone);
                }
            }
            for (Endereco enderecoCollectionEndereco : pessoa.getEnderecoCollection()) {
                Pessoa oldIdPessoafkOfEnderecoCollectionEndereco = enderecoCollectionEndereco.getIdPessoafk();
                enderecoCollectionEndereco.setIdPessoafk(pessoa);
                enderecoCollectionEndereco = em.merge(enderecoCollectionEndereco);
                if (oldIdPessoafkOfEnderecoCollectionEndereco != null) {
                    oldIdPessoafkOfEnderecoCollectionEndereco.getEnderecoCollection().remove(enderecoCollectionEndereco);
                    oldIdPessoafkOfEnderecoCollectionEndereco = em.merge(oldIdPessoafkOfEnderecoCollectionEndereco);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pessoa pessoa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa persistentPessoa = em.find(Pessoa.class, pessoa.getIdPessoa());
            Collection<Telefone> telefoneCollectionOld = persistentPessoa.getTelefoneCollection();
            Collection<Telefone> telefoneCollectionNew = pessoa.getTelefoneCollection();
            Collection<Endereco> enderecoCollectionOld = persistentPessoa.getEnderecoCollection();
            Collection<Endereco> enderecoCollectionNew = pessoa.getEnderecoCollection();
            List<String> illegalOrphanMessages = null;
            for (Telefone telefoneCollectionOldTelefone : telefoneCollectionOld) {
                if (!telefoneCollectionNew.contains(telefoneCollectionOldTelefone)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Telefone " + telefoneCollectionOldTelefone + " since its idPessoafk field is not nullable.");
                }
            }
            for (Endereco enderecoCollectionOldEndereco : enderecoCollectionOld) {
                if (!enderecoCollectionNew.contains(enderecoCollectionOldEndereco)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Endereco " + enderecoCollectionOldEndereco + " since its idPessoafk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Telefone> attachedTelefoneCollectionNew = new ArrayList<Telefone>();
            for (Telefone telefoneCollectionNewTelefoneToAttach : telefoneCollectionNew) {
                telefoneCollectionNewTelefoneToAttach = em.getReference(telefoneCollectionNewTelefoneToAttach.getClass(), telefoneCollectionNewTelefoneToAttach.getIdTelefone());
                attachedTelefoneCollectionNew.add(telefoneCollectionNewTelefoneToAttach);
            }
            telefoneCollectionNew = attachedTelefoneCollectionNew;
            pessoa.setTelefoneCollection(telefoneCollectionNew);
            Collection<Endereco> attachedEnderecoCollectionNew = new ArrayList<Endereco>();
            for (Endereco enderecoCollectionNewEnderecoToAttach : enderecoCollectionNew) {
                enderecoCollectionNewEnderecoToAttach = em.getReference(enderecoCollectionNewEnderecoToAttach.getClass(), enderecoCollectionNewEnderecoToAttach.getIdEndereco());
                attachedEnderecoCollectionNew.add(enderecoCollectionNewEnderecoToAttach);
            }
            enderecoCollectionNew = attachedEnderecoCollectionNew;
            pessoa.setEnderecoCollection(enderecoCollectionNew);
            pessoa = em.merge(pessoa);
            for (Telefone telefoneCollectionNewTelefone : telefoneCollectionNew) {
                if (!telefoneCollectionOld.contains(telefoneCollectionNewTelefone)) {
                    Pessoa oldIdPessoafkOfTelefoneCollectionNewTelefone = telefoneCollectionNewTelefone.getIdPessoafk();
                    telefoneCollectionNewTelefone.setIdPessoafk(pessoa);
                    telefoneCollectionNewTelefone = em.merge(telefoneCollectionNewTelefone);
                    if (oldIdPessoafkOfTelefoneCollectionNewTelefone != null && !oldIdPessoafkOfTelefoneCollectionNewTelefone.equals(pessoa)) {
                        oldIdPessoafkOfTelefoneCollectionNewTelefone.getTelefoneCollection().remove(telefoneCollectionNewTelefone);
                        oldIdPessoafkOfTelefoneCollectionNewTelefone = em.merge(oldIdPessoafkOfTelefoneCollectionNewTelefone);
                    }
                }
            }
            for (Endereco enderecoCollectionNewEndereco : enderecoCollectionNew) {
                if (!enderecoCollectionOld.contains(enderecoCollectionNewEndereco)) {
                    Pessoa oldIdPessoafkOfEnderecoCollectionNewEndereco = enderecoCollectionNewEndereco.getIdPessoafk();
                    enderecoCollectionNewEndereco.setIdPessoafk(pessoa);
                    enderecoCollectionNewEndereco = em.merge(enderecoCollectionNewEndereco);
                    if (oldIdPessoafkOfEnderecoCollectionNewEndereco != null && !oldIdPessoafkOfEnderecoCollectionNewEndereco.equals(pessoa)) {
                        oldIdPessoafkOfEnderecoCollectionNewEndereco.getEnderecoCollection().remove(enderecoCollectionNewEndereco);
                        oldIdPessoafkOfEnderecoCollectionNewEndereco = em.merge(oldIdPessoafkOfEnderecoCollectionNewEndereco);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pessoa.getIdPessoa();
                if (findPessoa(id) == null) {
                    throw new NonexistentEntityException("The pessoa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa pessoa;
            try {
                pessoa = em.getReference(Pessoa.class, id);
                pessoa.getIdPessoa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Telefone> telefoneCollectionOrphanCheck = pessoa.getTelefoneCollection();
            for (Telefone telefoneCollectionOrphanCheckTelefone : telefoneCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pessoa (" + pessoa + ") cannot be destroyed since the Telefone " + telefoneCollectionOrphanCheckTelefone + " in its telefoneCollection field has a non-nullable idPessoafk field.");
            }
            Collection<Endereco> enderecoCollectionOrphanCheck = pessoa.getEnderecoCollection();
            for (Endereco enderecoCollectionOrphanCheckEndereco : enderecoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pessoa (" + pessoa + ") cannot be destroyed since the Endereco " + enderecoCollectionOrphanCheckEndereco + " in its enderecoCollection field has a non-nullable idPessoafk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pessoa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pessoa> findPessoaEntities() {
        return findPessoaEntities(true, -1, -1);
    }

    public List<Pessoa> findPessoaEntities(int maxResults, int firstResult) {
        return findPessoaEntities(false, maxResults, firstResult);
    }

    private List<Pessoa> findPessoaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pessoa.class));
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

    public Pessoa findPessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pessoa> rt = cq.from(Pessoa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
