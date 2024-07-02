import React, { useRef, useState } from 'react';
// mui
import UploadFileIcon from '@mui/icons-material/UploadFile';
// custom
import ChatbotService from "../../services/ChatbotService";
import CustomTooltip from "./Tooltip";

/**
 * FileDropArea-Komponente
 * */
const FileDropArea = ({ setShowPopUp, setPopUpMessage, setColorPopUp }) => {
    const fileInputRef = useRef(null);
    const [lastFileName, setLastFileName] = useState(null);

    const handleDrop = (event) => {
        event.preventDefault();
        const file = event.dataTransfer.files[0];
        if (file) {
            handleFile(file);
        }
    };

    // Auswahl einer Datei über den Dateiauswahldialog
    const handleFileInput = (event) => {
        const file = event.target.files[0];
        if (file) {
            handleFile(file);
        }
    };

    // Hauptfunktion zum Verarbeiten der ausgewählten Datei
    const handleFile = (file) => {
        setLastFileName(file.name); // Setze lastFileName, wenn eine Datei hinzugefügt wird
        if (file.type === 'text/plain') {
            const reader = new FileReader();
            reader.onload = (e) => {
                const content = e.target.result;
                const jsonContent = {
                    fileContent: content,
                };
                const formData = new FormData();
                formData.append('file', file);
                ChatbotService.uploadFile(formData)
                    .then(() => {
                        URL.revokeObjectURL(file.preview);
                        setShowPopUp(true);
                        setColorPopUp("success");
                        setPopUpMessage("Datei erfolgreich hochgeladen!");
                        console.log('Dateiinhalt (als JSON):', JSON.stringify(jsonContent, null, 2));
                    })
                    .catch((error) => {
                        setShowPopUp(true);
                        setColorPopUp("error");
                        console.log('Error', error);
                        setPopUpMessage(error.response.data);
                    });
            };
            file.preview = URL.createObjectURL(file);
            reader.readAsText(file);
        } else {
            setShowPopUp(true);
            setColorPopUp("error");
            setPopUpMessage("Invalides Dateiformat. Bitte laden Sie eine .txt Datei hoch.");
        }
    };

    const handleDragOver = (event) => {
        event.preventDefault();
    };

    const handleClick = () => {
        fileInputRef.current.click();
    };

    const handleKeyDown = (event) => {
        if (event.key === 'Enter') {
            fileInputRef.current.click();
        }
    };

    return (
        <CustomTooltip title="Daten hier ablegen">
        <div
            role="button"
            tabIndex={0}
            aria-labelledby="fileDropAreaLabel"
            style={{
                border: '2px dashed #aaa',
                borderRadius: '5px',
                padding: '20px',
                textAlign: 'center',
                marginBottom: '20px',
                cursor: 'pointer',
            }}
            onDrop={handleDrop}
            onDragOver={handleDragOver}
            onClick={handleClick}
            onKeyDown={handleKeyDown}
        >
            <div id="fileDropAreaLabel">
                Ziehen Sie hier die .txt Datei rein <br /><br /> oder <br /><br />Klicken Sie hier, um eine Datei hochzuladen. <br /><br />
                {lastFileName && <div>Zuletzt hinzugefügte Datei: {lastFileName}</div>}
                <UploadFileIcon fontSize="large" />
            </div>
            <input
                type="file"
                accept=".txt"
                style={{ display: 'none' }}
                ref={fileInputRef}
                onChange={handleFileInput}
                onClick={(event)=> {
                    event.target.value = null
                }}
            />
        </div>
        </CustomTooltip>
    );
};

export default FileDropArea;
