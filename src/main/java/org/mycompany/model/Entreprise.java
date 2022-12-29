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
//@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id",scope = Entreprise.class)
public class Entreprise {

	@Id
	private int id;
	private String nom;
	private int taille;
	private double capital;

	private int moyNotes;

	@JsonIgnore
	@OneToMany(mappedBy = "Entreprise")
//	@JoinColumn(name = "idNotes")
	private List<NotesEntreprise> listeNotesEntreprise;

	@JsonIgnore
	@OneToMany(mappedBy = "entreprise")
//	@JoinColumn(name = "idEntreprise")
	private List<Projet> listeProjets;

	public Entreprise(int id, String nom, int taille, double capital, List<NotesEntreprise> listeNotesEntreprise,
			List<Projet> listeProjets) {
		super();
		this.id = id;
		this.nom = nom;
		this.taille = taille;
		this.capital = capital;
		int count = 1;
		int moyNote = 0;
		for (NotesEntreprise notes : listeNotesEntreprise) {
			if (notes.getEntreprise().getId() == id) {
				moyNotes += notes.getNote();
				count++;
			}
		}
		if (count == 1) {
			moyNotes = 0;
		} else {
			moyNotes = moyNotes / count;
		}
		moyNotes = moyNote;
		this.listeProjets = listeProjets;
		this.listeNotesEntreprise = listeNotesEntreprise;
	}

	public Entreprise(int id, String nom, int taille, double capital) {
		super();
		this.id = id;
		this.nom = nom;
		this.taille = taille;
		this.capital = capital;
	}

	public Entreprise() {
		super();
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

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

	public int getMoyNotes() {
		return moyNotes;
	}

	public void setMoyNotes(int moyNotes) {
		this.moyNotes = moyNotes;
	}

	public List<NotesEntreprise> getlisteNotesEntreprise() {
		return listeNotesEntreprise;
	}

	public void setlisteNotesEntreprise(List<NotesEntreprise> listeNotesEntreprise) {
		this.listeNotesEntreprise = listeNotesEntreprise;
	}



	public List<Projet> getListeProjets() {
		return listeProjets;
	}

	public void setListeProjets(List<Projet> listeProjets) {
		this.listeProjets = listeProjets;
	}

	@Override
	public String toString() {
		return "Entreprise [id=" + id + ", nom=" + nom + ", taille=" + taille + ", capital=" + capital + ", moyNotes="
				+ moyNotes + "]";
	}

}
