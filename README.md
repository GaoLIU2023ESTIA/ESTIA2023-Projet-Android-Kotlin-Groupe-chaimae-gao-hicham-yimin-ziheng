# projet-groupe_ziheng_hicham_gao_yimin_chaimae
## 1 Contributeurs
Ziheng WANG  
Gao LIU  
Yimin CAO   
Hicham FAIDI   
Chaimae SEGHIR  
## 2 Responsable
EDOUARD Amosse
## 3 Le sujet
Le but de l'application c'est la gestion des tâches, elle permet d'ajouter une tâche, lister toutes les tâches, terminer une tâche, rouvert une tâche et supprimé une tâche.  
Elle permet aussi aux utilisateurs d'utiliser l'application en locale au cas où il n'y a pas de connexion internet, et lors de la connexion elle permet aux utilisateurs de confier des tâches entre eux, et cela grace à la **synchronisation**  quand l'utilisateur est connecté à l'internet.
**Structure d'une tâche**
```json
{
        "_id": "6227e16b5869760016ae3f80",
        "task": "Fix Sink",
        "description": "This sink has been broken for too long now, it's time to fix it",
        "requested_by": "John",
        "assignee": "Doe",
        "due_date": "2021-03-09 10:00:00"
}
```
## 4 Fonctions réalisée
### Partie 1 - API REST.
  - Ajouter une tâche
  - Lister toutes les tâches
  - Terminer une tâche
  - Réouvert une tâche
  - Supprimer une tâche
### Partie 2 - Gestion d'une base de données locales
- Sauvegarder les tâches récupérées du serveur dans une base de données locales (Room)
- Si internet, effectuer les toutes les opérations en ligne et maintenir la base en local à jour
- Si hors connexion, utiliser uniquement les données en locales
- Quand la connexion internet est rétablie, synchroniser les opérations locales avec celles sur le serveur.
### Partie 3 - Créer des tests unitaires, d'intégrations et instrumentaires
- Faire des tests instrumentaires sur moins 3 cas d'utilisation
- Faire des tests d'intégration sur la base de données
## 5 Démonstration de L'App
l'ajout de la date pour une tâche
![MicrosoftTeams-image (2)]( https://user-images.githubusercontent.com/78562517/204643440-1bb03fc1-c87d-477c-85b9-320b890c8537.png )
La liste des tâches
![MicrosoftTeams-image (3)](https://user-images.githubusercontent.com/78562517/204643446-96dbd2fb-0ffe-4275-8006-40f7ee959686.png )
La liste des tâches avec une tâche fini
![MicrosoftTeams-image (1)](https://user-images.githubusercontent.com/78562517/204643450-c4da160f-6670-433e-bab9-574528fc5b81.png )
Le formulaire de création d'une tâche
![MicrosoftTeams-image (4)](https://user-images.githubusercontent.com/78562517/204643455-a8914e76-f0aa-4f54-93c4-04b0d2602554.png )
## 6 Rest à faire
on a pas de temps de finir la partie Bonus
- Partager les taches par Whatapp Facebook etc. 
- afficher une notification quand des nouvells taches sont crées
