Voici un modèle de **README** pour le TP décrivant votre projet **Android de consultation géographique**.

---

# Application Android - Consultation Géographique

## 📋 **Description du Projet**

Ce projet est une application Android permettant de :
- Visualiser des informations sur les différents pays du monde.
- Afficher une carte Google Maps pour un pays sélectionné.
- Internationaliser l'interface en proposant plusieurs langues (anglais et français).
- Récupérer dynamiquement des données via une API REST.

---

## 🛠️ **Fonctionnalités Principales**

1. **Écran de connexion** :
   - Une page d'accueil avec saisie de login et mot de passe.
   - Vérification basique du mot de passe ("JimMorrisson").

2. **Internationalisation** :
   - L'application propose une version anglaise (par défaut) et une version française.

3. **Liste des pays** :
   - Une liste défilante utilisant `RecyclerView` pour afficher les informations suivantes :
     - Nom du pays (commun et officiel)
     - Capitale
     - Monnaie (nom, trigramme et symbole)
     - Latitude et longitude
     - Drapeau du pays

4. **Récupération des données** :
   - Appel API REST via la bibliothèque **OkHTTP** pour obtenir des données des pays depuis [restcountries.com](https://restcountries.com).

5. **Affichage des cartes** :
   - Intégration de Google Maps pour afficher un marqueur sur le pays sélectionné.

---

## 📱 **Technologies Utilisées**

- **Langage** : Java (Android API 26+)
- **IDE** : Android Studio
- **Bibliothèques** :
   - **OkHTTP** : pour les requêtes réseau.
   - **Glide** : pour afficher les drapeaux depuis les URL.
   - **RecyclerView** : pour gérer l'affichage dynamique de la liste.
   - **Google Maps API** : pour l'affichage des cartes.
   - **SwipeRefreshLayout** : pour implémenter le **Pull-to-Refresh**.

---

## 🚀 **Installation et Exécution**

1. **Cloner le projet** :
   ```bash
   git clone <URL-DU-REPO>
   cd consultation-geographique
   ```

2. **Importer le projet** dans **Android Studio**.

3. **Configurer la clé Google Maps** :
   - Obtenez une clé API sur [Google Cloud Console](https://console.cloud.google.com).
   - Ajoutez cette clé dans `res/values/google_maps_api.xml` :
     ```xml
     <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">YOUR_API_KEY</string>
     ```

4. **Exécuter l'application** :
   - Lancer l'application sur un émulateur ou un appareil réel configuré.

---

## 🌍 **API Utilisée**

- **RestCountries API** : `https://restcountries.com/v3.1/all`

---

## 📸 **Captures d'Écran**

1. **Écran de connexion**  
   ![Login](placeholder_login.png)

2. **Liste des pays**  
   ![RecyclerView](placeholder_list.png)

3. **Carte Google Maps**  
   ![Google Maps](placeholder_maps.png)

---

## 💡 **Améliorations Futures**

- Ajouter une gestion des erreurs réseau plus robuste.
- Optimiser les performances pour les grandes quantités de données.
- Intégrer des fonctionnalités de recherche et de filtrage.

---

## 👨‍💻 **Auteur**

- **Nom** : [Votre nom]  
- **Contact** : [Votre e-mail ou GitHub]

---

**Bon développement ! 🚀**

---
