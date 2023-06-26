package br.edu.femass.dao;

import java.util.List;

import br.edu.femass.model.Livro;

public class LivroDao extends Dao<Livro>{
    public LivroDao(Class<Livro> entity){
        super(entity);
    }

    public Livro findByName(String nome) {
    return em.createQuery("SELECT u FROM Livro u WHERE u.nome = :nome", Livro.class)
             .setParameter("nome", nome)
             .getSingleResult();
    }

    public List<Livro> findLivrosWithCopias() {
        return em.createQuery("SELECT l FROM Livro l WHERE EXISTS (SELECT c FROM Copia c WHERE c.livro = l)", Livro.class)
                 .getResultList();
    }
}
