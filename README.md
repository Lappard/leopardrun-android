#Struktur
<img align="left" width="160" src="https://raw.githubusercontent.com/Lappard/leopardrun-android/master/doc/overview.png">
##Start der App
In libGDX gibt es unter Android eine Klasse "AndroidApplication", die von Activity erbt. So wird beim Starten der App ein Objekt dieser Klasse als MainActivity genutzt, die dann ein Objekt einer Klasse erzeugt, die das Interface "ApplicationListener" implmenetiert. Bei uns ist dies die Klass "LeopardRun".
LeopardRun enthält eine Liste von IScreens. IScreen ist ein Interface, dass das Interface Screen von libGDX um die Methode "setActive" erweitert, damit Transitionen zwischen einzelnen Screens realisiert werden kann. Dafür muss nämlich gewährleistet werden, dass ein Screen zwar angezeigt, aber die Logik nicht ausgeführt wird.

##Screens
###Menüs
Alle Screens, die ein Menü anzeigen, erben von MenuScreen. Hier haben wir Helferfunktionen zum einfachen Erstellen von Buttons und Labels implementiert, die dann von den Subklasse benutzt werden. Außerdem enthält MenuScreen bereits eine Table, die über die komplette Größe des Screens reicht und in die Buttons und Labels eingefügt werden können.
###GameScreen
Im GameScreen findet die meiste Logik des Spiels statt. Er enthält eine Stage von Scence2D, in der die Actors (Player, Ghost, FireWall, Feather, Coin, Obstacles sowie der Hintergrund) eingefügt werden. Außerdem besitzt er eine Instanz der abstrakten Klasse LevelCreator.
##Levelgenerierung
Je nach dem, ob man ein neues Spiel oder eine Challenge startet, wird dem GameScreen ein anderer LevelCreator übergeben. Bei neuen Spielen ist dies eine Instanz der Klasse NetworkLevelCreator, der die Leveldaten vom Server abfragt. Spielt man eine Challenge, wird ein GhostLevelCreator übergeben, der die Leveldaten der Challenge enthält und anhand dieser die Actors erstellt.
Der GameScreen fragt am Anfang und immer dann, wenn das Obstacle, das am weitesten rechts ist (also die höchste X-Koordinate besitzt) zwei Bildschirmlängen oder näher entfernt vom Spieler ist, beim entsprechenden LevelCreator neue LevelDaten ab, in dem er die Methode "requestLevelData" aufruft. Da die Generierung asynchron erfolgen kann (wenn die Daten vom Server stammen) gibt es hier keinen direkten Rückgabewert. Viel mehr wird ein Event ausgelöst, für das sich der GameScreen subscribed hat.
##EventSystem
Um die asynchronen Aufgaben wie die Netzwerkkommunikation übersichtlicher zu handhaben, haben wir das [EventBusSystem Otto](https://github.com/square/otto). Bei diesem kann man über Annonations (@Subscribe) Methoden einer Klasse für Events registrieren. Anhand der Parametertypen entscheidet Otto dann, welche Events empfangen werden.
##Manager
Ressourcen(Grafiken, Fonts, Sounds) werden zentral über das Singleton AssetManager geladen und verwaltet. Sounds können durch das Singleton SoundManager abgespielt und pausiert werden. Die Netzwerkkommunikation läuft komplett über das Eventsystem und wird vom Networkmanager organisiert.