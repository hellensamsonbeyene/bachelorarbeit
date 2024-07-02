import React, {useEffect, useState} from "react";
//mui
import Snackbar from "@mui/material/Snackbar";
import Alert from '@mui/material/Alert';
// custom
import NavigationBar from "../../NavigationBar/NavigationBar";
import CustomChatbot from "../../components/CustomChabot/CustomChatbot";
import VisualChatbot from "../../components/VisualChatbot/VisualChatbot";
import ChatbotService from "../../services/ChatbotService";

// default colors
const themeChatbot = {
    fontFamily: 'Arial, sans-serif',
    primary: '#002C34',
    headerFontSize: '15px',
    white: '#fff',
    secondary: '#4a4a4a',
    error: '#F0B2B2'
};

/**
 * Hauptseite-Komponente
 */
const MainPage = () => {
    const [showPopUp, setShowPopUp] = useState(false);
    const [popUpMessage, setPopUpMessage] = useState("");
    const [colorPopUp, setColorPopUp] = useState("");
    const [isMobile, setIsMobile] = useState(false)

    //choose the screen size
    const handleResize = () => {
        if (window.innerWidth < 720) {
            setIsMobile(true);
            ChatbotService.resetChatbot()
                .then(() => {
                    setShowPopUp(true);
                    setColorPopUp("success");
                    setPopUpMessage("Chatbot erfolgreich zurückgesetzt!");
                })
        } else {
            setIsMobile(false)
        }
    }

    // create an event listener
    useEffect(() => {
        window.addEventListener("resize", handleResize)
    })

    return (<>
        {!isMobile && <><NavigationBar theme={themeChatbot}/>
                        <CustomChatbot theme={themeChatbot} setShowPopUp={setShowPopUp} setPopUpMessage={setPopUpMessage} setColorPopUp={setColorPopUp}/>
                        <Snackbar
                            open={showPopUp}
                            anchorOrigin={{
                                vertical: 'bottom',
                                horizontal: 'left',
                            }}
                            autoHideDuration={5000} // 3 Sekunden
                            onClose={() => {
                                setShowPopUp(false)
                            }}
                        >
                        <Alert
                            severity={colorPopUp || "info"}
                            variant="filled"
                            sx={{width: '100%'}}
                        >
                            {popUpMessage}
                        </Alert>
                        </Snackbar>
                    </>}
            {isMobile&& <VisualChatbot theme={themeChatbot} setColorPopUp={setColorPopUp} setShowPopUp={setShowPopUp} setPopUpMessage={setPopUpMessage}/>
            }
        </>
    );
}

export default MainPage;
