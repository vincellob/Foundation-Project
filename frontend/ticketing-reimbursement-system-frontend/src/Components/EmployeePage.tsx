import React, { useState, useEffect } from 'react';
import axios from 'axios';

const EmployeePage: React.FC = () => {
  const [amount, setAmount] = useState<string>('');
  const [description, setDescription] = useState<string>('');
  const [message, setMessage] = useState<string>('');
  const [username, setUsername] = useState<string>('');
  const [tickets, setTickets] = useState<any[]>([]);

  useEffect(() => {
    fetchTickets();
  }, []);

  const fetchTickets = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/allTickets');
      setTickets(response.data);
    } catch (error) {
      console.error('Error fetching tickets:', error);
      setMessage('Unable to find tickets');
    }
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    if (!amount || !description) {
      setMessage('All fields required');
      return;
    }

    try {
      const response = await axios.post('http://localhost:8080/api/submit',
        {
          amount: parseFloat(amount),
          description,
        },
        {
          params: {
            username,
          },
        }
      );

      setMessage('Ticket submitted.');
      setAmount('');
      setDescription('');
      fetchTickets();
    } catch (error: any) {
      console.error('Error submitting ticket:', error);
      if (error.response && error.response.status === 400) {
        setMessage('Failed to submit ticket: Bad request.');
      } else {
        setMessage('Failed to submit ticket: An error occurred.');
      }
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h1>Employee Dashboard</h1>

      <h2>Submit a Ticket</h2>
      <form onSubmit={handleSubmit} style={{ maxWidth: '400px', margin: 'auto' }}>
        <div style={{ marginBottom: '10px' }}>
          <label htmlFor="amount" style={{ display: 'block', marginBottom: '5px' }}>
            Amount ($):
          </label>
          <input
            id="amount"
            type="number"
            step="0.01"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            required
            style={{ width: '100%', padding: '8px', boxSizing: 'border-box' }}
          />
        </div>

        <div style={{ marginBottom: '10px' }}>
          <label htmlFor="description" style={{ display: 'block', marginBottom: '5px' }}>
            Description:
          </label>
          <textarea
            id="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
            style={{ width: '100%', padding: '8px', boxSizing: 'border-box' }}
          />
        </div>

        <div style={{ marginBottom: '10px' }}>
          <label htmlFor="username" style={{ display: 'block', marginBottom: '5px' }}>
            Username:
          </label>
          <input
            id="username"
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            style={{ width: '100%', padding: '8px', boxSizing: 'border-box' }}
          />
        </div>

        <button
          type="submit"
          style={{
            display: 'block',
            width: '100%',
            padding: '10px',
            backgroundColor: '#4CAF50',
            color: 'white',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer',
          }}
        >
          Submit Ticket
        </button>
      </form>

      {message && (
        <p style={{ marginTop: '20px', color: message.includes('successfully') ? 'green' : 'red' }}>
          {message}
        </p>
      )}
      
      <h2>All Previous Tickets</h2>
      {tickets.length === 0 ? (
        <p>No previous tickets available.</p>
      ) : (
        <table style={{ width: '100%', borderCollapse: 'collapse' }}>
          <thead>
            <tr>
              <th style={{ padding: '10px', border: '1px solid #ddd' }}>Ticket ID</th>
              <th style={{ padding: '10px', border: '1px solid #ddd' }}>Amount ($)</th>
              <th style={{ padding: '10px', border: '1px solid #ddd' }}>Description</th>
              <th style={{ padding: '10px', border: '1px solid #ddd' }}>Status</th>
              <th style={{ padding: '10px', border: '1px solid #ddd' }}>Employee</th>
            </tr>
          </thead>
          <tbody>
            {tickets.map((ticket) => (
              <tr key={ticket.ticketId}>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>{ticket.ticketId}</td>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>${ticket.amount}</td>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>{ticket.description}</td>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>{ticket.status}</td>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>{ticket.employee.username}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default EmployeePage;