# Dungeon Crawler

Textbaserat spel skapat i Java, går ut på att spelaren går mellan rum. 
I rummen möts man av fiender, skatter eller "potions" som kan rädda ditt liv.  

Spelet är beslutsbyggande, i varje rum du står inför scenarion som du ska ta ställning till.
Det gör man genom att ta beslut i ja och nej frågor. Y/N, är förkortning för yes or no.

Strukten är följande:
- `game` (spelstart och loop)
- `entities` (Player, Enemy, Item)
- `map` (Room och kopplingar mellan rum)


Testning: 
- Korrekt förflyttning mellan rum.
- Testar hur HP påverkas efter träff med monster. 
- //Test av potion, se om HP-värdet höjs när spelare fångar potion.