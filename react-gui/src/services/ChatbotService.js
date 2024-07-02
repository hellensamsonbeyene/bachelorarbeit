import request from "./request";

// Funktion für die Analyse von Benutzereingaben
function analyzePost(userInput: string) {
    return request({
        url: "/analyze",
        method: "POST",
        data: userInput,
    });
}

// Funktion zum Hochladen von Dateien
function uploadFile(file) {
    return request({
        url: "/uploadFile",
        method: "POST",
        data: file,
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });
}

// Funktion zum Zurücksetzen des Chatbots
function resetChatbot() {
    return request({
        url: "/resetChatbot",
        method: "POST"
    });
}

const ChatbotService = {
    analyzePost,
    uploadFile,
    resetChatbot
};


export default ChatbotService;
