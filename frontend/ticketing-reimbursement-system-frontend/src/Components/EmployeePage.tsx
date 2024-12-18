import React, { useState } from "react";
import axios from "axios";

const EmployeePage: React.FC = () => {
  // State for ticket form inputs
  const [title, setTitle] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [amount, setAmount] = useState<number | "">("");
  const [message, setMessage] = useState<string>("");

  // Handle ticket submission
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const ticketData = {
      title,
      description,
      amount,
      status: "PENDING", // New tickets are submitted as "PENDING"
      employeeId: 1, // Replace with the actual employee ID based on your app's logic
    };

    try {
      const response = await axios.post("http://localhost:8080/api/submit", ticketData);
      setMessage("Ticket submitted successfully!");
      console.log("Response:", response.data);

      // Clear form after successful submission
      setTitle("");
      setDescription("");
      setAmount("");
    } catch (error: any) {
      setMessage("Error submitting ticket. Please try again.");
      console.error("Error:", error.response?.data || error.message);
    }
  };

  return (
    <div>
      <h1>Employee Page</h1>
      <h2>Submit a New Ticket</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Title:</label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Description:</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Amount:</label>
          <input
            type="number"
            value={amount}
            onChange={(e) => setAmount(Number(e.target.value))}
            required
          />
        </div>
        <button type="submit">Submit Ticket</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default EmployeePage;