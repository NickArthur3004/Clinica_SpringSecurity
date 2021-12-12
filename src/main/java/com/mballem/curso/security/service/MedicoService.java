package com.mballem.curso.security.service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.curso.security.domain.Medico;
import com.mballem.curso.security.repository.MedicoRepository;

@Service
public class MedicoService {
	
	@Autowired
	private MedicoRepository mr;
	
	@Transactional(readOnly = true)
	public  Medico buscarPorUsuarioId(Long id) {
		
		return mr.findByUsuarioId(id).orElse(new Medico());
	}

	@Transactional(readOnly = false)
	public void salvar(Medico medico) {
		
		mr.save(medico);
	}

	@Transactional(readOnly = false)
	public void editar(Medico medico) {
		
		Medico m2 = mr.findById(medico.getId()).get();
		m2.setCrm(medico.getCrm());
		m2.setDtInscricao(medico.getDtInscricao());
		m2.setNome(medico.getNome());
		if(!medico.getEspecialidades().isEmpty()) {
			m2.getEspecialidades().addAll(medico.getEspecialidades());
		}
	}

	@Transactional(readOnly = true)
	public Medico buscarPorEmail(String email) {
		
		
		return mr.findByUsuarioEmail(email);
	}

	@Transactional(readOnly = false)
	public void excluirEspecialidadePorMedico(Long idMed, Long idEsp) {
		
		Medico medico = mr.findById(idMed).get();
		medico.getEspecialidades().removeIf(e -> e.getId().equals(idEsp));
	}

}
