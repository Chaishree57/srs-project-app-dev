// components/Signup.js
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './Auth.css';

const Signup = ({ onLogin }) => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    role: 'VENDOR',
    franchiseId: '',
    territoryId: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (formData.password !== formData.confirmPassword) {
      setError('Passwords do not match');
      return;
    }
    
    setLoading(true);
    setError('');
    
    try {
      const payload = {
        username: formData.username,
        email: formData.email,
        password: formData.password,
        role: formData.role,
        franchiseId: formData.franchiseId || null,
        territoryId: formData.territoryId || null
      };

      const response = await fetch('https://8080-bcdcaffbfaaeeafadbdbacbbfeeccbcaf.premiumproject.examly.io/api/auth/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      });
      
      // SAFELY handle the response - check if it's JSON first
      let data;
      const contentType = response.headers.get('content-type');
      
      if (contentType && contentType.includes('application/json')) {
        data = await response.json();
      } else {
        // If response is not JSON, read as text and create error object
        const text = await response.text();
        data = { 
          success: false, 
          message: text || `Server error: ${response.status} ${response.statusText}` 
        };
      }
      
      if (data.success) {
        alert('Registration successful! Please login with your credentials.');
        window.location.href = '/login';
      } else {
        setError(data.message || 'Registration failed');
      }
    } catch (err) {
      setError('Network error. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const showFranchiseField = formData.role === 'VENDOR' || formData.role === 'FRANCHISE_MANAGER';
  const showTerritoryField = formData.role === 'VENDOR' || formData.role === 'TERRITORY_MANAGER';

  return (
    <div className="auth-container">
      <div className="auth-form">
        <div className="auth-header">
          <h2>Create an Account</h2>
          <p>Join SpiceMerchant and start your franchise journey today.</p>
        </div>
        
        {error && <div className="error-message">{error}</div>}
        
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Username:</label>
            <input
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
              required
              minLength="3"
              placeholder="Enter your username"
            />
          </div>
          
          <div className="form-group">
            <label>Email:</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
              placeholder="Enter your email"
            />
          </div>
          
          <div className="form-group">
            <label>Password:</label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
              minLength="6"
              placeholder="Enter your password"
            />
          </div>
          
          <div className="form-group">
            <label>Confirm Password:</label>
            <input
              type="password"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              required
              placeholder="Confirm your password"
            />
          </div>
          
          <div className="form-group">
            <label>Role:</label>
            <select
              name="role"
              value={formData.role}
              onChange={handleChange}
              required
            >
              <option value="VENDOR">Vendor</option>
              <option value="TERRITORY_MANAGER">Territory Manager</option>
              <option value="EVALUATOR">Evaluator</option>
              <option value="FRANCHISE_MANAGER">Franchise Manager</option>
            </select>
          </div>

          {showFranchiseField && (
            <div className="form-group">
              <label>Franchise ID (optional):</label>
              <input
                type="number"
                name="franchiseId"
                value={formData.franchiseId}
                onChange={handleChange}
                min="1"
                placeholder="Enter franchise ID"
              />
            </div>
          )}

          {showTerritoryField && (
            <div className="form-group">
              <label>Territory ID (optional):</label>
              <input
                type="number"
                name="territoryId"
                value={formData.territoryId}
                onChange={handleChange}
                min="1"
                placeholder="Enter territory ID"
              />
              <small className="field-note">Note: Territory ID must exist in database</small>
            </div>
          )}
          
          <button type="submit" className="auth-button" disabled={loading}>
            {loading ? 'Creating Account...' : 'Sign Up'}
          </button>
        </form>
        
        <div className="auth-footer">
          <p>Already have an account? <Link to="/login">Login here</Link></p>
        </div>
      </div>
      
      <div className="auth-decoration">
        <div className="decoration-content">
          <h3>Start Your Spice Journey</h3>
          <p>Become part of our growing family of spice merchants</p>
          <div className="spice-icons">
            <span>🌶️</span>
            <span>🍂</span>
            <span>🧂</span>
            <span>🌿</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Signup;