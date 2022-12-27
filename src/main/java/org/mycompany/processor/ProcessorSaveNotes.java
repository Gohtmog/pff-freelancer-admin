package org.mycompany.processor;



import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.model.CV;
import org.mycompany.model.Notes;
import org.mycompany.repo.ICVRepository;
import org.mycompany.repo.INotesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ProcessorSaveNotes implements Processor {


	@Autowired
	INotesRepository iNotesRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
//		System.out.println(exchange.getIn().getBody().toString());
		
		String CVJSON =  exchange.getIn().getBody(String.class);
		System.out.println("parser reussie  :" + CVJSON);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("mapper reussie ");
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		System.out.println("serialization reussie");
		Notes no = mapper.readValue(CVJSON, Notes.class);
		System.out.println("id : " + no.getId());
		System.out.println("note : " + no.getNote());
		System.out.println("candidat: " + no.getCandidat());
		System.out.println("class: " + no.getClass());

		iNotesRepository.save(no);

		System.out.println("On a bien récupéré et enregistré le CV depuis activeMQ : " + no.toString());

	}
}
