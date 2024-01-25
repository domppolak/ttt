## Projekt 2
Do projektu drugiego zostały wykorzystane obrazy (docker images) z projektu pierwszego podobnie jak baza danych RDS.

ELastic Beanstalk umożliwia załadowanie tylko jednego pliku (.yaml), tym samym zmienne środowiskowe musiały zostać wylistowane w tym pliku. Tym samym nasz plik [docker-compose-file](docker-compose.yaml) musi zostać uzupełniony odpowiedniemi wartościami zmiennych środowiskowych. 

###Tworzenie środowiska Elastic Beanstalk
Dla docker-compose wybieramy platformę dokera
![platform_docker.png](images/konfiguracja2.png)
Następnie w kolejnej dodajemy nasz plik z dockerem
![application_code.png](images/konfiguracja3.png)

##Stworzone środowisko Beanstalkg evn:
![beanstalk_env](images/gotowe_srodowisko.png)

##Stworzona instancja EC2 przez Elastic Beanstalk
![ec2_instance](images/ec2_gotowe.png)

Strona naszej aplikacji może zostać otworzona za wykorzystaniem linku naszej domeny utworzonej na Elastic Beanstalk.
