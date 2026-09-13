# Zasady pisania testów 
- Testy dla javy umieszczaj standardowo w folderze `test/` w drzewie katalogu odpowiadającym klasom z katalogu `src/`. 

- Tworząc klasę testującą inną klasę dodaj w nazwie klasy testującej sufix "Test", przykładowo "UserService" -> "UserServiceTest"

- Nazywając metody stosuj nazewnictwo "WhenCalled_MethodName_ShouldDoSomething", możesz też rozwinąć do "WhenCalled_MethodName_WithCondition_ShouldDoSomething" lub "WhenCalled_MethodName_ShouldDoSomething_WhenSomeCondition". Zauważ jak wygląda struktura i Case nazwe metod i jaką rolę pełnią podkreślenia. Staraj się nie dodawać "And" i dodawać za dużo do jednej metody, zrób po prostu nową metodę. 

- Staraj się tworzyć tylko jeden test dla jednej metody, chyba że występuje "if", jest możliwe że rzuci wyjątek i inne tego typu "odgałęzienia" i skutki wywołania danej metody. Sprawdź wtedy każde takie rozwidlenie i do każdego napisz test. Jeśli natomiast jest prosta metoda która potrzebuje np 4 asercji to nie bój się ich dodać w jednej metodzie testującej, pod warunkiem że nie ma ifów (jak wspomniano wcześniej).

- Stosuj zasadę AAA(Arrange-Act-Assert) oraz twórz w każdej metodzie testującej komentarze (`/* Arrange */`, `/* Act */`, `/* Assert */`), żeby pokazać jaka część kodu w danej metodzie testującej odpowiada za co. 

- Nie wybiegaj w przód i nie testuj tego czego nie musisz. Pisz proste testy aby sprawdzić zachowanie danej metody. Stosuj zasadę KISS(Keep It Simple, Stupid), nie musisz używać bardzo skomplikowanych i nieczytalnych operacji aby tylko zaoszczędzić miejsce - jeśli napiszesz test o 2-3 linijki dłuższy ale będzie czytelniejszy to lepiej trochę wydłużyć metodę testującą niż pisać kolosy.

- Po napisaniu testów upewnij się że są one proste oraz testują to co powinny. Jak już jesteś pewny że testy są napisane dobrze to w wiadomosci podsumowującej dokonane zmiany napisz w sformatowanej od myślników formie nazwy metod jakie przetestowałes a od "sub-myślników" operacje jakie zostały przetestowane. 
Przykład:
```md
-UserService.register(RegisterNewUserDto dto)  
--W przypadku pustego pola "password" w DTO zostanie rzucony wyjątek oraz nie zostanie wywołana metoda `save` na repozytorium.
``` 

- Jeśli poproszę cię o napisanie testów jednostkowych dla tego projektu to przejrzyj cały projekt, sprawdź jakie klasy i metody nie posiadają testów i napisz je według zasad. 

- Każda klasa powinna mieć własną klasę testującą, nawet jeśli klasy są proste i podobne do siebie to nie ładuje wszystkiego do jednej klasy testującej tylko utwórz klasy testujące dla każdej klasy.