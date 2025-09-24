# Reflektion över övningen.

## Varför föredra constructor injection över field och setter injection?

- Constructor injection säkerställer att en klass inte kan skapas utan sina beroenden
- Detta gör att beroendena är immutable och gör klassen enklare att testa (mocka)

## Vilka problem uppstod med egen DI-container som Weld löste automagiskt?

- Manuella registreringar av bindings, exempelvis MessageService -> EmailMessageService
- Implementera reflection för att hitta constructor och dess parametrar
- Lösa beroenden rekursivt vilket var väldigt komplicerat. (Tack AI).
- Hantera circular dependencies för att undvika stack overflow
- Hantera olika fel, exempelvis om det saknades konstruktorer.

## Vad Weld löste automagiskt?

- Alla beans hittades genom class path scanning (Vi slapp själva manuellt registrera dem)
- CDI hanterade hela livscykeln från skapande, caching och scopes.
- Löste problem med qualifiers när flera implementationer av ett interface fanns.

## Hur påverkar scopes objektens livslängd?

- @ApplicationScoped: En enda instans skapas under hela applikationens livslängd.
- @Dependent: Default scopet, där en ny instans skapas varje gång beanen injiceras.
- @Singleton: Funderar nästan som @ApplicationScoped 