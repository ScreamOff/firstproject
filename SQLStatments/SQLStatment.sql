CREATE  TABLE Gracze(
                        id_gracza INTEGER CONSTRAINT id_gracz_pk primary key autoincrement ,
                        haslo VARCHAR,
                        nazwa VARCHAR,
                        pieniadze INT DEFAULT 1000
);
CREATE  TABLE CzarnaLista(
                        id_gracza INTEGER CONSTRAINT id_gracz_pk primary key autoincrement ,
                        nazwa VARCHAR
);
--triger zeby nie mozna bylo dodawac graczy z czarnej listy
CREATE TRIGGER SprawdzCzarnaListe
    BEFORE INSERT ON Gracze
    FOR EACH ROW
    WHEN EXISTS (SELECT 1 FROM CzarnaLista WHERE nazwa = NEW.nazwa)
BEGIN
    SELECT RAISE(ABORT, 'Gracz jest na czarnej liście. Nie można go dodać do tabeli Gracze.');
END;
--triggery zeby automatycznie dodawalo do czarnej listy przy wywaleniu z kasyna
CREATE TRIGGER DodajDoCzarnejListy
AFTER DELETE ON Gracze
FOR EACH ROW
BEGIN
    INSERT INTO CzarnaLista (nazwa) VALUES (OLD.nazwa);
END;

