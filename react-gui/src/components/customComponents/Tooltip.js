import React from 'react';
import Tooltip from '@mui/material/Tooltip';
import {Typography} from "@mui/material";

const CustomTooltip = ({ title, children }) => {

    return (
        <Tooltip title={<Typography fontSize={20}>{title}</Typography>} arrow>
        {children}
        </Tooltip>
    );
};

export default CustomTooltip;
