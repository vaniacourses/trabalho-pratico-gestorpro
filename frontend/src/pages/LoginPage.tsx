import React from 'react';
import '../components/Login.css';
import LoginCard from '../components/LoginCard';

const LoginPage: React.FC = () => {
  return (
    <main className="login-container">
      <LoginCard />
    </main>
  );
};

export default LoginPage;