## BDAProject

### N'oubliez pas de citer les sources

Sujet: Base de données Fédérées

Participants :
- Nour Romdhane   
- Hassan El Omari Alaoui
- Mohammed Bouluad
- Sylvain Besnard
- Julien Marchais
- Julien Marcou
- Dan Seeruttun--Marie 


**Présentation LibreOffice :**
Dossier Documents\Presentation\presentation1.odt

**Rapport Overleaf :**
https://www.overleaf.com/3740559stsxny#/10713357/

Contenu des bases de données.
Données à reformater en cas de test !
MongoDB:
- EcoleMongoDB
  - IdEcole key
  - Nom

SQL:
- Formation
  - IDFormation key
  - Nom
  
- Personne
  - id key
  - Nom
  - Prenom
  - IDFormation foreign key
  - IDEcole
  
- Liaison
  - IDFormation
  - IDEcole