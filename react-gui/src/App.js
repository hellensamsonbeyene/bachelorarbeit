// react
import React from "react";
import { BrowserRouter as Router, Route, Routes} from "react-router-dom";

//costum
import "./styling/styles.css";
import MainPage from "./domain/mainPage/MainPage";

const App = () => {
  return(
      <React.Fragment>
        <Router>
          <Routes>
            <Route path='/mainpage' element={<MainPage/>} />
            {/*//DEFAULT ROUTE*/}
            <Route path='/' element={<MainPage/>} />
          </Routes>
        </Router>
      </React.Fragment>
  );
}

export default App;
