import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider, type RouteObject } from 'react-router-dom';

import App from './App.tsx';
import LoginPage from './pages/LoginPage.tsx';
import ProtectedLayout from './components/ProtectedLayout.tsx';
import { AuthProvider } from './contexts/AuthContext';

import 'bootstrap/dist/css/bootstrap.min.css';
import ITPage from './pages/ITPage.tsx';
import HRPage from './pages/HRPage.tsx';
import HREmployee from './pages/HREmployee.tsx';
import DirectoryPage from './pages/DirectoryPage.tsx';

const routes: RouteObject[] = [
  {
    path: '/',
    element: <App />,
  },
  {
    element: <ProtectedLayout/>,
    children: [
      {
        path: '/ti',
        element: <ITPage />
      },
      {
        path: '/fin',
        element: <App />
      },
      {
        path: '/admin',
        element: <App />
      },
      {
        path: '/rh',
        element: <HRPage />
      },
      {
        path: '/profile',
        element: <App />
      },
      {
        path: '/projetos',
        element: <App />
      },
      {
        path: '/dir',
        element: <DirectoryPage />
      },
    ]
  },
  {
    path: '/rh',
    element: <ProtectedLayout/>,
    children: [
      {
        element: <App />
      }
    ]
  },
  {
    path: '/fin',
    element: <ProtectedLayout/>,
    children: [
      {
        element: <App />
      }
    ]
  },
  {
    path: '/login',
    element: <LoginPage />,
  },
];

const router = createBrowserRouter(routes);

const root = ReactDOM.createRoot(document.getElementById('root')!);

root.render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  </React.StrictMode>
);