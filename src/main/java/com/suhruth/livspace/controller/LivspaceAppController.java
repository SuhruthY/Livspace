package com.suhruth.livspace.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.suhruth.livspace.exception.ResourceNotFoundException;
import com.suhruth.livspace.model.LivspaceFull;
import com.suhruth.livspace.repository.LivspaceEditRepository;
import com.suhruth.livspace.repository.LivspaceFullRepository;
import com.suhruth.livspace.repository.LivspaceRepository;

@Controller
@RequestMapping("/spaces")
public class LivspaceAppController {

	@Autowired
	LivspaceRepository livspaceRepo;

	@Autowired
	LivspaceEditRepository livspaceEditRepo;

	@Autowired
	LivspaceFullRepository livspaceFullRepo;

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping
	public String dashboard(Model model) {
		model.addAttribute("spaces", livspaceRepo.findAll());
		return "spaces";
	}

	@GetMapping("/{id}")
	public String space(@PathVariable("id") int id, Model model) {
		model.addAttribute("space", livspaceRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + id + " not found")));
		return "space";
	}

	@GetMapping("/dashboard")
	public String overview(Model model) {
		model.addAttribute("spaces", livspaceEditRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		return "dashboard";
	}

	@GetMapping("/view/{id}")
	public String view(@PathVariable("id") int id, Model model) {
		model.addAttribute("space", livspaceFullRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + id + " not found")));
		model.addAttribute("isEdit", false);
		model.addAttribute("typeList", findTypes());
		model.addAttribute("styleList", findStyles());
		return "action";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		model.addAttribute("space", livspaceFullRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + id + " not found")));
		model.addAttribute("isEdit", true);
		model.addAttribute("typeList", findTypes());
		model.addAttribute("styleList", findStyles());
		return "action";
	}

	@PostMapping("/save/{id}")
	public String save(@PathVariable("id") int id, @ModelAttribute LivspaceFull space, Model model) {
		livspaceFullRepo.save(space);
		model.addAttribute("spaces", livspaceFullRepo.findAll());
		return "redirect:/spaces/dashboard";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, Model model) {
		livspaceFullRepo.deleteById(id);
		model.addAttribute("spaces", livspaceFullRepo.findAll());
		return "redirect:/spaces/dashboard";
	}

	public List<String> findTypes() {
		return livspaceRepo.findAll().stream().map(item -> item.getType()).distinct().collect(Collectors.toList());
	}

	public List<String> findStyles() {
		return livspaceRepo.findAll().stream().map(item -> item.getStyle()).distinct().collect(Collectors.toList());
	}
}
