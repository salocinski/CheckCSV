# CheckCSV
Envoi de script SQL via un fichier CSV

/!\ L'application s'utilise avec un serveur local pour une utilisation avec un serveur distant, vous devez modifier les sources et
recompiler pour que l'information soit correcte /!\

Cette application permet de lancer un script SQL personnalisé via un fichier .csv.

Dans la v1.0, l'application est capable de parser un fichier .csv pour en ressortir des informations personnalisées.

Un modèle de fichier .csv pour la création d'une base de donnée et disponible sur le dépot. Le respect de la structure de ce fichier
d'exemple permet à l'application de parcourir le fichier et mettre en correspondance les intitulés de colonnes et les variables insérées
dans la requete SQL. S'il y a correspondance, la requete SQL sera personnalisée selon les informations indiquées sous chaque intitulé de
colonnes.

Je reste à disposition pour toute idées d'améliorations.
