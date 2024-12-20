import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const ManagerPage: React.FC = () => {
  const [pendingTickets, setPendingTickets] = useState<any[]>([]);
  const [errorMessage, setErrorMessage] = useState<string>('');
  const navigate = useNavigate();


  useEffect(() => {
    const isLoggedIn = localStorage.getItem('authToken');
    if (!isLoggedIn) {
      navigate('/');
    }
  }, [navigate]);

  useEffect(() => {
    const fetchPendingTickets = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/pending');
        setPendingTickets(response.data);
      } catch (error) {
        setErrorMessage('Error fetching pending tickets.');
      }
    };

    fetchPendingTickets();
  }, []);

  const handleProcessTicket = async (ticketId: number, status: string) => {
    try {
      await axios.post(`http://localhost:8080/api/process/${ticketId}?status=${status}`);
      setPendingTickets(pendingTickets.filter(ticket => ticket.ticketId !== ticketId));
    } catch (error) {
      setErrorMessage('Error processing the ticket.');
    }
  };

  const handleSignOut = () => {
    localStorage.clear();
    navigate('/');
  };

  return (
    <div style={{ padding: '20px' }}>
      <h1>Manager Dashboard</h1>
      <button
        onClick={handleSignOut}
        style={{
          position: 'absolute',
          top: '20px',
          right: '20px',
          padding: '10px',
          backgroundColor: '#FF5733',
          color: 'white',
          border: 'none',
          borderRadius: '4px',
          cursor: 'pointer',
        }}
      >
        Sign Out
      </button>

      <h2>Pending Tickets</h2>
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
      <table style={{ width: '100%', borderCollapse: 'collapse' }}>
          <thead>
            <tr>
              <th style={{ padding: '10px', border: '1px solid #ddd' }}>Ticket ID</th>
              <th style={{ padding: '10px', border: '1px solid #ddd' }}>Amount ($)</th>
              <th style={{ padding: '10px', border: '1px solid #ddd' }}>Description</th>
              <th style={{ padding: '10px', border: '1px solid #ddd' }}>Status</th>
              <th style={{ padding: '10px', border: '1px solid #ddd' }}>Actions</th>
            </tr>
          </thead>
        <tbody>
          {pendingTickets.length === 0 ? (
            <tr>
              <td colSpan={5}>No pending tickets.</td>
            </tr>
          ) : (
            pendingTickets.map((ticket) => (
              <tr key={ticket.ticketId}>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>{ticket.ticketId}</td>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>${ticket.amount}</td>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>{ticket.description}</td>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>{ticket.status}</td>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}> 
                  <button onClick={() => handleProcessTicket(ticket.ticketId, 'APPROVED')}>Approve</button>
                  <button onClick={() => handleProcessTicket(ticket.ticketId, 'DENIED')}>Deny</button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default ManagerPage;