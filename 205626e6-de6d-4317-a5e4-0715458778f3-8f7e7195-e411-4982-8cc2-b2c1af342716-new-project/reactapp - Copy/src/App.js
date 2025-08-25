import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Signup from './components/Signup';
import Home from './components/Home';
import ApplyForm from './components/ApplyForm';
import DisplayMerchant from './components/DisplaySpiceMerchant';
import Navbar from './components/NavBar';
import Footer from './components/Footer';
import './App.css';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Safe JSON parse utility
  const safeJsonParse = (str, defaultValue = null) => {
    try {
      return str ? JSON.parse(str) : defaultValue;
    } catch (error) {
      console.error('JSON parse error:', error);
      return defaultValue;
    }
  };

  // Check if user is logged in on app load
  useEffect(() => {
    const token = localStorage.getItem('token');
    const userData = localStorage.getItem('user');
    
    if (token && userData) {
      setIsAuthenticated(true);
      // SAFE parsing - use the utility function
      setUser(safeJsonParse(userData));
    }
    setLoading(false);
  }, []);

  const handleLogin = (userData) => {
    // Assuming userData contains both token and user info
    const { token, ...userInfo } = userData;
    
    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify(userInfo));
    setIsAuthenticated(true);
    setUser(userInfo);
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setIsAuthenticated(false);
    setUser(null);
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <Router>
      <div className="App">
        <Navbar isAuthenticated={isAuthenticated} user={user} onLogout={handleLogout} />
        <div className="main-content">
          <Routes>
            <Route 
              path="/" 
              element={<Home isAuthenticated={isAuthenticated} />} 
            />
            <Route 
              path="/login" 
              element={
                !isAuthenticated ? 
                  <Login onLogin={handleLogin} /> : 
                  <Navigate to="/apply" />
              } 
            />
            <Route 
              path="/signup" 
              element={
                !isAuthenticated ? 
                  <Signup onLogin={handleLogin} /> : 
                  <Navigate to="/apply" />
              } 
            />
            <Route 
              path="/apply" 
              element={
                isAuthenticated ? 
                  <ApplyForm user={user} /> : 
                  <Navigate to="/login" />
              } 
            />
            <Route 
              path="/merchant" 
              element={
                isAuthenticated ? 
                  <DisplayMerchant user={user}/> : 
                  <Navigate to="/login" />
              } 
            />
          </Routes>
        </div>
        <Footer />
      </div>
    </Router>
  );
}

export default App;