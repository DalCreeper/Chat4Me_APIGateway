# Chat4Me APIGateway
Progetto di esercitazione sulla comunicazione tra API.

Il sistema si articola in tre servizi principali: l’API Gateway, il Servizio di Autenticazione e il Servizio di Messaggistica, ciascuno con un ruolo chiave.

Vedi anche:
- [https://github.com/DalCreeper/Chat4Me_MessagingService]
- [https://github.com/DalCreeper/Chat4Me_AuthService]

---

## Servizio di gateway
L’API Gateway rappresenta il punto di ingresso centralizzato per tutte le richieste, gestendo il routing verso gli altri servizi e applicando le misure di sicurezza tramite l’uso di token JWT.
È il fulcro che connette il servizio di autenticazione e quello di messaggistica, garantendo che solo le richieste autorizzate vengano inoltrate.

---
