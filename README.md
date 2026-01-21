# TaskPulse – Productivity & Efficiency Tracker

**TaskPulse** to zaawansowana aplikacja mobilna stworzona w języku **Kotlin** z wykorzystaniem **Jetpack Compose**. Projekt służy do monitorowania osobistej efektywności poprzez system grywalizacji zadań, oferując trwałe przechowywanie danych, nowoczesną nawigację oraz pełne wsparcie dla wielu języków.

## Realizacja wymagań technicznych

### Interfejs i Zasoby (UI & Resources)
* **Kotlin & Jetpack Compose**: Interfejs w całości zbudowany przy użyciu funkcji `@Composable`, co zapewnia nowoczesny i deklaratywny kod.
* **Zasoby tekstowe**: Pełna separacja treści od logiki dzięki wykorzystaniu plików zasobów `strings.xml`.
* **Wielojęzyczność**: Wsparcie dla języka **polskiego, angielskiego oraz włoskiego** z możliwością dynamicznej zmiany lokalizacji bezpośrednio w interfejsie aplikacji.
* **Layout**: Architektura oparta na komponentach `Column`, `Row` oraz `Box`.
* **Komunikaty**: Wykorzystanie powiadomień `Toast` do informowania o osiągnięciach użytkownika.

### Interakcja i Logika (UX & Logic)
* **Stylizacja (Modifier)**: Zaawansowane wykorzystanie modyfikatorów do zarządzania wyglądem, marginesami oraz strefami bezpieczeństwa (`statusBarsPadding`).
* **Wprowadzanie danych**: Obsługa danych wejściowych przez `OutlinedTextField` wraz z poprawnym zarządzaniem stanem przez `rememberSaveable` (odporność na zmianę konfiguracji urządzenia).
* **Logika przetwarzania**: Dynamiczne przeliczanie punktów efektywności na poziomy zaawansowania użytkownika za pomocą instrukcji warunkowych.

### Funkcjonalności Zaawansowane (Advanced Components)
* **Nawigacja**: Implementacja `NavHost` i `navController` zapewniająca płynne przejścia między panelem głównym a logiem aktywności.
* **Baza danych Room**: Pełna integracja z lokalną bazą danych SQLite:
    * Asynchroniczny zapis zdarzeń przy użyciu `suspend functions`.
    * Reaktywne odświeżanie listy zadań przy użyciu strumieni `Flow`.
    * Możliwość trwałego usunięcia historii aktywności z poziomu aplikacji.

## Stos technologiczny
* **Język**: Kotlin.
* **UI**: Jetpack Compose.
* **Baza danych**: Room Persistence Library.
* **Nawigacja**: Jetpack Navigation Compose.
* **Przetwarzanie**: Kotlin Symbol Processing (KSP).
