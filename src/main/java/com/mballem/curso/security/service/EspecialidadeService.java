package com.mballem.curso.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.curso.security.datatables.Datatables;
import com.mballem.curso.security.datatables.DatatablesColunas;
import com.mballem.curso.security.domain.Especialidade;
import com.mballem.curso.security.repository.EspecialidadeRepository;

@Service
public class EspecialidadeService {
	
	@Autowired
	private EspecialidadeRepository er;
	
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(Especialidade especialidade) {
		er.save(especialidade);
		
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarEspecialidades(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.ESPECIALIDADES);
		Page<?> page = datatables.getSearch().isEmpty() 
				? er.findAll(datatables.getPageable())
						: er.findAllByTitulo(datatables.getSearch(), datatables.getPageable());
		
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Especialidade buscarPorId(Long id) {
		
		
		return er.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		er.deleteById(id);
		
	}

	@Transactional(readOnly = true)
	public List<String> buscarEspecialidadeByTermo(String termo) {
		// TODO Auto-generated method stub
		return er.findEspecialidadesByTermo(termo);
	}
	
	
	@Transactional(readOnly = true)
	public Set<Especialidade> buscarPorTitulos(String[] titulos) {
		
		return er.findByTitulos(titulos);
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarEspecialidadesPorMedico(Long id, HttpServletRequest request) {
		
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.ESPECIALIDADES);
		Page<Especialidade> page = er.findByIdMedico(id, datatables.getPageable());
		return datatables.getResponse(page);
	}

}
