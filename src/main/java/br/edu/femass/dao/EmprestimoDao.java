package br.edu.femass.dao;

import java.util.List;

import br.edu.femass.model.Emprestimo;

public class EmprestimoDao extends Dao<Emprestimo>{
    public EmprestimoDao(Class<Emprestimo> entity){
        super(entity);
    }

    //atualizada: agora verifico se está em andamento
    public List<Emprestimo> findByLeitor(String nome) {
    return em.createQuery("SELECT e FROM Emprestimo e WHERE e.leitor.nome = :nome AND e.isGoing = true", Emprestimo.class)
             .setParameter("nome", nome)
             .getResultList();
    }

    public List<Emprestimo> findByOnGoing() {
    return em.createQuery("SELECT e FROM Emprestimo e WHERE e.isGoing = true", Emprestimo.class)
             .getResultList();
}

    public List<Emprestimo> findByConcluded() {
    return em.createQuery("SELECT e FROM Emprestimo e WHERE e.isGoing = false", Emprestimo.class)
             .getResultList();
}
}
