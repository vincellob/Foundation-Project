import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (event: React.FormEvent) => {
    event.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/api/login", {
        username,
        password,
      });

      const user = response.data;

      if (user.role === "EMPLOYEE") {
        localStorage.setItem('authToken', response.data.authToken);
        navigate("/employee");
      } else if (user.role === "MANAGER") {
        localStorage.setItem('authToken', response.data.authToken);
        navigate("/manager");
      } else {
        setMessage("Error.");
      }
    } catch (error: any) {
      setMessage(
        error.response?.data || "Login failed. Invalid credentials."
      );
    }
  };

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
        flexDirection: "column",
      }}
    >
      <h2>Login</h2>
      <form
        onSubmit={handleLogin}
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          gap: "15px",
          width: "300px",
        }}
      >
        <div style={{ display: "flex", flexDirection: "column", width: "100%" }}>
          <label>Username:</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            style={{
              padding: "10px",
              border: "1px solid #ccc",
              borderRadius: "4px",
              width: "100%",
            }}
          />
        </div>
        <div style={{ display: "flex", flexDirection: "column", width: "100%" }}>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            style={{
              padding: "10px",
              border: "1px solid #ccc",
              borderRadius: "4px",
              width: "100%",
            }}
          />
        </div>
        <div style={{ display: "flex", justifyContent: "space-between", width: "100%" }}>
          <button
            type="submit"
            style={{
              padding: "10px 15px",
              backgroundColor: "#4CAF50",
              color: "white",
              border: "none",
              borderRadius: "4px",
              cursor: "pointer",
              width: "48%",
            }}
          >
            Login
          </button>
          <button
            type="button"
            onClick={() => navigate("/register")}
            style={{
              padding: "10px 15px",
              backgroundColor: "#008CBA",
              color: "white",
              border: "none",
              borderRadius: "4px",
              cursor: "pointer",
              width: "48%",
            }}
          >
            Register
          </button>
        </div>
      </form>
      {message && (
        <p style={{ marginTop: "15px", color: "red", textAlign: "center" }}>
          {message}
        </p>
      )}
    </div>
  );
};


export default Login;