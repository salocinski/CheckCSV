# CheckCSV
Envoi de script SQL via un fichier CSV

/!\ L'application s'utilise avec un serveur local pour une utilisation avec un serveur distant, vous devez modifier les sources et
recompiler pour que l'information soit correcte /!\

Cette application permet de lancer un script SQL personnalisé via un fichier .csv.

L'application est capable de parser un fichier modèle .csv pour en ressortir des informations personnalisées.

Un modèle (disponible sur le dépot) est défini pour la création d'une base de donnée. 

Un second modèle (disponible sur le dépot) est défini pour la création d'une table avec un champ. (travail en cours pour la création avec plusieurs champs).

Le respect de la structure des fichiers d'exemple permet à l'application de parcourir le fichier et mettre en correspondance les intitulés de colonnes et les variables insérées dans les requetes SQL. S'il y a correspondance, les requetes SQL seront personnalisées selon les informations indiquées sous chaque intitulé de colonnes.

Je reste à disposition pour toute idées d'améliorations.
