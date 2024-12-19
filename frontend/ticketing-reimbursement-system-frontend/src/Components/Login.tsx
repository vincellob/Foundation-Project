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
        navigate("/employee");
      } else if (user.role === "MANAGER") {
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
    <div>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <div>
          <label>Username: </label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div>
          <label>Password: </label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button type="submit">Login</button>
        <button type="button" onClick={() => navigate("/register")}>
          Register
        </button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default Login;