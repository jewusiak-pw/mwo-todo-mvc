﻿## Opis wykonanych kroków
1. Deployment do GCP API dedykowanego do testów UI - stworzyłem projekt w GCP, za pomocą cli `gcloud` byłem w stanie uruchomić API: ![GCP yaml](https://i.imgur.com/wBr5gb3.png)
2. Dla aplikacji MVC to-do utworzyłem konfigurację pod api na GCP: ![MVC app config](https://i.imgur.com/4DOfKKc.png)
3. Aplikację przetestowałem następującymi testami, metodyką given-when-then:
![UI look](https://i.imgur.com/9FBMcUN.png)![UI annotated](https://i.imgur.com/Mly2pSm.png)
	0. Inicjalizacja przed uruchomieniem testów - usunięcie danych z API testowego i użycie ChromeDriver do testów Selenium: ![init](https://i.imgur.com/PEWvDGu.png)
	1. Test dodawania listy i pobierania ich z bazy (**CR**UD): ![t1](https://i.imgur.com/g6ybhFA.png)
	2. Test dodawania 3 elementów do listy i pobierania danych z bazy (**CR**UD): ![t2](https://i.imgur.com/KmKcXwy.png)
	3. Test aktualizacji statusu 2 z 3 elementów na liście (CR**U**D): ![t3](https://i.imgur.com/bIqDynC.png)
	4. Test usuwania 2 z 3 elementów na liście (CRU**D**): ![t4](https://i.imgur.com/GmKYYdX.png)
	5. Test usuwania listy zawierającej elementy (CRU**D**): ![t5](https://i.imgur.com/x2s23il.png)
	6. Test usuwania listy bez elementów (CRU**D**): ![t6](https://i.imgur.com/VqnQGAs.png)
	7. Test usuwania 2 z 3 list na stronie (CRU**D**): ![t7](https://i.imgur.com/gyFzbrf.png)
4. Tworzę github action pipeline, który wrzucę do repozytorium:
![gh pipeline](https://i.imgur.com/mwakU4e.png)
5. Teraz dodaję Personal Access Token z Azure Devops (secrets.PAT) aby móc tworzyć Work Itemy w Azure: ![azure](https://i.imgur.com/qG32P6w.png)![gh secret](https://i.imgur.com/sS8d5pd.png)
6. Robię commit z dodaniem komentarza (brak zmiany funkcjonalnej = pozytywne testy), i tworzę PR i patrzę na workflow: ![comments](https://i.imgur.com/B6DSkyn.png)![workflow1](https://i.imgur.com/hrADmFC.png)![workflow2](https://i.imgur.com/6AYREJl.png)
7. Zatem merguję i commituję zmianę która powoduje nieprawidłowe działanie UI, tworzę PR i patrzę na workflow i raport: ![PR1](https://i.imgur.com/UqXwCH0.png)
Raport: ![report2](https://i.imgur.com/oAk61qh.png)
W Azure DevOps utworzony został Bug, z którego nagłówka adres prowadzi nas do konkretnego workflow który nie przeszedł: ![bug](https://i.imgur.com/0xvEKSo.png)
 Kod jest dostępny na github pod adresem: [https://github.com/jewusiak-pw/mwo-todo-mvc](https://github.com/jewusiak-pw/mwo-todo-mvc)
