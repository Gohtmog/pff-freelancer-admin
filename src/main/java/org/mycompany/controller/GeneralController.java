package org.mycompany.controller;

import java.util.List;

import org.mycompany.model.CV;
import org.mycompany.model.Candidat;
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
import org.mycompany.repo.ITestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralController {

	@Autowired
	ICandidatRepository icr;

	@Autowired
	IProjetRepository ipr;

	@Autowired
	ICVRepository icvr;

	@Autowired
	IEntrepriseRepository ier;

	@Autowired
	INotesRepository inr;
	
	@Autowired
	INotesEntrepriseRepository iner;

	@Autowired
	ITestRepository itr;

	
	@PutMapping("/lierEntrepriseNotesEntreprise/{idNote}/{idEnt}")
	public Entreprise lierEntrepriseNotesEntreprise(@PathVariable int idNoteE, @PathVariable int idEnt) {
		List<NotesEntreprise> listeN = ier.findById(idEnt).get().getlisteNotesEntreprise();
		listeN.add(iner.findById(idNoteE).get());
		Entreprise newEnt = ier.findById(idEnt).get();
		newEnt.setlisteNotesEntreprise(listeN);
		return ier.save(newEnt);
	}
	
	@PutMapping("/lierNotesEntrepriseEntreprise/{idNote}/{idEnt}")
	public NotesEntreprise lierNotesEntrepriseEntreprise(@PathVariable int idNoteE, @PathVariable int idEnt) {
		Entreprise ent = ier.findById(idEnt).get();
		NotesEntreprise nEnt = iner.findById(idNoteE).get();
		nEnt.setEntreprise(ent);
		return iner.save(nEnt);
	}


	
	@PutMapping("/lierEntrepriseProjets/{idPro}/{idEnt}")
	public Entreprise lierEntrepriseProjet(@PathVariable int idPro, @PathVariable int idEnt) {
		List<Projet> listeP = ier.findById(idEnt).get().getListeProjets();
		listeP.add(ipr.getById(idPro));
		Entreprise newEnt = ier.findById(idEnt).get();
		newEnt.setListeProjets(listeP);
		return ier.save(newEnt);
	}
	
	@PutMapping("/lierProjetEntreprise/{idPro}/{idEnt}")
	public Projet lierProjetEntreprise(@PathVariable int idPro, @PathVariable int idEnt) {
		Entreprise ent = ier.findById(idEnt).get();
		Projet pro = ipr.findById(idPro).get();
		pro.setEntreprise(ent);
		return ipr.save(pro);
	}


	@PutMapping("/lierCandidatProjets/{idPro}/{idCan}")
	public Candidat lierCandidatProjet(@PathVariable int idPro, @PathVariable int idCan) {
		List<Projet> listeP = icr.findById(idCan).get().getListeProjet();
		listeP.add(ipr.getById(idPro));
		Candidat newCan2 = icr.findById(idCan).get();
		newCan2.setListeProjet(listeP);
		return icr.save(newCan2);

	}

	@PutMapping("/lierProjetsCandidat/{idPro}/{idCan}")
	public Projet lierProjetCandidat(@PathVariable int idPro, @PathVariable int idCan) {
		List<Candidat> listeC = ipr.findById(idPro).get().getListeCandidats();
		listeC.add(icr.getById(idCan));
		Projet newProjet = ipr.findById(idPro).get();
		newProjet.setListeCandidats(listeC);
		return ipr.save(newProjet);

	}


	@PutMapping("/lierCandidatCVs/{idPro}/{idCan}")
	public Candidat lierCandidatCV(@PathVariable int idCV, @PathVariable int idCan) {
		List<CV> listeCV = icr.findById(idCan).get().getListeCV();
		listeCV.add(icvr.getById(idCV));
		Candidat newCan2 = icr.findById(idCan).get();
		newCan2.setListeCV(listeCV);
		return icr.save(newCan2);
	}
	
	@PutMapping("/lierCandidatNotes/{idPro}/{idCan}")
	public Candidat lierCandidatNotes(@PathVariable int idNotes, @PathVariable int idCan) {
		List<Notes> listeNotes = icr.findById(idCan).get().getListeNotes();
		listeNotes.add(inr.getById(idNotes));
		Candidat newCan2 = icr.findById(idCan).get();
		newCan2.setListeNotes(listeNotes);
		return icr.save(newCan2);
	}

}
