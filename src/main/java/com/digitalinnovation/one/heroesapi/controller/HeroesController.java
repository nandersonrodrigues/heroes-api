package com.digitalinnovation.one.heroesapi.controller;

import com.digitalinnovation.one.heroesapi.model.Heroes;
import com.digitalinnovation.one.heroesapi.repository.HeroesRepository;
import com.digitalinnovation.one.heroesapi.service.HeroesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.digitalinnovation.one.heroesapi.constants.HeroesConstants.HEROES_ENDPOINT_LOCAL;

@RestController
@Slf4j
public class HeroesController {

    HeroesService heroesService;
    HeroesRepository heroesRepository;

    private static final org.slf4j.Logger logs = org.slf4j.LoggerFactory.getLogger(HeroesController.class);

    public HeroesController (
            HeroesService heroesService,
            HeroesRepository heroesRepository
    ) {
        this.heroesService = heroesService;
        this.heroesRepository = heroesRepository;
    }

    @GetMapping(HEROES_ENDPOINT_LOCAL)
    public Flux<Heroes> getAllItems () {
        log.info("Requesting the list of all heroes");
        return heroesService.findAll();
    }

    @GetMapping(HEROES_ENDPOINT_LOCAL+"/id")
    public Mono<ResponseEntity<Heroes>> findHeroById (@PathVariable String id) {
        log.info("Requesting the hero with id {}", id);
        return heroesService.findById(id)
                .map((item) -> new ResponseEntity<Heroes>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<Heroes>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<Heroes> createHero (@RequestBody Heroes hero) {
        log.info("a new hero was created");
        return heroesService.save(hero);
    }

    @DeleteMapping(HEROES_ENDPOINT_LOCAL+"/id")
    @ResponseStatus(code = HttpStatus.CONTINUE)
    public Mono<HttpStatus> deleteHero (@PathVariable String id) {
        heroesService.deleteById(id);
        log.info("hero with id {} was deleted", id);
        return Mono.just(HttpStatus.CONTINUE);
    }
}
