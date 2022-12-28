package org.mycompany.processor;



import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.controller.GeneralController;
import org.mycompany.model.CV;
import org.mycompany.model.Entreprise;
import org.mycompany.model.Notes;
import org.mycompany.model.NotesEntreprise;
import org.mycompany.model.Projet;
import org.mycompany.repo.ICVRepository;
import org.mycompany.repo.ICandidatRepository;
import org.mycompany.repo.IEntrepriseRepository;
import org.mycompany.repo.INotesEntrepriseRepository;
import org.mycompany.repo.INotesRepository;
import org.mycompany.repo.IProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ProcessorSaveEntreprise implements Processor {

	@Autowired
	GeneralController generalController;
	@Autowired
	INotesEntrepriseRepository iNotesEntrepriseRepository;
	
	@Autowired
	IProjetRepository iProjetRepository;
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
		System.out.println("liste note: " + en.getlisteNotesEntreprise());
		System.out.println("liste Projet: " + en.getListeProjets());
		System.out.println("class: " + en.getClass());

		iEntrepriseRepository.save(en);

//		for (Projet pro : en.getListeProjets()) {
//			generalController.lienProjetEntreprise(pro, en);
//			iProjetRepository.save(pro);
//		}
//
//		for (NotesEntreprise notesE : en.getlisteNotesEntreprise()) {
//			generalController.lienNoteEntrepriseEntreprise(notesE, en);
//			iNotesEntrepriseRepository.save(notesE);
//		}
		System.out.println("On a bien récupéré et enregistré l'entreprise depuis activeMQ : " + en.toString());

	}
}
