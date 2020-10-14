package com.facec.controller;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.facec.model.Funcionario;
import com.facec.model.Projeto;
import com.facec.repository.FuncionarioRepository;
import com.facec.repository.ProjetoRepository;

@Controller
public class IndexController {

	@Autowired
	ProjetoRepository repository;
	@Autowired
	FuncionarioRepository repoFuncionario;

	@RequestMapping("/")
	public String iniciar(Model model) {

		model.addAttribute("nome", "Ranghetti");

		return "index";
	}

	@RequestMapping("/projeto")
	public String abrirLista(Model model) {

		model.addAttribute("projetos", repository.findAll());
		model.addAttribute("titulo", "Portifólio de Projetos");

		return "projeto";
	}

	@RequestMapping("/formulario")
	public String abrirFormulario(Projeto projeto, Model model) {
		return "formulario";
	}

	@PostMapping(value = "salvar")
	public String salvar(@Valid Projeto projeto, BindingResult result, Model model) {

		if(result.hasErrors()) {
			model.addAttribute("erros", result.getAllErrors());
			return abrirFormulario(projeto,model);
		}
		
		repository.save(projeto);

		return "redirect:projeto";
	}
	
	@PostMapping(value = "editar/salvar")
	public String atualizar(@Valid Projeto projeto, BindingResult result, Model model) {

		if(result.hasErrors()) {
			model.addAttribute("erros", result.getAllErrors());
			return abrirFormulario(projeto,model);
		}
		
		repository.save(projeto);

		return "redirect:../projeto";
	}

	@GetMapping(value = "editar")
	public String editar(@PathParam(value = "id") Long id, Model model) {
		Projeto p = repository.getOne(id);
		model.addAttribute("projeto", p);

		return "formulario";
	}

	@GetMapping(value = "excluir")
	public String excluir(@PathParam(value = "id") Long id) {

		repository.deleteById(id);

		return "redirect:../projeto";
	}

	@RequestMapping("/cadFuncionario")
	public String abrirFormFuncionario(Funcionario funcionario, Model model){
		return "cadFuncionario";
		
		
	}
	@PostMapping("/salvarFuncionario")
	public String salvaFuncionario(Funcionario funcionario,Model model) {
		
		
		repoFuncionario.save(funcionario);
		return listaFuncionario(model);
		
	}
	
	@GetMapping("/funcionario")
	public String listaFuncionario( Model model) {
		
		model.addAttribute("funcionarios", repoFuncionario.findAll());
		return "funcionario";
	}
	@GetMapping(value = "editarFun")
	public String editarFun(@PathParam(value = "id") Long id, Model model) {
		Funcionario f = repoFuncionario.getOne(id);
		model.addAttribute("fun", f);

		return "CadFuncionario";
	}

	
	
}

