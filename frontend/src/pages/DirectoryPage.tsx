import React, { useState } from 'react';
import Navbar from '../components/Navbar';
import '../components/global.css';

interface FileMetadata {
  id: number;
  name: string;
  type: 'PDF' | 'DOCX' | 'XLSX' | 'PNG' | 'Outro';
  size: number;
  uploadDate: string;
  isRestricted: boolean;
}

const mockFiles: FileMetadata[] = [
  { id: 1, name: 'Relatorio_Vendas_Q2.pdf', type: 'PDF', size: 2048000, uploadDate: '2025-07-05', isRestricted: false },
  { id: 2, name: 'Apresentacao_Resultados.docx', type: 'DOCX', size: 5120000, uploadDate: '2025-07-04', isRestricted: false },
  { id: 3, name: 'Orcamento_Marketing_Confidencial.xlsx', type: 'XLSX', size: 768000, uploadDate: '2025-07-03', isRestricted: true },
  { id: 4, name: 'Logo_Empresa_Final.png', type: 'PNG', size: 153600, uploadDate: '2025-07-02', isRestricted: false },
];

const FileIcon: React.FC<{ type: FileMetadata['type'] }> = ({ type }) => {
  const iconMap: Record<FileMetadata['type'], string> = {
    PDF: 'bi bi-file-earmark-pdf-fill text-danger',
    DOCX: 'bi bi-file-earmark-word-fill text-primary',
    XLSX: 'bi bi-file-earmark-excel-fill text-success',
    PNG: 'bi bi-file-earmark-image-fill text-info',
    Outro: 'bi bi-file-earmark-fill text-secondary',
  };
  return <i className={`${iconMap[type]} fs-4`}></i>;
};

const formatFileSize = (bytes: number): string => {
  if (bytes === 0) return '0 Bytes';
  const k = 1024;
  const sizes = ['Bytes', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
};

const DirectoryPage: React.FC = () => {
  const [files, setFiles] = useState<FileMetadata[]>(mockFiles);
  const [fileToUpload, setFileToUpload] = useState<File | null>(null);
  const [isUploading, setIsUploading] = useState(false);
  const [isRestrictedUpload, setIsRestrictedUpload] = useState(false);

  const handleFileSelect = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setFileToUpload(e.target.files[0]);
    }
  };

  const handleUpload = () => {
    if (!fileToUpload) return;

    setIsUploading(true);
    setTimeout(() => {
      const extension = fileToUpload.name.split('.').pop()?.toUpperCase() || '';
      let fileType: FileMetadata['type'] = 'Outro';
      if (extension === 'PDF') fileType = 'PDF';
      if (extension === 'DOCX') fileType = 'DOCX';
      if (extension === 'XLSX') fileType = 'XLSX';
      if (extension === 'PNG') fileType = 'PNG';
      
      const newFile: FileMetadata = {
        id: Math.max(...files.map(f => f.id), 0) + 1,
        name: fileToUpload.name,
        type: fileType,
        size: fileToUpload.size,
        uploadDate: new Date().toISOString().split('T')[0],
        isRestricted: isRestrictedUpload, 
      };

      setFiles(prevFiles => [newFile, ...prevFiles]);
      
      setIsUploading(false);
      setFileToUpload(null);
      setIsRestrictedUpload(false);
      alert('Arquivo enviado com sucesso!');
    }, 1500);
  };

  return (
    <>
      <Navbar />
      <main className="container mt-4 mb-5">
        <header className="mb-4">
          <h1 className="fw-bolder">Diretório de Arquivos</h1>
          <p className="text-secondary fs-5">Consulte e gerencie os arquivos compartilhados do departamento.</p>
        </header>

        <div className="row g-4">
          <div className="col-lg-4">
            <div className="card shadow-sm">
              <div className="card-body">
                <h5 className="card-title fw-semibold mb-3">
                  <i className="bi bi-cloud-arrow-up-fill me-2"></i>
                  Realizar Upload
                </h5>
                <input className="form-control" type="file" onChange={handleFileSelect} disabled={isUploading} />
                
                <div className="form-check mt-3">
                    <input 
                        className="form-check-input" 
                        type="checkbox" 
                        id="restrictCheckbox"
                        checked={isRestrictedUpload}
                        onChange={(e) => setIsRestrictedUpload(e.target.checked)}
                        disabled={isUploading}
                    />
                    <label className="form-check-label" htmlFor="restrictCheckbox">
                        Arquivo restrito
                    </label>
                </div>
                
                <div className="d-grid mt-3">
                  <button 
                    className="btn btn-primary fw-semibold" 
                    onClick={handleUpload} 
                    disabled={!fileToUpload || isUploading}
                  >
                    {isUploading ? (
                      <>
                        <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                        Enviando...
                      </>
                    ) : 'Enviar Arquivo'}
                  </button>
                </div>
              </div>
            </div>
          </div>
          
          <div className="col-lg-8">
            <div className="card shadow-sm">
              <div className="card-body">
                <h5 className="card-title fw-semibold mb-3">
                  <i className="bi bi-folder-fill me-2"></i>
                  Arquivos Armazenados
                </h5>
                <div className="table-responsive">
                  <table className="table table-hover align-middle">
                    <thead className="table-light">
                      <tr>
                        <th scope="col" style={{ width: '5%' }}></th>
                        <th scope="col">Nome do Arquivo</th>
                        <th scope="col">Tamanho</th>
                        <th scope="col">Data de Upload</th>
                        <th scope="col">Ações</th>
                      </tr>
                    </thead>
                    <tbody>
                      {files.map(file => (
                        <tr key={file.id}>
                          <td><FileIcon type={file.type} /></td>
                          <td className="text-truncate">
                            {file.isRestricted && (
                              <i className="bi bi-lock-fill text-warning me-2" title="Acesso Restrito"></i>
                            )}
                            {file.name}
                          </td>
                          <td>{formatFileSize(file.size)}</td>
                          <td>{new Date(file.uploadDate).toLocaleDateString('pt-BR', {timeZone: 'UTC'})}</td>
                          <td>
                            <a href="#" className="btn btn-sm btn-outline-secondary">
                              <i className="bi bi-download"></i>
                            </a>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>
    </>
  );
};

export default DirectoryPage;