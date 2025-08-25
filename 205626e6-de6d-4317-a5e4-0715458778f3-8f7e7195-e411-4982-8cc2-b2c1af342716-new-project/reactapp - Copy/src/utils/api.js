const API_BASE_URL = 'https://8080-bcdcaffbfaaeeafadbdbacbbfeeccbcaf.premiumproject.examly.io/api';

// Generic API request function
const apiRequest = async (endpoint, options = {}) => {
  try {
    const token = localStorage.getItem('token');
    const headers = {
      'Content-Type': 'application/json',
      ...(token && { 'Authorization': `Bearer ${token}` }),
      ...options.headers,
    };

    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
      headers,
      ...options,
    });

    // SAFELY handle the response - check content type first
    let data;
    const contentType = response.headers.get('content-type');
    
    if (contentType && contentType.includes('application/json')) {
      data = await response.json();
    } else {
      // If response is not JSON, read as text
      const text = await response.text();
      data = { 
        success: false, 
        message: text || `Server returned status: ${response.status}`,
        status: response.status
      };
    }
    
    if (!response.ok) {
      throw new Error(data.message || `HTTP error! status: ${response.status}`);
    }

    return data;
  } catch (error) {
    console.error('API request failed:', error);
    throw error;
  }
};

// Auth API calls
export const authAPI = {
  login: (credentials) => 
    apiRequest('/auth/login', {
      method: 'POST',
      body: JSON.stringify(credentials),
    }),

  signup: (userData) => 
    apiRequest('/auth/signup', {
      method: 'POST',
      body: JSON.stringify(userData),
    }),
};

// Spice Merchant API calls
export const spiceMerchantAPI = {
  getAll: () => 
    apiRequest('/spice-merchants'),

  getById: (id) => 
    apiRequest(`/spice-merchants/${id}`),

  create: (merchantData) => 
    apiRequest('/spice-merchants/post', { // Added '/post' to match your backend
      method: 'POST',
      body: JSON.stringify(merchantData),
    }),

  update: (id, merchantData) => 
    apiRequest(`/spice-merchants/${id}`, {
      method: 'PUT',
      body: JSON.stringify(merchantData),
    }),

  delete: (id) => 
    apiRequest(`/spice-merchants/${id}`, {
      method: 'DELETE',
    }),

  getByStatus: (status) => 
    apiRequest(`/spice-merchants/status/${status}`),
};

// Admin API calls (if needed)
export const adminAPI = {
  getAllUsers: () => 
    apiRequest('/admin/users'),
  
  getAllTerritories: () => 
    apiRequest('/admin/territories'),
};

export default apiRequest;