# Ridy

Ridy is een androidapplicatie ontworpen om exact bij te houden hoeveel een rit met de auto/fiets u kan opbrengen terwijl het rekening houdt met de kilometervergoeding van uw werk en/of brandstofkosten. De applicatie bepaalt uw locatie adhv de Google API en biedt een mooie UI waar u zich op kan zien bewegen. Ook kan u een overzicht krijgen van al uw ritten met bepaalde data zoals met welk voertuig u de rit heeft gemaakt en hoeveel geld u heeft bespaard.

## Het opstellen en opstarten van de applicatie

Deze instructies zullen u helpen bij het opstarten van de applicatie lokaal op uw emulator of op uw persoonlijk android device

### Voorwaarden

Voor het downloaden en installeren van de repository is het noodzakelijk een git installatie te hebben. Deze is downloadbaar op [Github](https://git-scm.com/downloads)

### Stap 1 - Clonen repository

Indien Git geïnstalleerd is zal de repository met het project moeten gecloned worden. Hiervoor moet er in de command line het volgende commando uitgevoerd worden

```
git clone https://github.com/HoGentTIN/native-apps-1-android-creative-app-ciwie36963.git
```

### Stap 2 - Sync gradle file with project and build project

Na het clonen van de repository is het vereist om de gradle file the synchroniseren met het project. Hiervoor moet er vanuit de command line eerst genavigeerd worden naar de project-folder met het volgende commando:

```
cd native-apps-1-android-creative-app-ciwie36963/
```

Vervolgens kan men de gradle file synchroniseren met het project adhv de volgende knop.

![snip20181216_1](https://user-images.githubusercontent.com/6177799/50057937-ef588980-0171-11e9-9bb1-e98bcfc0db07.png)

Hierna kan men het project builden adhv de volgende knop

![snip20181216_4](https://user-images.githubusercontent.com/6177799/50057949-026b5980-0172-11e9-9cd4-c4892a65562e.png)

### Stap 3 - Opstarten android app

Om de applicatie op te starten wordt klikt men op de volgende knop

![snip20181216_5](https://user-images.githubusercontent.com/6177799/50057950-026b5980-0172-11e9-82d7-c39a4e689890.png)

Hier heeft men de keuze om een geconnecteerd apparaat te kiezen of één van de beschikbare emulators. Indien er nog geen emulator op de computer staat kan men kiezen voor de optie "Create New Virtual Device".

![snip20181216_8](https://user-images.githubusercontent.com/6177799/50057951-026b5980-0172-11e9-8599-0abaa1c1eec5.png)

## Mappenstructuur

Alle code van het project is te vinden in de folder `app/src/` wanneer men zich in de hoofddirectory bevindt. In deze folder is de volgende onderverling gemaakt: 

| Map         | Functionaliteit                                              |
| ----------- | :----------------------------------------------------------- |
| androidTest | Deze map bevat alle testen die zijn uitgevoerd om te kijken of de applicatie 100% functioneel is. |
| main        | Zie volgende tabel.                                           |

Wanneer men zich in de main map bevindt komt men bij de volgende mappenstructuur.

| Map    | Functionaliteit                                              |
| ------ | ------------------------------------------------------------ |
| java   | Deze map bevat de businesslogica/het hart van de applicatie. Hierin staat alle code die de applicatie functioneel maakt. |
| res    | Deze map bevat de layout files voor de applicatie. Deze files presenteren maw de applicatie. |

Men kan nog doorgaan naar `java\com\example\alexander\ridy`, hier ziet men de volgende mappenstructuur.

| Map         | Functionaliteit                                              |
| ----------- | :----------------------------------------------------------- |
| Model       | Deze map bevat de logica van de applicatie.                  |
| View        | Deze map bevat bestanden die zorgen voor de interface van de applicatie. |
| ViewModel   | Deze map bevat modellen waar tijdelijk data kan in opgeslaan worden. |

## Dokka documentatie
Indien men een uitgebreide documentatie wil zien van elke functie/klasse/variabele/.. in het project kan men terecht in de map `doc` in de root directory. 
De documentatie kan opnieuw gegenereerd worden door de volgende stappen te ondernemen:

### Stap 1 - Ga naar de Help tab en selecteer find action
![snip20181226_14](https://user-images.githubusercontent.com/6177799/50427700-4a475a80-08af-11e9-82e3-530834dba204.png)

### Stap 2 - Voer het commando dokka uit om de documentatie te genereren
![snip20181226_16](https://user-images.githubusercontent.com/6177799/50427701-4adff100-08af-11e9-8570-882070ab0056.png)

De documentatie bevindt zich dan in de map `app\build\javadoc\app\`

## Pictures
![selectvehicle](https://user-images.githubusercontent.com/6177799/50425796-d80a5200-087d-11e9-986d-a43a9f9542cb.PNG)
![vehicledetails](https://user-images.githubusercontent.com/6177799/50425797-d80a5200-087d-11e9-988f-a222feae4f17.PNG)
![ride](https://user-images.githubusercontent.com/6177799/50425798-d8a2e880-087d-11e9-94a0-96608071b623.PNG)
![journal](https://user-images.githubusercontent.com/6177799/50425799-d8a2e880-087d-11e9-84c9-eb4d3ce60dcd.PNG)
![more](https://user-images.githubusercontent.com/6177799/50425800-d8a2e880-087d-11e9-8f6b-dd00483ec601.PNG)

## Author

Alexander, derdejaars student Toegepaste Informatica - Programmeren - Mobiele Applicaties

- Alexander Willems - [ciwie36963](https://github.com/ciwie36963)

## APK file

Link to apk file: https://ufile.io/wm0pn

(The link is valid for 30 days)

## License

Dit project is eigendom van Alexander Willems - 2019 van HoGent.
