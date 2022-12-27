package org.mycompany.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.model.CV;
import org.mycompany.repo.ICVRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessorSaveCV implements Processor {
	
	@Autowired
	ICVRepository icvRepository;
	
	
	@Override
	public void process(Exchange exchange) throws Exception {
//		System.out.println(exchange.getIn().getBody().toString());
		CV cv = (CV) exchange.getIn().getBody();
		System.out.println(cv);
		icvRepository.save(cv);
		
		System.out.println("On a bien récupéré et enregistré le CV depuis activeMQ : " + cv.toString());

	}
}
