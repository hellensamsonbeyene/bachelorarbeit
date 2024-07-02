// react
import React from "react";
import {Navbar} from 'react-bootstrap';
//material-ui
import {Grid, Box, Typography} from '@mui/material';
// custom imports
import TU_logo from "../assets/images/TU_Logo.svg"
import AOT_logo from "../assets/images/aot_logo.png"

/**
 * Banner-Komponente, oberer Banner der Seite
 * */
const NavigationBar = ({theme}) => {
    return (
        <React.Fragment>
            <Navbar fixed={"top"} >
                <nav align={"center"}>
                    <Grid container direction="column"  justifyContent="space-between" alignItems="stretch">
                        <Box flexGrow={1} sx = {{ boxShadow: 3, "display": "flex", "alignItems":"center", "marginBottom": "15px"}}>
                            <img src={TU_logo} height = "60px" style={{"margin":"15px"}} alt="Technische Universität Berlin"/>
                            <Grid container  direction="column" marginRight="15px" bgcolor={theme.primary}>
                                <Typography  className="pageTitle noselect" variant="h4" align='center'>
                                Mein eigener Chatbot
                            </Typography>
                            </Grid>
                            <img src={AOT_logo} height = "65px" style={{"margin":"15px", "marginLeft": "auto"}} alt="AOT"/>
                        </Box>
                        <Box>
                            <Grid container direction="row" alignItems="center">
                                <Grid item xs={2}>
                                </Grid>
                            </Grid>
                        </Box>
                    </Grid>
                </nav>
            </Navbar>
        </React.Fragment>
    );
};

export default NavigationBar;
