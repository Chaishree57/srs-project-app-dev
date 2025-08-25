// components/Home.js
import React from 'react';
import { Link } from 'react-router-dom';
import './Home.css';

const Home = ({ isAuthenticated }) => {
  return (
    <div className="home-container">
      <section className="hero-section">
        <div className="hero-content">
          <h1>Welcome to SpiceMerchant</h1>
          <p>Join our franchise network and bring authentic spices to your community</p>
          {!isAuthenticated && (
            <div className="hero-buttons">
              <Link to="/signup" className="btn btn-primary">Get Started</Link>
              <Link to="/login" className="btn btn-secondary">Login</Link>
            </div>
          )}
          {isAuthenticated && (
            <div className="hero-buttons">
              <Link to="/apply" className="btn btn-primary">Apply for Franchise</Link>
              <Link to="/merchant" className="btn btn-secondary">View Dashboard</Link>
            </div>
          )}
        </div>
        <div className="hero-image">
          <div className="spice-animation">
            <div className="spice-item">🌶️</div>
            <div className="spice-item">🍂</div>
            <div className="spice-item">🧂</div>
            <div className="spice-item">🌿</div>
          </div>
        </div>
      </section>

      <section className="features-section">
        <h2>Why Choose SpiceMerchant?</h2>
        <div className="features-grid">
          <div className="feature-card">
            <i className="fas fa-award"></i>
            <h3>Quality Assurance</h3>
            <p>We source only the finest spices from trusted suppliers worldwide.</p>
          </div>
          <div className="feature-card">
            <i className="fas fa-hand-holding-usd"></i>
            <h3>Profitability</h3>
            <p>Our franchise model offers excellent profit margins and growth potential.</p>
          </div>
          <div className="feature-card">
            <i className="fas fa-chalkboard-teacher"></i>
            <h3>Training & Support</h3>
            <p>Comprehensive training and ongoing support for all our franchise partners.</p>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Home;