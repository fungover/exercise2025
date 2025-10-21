Del 1 – Manuell konstruktorinjektion
Manuellt upplägg med hjälp av konstruktor, Dependency Injection, utan något ramverk.

Del 2 – Egen DI-container
DI-container (klassen SimpleContainer) har implementerats. Visar hur DI-ramverk fungerar 
men klassen används inte utan finns kvar i projektet som referens.

Del 3 – Weld (CDI)
Använder Weld för att hantera injektion automatiskt. Konstruktorer är markerade med @Inject, 
och en beans.xml-fil under META-INF aktiverar CDI. Vid körning startas applikationen via Weld och 
injicerar automatiskt alla beroenden.