### LoginInformation
- User:admin
- Lösen:admin123
- Som oinloggad så omdirigeras du automatiskt till login.


## **Alla tillgängliga endpoints:**
```
- GET    /api/events                    - Lista alla events
- GET    /api/events/{id}               - Hämta ett event
- GET    /api/events/upcoming           - Kommande events
- POST   /api/events                    - Skapa nytt event
- PUT    /api/events/{id}               - Uppdatera event
- DELETE /api/events/{id}               - Ta bort event
```
```
- GET    /api/participants              - Lista alla deltagare
- GET    /api/participants/{id}         - Hämta en deltagare
- GET    /api/participants/patrol/{id}  - Deltagare i en kår
- POST   /api/participants              - Skapa deltagare
- PUT    /api/participants/{id}         - Uppdatera deltagare
- DELETE /api/participants/{id}         - Ta bort deltagare
```
```
- GET    /api/patrols                   - Lista alla scoutkårer
- GET    /api/patrols/{id}              - Hämta en scoutkår
- POST   /api/patrols                   - Skapa scoutkår
- PUT    /api/patrols/{id}              - Uppdatera scoutkår
- DELETE /api/patrols/{id}              - Ta bort scoutkår
```
```
- GET    /api/allergens                 - Lista alla allergener
- GET    /api/allergens/{id}            - Hämta en allergen
- GET    /api/allergens/critical        - Kritiska allergener
- POST   /api/allergens                 - Skapa allergen
- PUT    /api/allergens/{id}            - Uppdatera allergen
- DELETE /api/allergens/{id}            - Ta bort allergen
```
```
- GET    /api/registrations             - Lista alla registreringar
- GET    /api/registrations/{id}        - Hämta en registrering
- GET    /api/registrations/event/{id}  - Registreringar för event
- POST   /api/registrations             - Skapa registrering
- PUT    /api/registrations/{id}/confirm - Bekräfta registrering
- PUT    /api/registrations/{id}/cancel  - Avboka registrering
- DELETE /api/registrations/{id}        - Ta bort registrering
```