// components/Login.js
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './Auth.css';

const Login = ({ onLogin }) => {
  const [formData, setFormData] = useState({
    username: '',
    password: ''
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
    setLoading(true);
    setError('');
    
    try {
      const response = await fetch('https://8080-bcdcaffbfaaeeafadbdbacbbfeeccbcaf.premiumproject.examly.io/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username: formData.username,
          password: formData.password
        }),
      });
      
      // SAFE RESPONSE HANDLING - Check if response exists and is JSON
      if (!response) {
        throw new Error('No response from server');
      }
      
      // Check content type before parsing as JSON
      const contentType = response.headers.get('content-type');
      let data;
      
      if (contentType && contentType.includes('application/json')) {
        data = await response.json();
      } else {
        // Handle non-JSON responses (HTML errors, CORS errors, etc.)
        const text = await response.text();
        throw new Error(`Server returned non-JSON: ${text.substring(0, 100)}...`);
      }
      
      if (data.success) {
        onLogin(data.data);
      } else {
        setError(data.message || 'Login failed');
      }
    } catch (err) {
      // More specific error messages
      if (err.message.includes('CORS') || err.message.includes('Failed to fetch')) {
        setError('CORS error: Make sure backend allows requests from this origin (port 8081)');
      } else if (err.message.includes('non-JSON')) {
        setError('Server error: Backend returned invalid response format');
      } else {
        setError(err.message || 'Network error. Please try again.');
      }
      console.error('Login error:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-form">
        <div className="auth-header">
          <h2>Login to SpiceMerchant</h2>
          <p>Welcome back! Please enter your details.</p>
        </div>
        
        {error && (
          <div className="error-message">
            <strong>Error:</strong> {error}
            <br />
            <small>Check browser console for details</small>
          </div>
        )}
        
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Username or Email:</label>
            <input
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
              required
              placeholder="Enter your username or email"
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
            />
          </div>
          <button 
            type="submit" 
            className="auth-button"
            disabled={loading}
          >
            {loading ? 'Logging in...' : 'Login'}
          </button>
        </form>
        
        <div className="auth-footer">
          <p>Don't have an account? <Link to="/signup">Sign up here</Link></p>
        </div>
      </div>
      
      <div className="auth-decoration">
        <div className="decoration-content">
          <h3>Spice Up Your Business</h3>
          <p>Join our network of successful spice merchants across the country</p>
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

export default Login;