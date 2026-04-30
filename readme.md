# Gestion de Films — API REST Spring Boot

![CI](https://github.com/nguetcheu/gestion-films/actions/workflows/ci.yml/badge.svg)

## Lancement

```bash
mvn clean install
mvn -pl films-web spring-boot:run
```

## Endpoints

```bash
# Créer
curl -X POST http://localhost:8080/api/films \
  -H "Content-Type: application/json" \
  -d '{"titre":"Inception","annee":2010,"dureeMinutes":148,"realisateurNom":"Nolan"}'

# Lister
curl http://localhost:8080/api/films

# Récupérer
curl http://localhost:8080/api/films/1

# Modifier
curl -X PUT http://localhost:8080/api/films/1 \
  -H "Content-Type: application/json" \
  -d '{"titre":"Inception","annee":2010,"dureeMinutes":150,"realisateurNom":"Nolan"}'

# Supprimer
curl -X DELETE http://localhost:8080/api/films/1
```

## Console H2

http://localhost:8080/h2-console — JDBC URL : `jdbc:h2:mem:films` — user : `sa`

## Profils Maven vs Spring

Un **profil Maven** (`mvn -P prod`) agit au moment du **build** : il change les plugins, les dépendances ou les ressources incluses dans le JAR. 
Un **profil Spring** (`--spring.profiles.active=prod`) agit au **runtime** : il charge `application-prod.yml` et configure les beans selon l'environnement (ex : PostgreSQL en prod, H2 en dev).