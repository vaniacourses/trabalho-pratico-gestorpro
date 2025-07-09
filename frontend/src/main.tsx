import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider, type RouteObject } from 'react-router-dom';

import App from './App.tsx';
import LoginPage from './pages/LoginPage.tsx';
import ITPage from './pages/ITPage.tsx';
import HRPage from './pages/HRPage.tsx';
import DirectoryPage from './pages/DirectoryPage.tsx';
import FinPage from './pages/FinPage.tsx';
import ProtectedLayout from './components/ProtectedLayout.tsx';
import RootLayout from './components/RootLayout.tsx';

import 'bootstrap/dist/css/bootstrap.min.css';

const routes: RouteObject[] = [
  {
    element: <RootLayout />,
    children: [
      {
        path: '/',
        element: <App />,
      },
      {
        element: <ProtectedLayout />,
        children: [
          { path: '/ti', element: <ITPage /> },
          { path: '/fin', element: <FinPage /> },
          { path: '/admin', element: <App /> },
          { path: '/rh', element: <HRPage /> },
          { path: '/profile', element: <App /> },
          { path: '/projetos', element: <App /> },
          { path: '/dir', element: <DirectoryPage /> },
        ]
      },
      {
        path: '/login',
        element: <LoginPage />,
      },
    ]
  }
];

const router = createBrowserRouter(routes);

const root = ReactDOM.createRoot(document.getElementById('root')!);

root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);