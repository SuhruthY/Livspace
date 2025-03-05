package com.suhruth.livspace.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.suhruth.livspace.exception.ResourceNotFoundException;
import com.suhruth.livspace.model.LivspaceFull;
import com.suhruth.livspace.repository.LivspaceEditRepository;
import com.suhruth.livspace.repository.LivspaceFullRepository;
import com.suhruth.livspace.repository.LivspaceRepository;

@Controller
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

	@GetMapping("/login")
	public String login(Authentication authentication) {

		if (authentication != null && authentication.isAuthenticated()) {
			
			boolean isAdmin = authentication.getAuthorities().stream()
					.anyMatch(auth -> auth.getAuthority().equalsIgnoreCase("ROLE_ADMIN"));
			
			if(isAdmin) {
				return "redirect:/spaces/dashboard"; 
			}
			
			return "redirect:/spaces"; 
		}
		return "/login";
	}

	@GetMapping("/spaces")
	public String dashboard(Model model) {
		model.addAttribute("spaces", livspaceRepo.findAll());
		return "spaces";
	}

	@GetMapping("/spaces/{id}")
	public String space(@PathVariable("id") int id, Model model) {
		model.addAttribute("space", livspaceRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + id + " not found")));
		return "space";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/spaces/dashboard")
	public String overview(Model model) {
		model.addAttribute("spaces", livspaceEditRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		return "dashboard";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/spaces/view/{id}")
	public String view(@PathVariable("id") int id, Model model) {
		model.addAttribute("space", livspaceFullRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + id + " not found")));
		model.addAttribute("isEdit", false);
		model.addAttribute("typeList", findTypes());
		model.addAttribute("styleList", findStyles());
		return "action";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/spaces/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		model.addAttribute("space", livspaceFullRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + id + " not found")));
		model.addAttribute("isEdit", true);
		model.addAttribute("typeList", findTypes());
		model.addAttribute("styleList", findStyles());
		return "action";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/spaces/save/{id}")
	public String save(@PathVariable("id") int id, @ModelAttribute LivspaceFull space, Model model) {
		livspaceFullRepo.save(space);
		model.addAttribute("spaces", livspaceFullRepo.findAll());
		return "redirect:/spaces/dashboard";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/spaces/delete/{id}")
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
