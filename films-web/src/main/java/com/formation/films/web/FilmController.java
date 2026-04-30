package com.formation.films.web;

import com.formation.films.domain.dto.FilmCreateRequest;
import com.formation.films.domain.dto.FilmResponse;
import com.formation.films.domain.dto.FilmUpdateRequest;
import com.formation.films.service.FilmService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<FilmResponse> create(@Valid @RequestBody FilmCreateRequest req) {
        FilmResponse response = filmService.create(req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public Page<FilmResponse> findAll(Pageable pageable) {
        return filmService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public FilmResponse findById(@PathVariable Long id) {
        return filmService.findById(id);
    }

    @PutMapping("/{id}")
    public FilmResponse update(@PathVariable Long id, @Valid @RequestBody FilmUpdateRequest req) {
        return filmService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        filmService.delete(id);
        return ResponseEntity.noContent().build();
    }
}