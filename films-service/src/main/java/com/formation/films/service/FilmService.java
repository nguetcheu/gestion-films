package com.formation.films.service;

import com.formation.films.domain.Film;
import com.formation.films.domain.Realisateur;
import com.formation.films.domain.dto.FilmCreateRequest;
import com.formation.films.domain.dto.FilmResponse;
import com.formation.films.domain.dto.FilmUpdateRequest;
import com.formation.films.service.exception.FilmNotFoundException;
import com.formation.films.service.exception.TitreDejaExistantException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FilmService {

    private final FilmRepository filmRepository;
    private final RealisateurRepository realisateurRepository;
    private final FilmMapper filmMapper;

    public FilmService(FilmRepository filmRepository,
                       RealisateurRepository realisateurRepository,
                       FilmMapper filmMapper) {
        this.filmRepository = filmRepository;
        this.realisateurRepository = realisateurRepository;
        this.filmMapper = filmMapper;
    }

    public FilmResponse create(FilmCreateRequest req) {
        if (filmRepository.existsByTitre(req.titre())) {
            throw new TitreDejaExistantException(req.titre());
        }
        Realisateur realisateur = realisateurRepository
                .findByNom(req.realisateurNom())
                .orElseGet(() -> realisateurRepository.save(new Realisateur(req.realisateurNom())));

        Film film = new Film(req.titre(), req.annee(), req.dureeMinutes(), realisateur);
        return filmMapper.toResponse(filmRepository.save(film));
    }

    @Transactional(readOnly = true)
    public FilmResponse findById(Long id) {
        return filmRepository.findById(id)
                .map(filmMapper::toResponse)
                .orElseThrow(() -> new FilmNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Page<FilmResponse> findAll(Pageable pageable) {
        return filmRepository.findAll(pageable).map(filmMapper::toResponse);
    }

    public FilmResponse update(Long id, FilmUpdateRequest req) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException(id));

        if (!film.getTitre().equals(req.titre()) && filmRepository.existsByTitre(req.titre())) {
            throw new TitreDejaExistantException(req.titre());
        }
        Realisateur realisateur = realisateurRepository
                .findByNom(req.realisateurNom())
                .orElseGet(() -> realisateurRepository.save(new Realisateur(req.realisateurNom())));

        film.setTitre(req.titre());
        film.setAnnee(req.annee());
        film.setDureeMinutes(req.dureeMinutes());
        film.setRealisateur(realisateur);
        return filmMapper.toResponse(filmRepository.save(film));
    }

    public void delete(Long id) {
        if (!filmRepository.existsById(id)) {
            throw new FilmNotFoundException(id);
        }
        filmRepository.deleteById(id);
    }
}