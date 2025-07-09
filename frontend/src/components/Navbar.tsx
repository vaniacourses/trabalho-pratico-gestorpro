import { Link } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import './Navbar.css';

const Navbar = () => {
  const { isAuthenticated, logout } = useAuth();

  return (
    <nav className="navbar bg-white shadow-sm">
      <div className="container-fluid">
        <a className="navbar-brand d-flex align-items-center" href="#">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-hexagon-fill logo-icon me-2" viewBox="0 0 16 16">
            <path fillRule="evenodd" d="M8.5.134a1 1 0 0 0-1 0l-6 3.577a1 1 0 0 0-.5.866v7.114a1 1 0 0 0 .5.866l6 3.577a1 1 0 0 0 1 0l6-3.577a1 1 0 0 0 .5-.866V4.577a1 1 0 0 0-.5-.866z"/>
          </svg>
          <span className="fw-bold fs-5">GestorPro</span>
        </a>

        <div className="d-flex align-items-center">
          <Link to="/" className="btn btn-link text-decoration-none me-2">
             Voltar para o início
          </Link>

          {isAuthenticated && (
            <button className="btn btn-outline-danger" onClick={logout}>
              Sair <i className="bi bi-box-arrow-right ms-1"></i>
            </button>
          )}
        </div>

      </div>
    </nav>
  );
}

export default Navbar;