package org.mycompany.processor;



import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.controller.GeneralController;
import org.mycompany.model.CV;
import org.mycompany.model.Candidat;
import org.mycompany.model.Notes;
import org.mycompany.model.Projet;
import org.mycompany.repo.ICVRepository;
import org.mycompany.repo.ICandidatRepository;
import org.mycompany.repo.INotesRepository;
import org.mycompany.repo.IProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ProcessorSaveCandidat implements Processor {

	@Autowired
	GeneralController generalController;
	@Autowired
	INotesRepository iNotesRepository;
	
	@Autowired
	ICandidatRepository iCandidatRepository;
	@Autowired
	IProjetRepository iProjetRepository;
	@Autowired
	ICVRepository icvRepository;
	
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
		System.out.println("listesCV: " + ca.getListeCV());
		System.out.println("listesNotes: " + ca.getListeNotes());		
		System.out.println("class: " + ca.getClass());

		iCandidatRepository.save(ca);
		
//		for (Projet pro : ca.getListeProjet()) {
//			generalController.lienProjetCandidat(pro, ca);
//			iProjetRepository.save(pro);
//		}
//		
//		for (CV cv : ca.getListeCV()) {
//			generalController.lienCVCandidat(cv, ca);
//			icvRepository.save(cv);
//		}
//		for (Notes notes : ca.getListeNotes()) {
//			generalController.lienNoteCandidat(notes, ca);
//			iNotesRepository.save(notes);
//		}

		System.out.println("On a bien récupéré et enregistré le Candidat depuis activeMQ : " + ca.toString());

	}
}
