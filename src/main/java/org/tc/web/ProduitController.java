package org.tc.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tc.dao.ProduitRepository;
import org.tc.entities.Produit;

@Controller
public class ProduitController {

	@Autowired
	ProduitRepository produitRepository;

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/index")
	public String getProduit(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "size", defaultValue = "5") int s,
			@RequestParam(name = "motCle", defaultValue = "") String mc) {
		// Page<Produit> pageProduits = produitRepository.findAll(new
		// PageRequest(p, s));
		Page<Produit> pageProduits = produitRepository.chercher("%" + mc + "%", new PageRequest(p, s));

		model.addAttribute("listProduitS", pageProduits.getContent());
		int[] pages = new int[pageProduits.getTotalPages()];
		model.addAttribute("pageS", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("size", s);
		model.addAttribute("motCle", mc);
		return "produits";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteProduit(Long id, String motCle, int page, int size) {
		Produit p = produitRepository.findById(id).orElse(null);
		produitRepository.delete(p);
		return "redirect:/index?page=" + page + "&size=" + size + "&motCle=" + motCle;
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String formProduit(Model model) {
		model.addAttribute("produit", new Produit());
		return "FormProduit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduit(Model model, @Valid Produit produit, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "FormProduit";
		}
		produitRepository.save(produit);
		return "Confirmation";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editProduit(Model model, Long id) {
		Produit produit = produitRepository.findById(id).orElse(null);
		model.addAttribute("produit", produit);
		return "EditProduit";
	}
}