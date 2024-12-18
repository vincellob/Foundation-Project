import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./Components/Login";
import EmployeePage from "./Components/EmployeePage";
import ManagerPage from "./Components/ManagerPage";
import Register from "./Components/Register";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/employee" element={<EmployeePage />} />
        <Route path="/manager" element={<ManagerPage />} />
      </Routes>
    </Router>
  );
}

export default App;