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
import org.mycompany.repo.INotesRepository;
import org.mycompany.repo.IProjetRepository;
import org.mycompany.repo.ITestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	ITestRepository itr;

	public void lienProjetCandidat(Projet pro, Candidat candidat) {
		List<Projet> lP = candidat.getListeProjet();
		List<Candidat> lC = pro.getListeCandidats();

		if (!(lP.contains(pro))) {
			lP.add(pro);
		} else {
			System.out.println("Le projet est déjà enregistré chez le candidat.");
		}

		if (!(lC.contains(candidat))) {
			lC.add(candidat);
		} else {
			System.out.println("Le candidat est déjà enregistré pour ce projet.");
		}

		candidat.setListeProjet(lP);
		pro.setListeCandidats(lC);
		System.out.println("On a mis à jour projet et/ou candidat.");

	}

	public void lienCVCandidat(CV cv, Candidat candidat) {
		List<CV> lCV = candidat.getListeCV();

		if (!(lCV.contains(cv))) {
			lCV.add(cv);
		} else {
			System.out.println("Le CV est déjà enregistré chez le candidat.");
		}

		if (cv.getCandidat() == null) {
			cv.setCandidat(candidat);
		} else {
			System.out.println("Le candidat est déjà enregistré pour ce CV.");
		}

		candidat.setListeCV(lCV);
		System.out.println("On a mis à jour CV et/ou candidat.");

	}

	public void lienNoteCandidat(Notes notes, Candidat candidat) {
		List<Notes> lN = candidat.getListeNotes();

		if (!(lN.contains(notes))) {
			lN.add(notes);
		} else {
			System.out.println("La note est déjà enregistrée chez le candidat.");
		}

		if (notes.getCandidat() == null) {
			notes.setCandidat(candidat);
		} else {
			System.out.println("Le candidat est déjà enregistré pour cette note.");
		}

		candidat.setListeNotes(lN);
		System.out.println("On a mis à jour notes et/ou candidat.");

	}

	public void lienNoteEntrepriseEntreprise(NotesEntreprise notesE, Entreprise ent) {
		List<NotesEntreprise> lNE = ent.getlisteNotesEntreprise();

		if (!(lNE.contains(notesE))) {
			lNE.add(notesE);
		} else {
			System.out.println("La note est déjà enregistrée chez l'entreprise.");
		}

		if (notesE.getEntreprise() == null) {
			notesE.setEntreprise(ent);
		} else {
			System.out.println("L'entreprise est déjà enregistrée pour cette note.");
		}

		ent.setlisteNotesEntreprise(lNE);
		System.out.println("On a mis à jour notes et/ou entreprise.");

	}

	public void lienProjetEntreprise(Projet pro, Entreprise ent) {
		List<Projet> lP = ent.getListeProjets();

		if (!(lP.contains(pro))) {
			lP.add(pro);
		} else {
			System.out.println("Le projet est déjà enregistré pour cette entreprise.");
		}

		if (pro.getEntreprise() == null) {
			pro.setEntreprise(ent);
		} else {
			System.out.println("L'entreprise est déjà enregistrée pour ce projet.");
		}

		ent.setListeProjets(lP);
		System.out.println("On a mis à jour projet et/ou entreprise.");

	}
	@PutMapping("/lierProjetsCandidat/{idPro}/{idCan}")
	public Projet lierProjetsCandidat( @PathVariable int idPro, @PathVariable int idCan) {
		System.out.println("initialisation du liage");
		System.out.println(ipr.findById(idPro).get());
		Projet pro = ipr.findById(idPro).get();
		System.out.println("projet" +pro);
		List<Candidat> listeC = ipr.findById(idPro).get().getListeCandidats();
		System.out.println("liste candidat : " +listeC);
		listeC.add(icr.findById(idCan).get());
		System.out.println("liste candidat modifier : " +listeC);
		Projet newProjet2 =  ipr.findById(idPro).get();
		
		newProjet2.setListeCandidats(listeC);
		System.out.println("le projet modifier :" + newProjet2);
		return ipr.findById(idPro).map(Projet -> {
			Projet.setId(newProjet2.getId());
			Projet.setIntitule(newProjet2.getIntitule());
			Projet.setSalaire(newProjet2.getSalaire());
			Projet.setDuree(newProjet2.getDuree());
			Projet.setTailleEquipe(newProjet2.getTailleEquipe());
			Projet.setEntreprise(newProjet2.getEntreprise());
			Projet.setListeCandidats(newProjet2.getListeCandidats());
			return ipr.save(Projet);
		}).orElseGet(() -> {
			return ipr.save(ipr.findById(idPro).get());
		});
	}
	
	
		@PutMapping("/lierCandidatProjets/{idPro}/{idCan}")
		public Candidat lierCandidatProjet(@PathVariable int idPro, @PathVariable int idCan) {
			List<Projet> listeP = icr.findById(idCan).get().getListeProjet();	
			listeP.add(ipr.getById(idPro));
			Candidat newCan2 =  icr.findById(idCan).get();
			newCan2.setListeProjet(listeP);
			return icr.save(newCan2);
//			return icr.findById(idCan).map(Candidat -> {
//				Candidat.setId(newCan2.getId());
//				Candidat.setNom(newCan2.getNom());
//				Candidat.setPrenom(newCan2.getPrenom());
//				Candidat.setMoyNotes(newCan2.getMoyNotes());
//				Candidat.setListeProjet(newCan2.getListeProjet());
//				Candidat.setListeCV(newCan2.getListeCV());
//				Candidat.setListeNotes(newCan2.getListeNotes());
//				return icr.save(Candidat);
//			}).orElseGet(() -> {
//				return icr.save(icr.getById(idCan));
//			});
			
		
	}



//	@PutMapping("/updateProjet{id}")
//	public Projet updateProjet(@RequestBody Projet newProjet, @PathVariable int id) {
//		return ier.findById(id).map(Projet -> {
//			Projet.setId(newProjet.getId());
//			Projet.setIntitule(newProjet.getIntitule());
//			Projet.setSalaire(newProjet.getSalaire());
//			Projet.setDuree(newProjet.getDuree());
//			Projet.setTailleEquipe(newProjet.getTailleEquipe());
//			Projet.setEntreprise(newProjet.getEntreprise());
//			Projet.setListeCandidats(newProjet.getListeCandidats());
//			return ier.save(Projet);
//		}).orElseGet(() -> {
//			return ier.save(newProjet);
//		});
	
}
