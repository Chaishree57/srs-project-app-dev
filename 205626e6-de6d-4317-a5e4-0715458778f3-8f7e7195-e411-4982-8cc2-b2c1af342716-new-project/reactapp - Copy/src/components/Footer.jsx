// components/Footer.js
import React from 'react';
import './Footer.css';

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-content">
        <div className="footer-section">
          <h3>SpiceMerchant</h3>
          <p>Bringing the finest spices to communities across the country through our franchise network.</p>
        </div>
        
        <div className="footer-section">
          <h4>Quick Links</h4>
          <ul>
            <li><a href="/">Home</a></li>
            <li><a href="/apply">Apply</a></li>
            <li><a href="/login">Login</a></li>
            <li><a href="/signup">Sign Up</a></li>
          </ul>
        </div>
        
        <div className="footer-section">
          <h4>Contact Us</h4>
          <p>Email: info@spicemerchant.com</p>
          <p>Phone: (555) 123-4567</p>
          <p>Address: 123 Spice Lane, Flavor Town, ST 12345</p>
        </div>
      </div>
      
      <div className="footer-bottom">
        <p>&copy; {new Date().getFullYear()} SpiceMerchant Franchise. All rights reserved.</p>
      </div>
    </footer>
  );
};

export default Footer;