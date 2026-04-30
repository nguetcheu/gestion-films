package com.formation.films.service;

import com.formation.films.domain.Film;
import com.formation.films.domain.Realisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FilmRepositoryTest {

    @Autowired TestEntityManager em;
    @Autowired FilmRepository filmRepository;

    private Realisateur realisateur;

    @BeforeEach
    void setUp() {
        realisateur = em.persistAndFlush(new Realisateur("Kubrick"));
    }

    @Test
    void findByTitre_existant() {
        em.persistAndFlush(new Film("2001", 1968, 149, realisateur));
        Optional<Film> result = filmRepository.findByTitre("2001");
        assertThat(result).isPresent();
    }

    @Test
    void findByTitre_inconnu() {
        assertThat(filmRepository.findByTitre("Inconnu")).isEmpty();
    }

    @Test
    void existsByTitre_vrai() {
        em.persistAndFlush(new Film("Shining", 1980, 146, realisateur));
        assertThat(filmRepository.existsByTitre("Shining")).isTrue();
    }

    @Test
    void existsByTitre_faux() {
        assertThat(filmRepository.existsByTitre("Inconnu")).isFalse();
    }
}