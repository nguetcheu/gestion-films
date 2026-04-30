package com.formation.films.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 300)
    private String titre;

    @Column(nullable = false)
    private Integer annee;

    @Column(nullable = false)
    private Integer dureeMinutes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "realisateur_id", nullable = false)
    private Realisateur realisateur;

    protected Film() {}

    public Film(String titre, Integer annee, Integer dureeMinutes, Realisateur realisateur) {
        this.titre = titre;
        this.annee = annee;
        this.dureeMinutes = dureeMinutes;
        this.realisateur = realisateur;
    }

    public Long getId() { return id; }
    public String getTitre() { return titre; }
    public Integer getAnnee() { return annee; }
    public Integer getDureeMinutes() { return dureeMinutes; }
    public Realisateur getRealisateur() { return realisateur; }

    public void setTitre(String titre) { this.titre = titre; }
    public void setAnnee(Integer annee) { this.annee = annee; }
    public void setDureeMinutes(Integer d) { this.dureeMinutes = d; }
    public void setRealisateur(Realisateur r) { this.realisateur = r; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        return Objects.equals(id, ((Film) o).id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}