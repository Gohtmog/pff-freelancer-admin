package org.mycompany.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;

@Entity
@Table
@Component
//@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id", scope = Candidat.class)
public class Candidat {

	@Id
	private int id;
	private String nom;
	private String prenom;

	private int moyNotes;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "T_Projet_Candidat_Associations", joinColumns = @JoinColumn(name = "idProjet"), inverseJoinColumns = @JoinColumn(name = "idCandidat"))
	private List<Projet> listeProjets;

	@JsonIgnore
	@OneToMany(mappedBy = "candidat")
//	@JoinColumn(name = "idCV")
	private List<CV> listeCV;

	@JsonIgnore
	@OneToMany(mappedBy = "candidat")
//	@JoinColumn(name = "idNotes")
	private List<Notes> listeNotes;

	public Candidat() {
		super();
	}

	public Candidat(int id, String nom, String prenom, List<Projet> listeProjet, List<CV> listeCV,
			List<Notes> listeNotes2) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		int count = 1;
		int moyNote = 0;
		for (Notes notes : listeNotes2) {
			if (notes.getCandidat().getId() == id) {
				moyNotes += notes.getNote();
				count++;
			}
		}
		if (count == 1) {
			moyNotes = 0;
		} else {
			moyNotes = moyNotes / count;
		}
		moyNote = moyNotes;
		this.listeProjets = listeProjet;
		this.listeCV = listeCV;
		this.listeNotes = listeNotes2;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getMoyNotes() {
		return moyNotes;
	}

	public void setMoyNotes(int moyNotes) {
		this.moyNotes = moyNotes;
	}

	public List<Projet> getListeProjet() {
		return listeProjets;
	}

	public void setListeProjet(List<Projet> listeProjet) {
		listeProjets = listeProjet;
	}

	public List<CV> getListeCV() {
		return listeCV;
	}

	public void setListeCV(List<CV> listeCV) {
		this.listeCV = listeCV;
	}

	public List<Notes> getListeNotes() {
		return listeNotes;
	}

	public void setListeNotes(List<Notes> listeNotes) {
		this.listeNotes = listeNotes;
	}

	@Override
	public String toString() {
		return "Candidat [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", moyNotes=" + moyNotes + "]";
	}

}
