[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fchocorion%2Fcdp-g1-eq1-dev&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=view&edge_flat=false)](https://hits.seeyoufarm.com)

# cdp-g1-eq1-dev

> Sprint 0 : 18 october -- 23 october <br>
> Sprint 1 : 2 november -- 13 november <br>
> Sprint 2 : 16 november -- 27 november <br>
> Sprint 3 : 30 november -- 11 december <br>



**Membre de l'équipe :**  Alexis Perignon, Robin Navarro, Chaïma Tarmil et Adrien Boitelle.

**Organisation :** En plus des fichiers .md, nous utilisons [ce tableur](https://docs.google.com/spreadsheets/d/1kiDOeNhRBDjNWb-zRZRpTgfK5jp7cz7PSObkAmD9f3s/edit#gid=0) pour nous y retrouver plus rapidement.

## Architecture du projet

- 3 images docker dans leur dossier respectif :
    - Back-end (Java)
    - Front-end (Angular)
    - Base de données (MySQL)

### Back-end

- main
    - domain : Définition des value object
    - dao : Définition abstraite des data access object
        - sql : Implémentation concrète des data access object et l'accès à la db
    - routes : Implémentation des routes de l'api
- test
    - dao/sql : Tests des comportements attendus lors des intéractions avec la base de données
    - domain : Tests de la validité des objets du domaine

### Front-end

- src : Point d'entrée de l'application
    - environments : Configuration du front-end
    - app : Sources du front-end
        - components : Implémentation des controlleurs
        - models : Définition du modèle
        - services : Implémentation des services
- cypress : Tests end to end

### Database

- sql-scripts : Scripts qui seront copiés dans l'initialisation du docker
    - xx_name.sql : Fichier sql qui sera exécuté par ordre alphabétique des noms de fichier, `xx` sert à les ordonner

## Workflow

### Linters

- Pour développer en back-end, nous utilisons checkstyle dont la configuration se trouve dans le fichier `.github/linters/sun_check.xml`
- Pour développer en front-end, nous utilisons tslint dont la configuration se trouve dans le fichier `src/front/tslint.json`
- Pour tester la propreté du code, des actions github pour le back et le front sont exécutées à chaque push et pull-request (voir dans `.github/workflows` les fichiers `back-lint.yml` et `front-lint.yml`)

### Commits

Les commits ne doivent concerner qu'une seule tâche/fonctionnalité, dans la mesure où segmenter les commits ne crée pas un état instable sur l'un d'entre eux. Si le commit n'est pas stable, ne pas l'envoyer sur `main` et annoter WIP (work in progress).

Le titre du commit doit contenir entre crochets la partie du projet concernée, exemple :
- [Kaban] -> modification des .md pour l'organisation
- [Refactor] -> indiquer des changements importants pour le projet
- [Back] -> modification de code dans `src/back`
- [DB] -> modification de code dans `src/bdd`
- [Tests] -> ajout de tests dans le projet
- [Actions] -> gestion des actions github
- [Cleaning] -> retouches apportées au code pour le rendre plus lisble, linter
- etc...

Lorsque l'on travaille sur une longue fonctionnalité, utiliser une branche à cet effet et push régulièrement pour que tous les développeurs puissent observer et potentiellement intervenir.

### Tests

**Tester intégralement**

La commande `docker-compose --file docker-compose.test.yml up --exit-code-from tests` effectue les groupes de tests suivants, de manière séquentielle :
- Création de la base de données
- Utilisation (directe) de la base de données
- Compilation du back-end
- Utilisation (depuis le back) de la base de données
- Utilisation des routes du back-end

Pour l'instant, les tests end to end avec cypress ne sont pas dockerisés, il faut les lancer à la main.

**Intégration automatique github :**

L'action `docker-publish.yml` lance le docker-compose des tests automatiquement à chaque push et pull-request.

**Tests pendant le développement :**
- Pour tester/reinitialiser manuellement la **base de données**, lancer `docker-compose -f docker-compose.yml up --build --force-recreate --renew-anon-volumes db app`
    - Cela va reconstruire entièrement la base de données et son schéma, si il fonctionne.
    - Ensuite cela va faire des insertions qui doivent elles aussi fonctionner
    - Pour visualiser et manipuler la base de données, phpmyadmin est disponible à `localhost:4242` (package app)
    - La base de données est accessible à `localhost:3307` (package db)
- Pour tester manuellement le **front-end**, lancer `docker-compose up db back`, il suffit ensuite d'utiliser `ng serve` pour compiler le front en temps réel.
- Pour tester manuellement le **back-end**, il faut utiliser la même commande que pour les tests complets.
- Pour les tests **end to end**, il faut utiliser cypress dans `src/front/cypress`


## Définition des Rôles

> ***Visiteur***
>
> Une personne non authentifiée qui accède au site. 
>
> ***Utilisateur***
>
> Toute personne authentifiée.
>
> ***Membre du projet***
>
> Personne authentifiée ayant accès à un projet en particulier.
>
> ***Scrum Master***
>
> Personne authentifiée ayant créé le projet.
>
> ***Product Owner***
>
> Personne authentifiée représentant le client du projet.



## Backlog

> Nous utilisons une difficulté par comparaison, en utilisant des nombres de la suite de fibonacci. 1 est donc la plus petite difficulté.



| ID    | Description                                                  | Difficulté | Priorité | Planification |
| ----- | ------------------------------------------------------------ | :--------: | :------: | :-----------: |
| US-1  | **En tant que** visiteur, **je souhaite** avoir une section “how to” comprenant la documentation utilisateur du site **afin de** pouvoir faciliter la compréhension des différentes fonctionnalités du site.<br>Sur la page principale du site (page contenant la liste des projets) onglet "How to" permettra d'accéder à la page de documentation utilisateur complète. Une icon visible sur chacunes des sections du site permettra d'être redirigé vers la page d'explication.<br>L'icon devra être visible et mise en évidence afin d'attirer l'attention de l'utilisateur. En fonction de la section depuis laquelle l'utilisateur accède à cette page il sera redirigé vers la section de la documentation correspondante.<br> Cette Documentation sera divisée en différentes parties. Chaques parties représentant une des différentes sections du site. |     1      |   Low    |   Sprint 1    |
| US-2  | **En tant qu’** utilisateur, **je souhaite** pouvoir visualiser une liste de tous les projets, ainsi que modifier, supprimer et créer un projet **afin de** pouvoir gérer les différents projets.<br> Une liste contenant tous les projets créés et leurs informations sera affichée. Un projet est défini par :<br>     - un nom, chaîne de caractère de taille maximum 50.<br>     - une description, chaîne de caractère de taille maximum 500.<br>    - une barre d'avancement (en pourcentage) représentant le travail restant à faire, c'est le nombre de tâche réalisées sur le nombre total.<br>   A droite de chaques descriptions de projet deux boutons seront présents. Un permettant de modifier les informations d'un projet (nom et description) et un permettant de supprimer un projet. Ces boutons sont visibles seulement pour les projets dont l'utilisateur est aussi scrum master. <br>Un bouton "Nouveau Projet" se trouvera près de la barre de recherche sur le haut de la page et permettra lorsqu'on appuie dessus d'ouvrir un formulaire d'ajout sous forme de popup.<br>Le formulaire comportera deux labels "Nom du projet" et "Description du Projet" ainsi que deux bouton "Abandonner" et "Créer". |     2      |   High   |   Sprint 1    |
| US-3  | **En tant qu’** utilisateur, **je souhaite** pouvoir rechercher un projet par un nom **afin** de pouvoir y accéder rapidement. Une barre de recherche se trouvera sur le haut de la page principale.<br>Un bouton "Rechercher" sera à sa droite et permettra de lancer un filtre sur les projets afin d'afficher seulement ceux contenant les termes de la recherche dans leur nom. |     2      |   Low    |   Sprint 1    |
| US-4  | **En tant qu’** utilisateur, après avoir sélectionné un projet, **je souhaite** pouvoir cliquer sur différents onglets, **afin de** pouvoir naviguer entre les différentes parties d'un projet. Sur la partie gauche de la page du projet il y aura un menu avec les onglets suivant :<br>    - Backlog<br>    - Tasks (kanban)<br>    - Planification (seul le scrum master y a accès)<br>    - Sprint actif<br>    - Les tests<br>    - Release<br>    - Documentation<br>    - Statistics<br> Lorsque l'utilisateur clique sur un des onglets la page correspondante s'ouvrira. Ce menu sera présent dans toutes les pages correspondantes aux différents onglets. Une version minimale (seulement les icons) existera lorsque l'utilisateur appuiera sur le bouton de réduction se trouvant sur le haut du menu. |     1      |   High   |   Sprint 1    |
| US-5  | **En tant qu’** utilisateur, **je souhaite** pouvoir visualiser le Backlog d'un projet **afin d'** avoir une vue d'ensemble des US. Cette page sera composée de : <br>     - Le menu de navigation à gauche<br>     - Une zone de travail avec :<br>         > En haut le nom de la section "BackLog"<br>         > Une zone Sprint contenant les sprints crés composée pour chaque sprint de: Le nom du sprint, le nombre de tâches associées à ce sprint, un bouton Démarrer  la liste des US correspondantes avec leur identification, leur nom, une icon représentant leur priorité (code couleur + fleches vers le haut ou le bas) et leur difficulté.<br>         > Sous la zone sprint un bouton d'ajout de nouveaux US<br>         > Une zone US Contenant les US créer qui n'ont pas encore étés associées à un sprint.<br> Elles seront représentés sous forme de liste avec leur identification, leur nom, leur proorité et leur difficulté. Un bouton "Creer un Sprint" se trouvera sur le haut de cette zone et un bouton "Ajouter une User Story" se trouvera en bas de cette zone. |     1      |   High   |   Sprint 2    |
| US-6  | **En tant que** product owner, **je souhaite** pouvoir créer une US pour un projet **afin de** compléter son backlog. Lorsque le product owner clique sur un des boutons "Ajouter une US" un formulaire s'ouvrira lui permettant de renseigner les informations concernant l'US. Ce formulaire comportera :<br>     - Un numéro d'identification de l'US avec le format "US-num" (num étant attribué automatique par incrémentation du nombre d'US dans le projet). Ce champ est pré-remplie, mais le PO peut le modifier s'il veut, tant que l'id reste unique.<br>    - Un nom avec un format "En tant que *rôle* je souhaine *Text* afin de *Text* (la partie texte étant ajouté par le product owner à l'écrit et la partie rôle ajouté à l'aide d'un menu déroulant contenant les différents rôles possibles pour ce projet. Le product owner pourra ajouter un rôle grace au bouton "Autre" se trouvant sur le dernier item du menu déroulant. Le rôle sera ajouté pour le projet)<br>    - Une difficulté : un nombre de la suite de fibonacci (menu déroulant)<br>    - Une priorité : Low, Medium, High (menu déroulant) |     2      |   High   |   Sprint 2    |
| US-7  | **En tant que** scrum master, **je souhaite** pouvoir changer l'emplacement d'une US d'un projet à l'aide d'un glissé-déposé, **afin de** changer la planification d'une US.<br>Lorsque le scrum master réalise un mouvement de drag'n'drop sur une US vers un sprint ou vers la zone contenant toutes les US sans sprint, si il drop l'US dans une zone valide alors celle-ci est automatiquement ajouté à la zone, sinon elle retrouve sa place initiale.<br>Il est impossible de changer la planification d'un US si toutes ses tâches liées ne sont pas dans la catégorie TODO. |     3      |  Medium  |   Sprint 2    |
| US-8  | **En tant que** scrum master, **je souhaite** pouvoir démarrer un sprint d'un projet, **afin de** commencer à traiter les US qui le compose. <br>Lorsque le scrum master, clique sur le bouton "Démarrer" d'un sprint celui-ci devient le nouveau sprint actif. Si un sprint est déjà activé alors un message d'erreur s'affichera pour permettre au scrum master, de choisir: soit de ne pas activer le nouveau sprint soit d'arrêter le sprint. <br> Lorsque le sprint est activé il doit apparaitre dans la section "Sprint Actif". |     2      |   High   |   Sprint 3    |
| US-9  | **En tant que** scrum master, **je souhaite** ajouter des membres d’équipe au projet, **afin de** pouvoir planifier la répartition des tâches entre ces différents membres.  Une liste des différents membres du projet se trouvera sur coté droit de la page. Un bouton "Ajouter un membre" se trouvera sous cette liste et permettra d'ouvrir un formulaire d'ajout de membre. Le formulaire permettra de récupérer les informations qui composent un membre:<br>    - Un nom<br>    - Une spécialité: Ce type doit correspondre aux spécialitées renseignées dans les taches (Front/back/bdd…)<br>    - Un statut Junior ou Senior précisant l’expérience de l’utilisateur. |     2      |  Medium  |   Sprint 2    |
| US-10 | **En tant que**  scrum master, **je souhaite** pouvoir créer une planification automatique pour un projet et la visualiser **afin de** m'aider à organiser le projet.<br>Un bouton "Générer la planification prévisionnelle" se trouvera sur le haut de page. Lorsque le scrum master, cliquera dessus un plannification prévisionnelle sera créé et affichée sous forme d'un tableau. Les différentes tâches seront affectées aux différents membres du projet de sorte a optimiser le nombre de jours nécessaires à la réalisation de celles -ci ainsi qu'en favorisant l'attribution des tâches enfonction des spécialités des membres.<br>Cette génération peut être réalisée à différents moments du projet et ne prendra en compte à chaque fois que les tâches n'étant pas déjà à l'état fini (Done). |     5      |  Medium  |   Sprint 2    |
| US-11 | **En tant que** scrum master, **je souhaite** pouvoir affecter les tâches aux membres de mon équipe, en fonction de la plannification prévisionnelle **afin d'** organiser le projet.<br>Un bouton "Affecter" se trouvera sur le haut de la page à côté du bouton "Générer la plannification prévisionnelle". Il permettra d'ajouter aux différentes tâches le membre correspondant dans la plannification prévisionnelle. Si la plannification prévisionnelle n'as pas été générée une popup apparaittra pour prévenir le scrum master, qu'il doit préalablement généré une plannification. |     1      |  Medium  |   Sprint 3    |
| US-12 | **En tant que** scrum master, **je souhaite** pouvoir générer un diagramme de PERT pour un projet **afin de** pouvoir mieux plannifier mon projet.<br>Le scrum master, pourra cliquer sur un bouton "Générer Pert" qui se trouvera sur le haut de la page afin de générer le diagramme de Pert correspondant aux tâches du projet. Le diagramme généré s'affichera dans une pop up qui contiendra deux boutons. Un bouton permettant de fermer la popup et un permettant d'exporter celui ci au format "jpeg". |     5      |   Low    |   Sprint 3    |
| US-13 | **En tant que** scrum master **je souhaite** pouvoir avoir accès à une estimation du temps de réalisation de l'ensemble des tâches **afin de** mieux voir le temps nécessaire à la réalisation du projet.<br>Une zone de la page sera dédiée au "résumé" du projet. Cette zone comportera :<br>    - le nombre total de tâches<br>    - le coût total de toutes les tâches |     1      |   Low    |   Sprint 3    |
| US-14 | **En tant que**  scrum master, **je souhaite** pouvoir indiquer l'absence d’un membre et recalculer automatiquement la planification d'un projet **afin de** mettre à jour la planification et palier aux différents cas de figure.<br>Au niveau de la liste des membres du projet, en cliquand sur le nom d'un membre un menu s'affichera et un bouton "Ajouter une absence" et un bouton "Modifier absence" seront présents. Lorsque le scrum master, clique sur celui d'ajout un formulaire pour signaler l'absence de ce membre appraitra. Il faudra renseigner :<br>    - La date de début de l’absence<br>    - La date de fin de l’absence <br>Quand cette absence est ajoutée, si la plannification prévisionnelle a déjà été générée alors celle-ci est générée a nouveau afin de prendre en compte cette absence sinon, à la prochaine génération elles seront prise automatiquement en compte. Si le scrum master, clique sur le formulaire de modification des absences, une popup contenant les différentes absences enregistrée pour ce membre apparaissent sous forme de liste.<br>Pour chacunes d'elles un bouton "Modifier" et un bouton "Supprimer" apparaitront. Si le scrum master, clique sur "Modifier" il aura le meme formulaire que celui d'ajout qui s'affichera de manière pré-remplie, si il clique sur supprimer l'absence est supprimé. Lorsqu'il clique sur le bouton "Fermer" de la pop up la plannification est automatiquement généré à nouveau. |     3      |  Medium  |   Sprint 3    |
| US-15 | **En tant qu’** utilisateur, **je souhaite** pouvoir ajouter, supprimer, modifier les tâches d'un projet dans la section *Task* **afin de** gérer les tâches. Une tâche est définie par:<br>    - Un identifiant  : généré de manière automatique de manière à ce qu'il soit unique<br>    - Un titre  : chaine de caractère de taille maximum 30 (obligatoire)<br>    - Un chiffrage: chiffrage en heure/homme<br>    - Une description : Chaine de caractère de taille maximum 200<br>    - Une definition of Done : Liste de chaine de caractère de taille maximum 50 (obligatoire)<br>    - Une ou plusieurs dépendances aux autres tâches <br>    - Une ou plusieurs User Story associées<br>    - Un type (Front, Back, Bdd, Design...)<br>    - Un état (To-Do, Doing, Done ) ((To-do par défaut à la création)<br>    - Un membre d'équipe en charge de cet tâche.<br>Tout champs pourra par la suite être modifié (excepté pour l'identifiant), tant que la tâche n'est pas dans l'état DONE .<br>Lors de l’ajout de dépendances ,on doit pouvoir visualiser toutes les tâches pour sélectionner les tâches dont la nouvelle dépend. Le même méchanisme doit être mis en place pour l’ajout d’userStory associée. Lors de l’ajout d’un type, on doit pouvoir seléctionner un type parmis ceux existant, ou en créer un nouveau. |     5      |   High   |   Sprint 2    |
| US-16 | **En tant que** membre du projet, **je souhaite** visualiser les tâches d'un projet en fonction de leur état, sous forme de colonnes  (Une colonne par état de tâche) dans la section *Task* **afin de** pouvoir avoir une vue totale des tâches qui restent à réaliser.<br>On doit pouvoir glisser/déposer une tâche d’une colonne à l’autre afin de changer son état de manière ergonomique |     3      |   High   |   Sprint 2    |
| US-17 | **En tant qu'** utilisateur, **je souhaite** pouvoir valider un élément de la DOD **afin de** mettre à jour l'avancement d'une tâche.<br>Devant la définition d'un élément du DOD, je dois avoir une case que je peux cocher, ou décocher. Un aperçu de l'avancement du DOD doit être disponible sur la tâche.<br> |     3      |  Medium  |   Sprint 2    |
| US-18 | **En tant que** scrum master, **je souhaite** pouvoir attribuer une tâche à un membre de l’équipe, **afin de** faciliter la répartition de celles-ci.<br>Je peux cliquer sur une tâche, et sélectionner dans un menu déroulant un des utilisateurs présents dans le projet ou en ajouter un nouveau. |     3      |   Low    |   Sprint 2    |
| US-19 | **En tant que** membre du projet, **je souhaite** pouvoir afficher les tâches d'un projet en appliquant des filtres dans la section *Tasks* **afin de** facilité la lecture de tâches en particulier.<br>Par exemple, je souhaite ne voire que les tâches associées à au moins une US du sprint X, que les tâches attribuées au membre Y, ... |     2      |   Low    |   Sprint 2    |
| US-20 | **En tant qu'** utilisateur **je souhaite** pouvoir accéder à une vue du sprint actif **afin de** pouvoir gérer l'avancé du projet et de la réalisation des US.  Une section "Sprint Actif" sera crée contenant :<br>     - Le nom du Sprint actif<br>     - La liste des US correspondantes au Sprint actif. Chaque Us étant représentées par leur identifiant et leur nom. Chaque item de cette liste sera cliquable et permettra l'affichage des tâches associées. Elles seront représentées par leur Identifiant et leur titre. Seules tâches non terminées (To-Do ou Doing) seront affichées. Les tâches encore dépendantes de tâches non réalisées seront grisées. Si l'utilisateur passe le curseur dessus une pop-up s'ouvrira et affichera la listes de ces tâches. Celle qui au contraire ne sont plus dépendante de tâhces non réalisées ne sont pas grisées. Cette liste de tâches associées se met à jour automatiquement. |     1      |   High   |   Sprint 3    |
| US-21 | **En tant que** scrum master, **je souhaite** pouvoir clôturer le sprint actif d'un projet **afin de**  l’archiver.<br>Je dois pouvoir cliquer sur le bouton "Terminer" qui ouvrira une pop up de validation. Si certaines US sont encore liées à des tâches non terminées il sera proposé de déplacer ces US dans le sprint suivant. En cas de refus, l'US retourne à l'état non planifiée.<br>L'état des tâches n'est pas modifié par la clôture d'un sprint. Un message disant qu'aucun sprint n'est actif est ajouté. |     2      |   High   |   Sprint 3    |
| US-22 | **En tant qu’** utilisateur, **je souhaite** avoir une section Documentation sur la page d'un projet **afin d’** y poster les liens des différentes documentations (installation et utilisation) de mon projet.<br>La page est composée du menu de navigation sur la gauche et d'une zone de travail. Cette zone comportera différentes sections:<br>    - une section liens : permettant d'ajouter un lien vers une doc grâce à un bouton "ajouter un lien" qui ouvrira un formulaire d'ajout. La liste des liens ajoutées sera une liste de liens cliquables.<br>    - une section texte : permettant à l'utilisateur d'ajouter en format Text la documentation.<br>Un bouton "ajouter post" ouvrivra un formulaire contenant un champs input dans lequel l'utilisateur pourra écrire ce qu'il veut au format MarkDown. La liste des post se fera sous forme de lien cliquable permettant d'ouvrir a nouveau l'édition du post. Les posts pourront être visualisés automatiquement sous cette liste. |     5      |  Medium  |   Sprint 1    |
| US-23 | **En tant qu’** utilisateur, **je souhaite** pouvoir ajouter, modifier, visualiser les tests **afin d'** avoir un suivi complet des tests de mon projet. Une liste des différents tests sera présente. <br/>Chaques tests sera défini par :<br/>    - Un nom (obligatoire)<br/>    - Une description (obligatoire)<br/>    - La date d’exécution du test<br/>    - Le résultat de la dernière exécution du test <br/>    - Pour chaque test, on doit pouvoir cliquer sur ‘Valider’ ou ‘Refuser’, ce qui met à jour l’état du test et sa date d’exécution. (obligatoire)<br/>Un indicateur se trouvant sur le haut de la page doit nous permettre de savoir où en est l’exécution des tests (pourcentage de test réussis) Un bouton "Ajouter Test" se trouvera au dessus de la liste des tests et permettra d'ouvrir un formulaire d'ajout. |     2      |   Low    |   Sprint 1    |
| US-24 | **En tant qu'** utilisateur, **je souhaite** pouvoir importer un fichier récapitulatif  des tests exécutés pour un certain projet **afin de** pouvoir ajouter automatiquement les tests depuis ce fichier. Dès qu'une changement est effectué, l'ancien résultat est archivé.<br/>Sur la page des Tests un bouton "Importer" se trouvera à droite sur le haut de la page. Ce bouton permettra d'ouvrir un formulaire dans lequel l'utilisateur pourra importer un fichier de test. <br>Si le fichier est dans le format adéquat (format stipulé dans la section "How to" dans la partie "Les tests" )  les nouveaux tests seront ajoutés et/ou les tests existants seront mit à jour. Le fichier est ensuite détruit. <br/>Je dois également pouvoir envoyer ce fichier sans passer par l'interface graphique, en envoyant simplement mon fichier au serveur. |     5      |   High   |   Sprint 1    |
| US-28 | **En tant qu'** utilisateur, **je souhaite** pouvoir accéder au tests archivés, **afin de** suivre leur évolution.<br>Les archives doivent être listées par ordre chronologique. En clique sur une archive, on doit voire les tests qui compose l'archive, leur résultat avec les dates d'exécutions. |     2      |  Medium  |   Sprint 3    |
| US-25 | **En tant que** scrum master, **je souhaite** avoir une section release **afin d’** y publier les liens des différentes releases d'un projet.<br>Cette page sera composée, du menu de navigation à gauche et d'une zone de travail. Sur cette zone se trouvera une liste des différentes release réalisées. Elles seront affichées dans l'ordre de créations (de la plus ancienne à la plus récente).<br>Elles seront définies par leur nom et leurs dates de créations.  Le scrum master pourra jouter une nouvelle release en appuyant sur le bouton "Ajouter une release" en haut a droite de la zone. L'appuie sur ce bouton permettra l'ouverture d'un formulaire qui permettra de définir une release avec :<br>    - Une archive zip.<br>    - Un numéro de version au format SemVer (majeur.mineur.patch)<br>    - Date de sortie de la release<br>    - US implémentées dans la release : checkbox de toutes les US. |     3      |   High   |   Sprint 3    |
| US-26 | **En tant qu’** utilisateur, **je souhaite** pouvoir visualiser le burndown chart correspondant à mon projet **afin d’** avoir une meilleure vue de l’avancement. <br>  Cet accès se fait depuis la section Statistics. Le graphique se mettra à jour automatiquement dès qu'un changement d'état a lieu dans une tâche. |     5      |  Medium  |   Sprint 3    |
| US-27 | **En tant qu'** utilisateur, **je souhaite** pourvoir générer un graphique montrant la *vélocité*  d'un projet, **afin d'** avoir une meilleure vue de l'avancement. Ce graphique doit se générer dans la section Statistics. |     3      |  Medium  |   Sprint 3    |

