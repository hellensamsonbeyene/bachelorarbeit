import React from 'react';
import '../../styling/styles.css';

const HowToChatbot = () => {
    return (
        <div className="how-to-container">
            <h1>So erstellst du deinen Chatbot:</h1>
            <h2>0. Zunächst ist der ZEKI-Chatbot aktiv</h2>
                <div className="how-to-step">
                    Probiere ihn gerne aus! <br/>
                    Mit dem Button "Zurücksetzen" kannst du den Chatbot jederzeit auf den <br/>ZEKI-Chatbot zurücksetzen!
                </div>
            <h2>1. Bereite deine strukturierte Textdatei vor:</h2>
            <div className="how-to-step">
                Erstelle eine Textdatei in folgendem Format:
            </div>
            <pre>
        {`    #Standardnachricht
    [Standardnachricht]
    
    #Entitäten und Antworten
    [Entitätsname] : [Entitätsantwort]`}
      </pre>
            <div className="how-to-step">
        <span className="bold-text">
        Entitäten sind Wörter, die dein Chatbot in den Nachrichten erkennen soll. Sie dürfen keine Stoppwörter (inhaltslose Wörter in einem Satz) sein oder Leerzeichen besitzen!
            <br/>
        Die Entitäten werden absteigend priorisiert, also achte auf die Reihenfolge deiner Entitäten!
        </span>
            </div>
            <h3 className="how-to-subtitle">Beispiel für Entitäten:</h3>
            <pre>
        {`    #Entitäten und Antworten
                
    Hallo: Guten Tag! Willkommen!
    Morgen: Guten Morgen!
    Hilfe: Wie kann ich Ihnen helfen?`}
      </pre>
            <h2>2. Speichern und Hochladen:</h2>
            <p className="how-to-step">
                Speichere die Datei im <code>.txt</code> Format. <br/>Klicke auf
                "Datei hochladen" und wähle deine vorbereitete <code>.txt</code>-
                Datei aus.
            </p>
            <h2>3. Teste deinen Chatbot:</h2>
            <p className="how-to-step">
                Du hast erfolgreich deine Textdatei hochgeladen und dein Chatbot ist jetzt einsatzbereit.
                Starte eine Konversation mit dem Chatbot und schau, wie er auf verschiedene Benutzeranfragen
                reagiert. Passe bei Bedarf deine Textdatei an und lade sie anschließend wieder hoch.
            </p>
        </div>
    );
};

export default HowToChatbot;
