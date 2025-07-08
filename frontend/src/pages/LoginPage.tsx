import React from 'react';
import '../components/Login.css';
import LoginCard from '../components/LoginCard';
import Navbar from '../components/Navbar'

const LoginPage: React.FC = () => {
  return (
    <>
      <Navbar />
      <main className="login-container">
        <LoginCard />
      </main>
    </>
  );
};

export default LoginPage;