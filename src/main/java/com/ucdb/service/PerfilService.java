package com.ucdb.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucdb.dao.CommentDAO;
import com.ucdb.dao.DisciplinaDAO;
import com.ucdb.dao.PerfilDAO;
import com.ucdb.dao.ReplyCommentDAO;
import com.ucdb.dao.UserDAO;
import com.ucdb.model.Comment;
import com.ucdb.model.Disciplina;
import com.ucdb.model.Perfil;
import com.ucdb.model.ReplyComment;
import com.ucdb.model.User;

@Service
public class PerfilService {

	@Autowired
	private PerfilDAO perfilDAO;

	@Autowired
	private DisciplinaDAO disciplinaDAO;

	@Autowired
	private UserDAO userDAO;

	public Perfil create(long id) {
		Disciplina d = this.disciplinaDAO.findById(id);
		Perfil perfil = new Perfil();
		perfil.setId(id);
		perfil.setDisciplina(d);

		return this.perfilDAO.save(perfil);
	}

	public List<Perfil> createAll(List<Disciplina> disciplinas) {
		List<Perfil> listPerfil = new ArrayList<Perfil>();
		Iterator<Disciplina> it = disciplinas.iterator();
		while (it.hasNext()) {
			Disciplina d = it.next();
			d = this.disciplinaDAO.findById(d.getId());
			Perfil p = new Perfil();
			p.setId(d.getId());
			p.setDisciplina(d);
			listPerfil.add(p);
		}
		return this.perfilDAO.saveAll(listPerfil);
	}

	public Perfil getById(long codigo, String email) {
		Perfil p = this.perfilDAO.findById(codigo);
		User u = this.userDAO.findByEmail(email);
		if (u != null && p != null) {
			if (p.getUsers().contains(u)) {
				p.setUsuarioCurtiu(true);
			} else {
				p.setUsuarioCurtiu(false);
			}

			for (Comment c : p.getComments()) {
				for (ReplyComment r : c.getReply()) {
					if (r.getUser().equals(u.getEmail())) {
						r.setUsuarioComentou(true);
					} else {
						r.setUsuarioComentou(false);
					}
				}

				if (c.getUser().equals(u.getEmail())) {
					c.setUsuarioComentou(true);
				} else {
					c.setUsuarioComentou(false);
				}
			}
			return p;
		}

		return null;
	}

	public Perfil usuarioCurtiu(long id, String email) {
		User u = this.userDAO.findByEmail(email);
		Perfil p = this.perfilDAO.findById(id);
		if (p != null) {
			if (!p.getUsers().contains(u)) {
				p.getUsers().add(u);
				p.addLikes();
			} else {
				p.getUsers().remove(u);
				p.removeLikes();
			}
			this.perfilDAO.save(p);
			return getById(id, email);
		}
		return null;
	}

	public List<Perfil> getAll() {
		return this.perfilDAO.findAllById();
	}

	public List<Perfil> getAllByLikes() {
		return this.perfilDAO.findAllByLikes();
	}

	public List<Perfil> getAllByComments() {
		return this.perfilDAO.findAllByComments();
	}
	
	public List<Perfil> getAllByCommentsAsc() {
		return this.perfilDAO.findAllByCommentsAsc();
	}

}
