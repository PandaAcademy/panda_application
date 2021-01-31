# panda_application
W tym repozytorium znajdziecie pliki źródłowe aplikacji pozwalające na powrócenie do wybranego etapu pracy podczas kursu DevOps Core.

## Galezie

|Galaz  | Blok  | Opis  |
|---|---|---|
| master | Maven 1 | Podstawowa wersja aplikacji |
| selenium_local | Maven 2 | Testy selenium wykonywane "lokalnie" |
| docker | Maven 2 | Profil z budowaniem obrazu dockerowego |
| selenium_grid  | Projekt CI/CD/CD 2   | Testy selenium z użyciem Selenium Grid |
| pipeline | Projekt CI/CD/CD 5 | Jenkinsfile |
| infrastructure | Projekt CI/CD/CD 6 | Dodany katalog infrastructure z plikami terraform i ansible| Końcowa wersja projektu |

# Instructions

This is an production application sample for the learning purposes.

The app consist of:

    Sample Java web app with Junit test and Selenium test
    Maven pom.xml with all build definitions for the above
    Jenkins with automatic Maven settings injection
    Selenium grid with hub and firefox node
    Artifactory

How to run?
    In order to run you need additional repository consisting of full environment and docker-compose and Dockerfile files.

    clone the panda-env repository to your local drive
    run sudo ./start.sh
    Jenkins is available at localhost:8880
        panda/panda
    Artifactory is available at localhost:8081
        admin/pandapass
    Selenium grid is available at localhost:4444
    Application (once it starts) is available at localhost:8080

