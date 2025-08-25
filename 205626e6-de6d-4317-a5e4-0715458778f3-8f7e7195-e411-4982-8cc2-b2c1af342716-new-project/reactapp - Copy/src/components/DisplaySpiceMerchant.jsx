import React, { useState, useEffect } from 'react';
import './DisplaySpiceMerchant.css';

const DisplayMerchant = ({ user }) => {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchApplications = async () => {
      try {
        const token = localStorage.getItem('token');
        
        // Check if token exists
        if (!token) {
          setError('Authentication token not found. Please log in again.');
          setLoading(false);
          return;
        }
        
        // Check if user exists and has an ID
        if (!user || !user.id) {
          setError('User information not available. Please log in again.');
          setLoading(false);
          return;
        }
        
        console.log('Fetching applications for user ID:', user.id);
        
        const response = await fetch(`https://8080-bcdcaffbfaaeeafadbdbacbbfeeccbcaf.premiumproject.examly.io/api/spice-merchants/user/${user.id}`, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        
        console.log('Response status:', response.status);
        
        // Check if the endpoint exists (404) or if there are no applications
        if (response.status === 404) {
          // Try the alternative approach - get all applications
          console.log('User endpoint not found, trying to fetch all applications');
          await fetchAllApplications(token);
          return;
        }
        
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const contentType = response.headers.get('content-type');
        if (!contentType || !contentType.includes('application/json')) {
          throw new Error('Server returned non-JSON response');
        }
        
        const result = await response.json();
        console.log('API response:', result);
        
        if (result.success) {
          setApplications(result.data);
        } else {
          // If no applications found, try the alternative approach
          if (result.message && result.message.includes('No applications found')) {
            await fetchAllApplications(token);
          } else {
            throw new Error(result.message || 'Failed to fetch applications');
          }
        }
      } catch (err) {
        console.error('Error fetching applications:', err);
        setError(err.message || 'Failed to load applications');
      } finally {
        setLoading(false);
      }
    };

    const fetchAllApplications = async (token) => {
      try {
        console.log('Fetching all applications as fallback');
        const response = await fetch('https://8080-bcdcaffbfaaeeafadbdbacbbfeeccbcaf.premiumproject.examly.io/api/spice-merchants/get', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const result = await response.json();
        
        if (result.success) {
          // Try to filter by user if possible, otherwise show all
          const userApplications = result.data.filter(app => 
            app.user && app.user.id === user.id
          );
          
          if (userApplications.length > 0) {
            setApplications(userApplications);
          } else {
            // Show all applications if we can't filter by user
            setApplications(result.data);
            setError('Note: Showing all applications (user filtering not available)');
          }
        } else {
          throw new Error(result.message || 'Failed to fetch applications');
        }
      } catch (err) {
        console.error('Error fetching all applications:', err);
        setError('Failed to load applications: ' + err.message);
      }
    };

    if (user) {
      fetchApplications();
    } else {
      setLoading(false);
      setError('User information not available. Please log in again.');
    }
  }, [user]);

  if (loading) {
    return <div className="loading">Loading your applications...</div>;
  }

  return (
    <div className="merchant-container">
      <div className="merchant-header">
        <h1>Your SpiceMerchant Dashboard</h1>
        <p>Welcome, {user?.name || user?.username}! Here's your application status and information.</p>
      </div>
      
      {error && !applications.length && (
        <div className="error-message">
          <strong>Error:</strong> {error}
        </div>
      )}
      
      {error && applications.length > 0 && (
        <div className="warning-message">
          <strong>Note:</strong> [Error - You need to specify the message]
        </div>
      )}
      
      {applications.length > 0 ? (
        applications.map((application) => (
          <div key={application.id} className="application-details">
            <div className="status-card">
              <h3>Application Status</h3>
              <div className={`status-badge ${application.status ? application.status.toLowerCase() : 'submitted'}`}>
                {application.status || 'SUBMITTED'}
              </div>
            </div>
            
            <div className="application-info">
              <h3>Your Application Details</h3>
              
              <div className="info-grid">
                <div className="info-item">
                  <label>Business Name:</label>
                  <span>{application.name || 'Not specified'}</span>
                </div>
                
                <div className="info-item">
                  <label>Spices:</label>
                  <span>{application.spices || 'Not specified'}</span>
                </div>
                
                <div className="info-item">
                  <label>Experience (Years):</label>
                  <span>{application.experience || '0'}</span>
                </div>
                
                <div className="info-item full-width">
                  <label>Store Location:</label>
                  <span>{application.storeLocation || 'Not specified'}</span>
                </div>
                
                <div className="info-item">
                  <label>Phone Number:</label>
                  <span>{application.phoneNumber || 'Not specified'}</span>
                </div>
                
                <div className="info-item">
                  <label>Application Date:</label>
                  <span>
                    {application.applicationDate 
                      ? new Date(application.applicationDate).toLocaleDateString() 
                      : new Date().toLocaleDateString()
                    }
                  </span>
                </div>
                
                {application.territoryId && (
                  <div className="info-item">
                    <label>Territory ID:</label>
                    <span>{application.territoryId}</span>
                  </div>
                )}
                
                {application.evaluatorId && (
                  <div className="info-item">
                    <label>Evaluator ID:</label>
                    <span>{application.evaluatorId}</span>
                  </div>
                )}
                
                {application.user && (
                  <div className="info-item">
                    <label>User ID:</label>
                    <span>{application.user.id || application.user}</span>
                  </div>
                )}
              </div>
            </div>
          </div>
        ))
      ) : (
        <div className="no-application">
          <h3>You haven't submitted an application yet.</h3>
          <p>Complete our franchise application to get started on your SpiceMerchant journey.</p>
          <a href="/apply" className="btn btn-primary">Apply Now</a>
        </div>
      )}
    </div>
  );
};

export default DisplayMerchant;