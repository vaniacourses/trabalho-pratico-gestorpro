import React from 'react';
import DashboardCard from '../components/DashboardCard';
import '../components/Dashboard.css';

const DashboardPage: React.FC = () => {
  return (
    <main className="container my-4 my-md-5">
      <div className="row">
        <div className="col-12 col-md-6 col-lg-3">
          <DashboardCard bgColor="#f1f3f5" textColorClassName="text-dark">
            <div className="mb-auto">
              <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" className="bi bi-hexagon-fill" viewBox="0 0 16 16" style={{ color: '#495057' }}>
                <path fillRule="evenodd" d="M8.5.134a1 1 0 0 0-1 0l-6 3.577a1 1 0 0 0-.5.866v7.114a1 1 0 0 0 .5.866l6 3.577a1 1 0 0 0 1 0l6-3.577a1 1 0 0 0 .5-.866V4.577a1 1 0 0 0-.5-.866z"/>
              </svg>
            </div>
            <div><h3 className="fw-bold">GestorPro</h3></div>
          </DashboardCard>

          <DashboardCard bgColor="#038554" to="/profile" minHeight="280px">
            <div className="d-flex flex-column h-100 justify-content-end">
              <h3 className="fw-bolder h2">MEU PERFIL</h3>
              <div className="arrow-icon mt-3"><i className="bi bi-arrow-right"></i></div>
            </div>
          </DashboardCard>
        </div>

        <div className="col-12 col-md-6 col-lg-3">
          <DashboardCard title="Gestão Empresarial" to="/admin" iconName="plus" bgColor="#005227" minHeight="280px" />
          <DashboardCard title="Recursos Humanos" to="/rh" iconName="plus" bgColor="#ffffff" textColorClassName="text-dark" />
        </div>

        <div className="col-12 col-md-6 col-lg-3">
          <DashboardCard title="Tecnologia da Informação" to="/ti" iconName="plus" bgColor="#ffffff" textColorClassName="text-dark" />
          <DashboardCard title="Gestão de Projetos" to="/projetos" iconName="plus" bgColor="#03bb85" minHeight="280px" />
        </div>

        <div className="col-12 col-md-6 col-lg-3">
          <DashboardCard title="Finanças" to="/fin" iconName="plus" bgColor="#038554" minHeight="140px" />
          <DashboardCard customClassName="card-directory" to="/dir" minHeight="340px">
            <div className="card-img-overlay"></div>
            <h3 className="fw-bolder h2 mt-auto">Diretório Comum</h3>
          </DashboardCard>
        </div>
      </div>
    </main>
  );
};

export default DashboardPage;