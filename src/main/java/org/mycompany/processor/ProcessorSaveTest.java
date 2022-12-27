package org.mycompany.processor;



import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.model.CV;
import org.mycompany.model.Test;
import org.mycompany.repo.ICVRepository;
import org.mycompany.repo.ITestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ProcessorSaveTest implements Processor {


	@Autowired
	ITestRepository iTestRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
//		System.out.println(exchange.getIn().getBody().toString());
		
		String CVJSON =  exchange.getIn().getBody(String.class);
		System.out.println("parser reussie  :" + CVJSON);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("mapper reussie ");
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		System.out.println("serialization reussie");
		Test te = mapper.readValue(CVJSON, Test.class);
		System.out.println("id : " + te.getId());
		System.out.println("sujet : " + te.getSujet());
		System.out.println("valide: " + te.getValide());
		System.out.println("list candidat: " + te.getListeCandidats());
		System.out.println("liste entreprise: " + te.getListeEntreprises());
		System.out.println("class: " + te.getClass());

		iTestRepository.save(te);

		System.out.println("On a bien récupéré et enregistré le CV depuis activeMQ : " + te.toString());

	}
}
