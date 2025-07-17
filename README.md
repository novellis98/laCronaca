# La Cronaca

## Descrizione

Applicazione monolitica sviluppata in **Java** con **Spring Boot** e **Thymeleaf**, pensata per gestire articoli con quattro ruoli utenti distinti:

- **Admin**: gestisce utenti e ruoli, può cambiare ruoli e invia email di notifica al cambio ruolo.
- **Revisore**: valuta gli articoli scritti dai writer, può accettarli o rifiutarli.
- **Writer**: crea articoli con titolo, sottotitolo, descrizione e foto opzionale (uploadata su Supabase).
- **User**: può solo visualizzare gli articoli pubblicati.

La sicurezza è gestita con **Spring Security**, che limita l’accesso alle funzionalità in base al ruolo.

---

## Caratteristiche principali

- Sistema di autenticazione e autorizzazione con ruoli e permessi.
- Creazione, revisione e pubblicazione degli articoli.
- Upload opzionale di immagini degli articoli tramite integrazione con **Supabase**.
- Invio automatico di email per notifiche cambio ruolo utente.
- Ricerca globale degli articoli filtrata per titolo, sottotitolo o descrizione.
- Frontend server-side con **Thymeleaf**.
