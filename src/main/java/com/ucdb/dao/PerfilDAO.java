package com.ucdb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ucdb.model.Perfil;

@Repository
public interface PerfilDAO extends JpaRepository<Perfil, Long>{

	public Perfil save(Perfil perfil);
	
	public Perfil findById(long id);
	
	@Query(value = "select u from Perfil u order by u.id ASC")
	public List<Perfil> findAllById();
	
	@Query(value = "select u from Perfil u order by u.likes DESC, u.id ASC")
	public List<Perfil> findAllByLikes();
	
	@Query(value = "select u from Perfil u order by u.commentsNumber DESC, u.id ASC")
	public List<Perfil> findAllByComments();
	
	@Query(value = "select u from Perfil u order by u.commentsNumber ASC, u.id ASC")
	public List<Perfil> findAllByCommentsAsc();
	
}
