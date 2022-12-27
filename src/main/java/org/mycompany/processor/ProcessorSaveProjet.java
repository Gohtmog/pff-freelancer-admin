package org.mycompany.processor;



import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.model.CV;
import org.mycompany.model.Projet;
import org.mycompany.repo.ICVRepository;
import org.mycompany.repo.IProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ProcessorSaveProjet implements Processor {


	@Autowired
	IProjetRepository iProjetRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
//		System.out.println(exchange.getIn().getBody().toString());
		
		String CVJSON =  exchange.getIn().getBody(String.class);
		System.out.println("parser reussie  :" + CVJSON);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("mapper reussie ");
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		System.out.println("serialization reussie");
		Projet pr = mapper.readValue(CVJSON, Projet.class);
		System.out.println("id : " + pr.getId());
		System.out.println("intitule : " + pr.getIntitule());
		System.out.println("salaire: " + pr.getSalaire());
		System.out.println("duree: " + pr.getDuree());
		System.out.println("taille equipe: " + pr.getTailleEquipe());
		System.out.println("entreprise: " + pr.getEntreprise());
		System.out.println("listeCandidats: " + pr.getListeCandidats());
		System.out.println("class: " + pr.getClass());

		iProjetRepository.save(pr);

		System.out.println("On a bien récupéré et enregistré le CV depuis activeMQ : " + pr.toString());

	}
}
