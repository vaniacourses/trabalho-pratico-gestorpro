import React from 'react';
import { Link } from 'react-router-dom';

interface DashboardCardProps {
  bgColor?: string;
  minHeight?: string;
  textColorClassName?: 'text-dark' | 'text-white';
  customClassName?: string;
  to?: string;
  title?: string;
  iconName?: string;

  children?: React.ReactNode;
}

const DashboardCard: React.FC<DashboardCardProps> = ({
  bgColor = '#ffffff',
  minHeight = '200px',
  textColorClassName = 'text-white',
  customClassName = '',
  to,
  title,
  iconName,
  children
}) => {
  const iconClass = textColorClassName === 'text-dark' ? 'card-icon-dark' : 'card-icon';

    const cardContent = (
    <div
      className={`card card-custom d-flex flex-column p-4 ${textColorClassName} ${customClassName}`.trim()}
      style={{ backgroundColor: bgColor, minHeight: minHeight }}
    >
      {children ? (
        children
      ) : (
        <>
          {iconName && <i className={`bi bi-${iconName} ${iconClass}`}></i>}
          {title && <h3 className="fw-bolder h2 mt-auto">{title}</h3>}
        </>
      )}
    </div>
  )

  if (to) {
    return (
      <Link to={to} style={{ textDecoration: 'none' }}>
        {cardContent}
      </Link>
    );
  }

  return cardContent;
};

export default DashboardCard;