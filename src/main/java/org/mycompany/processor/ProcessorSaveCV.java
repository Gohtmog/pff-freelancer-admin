package org.mycompany.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.controller.GeneralController;
import org.mycompany.model.CV;
import org.mycompany.model.Candidat;
import org.mycompany.repo.ICVRepository;
import org.mycompany.repo.ICandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ProcessorSaveCV implements Processor {

	@Autowired
	GeneralController generalController;

	@Autowired
	ICVRepository icvRepository;

	@Autowired
	ICandidatRepository iCandidatRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
//		System.out.println(exchange.getIn().getBody().toString());

		String CVJSON = exchange.getIn().getBody(String.class);
		System.out.println("parser reussie  :" + CVJSON);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("mapper reussie ");
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		System.out.println("serialization reussie");
		CV cv = mapper.readValue(CVJSON, CV.class);
		System.out.println("id : " + cv.getId());
		System.out.println("description : " + cv.getDescription());
		System.out.println("candidat: " + cv.getCandidat());
		System.out.println("class: " + cv.getClass());
		icvRepository.save(cv);
//		Candidat ca = cv.getCandidat();
//		generalController.lienCVCandidat(cv, ca);
//		iCandidatRepository.save(ca); 
		System.out.println("On a bien récupéré et enregistré le CV depuis activeMQ : " + cv.toString());

	}
}
