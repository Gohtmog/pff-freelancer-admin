package org.mycompany.processor;



import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.model.CV;
import org.mycompany.model.Notes;
import org.mycompany.model.NotesEntreprise;
import org.mycompany.repo.ICVRepository;
import org.mycompany.repo.INotesEntrepriseRepository;
import org.mycompany.repo.INotesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ProcessorSaveNotesEntreprise implements Processor {


	@Autowired
	INotesEntrepriseRepository iNotesEntrepriseRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
//		System.out.println(exchange.getIn().getBody().toString());
		
		String CVJSON =  exchange.getIn().getBody(String.class);
		System.out.println("parser reussie  :" + CVJSON);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("mapper reussie ");
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		System.out.println("serialization reussie");
		NotesEntreprise no = mapper.readValue(CVJSON, NotesEntreprise.class);
		System.out.println("id : " + no.getId());
		System.out.println("note : " + no.getNote());
		System.out.println("candidat: " + no.getEntreprise());
		System.out.println("class: " + no.getClass());

		iNotesEntrepriseRepository.save(no);

		System.out.println("On a bien récupéré et enregistré le CV depuis activeMQ : " + no.toString());

	}
}
