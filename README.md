# tp rsx bezzazi

## TP 3 : Serveur TCP
`Author: Abir Bezzazi`

### Exo 1

Q1. Quelles sont les étapes du traitement d'une requête ?

    Pour traiter une requete, on commence par créer une instance de la classe `ServerSocket`, puis on boucle `socket.isClosed()`, on récupère le client connecté avec la méthode `accept()`. En suite on imprime un message dans le flux de sortie du client avec la méthode `print()` d'une socket client qui est obtenu par le retour de la méthode accept() de la socket server. Finalement on ferme la socket du client avec la méthode `close()`.


Q2. Quelles sont les exceptions à traiter pour chaque étape de votre programme ? S'assurer que chacune est bien affiché à l'administrateur du serveur.

    L'exception à traiter est `IOException`. Cette exception sera traitée lors de la création de la socket du serveur (`ServerSocket`)
    et aussi lors de l'ouverture, écriture et fermeture du fichier .log qui contient les traces de chaque connexion.


Q3. Une fois le programme réalisé, comment en tester son bon fonctionnement ?

    Pour tester le programme nous allons utiliser le protocole Telnet. L'instruction qui nous permet de tester la connectivité avec le serveur est :
```bash
telnet <adresse_ip_serveur> <port_seveur>
```
    Le message `"Bienvenue sur mon serveur et au revoir"` sera imprimé dans la sortie du client, et dans le serveur on aura l'adresse ip du client, date et l'heure actuelle. 


Q4. S'assurer que le programme fonctionne en boucle, c'est-a-dire, qu'il traite plusieurs requêtes les unes après les autres. Ce faisant, le serveur peut recevoir une succession de requêtes de différents clients : comment garder la trace toutes les connections ayant eu lieu ?

    J'ai choisi de garder les traces dans un fichier .log, Java nous permet l'ouverture et l'écriture sur un fichier avec la classe `java.io.FileWrite`.
    On récupère l'adresse ip du client avec la méthode `getInetAddress()` et la date actuelle avec la classe `java.util.Date`
    sans oublier de le mettre dans le bon format avec la classe `java.text.SimpleDateFormat`, pour l'heure
    *hh:mm:ss* et pour la date *dd-MM-yyyy*. Puis on ajoute ça dans le fichier log avec la méthode `write()`.

    Toutes les traces se trouvent dans le fichier `serveur_exo1.log`.

### Exo 2

Q1. Comment et quand créer un nouveau Thread pour un client dans votre programme ?

    Un nouveau Thread sera crée lors de chaque nouvelle connexion. Ma classe MyRunnabe qui hérite de la classe Thread est créée pour gérér ça.

Q2. Quelles sont les primitives permettant de recevoir des chaines de caractères sur une Socket ?

    La classe OutputStream contient 2 méthodes qui nous permettant de gérér la réception des chaines de caractères, la méthode write() pour l'ecriture sur la sortie standard d'une socket et la méthode flush() pour vider le flux de sortie et force l'écriture de tous les octets de sortie mis en mémoire tampon.

Q3. Comment faire pour retransmettre ces chaines vers tous les utilisateurs connectés ? Comment partager en Java, au niveau du constructeur de chaque Thread, une structure globale visible et mise à jour par tous lesThreads ?

    On crée une liste qui est donnée en paramètre dans notre classe MyRunnable qui hérite de Thread. On boucle sur cette liste et on impirme sur la sortie de chaque socket un message avec le compteur qui lui même est donnée en paramétre de la classe MyRunnable et qui est incrémenté avant chaque nouvelle connexion.

Q4. S'assurer que le programme fonctionne en boucle, c'est-a-dire, qu'il traite plusieurs requêtes les unes après les autres. Ce faisant, le serveur peut recevoir une succession de requêtes de différents clients : comment garder la trace toutes les connections ayant eu lieu ?

    Pour garder la tarces de toutes les connections on suit la même procédure que celle de la Question 4 de l'exercice 1, les logs seront stockés dans un fichier server_exo2.log.

Q5. Lorsqu'un client telnet quitte normalement (CTRL-D), ou alors est intempestivement arrêté, comment s'assurer du bon fonctionnement de l'application.

    Aprés l'ajout du client à la liste des clients, on filtre cette liste avec le prédicat de !s.isClosed(). Comme ça, quand le thread sera crée il prendra en pramètre la liste filtrée. Comme ça la methode sendToAll enverra le message qu'aux sockets qui sont toujours connectées.

