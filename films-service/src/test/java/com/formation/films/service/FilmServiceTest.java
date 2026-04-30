package com.formation.films.service;

import com.formation.films.domain.dto.FilmCreateRequest;
import com.formation.films.service.exception.FilmNotFoundException;
import com.formation.films.service.exception.TitreDejaExistantException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock FilmRepository filmRepository;
    @Mock RealisateurRepository realisateurRepository;
    @Mock FilmMapper filmMapper;
    @InjectMocks FilmService filmService;

    @Test
    void create_titreDeja_leveException() {
        when(filmRepository.existsByTitre("Inception")).thenReturn(true);
        assertThatThrownBy(() -> filmService.create(
                new FilmCreateRequest("Inception", 2010, 148, "Nolan")))
                .isInstanceOf(TitreDejaExistantException.class);
    }

    @Test
    void findById_inconnu_leveException() {
        when(filmRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> filmService.findById(99L))
                .isInstanceOf(FilmNotFoundException.class);
    }
}