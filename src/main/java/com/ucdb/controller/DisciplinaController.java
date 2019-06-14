package com.ucdb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ucdb.model.Disciplina;
import com.ucdb.service.DisciplinaService;

@RestController
@RequestMapping({ "/v1/disciplinas" })
public class DisciplinaController {

	private DisciplinaService disciplinaService;

	public DisciplinaController(DisciplinaService disciplinaService) {
		this.disciplinaService = disciplinaService;
	}

	@PostMapping(value = "/")
	@ResponseBody
	public ResponseEntity<Disciplina> create(@RequestBody Disciplina nome) {
		Disciplina newDisciplina = disciplinaService.create(nome);
		if (newDisciplina == null) {
			throw new InternalError("Something went wrong");
		}
		return new ResponseEntity<Disciplina>(newDisciplina, HttpStatus.CREATED);

	}

	@GetMapping(value = "/{substring}")
	@ResponseBody
	public ResponseEntity<List<Disciplina>> findBySubString(@PathVariable String substring) {
		List disciplinas = disciplinaService.findBySubString(substring);
		return new ResponseEntity<List<Disciplina>>(disciplinas, HttpStatus.OK);
	}

}