Zadanie 1 - Rozpoczynanie gry;

Po stronie klienta i po stronie servera rozpoczyna się gra.

AC:

- [x] Serwer utrzymuje połączenie do dwoch klientow
- [x] Serwer moze wysłac event do konkretnego gracza
- [] Serwer posiada logike do rozpoczecia gry
- [x] Client umie podłączyc sie do serwera
- [] Client rozpoznaje eventy potrzebne do rozpoczecia gry tj ConnectionAcceptEvent, CardEvent
- [x] Client po nawiazaniu połączenia dostaje event ConnectionAcceptEvent z którego wynika jakie ma id
- [] Client po dostaniu eventu CardEvent wyswietla nowa kartę jesli owner jest rowny jego id są to jego kart w p.p
  gracza przeciwnego
- [] Karta private gracza przeciwnego jest odwrócona.
- [x] Po stronie klienta nasłuch na eventy powinien znajdowac sie w innym wątku niż wątek obsługujcy GUI aby nie
  blokowały się nawzajem
  // java use a separate thread to show a swing java gui
  // musi byc komunikacja obiekt połączenia do gui zeby jak polaczenie odbierze obiekt event mogl wywolac metode hanlde
  event na obiekcie gui
  //
  class ClientConnection extends Thread {}
  class MainGUI extends Thread {}
  var gui new MainGUI();
  var connection = new ClientConnection(gui);

gui.start()
connection.start()

w connection jak przyjdzie event to gui.handleEvent(event)
gdzie while true () zeby sie aplikacja nie zakonczyla
