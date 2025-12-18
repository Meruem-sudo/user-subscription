# User-Subscription

User-Subscription è un'applicazione web sviluppata con Spring Boot e Maven che consente agli utenti di gestire i propri abbonamenti e tenere traccia delle prossime scadenze di pagamento.

L'applicazione è stata progettata per garantire sicurezza e integrità dei dati, prevenendo minacce comuni. Futuri aggiornamenti includeranno miglioramenti all'UX e al frontend.

# 1. Schema Entità-Relazioni
<img width="511" height="212" alt="ER Schema" src="https://github.com/user-attachments/assets/c8617afe-cdce-4298-98b8-48a35620f6c9" />

# 2. Requisiti

Prima di eseguire l'applicazione, assicurati di avere:

-Java 21

-Maven

-Variabili d'ambiente per la configurazione della password del database

# 3. Installazione Java


# Windows

Scarica Java dal sito ufficiale di Adoptium Temurino Oracle JDK.Installa seguendo le istruzioni e verifica l’installazione aprendo il prompt dei comandi:

java -version

Dovresti vedere:

openjdk version "21.0.x" 2025-xx-xx


# macOS

Scarica il pacchetto .pkg dal sito di Adoptium Temurin.Installa Java e verifica tramite terminale:

java -version

# Linux

sudo apt update

sudo apt install openjdk-17-jdk

java -version


# 4. Installazione Maven 
Apache Maven è necessario per costruire e gestire il progetto Spring Boot.

# Windows
Scarica l’ultima versione di Maven da Apache Maven Downloads(file zip).Estrai l’archivio in una cartella, ad esempio:
C:\Program Files\Apache\Maven

Configura la variabile d’ambiente MAVEN_HOME:
setx MAVEN_HOME "C:\Program Files\Apache\Maven\apache-maven-x.x.x"


Aggiungi Maven al PATH:
setx PATH "%PATH%;%MAVEN_HOME%\bin"


Verifica l’installazione:
mvn -v


Dovresti vedere output simile a:
Apache Maven 3.x.x
Java version: 17.0.x

# macOS

Puoi installare Maven con Homebrew:

brew update
brew install maven


Verifica l’installazione:

mvn -v

# Linux 

Installa Maven tramite apt:

sudo apt update
sudo apt install maven


Verifica l’installazione:

mvn -v


# 5. Scaricare l’applicazione

Clona il repository e spostati nella cartella del progetto:

git clone https://github.com/tuo-utente/my-subscription-app.git
cd my-subscription-app


# 6. Compilare il progetto

Per generare il jar eseguibile:

```bash
mvn clean package


Il file verrà creato in:

target/myapp-0.0.1-SNAPSHOT.jar


# 7. Variabili d’ambiente 

Devi impostare le variabili d’ambiente per far funzionare l'applicazione (DB_PASSWORD):

Windows (cmd)
set DB_PASSWORD="mia password"

Linux/macOS
export DB_PASSWORD="mia password"

Poi avvia l’app normalmente:
java -jar target/myapp-0.0.1-SNAPSHOT.jar


# 8. Eseguire l’applicazione

Avvia il server integrato (Tomcat) e l’app:
java -jar target/myapp-0.0.1-SNAPSHOT.jar


Apri il browser e visita:
http://localhost:8080
