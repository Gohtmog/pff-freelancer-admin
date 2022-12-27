package org.mycompany.processor;



import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.model.CV;
import org.mycompany.model.Candidat;
import org.mycompany.repo.ICVRepository;
import org.mycompany.repo.ICandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ProcessorSaveCandidat implements Processor {


	@Autowired
	ICandidatRepository iCandidatRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
//		System.out.println(exchange.getIn().getBody().toString());
		
		String CAJSON =  exchange.getIn().getBody(String.class);
		System.out.println("parser reussie  :" + CAJSON);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("mapper reussie ");
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		System.out.println("serialization reussie");
		Candidat ca = mapper.readValue(CAJSON, Candidat.class);
		System.out.println("id : " + ca.getId());
		System.out.println("nom : " + ca.getNom());
		System.out.println("prenom: " + ca.getPrenom());
		System.out.println("moynotes: " + ca.getMoyNotes());
		System.out.println("listesProjet: " + ca.getListeProjet());
		System.out.println("listesTest: " + ca.getListeTest());
		System.out.println("listesCV: " + ca.getListeCV());
		System.out.println("listesNotes: " + ca.getListeNotes());		
		System.out.println("class: " + ca.getClass());

		iCandidatRepository.save(ca);

		System.out.println("On a bien récupéré et enregistré le CV depuis activeMQ : " + ca.toString());

	}
}
