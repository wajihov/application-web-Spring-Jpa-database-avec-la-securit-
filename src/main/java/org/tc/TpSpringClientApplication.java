package org.tc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.tc.dao.ProduitRepository;
import org.tc.entities.Produit;

@SpringBootApplication
public class TpSpringClientApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(TpSpringClientApplication.class, args);
		ProduitRepository produitRepository = ctx.getBean(ProduitRepository.class);
		produitRepository.save(new Produit("ASDPM", 1500, 10));
		produitRepository.save(new Produit("AFGPM", 9500, 60));
		produitRepository.save(new Produit("FGHNBVG", 4300, 79));

		System.out.println("$$$$$$$$$$$$$$ FindAll $$$$$$$$$$$$$$$$$");
		produitRepository.findAll().forEach(p -> System.out.println("designation : " + p.getDesignation()));

		System.out.println("$$$$$$$$$$$$$$ FindOne $$$$$$$$$$$$$$$$$");
		Produit produit = produitRepository.findById(2L).orElse(null);
		System.out.println("le designation : " + produit.getDesignation());
		
		System.out.println("$$$$$$$$$$$$$$ delete One $$$$$$$$$$$$$$$$$");
		
		/*Produit p1= produitRepository.findById(13L).orElse(null);
		produitRepository.delete(p1);*/
		
		Produit p2= produitRepository.findById(9L).orElse(null);
		p2.setDesignation("PPML");
		p2.setPrix(55260);
		p2.setQuantite(520);
		produitRepository.save(p2);
	}
}
