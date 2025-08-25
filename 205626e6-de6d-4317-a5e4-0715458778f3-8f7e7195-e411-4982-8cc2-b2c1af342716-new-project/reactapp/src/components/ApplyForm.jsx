import React, { useState } from 'react';
import './ApplyForm.css';

const ApplyForm = ({ user }) => {
  const [formData, setFormData] = useState({
    name: '',
    spices: '',
    experience: '',
    storeLocation: '',
    phoneNumber: '',
    territoryId: '' // If needed
  });
  const [submitted, setSubmitted] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    
    try {
      const token = localStorage.getItem('token');
      // Correct the endpoint to match your backend
      const response = await fetch('https://8080-bcdcaffbfaaeeafadbdbacbbfeeccbcaf.premiumproject.examly.io/api/spice-merchants/post', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
          ...formData,
          experience: parseInt(formData.experience), // Convert to number
        }),
      });
      
      // SAFELY handle the response - check if it's JSON first
      let result;
      const contentType = response.headers.get('content-type');
      
      if (contentType && contentType.includes('application/json')) {
        result = await response.json();
      } else {
        // If response is not JSON, read as text
        const text = await response.text();
        result = { 
          success: false, 
          message: text || `Server returned status: ${response.status}` 
        };
      }
      
      if (response.ok) {
        setSubmitted(true);
      } else {
        alert(`Application submission failed: ${result.message}`);
      }
    } catch (err) {
      alert('Network error. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  if (submitted) {
    return (
      <div className="apply-container">
        <div className="success-message">
          <h2>Application Submitted Successfully!</h2>
          <p>Thank you for applying to become a SpiceMerchant franchisee. We will review your application and contact you shortly.</p>
          <a href="/merchant" className="btn btn-primary">View Your Dashboard</a>
        </div>
      </div>
    );
  }

  return (
    <div className="apply-container">
      <div className="apply-header">
        <h1>Franchise Application</h1>
        <p>Complete the form below to apply for a SpiceMerchant franchise opportunity.</p>
      </div>
      
      <form className="apply-form" onSubmit={handleSubmit}>
        <div className="form-section">
          <h3>Business Information</h3>
          <div className="form-row">
            <div className="form-group">
              <label>Proposed Business Name *</label>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleChange}
                required
              />
            </div>
          </div>
          
          <div className="form-row">
            <div className="form-group">
              <label>Spices You Plan to Sell *</label>
              <input
                type="text"
                name="spices"
                value={formData.spices}
                onChange={handleChange}
                required
              />
            </div>
          </div>
          
      x    <div className="form-row">
            <div className="form-group">
              <label>Store Location (Address) *</label>
              <input
                type="text"
                name="storeLocation"
                value={formData.storeLocation}
                onChange={handleChange}
                required
              />
            </div>
          </div>
          
          <div className="form-row">
            <div className="form-group">
              <label>Phone Number *</label>
              <input
                type="tel"
                name="phoneNumber"
                value={formData.phoneNumber}
                onChange={handleChange}
                required
                pattern="[0-9]{10}"
                title="Please enter exactly 10 digits"
              />
            </div>
          </div>
        </div>
        
        <div className="form-section">
          <h3>Experience Information</h3>
          <div className="form-row">
            <div className="form-group">
              <label>Years of Business Experience *</label>
              <input
                type="number"
                name="experience"
                value={formData.experience}
                onChange={handleChange}
                min="0"
                required
              />
            </div>
          </div>
        </div>
        
        <button 
          type="submit" 
          className="submit-btn"
          disabled={loading}
        >
          {loading ? 'Submitting...' : 'Submit Application'}
        </button>
      </form>
    </div>
  );
};

export default ApplyForm;