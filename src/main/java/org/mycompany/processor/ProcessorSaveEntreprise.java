package org.mycompany.processor;



import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.model.Entreprise;
import org.mycompany.repo.IEntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ProcessorSaveEntreprise implements Processor {


	@Autowired
	IEntrepriseRepository iEntrepriseRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
//		System.out.println(exchange.getIn().getBody().toString());
		
		String CVJSON =  exchange.getIn().getBody(String.class);
		System.out.println("parser reussie  :" + CVJSON);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("mapper reussie ");
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		System.out.println("serialization reussie");
		Entreprise en = mapper.readValue(CVJSON, Entreprise.class);
		System.out.println("id : " + en.getId());
		System.out.println("nom : " + en.getNom());
		System.out.println("taille: " + en.getTaille());
		System.out.println("moy note: " + en.getMoyNotes());		
		System.out.println("liste note: " + en.getListeNotes());
		System.out.println("liste Test: " + en.getListeTests());
		System.out.println("liste Projet: " + en.getListeProjets());
		System.out.println("class: " + en.getClass());

		iEntrepriseRepository.save(en);

		System.out.println("On a bien récupéré et enregistré le CV depuis activeMQ : " + en.toString());

	}
}
