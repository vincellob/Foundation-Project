import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ManagerPage: React.FC = () => {
  const [pendingTickets, setPendingTickets] = useState<any[]>([]);
  const [errorMessage, setErrorMessage] = useState<string>('');

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

  return (
    <div style={{ padding: '20px' }}>
      <h1>Manager Dashboard</h1>
      <h2>Pending Tickets</h2>
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
      <table>
        <thead>
          <tr>
            <th>Ticket ID</th>
            <th>Amount</th>
            <th>Description</th>
            <th>Status</th>
            <th>Actions</th>
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
                <td>{ticket.ticketId}</td>
                <td>{ticket.amount}</td>
                <td>{ticket.description}</td>
                <td>{ticket.status}</td>
                <td>
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