# Porsche Retirement Widget v7

Wersja v7 bazuje bezpośrednio na v6. Obie bitmapy cluster_40.png i cluster_60.png pozostają bez zmian.

## Zmiany
- Większa i przesunięta niżej liczba dni w środkowym wyświetlaczu (96 px, baseline 639).
- Większa i przesunięta niżej data 07.11.2028 (43 px, baseline 682).
- Większa i przesunięta niżej wartość seksapilu 40%/60% (66 px, baseline 666).
- Nie zmieniono logiki odliczania, ruchu wskazówki ani przełączania bitmap.
- Wersja aplikacji 7.0 (versionCode 7).

## GitHub
Prześlij zawartość ZIP do repozytorium Porsche-Retirement-Widget-v7 z zachowaniem katalogów, a następnie dodaj workflow .github/workflows/build.yml. Po zielonym przebiegu Actions pobierz artifact Porsche-Retirement-v7-APK i wypakuj app-debug.apk.

## Instalacja
Android może odmówić instalacji nad poprzednią wersją z powodu różnicy podpisów debug. Wtedy odinstaluj poprzednią wersję, zainstaluj v7 i dodaj widget ponownie.
