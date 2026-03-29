# LibraryManagementSystem
Java library management system with OOP principles

Es soll ein Bibliothekssystem abgebildet werden, in dem Bücher und Personen verwaltet werden. Ein Buch hat einen Titel, einen Autor, ein Genre und einen Zustand. Personen haben einen Vor- und Nachnamen, ein Geburtsdatum sowie eine Adresse. Ausgeliehene Bücher müssen innerhalb eines Monats zurückgegeben werden. Wenn ein Buch nicht pünktlich zurückgebracht wird, fällt eine Gebühr pro angefangene Woche an. Sollte ein Buch beschädigt zurückgegeben werden, wird eine zusätzliche Gebühr fällig.
Die Geschäftslogik umfasst dabei folgende Punkte:

- Das Verwalten von Büchern (Hinzufügen, Bearbeiten und Löschen von Büchern).
- Das Verwalten von Personen (Hinzufügen, Bearbeiten und Löschen von Personendaten).
- Das Ausleihen von Büchern an Personen.
- Das Zurückgeben von Büchern einschließlich der Berechnung von potentiellen Strafgebühren bei verspäteter Rückgabe oder Beschädigung des Buches.
- Offene Gebühren werden bei der betreffenden Person erfasst und verwaltet.
- Bücher und Personen sind entsprechend ihres Titels oder Namens lexikographisch vergleichbar.
- Der Filter für Bücher soll folgende Kriterien erfüllen:

* Genre
* Autor
* Titel (enthält Textbausteine)


- Der Filter für Personen soll folgende Kriterien erfüllen:

- Ausgeliehene Bücher
- Offene Gebühren in Höhe von (von – bis)
- Anzahl der ausgeliehenen Bücher (von – bis)


- Es ist eine geeignete String-Repräsentation der Personenkartei und der Leihliste umzusetzen.