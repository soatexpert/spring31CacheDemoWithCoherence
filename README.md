Source code pour illustrer l'article de Lo�c Le Doyen "Spring 3.1 : Utiliser l�abstraction de cache � 2 le retour" http://blog.soat.fr/?p=9237

La d�pendance maven coherence n'est pas sur le central maven (car pas public), il faut

1) La t�l�charger ici http://www.oracle.com/technetwork/middleware/coherence/downloads/index.html)
2) Puis l'installer manuellement comme �a :
mvn install:install-file -DgroupId=tangosol  \
-DartifactId=coherence  \
-Dversion=3.7.1  \
-Dfile=coherence.jar  \
-Dpackaging=jar \
-DgeneratePom=true


