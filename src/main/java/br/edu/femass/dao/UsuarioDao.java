package br.edu.femass.dao;

import java.util.List;

import br.edu.femass.model.Usuario;

public class UsuarioDao extends Dao<Usuario>{
    public UsuarioDao(Class<Usuario> entity){
        super(entity);
    }

    public Usuario findByName(String nome) {
    return em.createQuery("SELECT u FROM Usuario u WHERE u.login = :nome", Usuario.class)
             .setParameter("nome", nome)
             .getSingleResult();
    }

    public List<Usuario> findListByName(String nome) {
    return em.createQuery("SELECT u FROM Usuario u WHERE u.login = :nome", Usuario.class)
             .setParameter("nome", nome)
             .getResultList();
    }
}
